package com.example.storagefileproject.service;


import com.example.storagefileproject.entity.ImageData;
import com.example.storagefileproject.repository.StorageRepository;
import com.example.storagefileproject.util.ImageUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StorageService {

    private StorageRepository storageRepository;

    public String uploadImage(MultipartFile file) throws IOException {
        ImageData imageData = storageRepository.save(ImageData.builder()
                                 .name(file.getName())
                                 .type(file.getContentType())
                                 .imageDataBytes(ImageUtils.compressImage(file.getBytes()))
                                 .build());
        if(imageData!=null){
            return "file uploaded successfully : "+file.getOriginalFilename();
        }
        return "null";
    }

    public byte[] downloadImage(String fileName){
        Optional<ImageData> dbImageData = storageRepository.findByName(fileName);
        byte[]decompressedImage = ImageUtils.decompressImage(dbImageData.get().getImageDataBytes());
        return decompressedImage;

    }

}
