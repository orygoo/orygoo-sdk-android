package com.example.orygoolibrary.rest;

public class APIUtils {
    private APIUtils(){
    };

    public static final String API_URL = "https://api.orygoo.com/";

    public static OrygooApiService getOrygooService(){
        return RestClient.getClient(API_URL).create(OrygooApiService.class);
    }
}
