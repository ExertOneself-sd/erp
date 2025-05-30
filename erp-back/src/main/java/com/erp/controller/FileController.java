package com.erp.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RestController
@CrossOrigin
public class FileController {

    @PostMapping("/uploadImg")
    public String uploadImg(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return "文件为空";
        }

        String originalName = file.getOriginalFilename();
        if (originalName == null || originalName.lastIndexOf(".") == -1) {
            return "文件名无效";
        }

        String ext = originalName.substring(originalName.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + ext;
        System.out.println("name="+fileName);
        try {
            // 获取类路径根目录
            URL classpathUrl = FileController.class.getResource("/");
            if (classpathUrl == null) {
                throw new IOException("无法获取类路径");
            }
            String classpath = URLDecoder.decode(classpathUrl.getPath(), StandardCharsets.UTF_8);

            // 构建目标目录路径
            String targetDirPath = classpath + "static/imgs";
            File targetDir = new File(targetDirPath);

            // 确保目录存在
            if (!targetDir.exists() && !targetDir.mkdirs()) {
                throw new IOException("无法创建目录: " + targetDirPath);
            }

            // 保存文件
            File targetFile = new File(targetDir, fileName);
            FileUtils.copyInputStreamToFile(file.getInputStream(), targetFile);

            return "http://localhost:8081/imgs/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败: " + e.getMessage();
        }
    }
}