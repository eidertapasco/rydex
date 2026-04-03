package com.bikeshop.rydex.controller;

import com.bikeshop.rydex.service.CloudinaryService; // Importams el nuevo servicio
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/media")
public class MediaController {

    // 1. Declaramos la dependencia de nuestro servicio de la nube
    private final CloudinaryService cloudinaryService;

    // 2. Inyectamos el servicio a través del constructor
    public MediaController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // 3. Aqui dejamos de utilizar antes la logica de guardar en el disco
            // Ya no creamos carpetas locales (Files, Paths, UUID se van).
            // Le delegamos toda la responsabilidad de guardar la imagen a Cloudinary.
            // Cloudinary internamente genera el UUID único y la guarda en sus servidores.
            String imageUrl = cloudinaryService.subirImagen(file);

            // 4. Cloudinary nos devuelve un enlace público y seguro (ej. https://res.cloudinary.com/...)
            // Se lo mandamos a Angular en el mismo formato JSON de siempre: { "url": "..." }
            return ResponseEntity.ok(Map.of("url", imageUrl));

        } catch (IOException e) {
            // Si hay un fallo de conexión con la API de Cloudinary, atrapamos el error
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("error", "Error al subir la imagen a la nube"));
        }
    }
}