package com.avada.kino.service;

import com.avada.kino.KinoCmsApp;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UploadService {
    public String upload(MultipartFile multipartFile) {
        ApplicationHome home = new ApplicationHome(KinoCmsApp.class);
        Path uploadsDirPath = Paths.get(home.getDir().getPath() + File.separator + "uploads");

        try {
            if (!new File(uploadsDirPath.toUri()).exists()) {
                Files.createDirectory(uploadsDirPath);
            }
            multipartFile.transferTo(
                    Paths.get(uploadsDirPath.toString() + File.separator + multipartFile.getOriginalFilename())
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return uploadsDirPath.toString();
    }

    public void delete(String fileName) {
        ApplicationHome home = new ApplicationHome(KinoCmsApp.class);
        Path uploadsDirPath = Paths.get(home.getDir().getPath() + File.separator + "uploads");

        try {
            Files.deleteIfExists(Path.of(uploadsDirPath + File.separator + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
