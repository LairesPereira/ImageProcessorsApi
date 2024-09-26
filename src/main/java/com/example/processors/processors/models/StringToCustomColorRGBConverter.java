package com.example.processors.processors.models;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import com.example.processors.processors.models.CustomColorRGB;

@Component
public class StringToCustomColorRGBConverter implements Converter<String, CustomColorRGB> {

    @Override
    public CustomColorRGB convert(String source) {
        String[] values = source.split(",");
        try {
            int r = Integer.parseInt(values[0].trim());
            int g = Integer.parseInt(values[1].trim());
            int b = Integer.parseInt(values[2].trim());
            return new CustomColorRGB(r, g, b);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invalid color format. Expected 'r,g,b'.");
        }
    }
}