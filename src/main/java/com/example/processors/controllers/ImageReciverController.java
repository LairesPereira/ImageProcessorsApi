package com.example.processors.controllers;

import com.example.processors.processors.ImageProcessor;
import com.example.processors.processors.models.CustomColorRGB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.processors.processors.models.CustomColorRGB;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;

@RestController
@CrossOrigin("*")
public class ImageReciverController {

    @Autowired
    ImageProcessor processor;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @RequestMapping(value = "/processors/{processorType}", method = RequestMethod.POST)
    public ResponseEntity<byte[]> upload(
            @RequestParam("image") MultipartFile firstFile,
            @RequestParam(value = "second-image", required = false) MultipartFile secondFile,
            @RequestParam(value = "rgb-remove", required = false) CustomColorRGB rgbRemoveColor,
            @PathVariable("processorType") String processorType) {

            String fileType = "png";

            ArrayList<MultipartFile> files = new ArrayList<>();

        try {
            Collections.addAll(files, firstFile, secondFile);
            BufferedImage processingHelperResult =  new ImageProcessorTypeHelper(files, rgbRemoveColor, processorType).processFiles();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(processingHelperResult, fileType, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG); // ou use MediaType.IMAGE_PNG se for PNG
            return ResponseEntity.ok().headers(headers).body(imageBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}
