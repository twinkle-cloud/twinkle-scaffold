package com.twinkle.scaffold.modules.base.file.api;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.twinkle.scaffold.common.constants.ResultCode;
import com.twinkle.scaffold.common.data.GeneralResult;
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
    
}
