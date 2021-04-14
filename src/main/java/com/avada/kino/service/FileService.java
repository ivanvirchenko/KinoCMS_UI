package com.avada.kino.service;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    private final File homePath = new ApplicationHome().getDir();
    private final File uploadDir = new File(homePath.getAbsolutePath() + File.separator + "uploads");

    public String saveFile(MultipartFile file, String dirName) {
        checkUploadDir();
        checkPathDir(dirName);
        String prefix = UUID.randomUUID().toString();
        Path uploadPath = Paths.get(
                uploadDir.getAbsolutePath()
                        + File.separator
                        + dirName
                        + File.separator
                        + prefix
                        + "@"
                        + file.getOriginalFilename()
        );

        try {
            file.transferTo(uploadPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prefix + "@" + file.getOriginalFilename();
    }
    public void deleteFile(String name, String folder) {
        Path path = Paths.get(uploadDir.getAbsolutePath() + File.separator + folder + File.separator + name);
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkUploadDir() {
        if (!uploadDir.exists()) {
            try {
                Files.createDirectory(uploadDir.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void checkPathDir(String dirName) {
        File uploadPathDir = new File(uploadDir.getAbsolutePath() + File.separator + dirName);
        if (!uploadPathDir.exists()) {
            try {
                Files.createDirectory(uploadPathDir.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
