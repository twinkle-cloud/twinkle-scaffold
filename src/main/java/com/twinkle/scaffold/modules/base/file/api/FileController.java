package com.twinkle.scaffold.modules.base.file.api;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.twinkle.scaffold.common.constants.ResultCode;
import com.twinkle.scaffold.common.data.GeneralResult;
import com.twinkle.scaffold.modules.base.file.data.SimpleFile;

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

    @ApiOperation(value = "文件上传", notes = "单文件上传")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "file",dataType="__file",required = true)})
    @PostMapping(value="/noauth/v1/file/singlefile")
    public GeneralResult<SimpleFile> uploadSinglefile(@RequestPart(value="file",required = true) MultipartFile file) {
        log.info("file info name = {},origName = {},size = {},contentType = {}",
                file.getName(),file.getOriginalFilename(),file.getSize(),file.getContentType());
        SimpleFile newFile = new SimpleFile();
        newFile.setId(UUID.randomUUID().toString());
        newFile.setName(file.getOriginalFilename());
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
