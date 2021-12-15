package com.example.orygoolibrary.model;

public class InitializeModel {
    private String sessionToken;
    private String clientSessionId;
    private String userId;
    private String anonymousId;

    private String deviceId;
    private String userEmail;
    private String userPhoneNumber;
    private String clientInterface;
    private String deviceModel;
    private String deviceOsVersion;
    private String clientVersion;
    private String language;
    private String country;

    public InitializeModel(
            String clientSessionId,
            String userId,
            String anonymousId,
            String deviceId,
            String userEmail,
            String userPhoneNumber,
            String clientInterface,
            String deviceModel,
            String deviceOsVersion,
            String clientVersion,
            String language,
            String country
    ) {
        this.userId = userId;
        this.deviceId = deviceId;
        this.anonymousId = anonymousId;
        this.clientInterface = clientInterface;
        this.clientSessionId = clientSessionId;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
        this.deviceModel = deviceModel;
        this.deviceOsVersion = deviceOsVersion;
        this.clientVersion = clientVersion;
        this.language = language;
        this.country = country;
    }

    public void setAnonymousId(String anonymousId) {
        this.anonymousId = anonymousId;
    }

    public String getClientSessionId() {
        return clientSessionId;
    }

    public String getUserId() {
        return userId;
    }

    public String getAnonymousId() {
        return anonymousId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public String getClientInterface() {
        return clientInterface;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public String getDeviceOsVersion() {
        return deviceOsVersion;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }

    public String getSessionToken() {
        return sessionToken;
    }
}
