package com.example.storagefileproject.controller;

import com.example.storagefileproject.repository.FileDataRepository;
import com.example.storagefileproject.service.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class ImageController {

    private StorageService storageService;


    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file) throws IOException {
        String response = storageService.uploadImage(file);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName){
        byte[] image = storageService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }

    @PostMapping("/upload-directory")
    public ResponseEntity<?> uploadImageToDirectory(@RequestParam("image")MultipartFile file) throws IOException {
        String response = storageService.uploadImageToDirectory(file);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/download-directory/{fileName}")
    public ResponseEntity<?> downloadImageToDirectory(@PathVariable String fileName) throws IOException {
        byte[] image = storageService.downloadImageFromDirectory(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }


}
