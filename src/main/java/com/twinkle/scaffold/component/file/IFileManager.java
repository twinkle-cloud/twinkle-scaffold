package com.twinkle.scaffold.component.file;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

import org.springframework.web.multipart.MultipartFile;

import com.twinkle.scaffold.component.file.data.SimpleFile;

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
    public SimpleFile storeFile(MultipartFile multipartFile) throws SerialException, SQLException, IOException ;
    
    /**
     * 根据Id,查询文件，返回一个文件流
     * */
    public SimpleFile getFileById(String id) throws SQLException;
    
    /**
     * 更新文件
     * */
    public SimpleFile updateFile(String id,MultipartFile multipartFile) throws SerialException, SQLException, IOException ;

}
