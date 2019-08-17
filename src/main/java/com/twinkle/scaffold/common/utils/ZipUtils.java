package com.twinkle.scaffold.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import lombok.Data;
import lombok.ToString;

/**
 * 文件压缩工具 <br/>
 * Date:    2019年8月17日 下午2:33:18 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
public class ZipUtils {
    
    private static final int BUFFER_SIZE = 2 * 1024;

    /**
     * 将多文件压缩至输出流
     * */
    public static void compressToOut(OutputStream out,List<SourceFile> sourceFileList) throws IOException {
        ZipOutputStream zos = new ZipOutputStream(out);
        // 对重复的文件名重命名
        Map<String,Integer> fileNameCount = new HashMap<>();
        for (SourceFile sourceFile : sourceFileList) {
            if(!fileNameCount.containsKey(sourceFile.getName())){
                fileNameCount.put(sourceFile.getName(),0);
            }else{
                Integer count = fileNameCount.get(sourceFile.getName());
                count++;
                sourceFile.setName(sourceFile.getName()+"_"+count);
                fileNameCount.put(sourceFile.getName(),count);
            }
        }
        // 将文件压缩并放入输出流
        byte[] buf = new byte[BUFFER_SIZE];
        for (SourceFile sourceFile : sourceFileList) {
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(sourceFile.getName()));
            if(sourceFile.getInputStream() != null){
                InputStream in = sourceFile.getInputStream();
                int len;
                while ((len = in.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
            }
            zos.closeEntry();
            zos.flush();
        }
    }
    
    /**
     * 待压缩的源文件
     * */
    @Data
    @ToString
    public static class SourceFile {
        private String name;
        private InputStream inputStream;
        
        public SourceFile(String name,InputStream inputStream){
            this.name = name;
            this.inputStream = inputStream;
        }
        
    }
    
}
