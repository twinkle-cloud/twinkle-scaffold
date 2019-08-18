package com.twinkle.scaffold.component.file;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.twinkle.scaffold.common.constants.ResultCode;
import com.twinkle.scaffold.common.data.file.SimpleFile;
import com.twinkle.scaffold.common.error.GeneralException;
import com.twinkle.scaffold.component.file.repo.FileEntryRepo;
import com.twinkle.scaffold.component.file.repo.domain.FileEntry;

/**
 * 通过数据库保存文件  <br/>
 * Date:    2019年8月10日 下午9:57:35 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Component
public class JdbcFileManager implements IFileManager {

    @Autowired
    private FileEntryRepo fileEntryRepo;
    
    @Override
    public List<SimpleFile> storeFiles(MultipartFile[] multipartFiles) throws SerialException, SQLException, IOException {
        List<FileEntry> fileEntryList = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            FileEntry fileEntry = buildFileEntry(multipartFile);
            fileEntryList.add(fileEntry);
        }
        fileEntryRepo.saveAll(fileEntryList);
        List<SimpleFile> simpleFileList = new ArrayList<>();
        for (FileEntry fileEntry : fileEntryList) {
            SimpleFile simpleFile = new SimpleFile();
            simpleFile.setId(fileEntry.getId());
            simpleFile.setName(fileEntry.getName());
            simpleFile.setContentType(fileEntry.getContentType());
            simpleFileList.add(simpleFile);
        }
        return simpleFileList;
    }
    
    @Override
    public SimpleFile storeFile(MultipartFile multipartFile) throws SerialException, SQLException, IOException {
        FileEntry fileEntry = buildFileEntry(multipartFile);
        fileEntryRepo.save(fileEntry);
        SimpleFile simpleFile = new SimpleFile();
        simpleFile.setId(fileEntry.getId());
        simpleFile.setName(fileEntry.getName());
        simpleFile.setContentType(fileEntry.getContentType());
        return simpleFile;
    }

    @Override
    public SimpleFile getFileById(String id) throws SQLException {
        Optional<FileEntry> optional = fileEntryRepo.findById(id);
        if(!optional.isPresent()){
            throw new GeneralException(ResultCode.NO_ENTRY);
        }
        FileEntry fileEntry = optional.get();
        SimpleFile simpleFile = new SimpleFile();
        simpleFile.setId(fileEntry.getId());
        simpleFile.setName(fileEntry.getName());
        simpleFile.setSize(fileEntry.getSize());
        simpleFile.setContentType(fileEntry.getContentType());
        simpleFile.setInputStream(fileEntry.getContent().getBinaryStream());
        return simpleFile;
    }

    @Override
    public void deleteFile(String id) {
        fileEntryRepo.deleteById(id);
    }

    @Override
    public void deleteFiles(String[] ids) {
        fileEntryRepo.deleteByIdIn(ids);
    }

    private FileEntry buildFileEntry(MultipartFile multipartFile) throws SerialException, SQLException, IOException{
        FileEntry fileEntry = new FileEntry();
        fileEntry.setName(multipartFile.getOriginalFilename());
        fileEntry.setContentType(multipartFile.getContentType());
        fileEntry.setSize(multipartFile.getSize());
        Blob content = new SerialBlob(multipartFile.getBytes());
        fileEntry.setContent(content);
        return fileEntry;
    }

    @Override
    public List<SimpleFile> getFileByIds(String[] ids) throws SQLException {
        List<FileEntry> fileEntrys = fileEntryRepo.findByIdIn(ids);
        if(CollectionUtils.isEmpty(fileEntrys)){
            throw new GeneralException(ResultCode.NO_ENTRY);
        }
        List<SimpleFile> result = new ArrayList<>();
        for (FileEntry fileEntry : fileEntrys) {
            SimpleFile simpleFile = new SimpleFile();
            simpleFile.setId(fileEntry.getId());
            simpleFile.setName(fileEntry.getName());
            simpleFile.setSize(fileEntry.getSize());
            simpleFile.setContentType(fileEntry.getContentType());
            simpleFile.setInputStream(fileEntry.getContent().getBinaryStream());
            result.add(simpleFile);
        }
        return result;
    }
    
}
