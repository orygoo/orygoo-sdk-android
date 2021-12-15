package com.example.orygoolibrary.model;

public class EventTrackModel {
    private String eventName;
    private String metadata;
    private String monetaryValue;
    private String value;
    private Long timestamp;

    public EventTrackModel(String eventName, String metadata, String monetaryValue, String value, Long timestamp) {
        this.metadata = metadata;
        this.eventName = eventName;
        this.monetaryValue = monetaryValue;
        this.value = value;
        this.timestamp = timestamp;
    }
}
