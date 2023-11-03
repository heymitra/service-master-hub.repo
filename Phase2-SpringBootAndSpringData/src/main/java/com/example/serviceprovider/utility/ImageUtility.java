package com.example.serviceprovider.utility;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.File;
import java.io.IOException;


@Component
public class ImageUtility {

    public byte[] convertImageToByteArray(BufferedImage image) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    public void storeExpertPersonalPhoto(byte[] personalPhoto, String fileName) {
        String folderPath = "C:\\Users\\Mitra\\Downloads\\JAVA\\OK\\final-project\\service-master-hub.repo\\Phase2-SpringBootAndSpringData\\images";
        File folder = new File(folderPath);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        String filePath = folderPath + File.separator + fileName;

        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(personalPhoto));

            if (image != null) {
                ImageIO.write(image, "jpg", new File(filePath));
            } else {
                throw new IOException("Failed to read the image from the byte array");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to store the expert's personal photo: " + e.getMessage(), e);
        }
    }
}