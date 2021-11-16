package com.example.orygoolibrary.model;

public class TrackModel {
    private String eventName;
    private String metadata;
    private String monetaryValue;
    private String value;
    private Integer timestamp;

    public TrackModel(String eventName, String metadata, String monetaryValue, String value, int timestamp) {
        this.metadata = metadata;
        this.eventName = eventName;
        this.monetaryValue = monetaryValue;
        this.value = value;
        this.timestamp = timestamp;
    }
}
