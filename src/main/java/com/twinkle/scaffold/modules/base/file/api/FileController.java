package com.twinkle.scaffold.modules.base.file.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.twinkle.scaffold.common.constants.ResultCode;
import com.twinkle.scaffold.common.data.GeneralResult;
import com.twinkle.scaffold.common.utils.ApiParamUtils;
import com.twinkle.scaffold.common.utils.ZipUtils;
import com.twinkle.scaffold.component.file.IFileManager;
import com.twinkle.scaffold.component.file.data.SimpleFile;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 文件管理接口API <br/>
 * Date:    2019年8月10日 下午5:18:43 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@RestController
@Slf4j
public class FileController {

    @Autowired
    private IFileManager jdbcFileManager;
    
    @ApiOperation(value = "文件上传", notes = "单文件上传")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header", name = "Authorization", required = true,defaultValue = "Bearer "),
        @ApiImplicitParam(name = "file",dataType="__file",required = true)})
    @PostMapping(value="/auth/v1/file/singlefile")
    public GeneralResult<SimpleFile> uploadSinglefile(@RequestPart(value="file",required = true) MultipartFile file) throws SerialException, SQLException, IOException {
        log.info("file info name = {},origName = {},size = {},contentType = {}",
                file.getName(),file.getOriginalFilename(),file.getSize(),file.getContentType());
        SimpleFile newFile = jdbcFileManager.storeFile(file);
        return new GeneralResult(ResultCode.OPERATE_SUCCESS,null,newFile);
    }
    
    @ApiOperation(value = "文件上传", notes = "支持多文件上传")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header", name = "Authorization", required = true,defaultValue = "Bearer "),
        @ApiImplicitParam(name = "files",dataType="__file",required = true)})
    @PostMapping(value="/auth/v1/file/files")
    public GeneralResult<SimpleFile> uploadFiles(@RequestPart(value="files",required = true) MultipartFile[] files) throws SerialException, SQLException, IOException {
        List<SimpleFile> newFileList = jdbcFileManager.storeFiles(files);
        return new GeneralResult(ResultCode.OPERATE_SUCCESS,null,newFileList);
    }
    
    @ApiOperation(value = "文件删除", notes = "单文件删除")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header", name = "Authorization", required = true,defaultValue = "Bearer "),
        @ApiImplicitParam(name = "id",value="文件ID",required = true)})
    @DeleteMapping(value="/auth/v1/file/singlefile/{id}")
    public GeneralResult<String> deleteSinglefile(@PathVariable("id") String id) {
        jdbcFileManager.deleteFile(id);
        return new GeneralResult(ResultCode.OPERATE_SUCCESS,null,id);
    }
    
    @ApiOperation(value = "文件删除", notes = "文件批量删除")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header", name = "Authorization", required = true,defaultValue = "Bearer ")})
    @DeleteMapping(value="/auth/v1/file/files")
    public GeneralResult<String[]> deleteSinglefile(@RequestBody String[] ids) {
        jdbcFileManager.deleteFiles(ids);
        return new GeneralResult(ResultCode.OPERATE_SUCCESS,null,ids);
    }
    
    @ApiOperation(value = "文件下载", notes = "文件下载",produces="application/octet-stream")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header", name = "Authorization", required = true,defaultValue = "Bearer "),
        @ApiImplicitParam(name = "id",value="文件ID",required = true)})
    @GetMapping(value="/auth/v1/file/singlefile/{id}")
    public ResponseEntity<InputStreamResource> getfile(@PathVariable("id") String id) throws SQLException {
        SimpleFile simpleFile = jdbcFileManager.getFileById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");  
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", simpleFile.getName()));  
        headers.add("Pragma", "no-cache");  
        headers.add("Expires", "0");  
        return ResponseEntity  
                .ok()  
                .headers(headers)  
                .contentLength(simpleFile.getSize())  
                .contentType(MediaType.valueOf(simpleFile.getContentType()))  
                .body(new InputStreamResource(simpleFile.getInputStream()));  
    }
    
    @ApiOperation(value = "文件下载", notes = "支持断点续传的文件下载,适合连接流媒体",produces="application/octet-stream")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header", name = "Authorization", required = true,defaultValue = "Bearer "),
        @ApiImplicitParam(name = "id",value="文件ID",required = true)})
    @GetMapping(value="/auth/v1/file/singlefile/{id}/ranges")
    public void noauthGetfile(@PathVariable("id") String id,HttpServletRequest request, HttpServletResponse response) throws SQLException {
        SimpleFile simpleFile = jdbcFileManager.getFileById(id);
        InputStream inputStream = simpleFile.getInputStream();
        long size = simpleFile.getSize();
        String name = simpleFile.getName();
        simpleFile.setInputStream(inputStream);
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", name));  
        response.setHeader("Cache-Control","no-cache, no-store, max-age=0, must-revalidate");
        response.setContentType(simpleFile.getContentType());
        long fromPos = 0;
        long downSize = size;
        if (request.getHeader("Range") != null) {
            long toPos = 0;
            String range = request.getHeader("Range");
            String bytes = range.replaceAll("bytes=", "");
            String[] ary = bytes.split("-");
            fromPos = Long.parseLong(ary[0]);
            if (ary.length == 1) {
                toPos = fromPos+1024*1024*2;
                if(toPos > size){
                    toPos = size;
                }
            }else{
                toPos = Long.parseLong(ary[1]);
            }
            if (toPos > fromPos) {
                downSize = (int) (toPos - fromPos);
            }
            toPos = toPos -1;
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Content-Range", "bytes "+fromPos+"-"+toPos+"/"+size);
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
        }
        response.setHeader("Content-Length", downSize + "");
        try {
            inputStream.skip(fromPos);
            // 缓冲区大小
            int bufLen = (int) (size < 2048 ? size : 2048);
            byte[] buffer = new byte[bufLen];
            int num;
            int count = 0; // 当前写到客户端的大小
            OutputStream out = response.getOutputStream();
            while ((num = inputStream.read(buffer)) != -1) {
                out.write(buffer);
                count += num;
                //处理最后一段，计算不满缓冲区的大小
                if (size - count < bufLen) {
                    bufLen = (int) (size-count);
                    if(bufLen==0){
                        break;
                    }
                    buffer = new byte[bufLen];
                }
            }
            out.flush();
        } catch (IOException e) {
            log.warn(e.getMessage());
        } finally {
            try {
                response.getOutputStream().close();
            } catch (IOException e) {
                log.warn(e.getMessage());
            }
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.warn(e.getMessage());
                }
            }
        }
    }
    
    @ApiOperation(value = "文件下载", notes = "支持压缩文件，适合多文件下载",produces="application/octet-stream")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header", name = "Authorization", required = true,defaultValue = "Bearer ")})
    @GetMapping(value="/auth/v1/file/files")
    public void noauthGetfile(@RequestParam("ids") String id,HttpServletResponse response) throws SQLException, IOException {
        List<String> ids = ApiParamUtils.convertToStringList(id);
        List<SimpleFile> simpleFiles = jdbcFileManager.getFileByIds(ids.toArray(new String[ids.size()]));
        List<ZipUtils.SourceFile> sourceFileList = new ArrayList<>();
        for (SimpleFile simpleFile : simpleFiles) {
            sourceFileList.add(new ZipUtils.SourceFile(simpleFile.getName(),simpleFile.getInputStream()));
        }
        OutputStream out = response.getOutputStream();
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"",RandomStringUtils.randomNumeric(6)+".zip"));  
        response.setContentType("application/zip");
        ZipUtils.compressToOut(out, sourceFileList);
        out.close();
    }
}
