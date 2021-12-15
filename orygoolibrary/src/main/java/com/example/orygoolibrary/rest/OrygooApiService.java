package com.example.orygoolibrary.rest;

import com.example.orygoolibrary.model.InitializeModel;
import com.example.orygoolibrary.model.EventTrackModel;
import com.example.orygoolibrary.model.VariantsResult;
import com.example.orygoolibrary.model.VariantsModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface OrygooApiService {
    @POST("session/initialization")
    Call<InitializeModel> initialize(@Body InitializeModel session, @Header("X-Orygoo-Client") String clientKey, @Header("X-Orygoo-Secret") String secretKey);

    @POST("/abtest/variants")
    Call<Map<String, VariantsResult>> getVariants(@Body VariantsModel namespaces, @Header("X-Orygoo-Client") String clientKey, @Header("X-Orygoo-Secret") String secretKey, @Header("X-Orygoo-Session-Token") String sessionToken);

    @PUT("/track/event")
    Call<EventTrackModel> trackEvent(@Body EventTrackModel trackEventPayload, @Header("X-Orygoo-Client") String clientKey, @Header("X-Orygoo-Secret") String secretKey, @Header("X-Orygoo-Session-Token") String sessionToken);
}
