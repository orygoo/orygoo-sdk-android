package com.example.orygoolibrary.rest;

import com.example.orygoolibrary.OrygooVariantsResult;

public interface GetTrackEvent {
    void onSuccess(String status);

    void onFailure(String throwableError);
}
