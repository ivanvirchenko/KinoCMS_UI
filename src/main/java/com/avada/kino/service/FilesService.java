package com.avada.kino.service;

import com.avada.kino.KinoCmsApp;
import com.avada.kino.util.UploadPaths;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FilesService {

    private final ApplicationHome HOME = new ApplicationHome(KinoCmsApp.class);

    public String uploadFile (MultipartFile multipartFile, String entityPathString) {
        try {
            multipartFile.transferTo(
                    Paths.get(
                            HOME.getDir().getAbsolutePath() + entityPathString + multipartFile.getOriginalFilename()
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return entityPathString.replace("/static", "") + multipartFile.getOriginalFilename();
    }

    public void deleteFile(String fileName, String path) {
        try {
            Files.delete(Paths.get(HOME.getDir().getAbsolutePath() + path + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
