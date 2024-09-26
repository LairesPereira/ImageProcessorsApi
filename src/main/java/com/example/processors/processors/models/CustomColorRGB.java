package com.example.processors.processors.models;

public class CustomColorRGB {
    private int r;
    private int g;
    private int b;
    private float a;

    // Construtores, getters e setters
    public CustomColorRGB() {}

    public CustomColorRGB(int r, int g, int b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public CustomColorRGB(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public float getA() {
        return a;
    }

    public void setA(float a) {
        this.a = a;
    }

    @Override
    public String toString() {
        return String.format("R: %d, G: %d, B: %d, A: %.2f", r, g, b, a);
    }
}
