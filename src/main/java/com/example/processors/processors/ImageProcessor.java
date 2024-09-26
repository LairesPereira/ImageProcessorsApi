package com.example.processors.processors;

import com.example.processors.processors.models.Processors;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;

@Service
public class ImageProcessor extends Processors {
    int[] filter = {-2,-1,0,-1,1,1,0,1,2};

    public BufferedImage binarization(BufferedImage img) {
        for(int row = 0; row < img.getWidth(); row++) {
            for(int col = 0; col < img.getHeight(); col++) {
                int pixel = img.getRGB(row, col);
                Color color = new Color(pixel, true);
                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();
                int colorsValue = (r + g + b) / 3;
                if (colorsValue > 127) {
                    img.setRGB(row, col, new Color(255, 255, 255).getRGB());
                } else {
                    img.setRGB(row, col, 0);
                }
            }
        }
        return img;
    }

    public BufferedImage colorInverter(BufferedImage originalFile) {
        BufferedImage img = originalFile;
        for(int row = 0; row < img.getWidth(); row++) {
            for(int col = 0; col < img.getHeight(); col++) {
                int pixel = img.getRGB(row, col);
                Color color = new Color(pixel, true);
                int r = 255 - color.getRed();
                int g = 255 - color.getGreen();
                int b = 255 - color.getBlue();
                img.setRGB(row, col, new Color(r, g, b).getRGB());
            }
        }
        return img;
    }

    public BufferedImage removeColorByThreshold(BufferedImage img, int threshold) {
        BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for(int row = 0; row < img.getWidth(); row++) {
            for(int col = 0; col < img.getHeight(); col++) {
                int pixel = img.getRGB(row, col);
                Color color = new Color(pixel, true);
                int r = color.getRed();
                int g = color.getGreen();
                int b =  color.getBlue();
                int colorsValue = (r + g + b) / 3;
                if (colorsValue > threshold) {
                    result.setRGB(row, col, new Color(0, 0, 0, 0).getRGB());
                } else {
                    result.setRGB(row, col, pixel);
                }
            }
        }
        return result;
    }

