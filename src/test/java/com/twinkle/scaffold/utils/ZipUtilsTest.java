package com.twinkle.scaffold.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.junit.Test;

import com.twinkle.scaffold.common.utils.ZipUtils;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年8月17日 下午4:04:12 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
public class ZipUtilsTest {

    @Test
    public void compressToOutTest() throws IOException{
        OutputStream out = new FileOutputStream(new File("test.zip"));
        List<ZipUtils.SourceFile> sourceFileList = new ArrayList<>();
        InputStream in = new FileInputStream(new File("file1.txt"));
        sourceFileList.add(new ZipUtils.SourceFile("/path1/file1.txt",in));
        in = new FileInputStream(new File("/path1/file2.txt"));
        sourceFileList.add(new ZipUtils.SourceFile("file2.txt",in));
        Long start = System.currentTimeMillis();
        ZipUtils.compressToOut(out, sourceFileList);
        in.close();
        out.close();
        Long end = System.currentTimeMillis();
        System.out.println("耗时："+(end - start)+"ms");
    }
}
