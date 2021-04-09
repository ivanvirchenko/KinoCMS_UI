package com.avada.kino.controllers.admin;

import com.avada.kino.service.FilesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/files")
@RequiredArgsConstructor
public class FilesController {

    private final FilesService service;

    @PostMapping
    public ResponseEntity<?> deleteFile(@RequestParam String path) {
        String[] parts = path.split("/");
        String name = parts[parts.length - 1];

        return ResponseEntity.ok(name);
    }
}
