package com.twinkle.scaffold.component.file;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.springframework.web.multipart.MultipartFile;

import com.twinkle.scaffold.common.data.file.SimpleFile;

/**
 * 文件管理组件 <br/>
 * Date:    2019年8月10日 下午9:44:34 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
public interface IFileManager {

    /**
     * 保存文件
     * */
    public List<SimpleFile> storeFiles(MultipartFile[] multipartFiles) throws SerialException, SQLException, IOException ;
    
    /**
     * 保存文件
     * */
    public SimpleFile storeFile(MultipartFile multipartFile) throws SerialException, SQLException, IOException ;
    
    /**
     * 根据Id,查询文件，返回一个文件流
     * */
    public SimpleFile getFileById(String id) throws SQLException;
    
    /**
     * 根据Id列表,查询文件，返回多个文件流
     * */
    public List<SimpleFile> getFileByIds(String[] ids) throws SQLException;
    
    
    /**
     * 删除单个文件
     * */
    public void deleteFile(String id);
    
    /**
     * 批量删除文件
     * */
    public void deleteFiles(String[] ids);

}
