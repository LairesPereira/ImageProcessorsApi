package com.example.processors.controllers;

import com.example.processors.processors.ImageProcessor;
import com.example.processors.processors.models.CustomColorRGB;
import com.example.processors.processors.models.CustomColorRGB;
import com.example.processors.ImageprocessorApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ImageProcessorTypeHelper {
    ImageProcessor processor = new ImageProcessor();

    private final ArrayList<MultipartFile> images = new ArrayList<>();
    private final CustomColorRGB rgbRemoveColor;
    private final String processorType;

    public ImageProcessorTypeHelper(ArrayList<MultipartFile> images, CustomColorRGB threshold, String processorType) {
        this.images.addAll(images);
        this.rgbRemoveColor = threshold;
        this.processorType = processorType;
    }

    public BufferedImage processFiles() throws IOException {
        BufferedImage resultImage = null;
        switch (processorType) {
            case "binarization" -> resultImage =  processor.binarization(ImageIO.read(images.get(0).getInputStream()));
            case "colorInverter" -> resultImage = processor.colorInverter(ImageIO.read(images.get(0).getInputStream()));
            case "color-remove" -> resultImage = processor.removeColorByThreshold(ImageIO.read(images.get(0).getInputStream()), (rgbRemoveColor.getR() + rgbRemoveColor.getG() + rgbRemoveColor.getB()) / 3);
            case "replace-background" -> resultImage = processor.replaceBackground(ImageIO.read(images.get(0).getInputStream()), ImageIO.read(images.get(1).getInputStream()), (rgbRemoveColor.getR() + rgbRemoveColor.getG() + rgbRemoveColor.getB()) / 3);
            case "sum" -> resultImage = processor.sumOrSubtractImages(ImageIO.read(images.get(0).getInputStream()), ImageIO.read(images.get(1).getInputStream()), processorType);
            case "sub" -> resultImage = processor.sumOrSubtractImages(ImageIO.read(images.get(0).getInputStream()), ImageIO.read(images.get(1).getInputStream()), processorType);
            case "sharpen-border" -> resultImage = processor.applyFilter(ImageIO.read(images.get(0).getInputStream()));
        }
        return resultImage;
    }
}
