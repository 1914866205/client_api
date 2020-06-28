package com.niit.soft.client.api.util.Alioss;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author wl
 * @ClassNameasd
 * @Description TODO
 * @Date 2020/5/20
 * @Version 1.0
 */
public class FileUtil {
    /**
     * 将接收的MultipartFile数组转为File数组
     *
     * @param sourceFiles
     * @return
     */
    public static List<File> getFiles(MultipartFile[] sourceFiles) {
        List<File> fileList = new ArrayList<>(sourceFiles.length);
        for (MultipartFile sourceFile : sourceFiles) {
            // 获取文件名
            String fileName = sourceFile.getOriginalFilename();
            //uuid生成主文件名
            String prefix = UUID.randomUUID().toString();
            assert fileName != null;
            //源文件的扩展名
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            //创建File类型的临时文件
            File tempFile;
            try {
                tempFile = File.createTempFile(prefix, suffix);
                sourceFile.transferTo(tempFile);
                fileList.add(tempFile);
            } catch (IOException transferToe) {
                transferToe.printStackTrace();
            }
        }
        return fileList;
    }
}