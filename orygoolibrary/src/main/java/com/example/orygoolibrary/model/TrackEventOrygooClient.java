package com.example.orygoolibrary.model;

public class TrackEventOrygooClient {
    private String eventName;
    private String metadata;
    private String monetaryValue;
    private String value;

    public TrackEventOrygooClient(String eventName, String metadata, String monetaryValue, String value) {
        this.metadata = metadata;
        this.eventName = eventName;
        this.monetaryValue = monetaryValue;
        this.value = value;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getMonetaryValue() {
        return monetaryValue;
    }

    public void setMonetaryValue(String monetaryValue) {
        this.monetaryValue = monetaryValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
