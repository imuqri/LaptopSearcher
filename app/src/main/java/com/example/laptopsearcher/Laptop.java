package com.example.laptopsearcher;

public class Laptop {
    private String name;
    private String lowestPrice;
    private String imageUrl;

    public Laptop(String name, String lowestPrice, String imageUrl) {
        this.name = name;
        this.lowestPrice = lowestPrice;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getLowestPrice() {
        return lowestPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
