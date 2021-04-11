package com.avada.kino.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileService {

    public String saveFile(MultipartFile file, String path) {
        String resultPath = path + File.separator + file.getOriginalFilename();
        try {
            file.transferTo(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultPath;
    }

    public void deleteFile(String path, String name) {
        try {
            Files.delete(Path.of(path + File.separator + name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
