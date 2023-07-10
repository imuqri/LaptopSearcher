package com.example.laptopsearcher;

public class Laptop {
    private String name;
    private String lowestPrice;
    private String imageUrl;
    private  String linkUrl;

    public Laptop(String name, String lowestPrice, String imageUrl, String linkUrl) {
        this.name = name;
        this.lowestPrice = lowestPrice;
        this.imageUrl = imageUrl;
        this.linkUrl = linkUrl;
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

    public String getLinkUrl() {
        return linkUrl;
    }
}
