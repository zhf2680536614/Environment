package com.atey;

import com.atey.utils.MinioFileStorageUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootTest(classes = EnvironmentApplication.class)
public class TestApp {
    private final MinioFileStorageUtil fileStorageUtil;

    @Autowired
    public TestApp(MinioFileStorageUtil fileStorageUtil) {
        this.fileStorageUtil = fileStorageUtil;
    }

    @Test
    public void uploadImage() throws IOException {
        FileInputStream inputStream = new FileInputStream("D:\\app.jpg");
        String url = fileStorageUtil.uploadImage("", "aaaapp.jpg", inputStream);
        System.out.println(url);
        System.out.println("图片上传测试");
        inputStream.close();
    }
}