    public BufferedImage replaceBackground(BufferedImage originalFile, BufferedImage secondaryImage, int threshold) {
        try {
            BufferedImage img = originalFile;
            BufferedImage secondImage = secondaryImage;
            BufferedImage noBackground = removeColorByThreshold(img, threshold);
            // find what image is bigger and create a white canva that size.
            BufferedImage whiteCanva = (img.getWidth() + img.getHeight() > secondImage.getWidth() + secondImage.getHeight()) ?
                    new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB) :
                    new BufferedImage(secondImage.getWidth(), secondImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

            for(int row = 0; row < img.getWidth(); row++) {
                for (int col = 0; col < img.getHeight(); col++) {
                    if(noBackground.getRGB(row,col) == 0)
                        whiteCanva.setRGB(row, col, secondImage.getRGB(row, col));
                    else whiteCanva.setRGB(row, col, img.getRGB(row, col));
                }
            }
            return whiteCanva;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BufferedImage sumOrSubtractImages(BufferedImage firstImage, BufferedImage secondImage, String operationType) {
        BufferedImage zImage = new BufferedImage(firstImage.getWidth(), secondImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        BufferedImage normalizedResult = null;

        int fMax = 0;
        int fMin = 255;
        int rSum = 0;

        for(int row = 0; row < firstImage.getWidth(); row++) {
            for (int col = 0; col < firstImage.getHeight(); col++) {

                int firstPixel = firstImage.getRGB(row, col);
                int secondPixel = secondImage.getRGB(row, col);
                Color firstColor = new Color(firstPixel);
                Color secondColor = new Color(secondPixel);
                rSum = (firstColor.getRed() + secondColor.getRed());

                if(rSum > fMax) { fMax = rSum; }
                if(rSum < fMin) { fMin = rSum; }
            }
        }

        for(int row = 0; row < firstImage.getWidth(); row++) {
            for (int col = 0; col < firstImage.getHeight(); col++) {
                int firstPixel = firstImage.getRGB(row, col);
                int secondPixel = secondImage.getRGB(row, col);
                Color firstColor = new Color(firstPixel);
                Color secondColor = new Color(secondPixel);
                int f = 0;

                if (operationType.equals("sum")) {
                    f = firstColor.getRed() + secondColor.getRed();
                } else if (operationType.equals("sum")) {
                    f = firstColor.getRed() - secondColor.getRed();
                }
                else if (operationType.equals("and")){
                    f = firstColor.getRed() & secondColor.getRed();
                } else if (operationType.equals("or")) {
                    f = firstColor.getRed() | secondColor.getRed();
                } else if (operationType.equals("xor")) {
                    f = firstColor.getRed() ^ secondColor.getRed();
                }

                double factor = 255.0 / (fMax - fMin);
                int g = (int) (factor * (f - fMin));
                System.err.println(f);
                System.err.println(g);
                if(g < 0 ) {
                    g = g*=-1;
                }

                zImage.setRGB(row, col, new Color(g,g,g).getRGB());
                normalizedResult = zImage;
            }
        }
        return normalizedResult;
    }

    public  BufferedImage applyFilter(BufferedImage originalFile) {
        BufferedImage finalImage = new BufferedImage(originalFile.getWidth(), originalFile.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Color px1, px2, px3, px4, px5, px6, px7, px8, px9;

        for(int row = 1; row < originalFile.getWidth() - 1; row++) {
            for(int col = 1; col < originalFile.getHeight() - 1; col++) {
                px1 = new Color(originalFile.getRGB(row - 1, col - 1));
                px2 = new Color(originalFile.getRGB(row - 1, col));
                px3 = new Color(originalFile.getRGB(row - 1, col + 1));

                px4 = new Color(originalFile.getRGB(row, col - 1));
                px5 = new Color(originalFile.getRGB(row , col));
                px6 = new Color(originalFile.getRGB(row, col + 1));

                px7 = new Color(originalFile.getRGB(row + 1, col - 1));
                px8 = new Color(originalFile.getRGB(row + 1, col));
                px9 = new Color(originalFile.getRGB(row + 1, col + 1));


                int valueR =
                    (filter[0]*px1.getRed()) +
                    (filter[1]*px2.getRed()) +
                    (filter[2]*px3.getRed()) +
                    (filter[3]*px4.getRed()) +
                    (filter[4]*px5.getRed()) +
                    (filter[5]*px6.getRed()) +
                    (filter[6]*px7.getRed()) +
                    (filter[7]*px8.getRed()) +
                    (filter[8]*px9.getRed());

                int valueG =
                        (filter[0]*px1.getGreen()) +
                        (filter[1]*px2.getGreen()) +
                        (filter[2]*px3.getGreen()) +
                        (filter[3]*px4.getGreen()) +
                        (filter[4]*px5.getGreen()) +
                        (filter[5]*px6.getGreen()) +
                        (filter[6]*px7.getGreen()) +
                        (filter[7]*px8.getGreen()) +
                        (filter[8]*px9.getGreen());

                int valueB =
                    (filter[0]*px1.getBlue()) +
                    (filter[1]*px2.getBlue()) +
                    (filter[2]*px3.getBlue()) +
                    (filter[3]*px4.getBlue()) +
                    (filter[4]*px5.getBlue()) +
                    (filter[5]*px6.getBlue()) +
                    (filter[6]*px7.getBlue()) +
                    (filter[7]*px8.getBlue()) +
                    (filter[8]*px9.getBlue());

                if (valueR > 255) valueR = 255;
                if (valueG > 255) valueG = 255;
                if (valueB > 255) valueB = 255;

                if (valueR < 0) valueR = 0;
                if (valueG < 0) valueG = 0;
                if (valueB < 0) valueB = 0;

                Color finalColor = new Color(valueR, valueG, valueB);
                finalImage.setRGB(row, col, finalColor.getRGB());
            }
        }
        return finalImage;
    }
}