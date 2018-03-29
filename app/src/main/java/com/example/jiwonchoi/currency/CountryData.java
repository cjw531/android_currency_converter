package com.example.jiwonchoi.currency;

/**
 * Created by jiwonchoi on 2018. 3. 23..
 */

public class CountryData {

    private String country;
    private Integer imageId;

    public CountryData(String country, Integer imageId) {
        this.country = country;
        this.imageId = imageId;
    }

    public String getCountry() {
        return country;
    }

    public Integer getImageId() {
        return imageId;
    }
}
