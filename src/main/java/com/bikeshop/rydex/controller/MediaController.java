package com.bikeshop.rydex.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/media")
public class MediaController {

    // Carpeta local donde se guardarán las fotos físicamente
    private final String UPLOAD_DIR = "uploads/";

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // 1. Crear carpeta si no existe
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 2. Generar un nombre único (ej. 123e4567-e89b-12d3_marlin7.jpg)
            String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(filename);

            // 3. Guardar el archivo en el disco duro
            Files.copy(file.getInputStream(), filePath);

            // 4. Devolver la URL pública que Angular usará
            String imageUrl = "/uploads/" + filename;
            return ResponseEntity.ok(Map.of("url", imageUrl));

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "Error al subir la imagen"));
        }
    }
}
