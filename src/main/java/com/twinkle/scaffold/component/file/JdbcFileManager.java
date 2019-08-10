package com.twinkle.scaffold.component.file;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.twinkle.scaffold.common.constants.ResultCode;
import com.twinkle.scaffold.common.error.GeneralException;
import com.twinkle.scaffold.component.file.data.SimpleFile;
import com.twinkle.scaffold.component.file.repo.FileEntryRepo;
import com.twinkle.scaffold.component.file.repo.domain.FileEntry;

/**
 * TODO ADD DESC <br/>
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
    public SimpleFile storeFile(MultipartFile multipartFile) throws SerialException, SQLException, IOException {
        FileEntry fileEntry = new FileEntry();
        fileEntry.setName(multipartFile.getOriginalFilename());
        fileEntry.setContentType(multipartFile.getContentType());
        fileEntry.setSize(multipartFile.getSize());
        Blob content = new SerialBlob(multipartFile.getBytes());
        fileEntry.setContent(content);
        fileEntryRepo.save(fileEntry);
        SimpleFile simpleFile = new SimpleFile();
        simpleFile.setId(fileEntry.getId());
        simpleFile.setName(multipartFile.getOriginalFilename());
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
    public SimpleFile updateFile(String id, MultipartFile multipartFile)  throws SerialException, SQLException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

}
