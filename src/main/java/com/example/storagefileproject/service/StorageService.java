package com.example.storagefileproject.service;


import com.example.storagefileproject.entity.FileData;
import com.example.storagefileproject.entity.ImageData;
import com.example.storagefileproject.repository.FileDataRepository;
import com.example.storagefileproject.repository.StorageRepository;
import com.example.storagefileproject.util.ImageUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StorageService {

    private StorageRepository storageRepository;
    private FileDataRepository fileDataRepository;

    private final String DIRECTORY_PATH = "C:/Users/rayde/OneDrive/Desktop/Development/Projects/Backend/File-store/directory-project-test/";

    //Method for uploading using the database for saving the file
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

    //Method for downloading using the database for saving the file
    public byte[] downloadImage(String fileName){
        Optional<ImageData> dbImageData = storageRepository.findByName(fileName);
        byte[]decompressedImage = ImageUtils.decompressImage(dbImageData.get().getImageDataBytes());
        return decompressedImage;

    }

    //Method for uploading a file to a Storage Directory
    public String uploadImageToDirectory(MultipartFile file) throws IOException {
        String filePath = DIRECTORY_PATH+file.getOriginalFilename();

        FileData fileData = fileDataRepository.save(FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath)
                .build());

        file.transferTo(new File(filePath));//This code allows to copy a file to another destination easily

        if(fileData!=null){
            return "file uploaded successfully : "+filePath;
        }
        return "null";
    }

    public byte[] downloadImageFromDirectory(String fileName) throws IOException {
        Optional<FileData> fileData = fileDataRepository.findByName(fileName);
        String filePath = fileData.get().getFilePath();
        byte[] image = Files.readAllBytes(new File(filePath).toPath());//This code allows to read a file from a directory
        return image ;

    }


}
