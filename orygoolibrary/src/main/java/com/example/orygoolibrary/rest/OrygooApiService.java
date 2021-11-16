package com.example.orygoolibrary.rest;

import com.example.orygoolibrary.model.SessionModel;
import com.example.orygoolibrary.model.TrackModel;
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
    Call<SessionModel> initialize(@Body SessionModel session, @Header("X-Orygoo-Client") String clientKey, @Header("X-Orygoo-Secret") String secretKey);

    @POST("/abtest/variants")
    Call<Map<String, VariantsResult>> getVariants(@Body VariantsModel namespaces, @Header("X-Orygoo-Client") String clientKey, @Header("X-Orygoo-Secret") String secretKey, @Header("X-Orygoo-Session-Token") String sessionToken);

    @PUT("/track/event")
    Call<TrackModel> trackEvent(@Body TrackModel trackEventPayload, @Header("X-Orygoo-Client") String clientKey, @Header("X-Orygoo-Secret") String secretKey, @Header("X-Orygoo-Session-Token") String sessionToken);
}
