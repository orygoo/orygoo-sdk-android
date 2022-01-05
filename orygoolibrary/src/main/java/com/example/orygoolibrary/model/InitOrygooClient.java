package com.example.orygoolibrary.model;

public class InitOrygooClient {
    private String clientSessionId;
    private String userId;
    private String anonymousId;
    private String userEmail;
    private String userPhoneNumber;
    private String clientVersion;
    private String language;
    private String country;

    public InitOrygooClient(
            String clientSessionId,
            String userId,
            String userEmail,
            String userPhoneNumber,
            String clientVersion,
            String language,
            String country
    ) {
        this.clientSessionId = clientSessionId;
        this.userId = userId;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
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

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
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
}
