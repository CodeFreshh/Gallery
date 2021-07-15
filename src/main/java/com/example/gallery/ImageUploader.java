package com.example.gallery;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import com.example.gallery.Model.Image;
import com.example.gallery.Repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class ImageUploader {

    private Cloudinary cloudinary;
    private ImageRepository imageRepository;

    @Value("df8n1ed6g")
    private String cloudNameValue;
    @Value("358556747627427")
    private String apiKeyValue;
    @Value("QaAAhhcb29LgWK9mTFsVHoYh2q4")
    private String apiSecretValue;

    @Autowired
    public ImageUploader(ImageRepository imageRepository,
                         @Value("df8n1ed6g") String cloudNameValue,
                         @Value("358556747627427") String apiKeyValue,
                         @Value("QaAAhhcb29LgWK9mTFsVHoYh2q4") String apiSecretValue) {
        this.imageRepository = imageRepository;
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudNameValue,
                "api_key", apiKeyValue,
                "api_secret", apiSecretValue));
    }

    public String uploadFileAndSaveToDb(String path) {
        File file = new File(path);
        Map uploadResult = null;
        try {
            uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            imageRepository.save(new Image(uploadResult.get("url").toString()));
        } catch (IOException e) {
            // todo
        }
        return uploadResult.get("url").toString();
    }


}