package com.niit.soft.client.api.util.Alioss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author wl
 * @ClassNameAliossUtil
 * @Description TODO
 * @Date 2020/5/20
 * @Version 1.0
 */
@Slf4j
public class AliossUtil {

    /**
     * 阿里云OOS上传文件工具类
     *
     * @author mqxu
     */


    /**
     * 将单个文件上传到阿里对象存储
     *
     * @param file
     * @return
     */
    public static String upload(File file) {
        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
        String accessKeyId = "LTAI4Fomj1SE25BiCMP4N1PH";
        String accessKeySecret = "sGONg7XTpY3dHRLafqR6u1DYP3Kpi4";
        String bucketName = "space1821";
        String filePath = "UserAvatar/";
        String fileName = file.getName();
        String newFileName = UUID.randomUUID().toString() + fileName.substring(fileName.indexOf("."));
        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//        ossClient.set
        // 上传文件到指定位置，并使用UUID更名
        ossClient.putObject(bucketName, filePath + newFileName, file);
        // 拼接URL
        String url = "https://niit-soft.oss-cn-hangzhou.aliyuncs.com/" + filePath + newFileName;
        ossClient.shutdown();
        return url;
    }

    public static List<String> upload(MultipartFile[] sourceFiles) {
        List<String> tempFiles = new ArrayList<>(10);
        for (MultipartFile sourceFile : sourceFiles) {
            System.out.println(sourceFile);
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
                System.out.println(tempFile);
                // 将MultipartFile转换成File
                sourceFile.transferTo(tempFile);
                tempFiles.add(upload(tempFile));
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return tempFiles;
    }

}
