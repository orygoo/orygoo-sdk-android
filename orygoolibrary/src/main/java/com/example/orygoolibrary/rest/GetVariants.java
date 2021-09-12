package com.example.orygoolibrary.rest;

import com.example.orygoolibrary.OrygooVariantsResult;
import com.example.orygoolibrary.model.VariantsResult;

import java.util.Map;

public interface GetVariants {
    void onSuccess(OrygooVariantsResult variants);

    void onFailure(String throwableError);
}
