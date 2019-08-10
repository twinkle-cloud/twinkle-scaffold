package com.twinkle.scaffold.modules.base.file.api;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    
    @ApiOperation(value = "文件下载", notes = "文件下载",produces="application/octet-stream")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id",value="文件ID",required = true)})
    @GetMapping(value="/noauth/v1/file/files/{id}")
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
    
    @ApiOperation(value = "文件上传", notes = "单文件上传")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "file",dataType="__file",required = true)})
    @PostMapping(value="/noauth/v1/file/singlefiles")
    public GeneralResult<SimpleFile> uploadSinglefile(@RequestPart(value="file",required = true) MultipartFile file) throws SerialException, SQLException, IOException {
        log.info("file info name = {},origName = {},size = {},contentType = {}",
                file.getName(),file.getOriginalFilename(),file.getSize(),file.getContentType());
        SimpleFile newFile = jdbcFileManager.storeFile(file);
        return new GeneralResult(ResultCode.OPERATE_SUCCESS,null,newFile);
    }
    
    @ApiOperation(value = "文件上传", notes = "支持多文件上传")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "files",dataType="__file",required = true)})
    @PostMapping(value="/noauth/v1/file/files")
    public GeneralResult<SimpleFile> uploadFiles(@RequestPart(value="files",required = true) MultipartFile[] files) {
        List<SimpleFile> newFileList = new ArrayList<>();
        for (MultipartFile file : files) {
            log.info("file info name = {},origName = {},size = {},contentType = {}",
                    file.getName(),file.getOriginalFilename(),file.getSize(),file.getContentType());
            SimpleFile newFile = new SimpleFile();
            newFile.setId(UUID.randomUUID().toString());
            newFile.setName(file.getOriginalFilename());
            newFileList.add(newFile);
        }
        return new GeneralResult(ResultCode.OPERATE_SUCCESS,null,newFileList);
    }
    
}
