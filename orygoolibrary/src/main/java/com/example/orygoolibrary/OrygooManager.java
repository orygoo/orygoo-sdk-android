package com.example.orygoolibrary;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.orygoolibrary.model.InitializeModel;
import com.example.orygoolibrary.model.InitOrygooClient;
import com.example.orygoolibrary.model.TrackEventOrygooClient;
import com.example.orygoolibrary.model.EventTrackModel;
import com.example.orygoolibrary.model.VariantsResult;
import com.example.orygoolibrary.model.VariantsModel;
import com.example.orygoolibrary.rest.APIUtils;
import com.example.orygoolibrary.rest.GetTrackEvent;
import com.example.orygoolibrary.rest.GetVariants;
import com.example.orygoolibrary.rest.OrygooApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrygooManager {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Nullable private final String clientKey;
    @Nullable private final String secretKey;
    @Nullable private final Context context;
    @Nullable private Map<String, VariantsResult> variantList;

    public void setOrygooService(OrygooApiService orygooService) {
        this.orygooService = orygooService;
    }

    public OrygooApiService getOrygooService() {
        return orygooService;
    }

    OrygooApiService orygooService;

    @Nullable
    public String getClientKey() {
        return clientKey;
    }

    @Nullable
    public String getSecretKey() {
        return secretKey;
    }

    @Nullable
    public Context getContext() {
        return context;
    }

    @Nullable
    public Map<String, VariantsResult> getVariantList() {
        return variantList;
    }

    public void setVariantList(Map<String, VariantsResult> variantList) {
        this.variantList = variantList;
        Log.d("GETTT Set", String.valueOf(variantList));
    }

    OrygooManager(@Nullable String clientKey, @Nullable String secretKey, @Nullable Context context, @Nullable HashMap<String, VariantsResult> variantList) {
        this.clientKey = clientKey;
        this.secretKey = secretKey;
        this.context = context;
        this.variantList = variantList;

        orygooService = APIUtils.getOrygooService();

        if (clientKey == null && secretKey == null) {
            Log.d("ERROR", "clientKey and secretKey are both null!");
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        @Nullable private String secretKey;
        @Nullable private String clientKey;
        @Nullable private HashMap<String, VariantsResult> variantList;

        Builder() {
            this.secretKey = null;
            this.clientKey = null;
            this.variantList = null;
        }

        public OrygooManager build(Context context) {
            return new OrygooManager(clientKey, secretKey, context, variantList);
        }

        public Builder widthSDKKey(String clientKey, String secretKey) {
            this.clientKey = clientKey;
            this.secretKey = secretKey;
            return this;
        }
    }

    public void initOrygoo(InitOrygooClient session) {
        SharedPrefManager manager = new SharedPrefManager(getContext());

        String anonymousId = manager.getItem("anonymousId");

        manager.removeItem("variants");

        if(!anonymousId.isEmpty()){
            session.setAnonymousId(anonymousId);
        } else {
            String anonId = UUID.randomUUID().toString();
            session.setAnonymousId(anonId);
            manager.saveItem("anonymousId", anonId);
        }

        InitializeModel initModel = new InitializeModel(
                "1",
                session.getUserId(),
                session.getAnonymousId(),
                "12",
                session.getUserEmail(),
                session.getUserPhoneNumber(),
                "ANDROID",
                "samsung",
                "12",
                session.getClientVersion(),
                session.getLanguage(),
                session.getCountry()
        );

        Call<InitializeModel> call = orygooService.initialize(initModel, getClientKey(), getSecretKey());

        call.enqueue(new Callback<InitializeModel>() {
            @Override
            public void onResponse(Call<InitializeModel> call, Response<InitializeModel> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                InitializeModel sessionResponse = response.body();
                String content = "";
                content += "SessionToken: " + sessionResponse.getSessionToken() + "\n";

                // Context object is require to create its object.
                SharedPrefManager manager = new SharedPrefManager(getContext());

                manager.saveItem("sessionToken", sessionResponse.getSessionToken());

                Log.d("Orygoo Initialize :", content);
            }

            @Override
            public void onFailure(Call<InitializeModel> call, Throwable t) {
                Log.e("TAG", "Got error : " + t.getLocalizedMessage());
            }
        });
    }

    public void getVariants(VariantsModel namespaces, List<String> defaultValue, GetVariants customCallback) {

        // Context object is require to create its object.
        SharedPrefManager manager = new SharedPrefManager(getContext());

        String token = manager.getItem("sessionToken");
        String variants = manager.getItem("variants");

        Log.d("Orygoo token :", manager.getItem("variants"));

        if(!token.isEmpty()){
            if(!variants.isEmpty()){
                try {
                    customCallback.onSuccess(MAPPER.readValue(variants, OrygooVariantsResult.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                Call<Map<String, VariantsResult>> call = orygooService.getVariants(namespaces, getClientKey(), getSecretKey(), manager.getItem("sessionToken"));

                call.enqueue(new Callback<Map<String, VariantsResult>>() {
                    @Override
                    public void onResponse(Call<Map<String, VariantsResult>> call, Response<Map<String, VariantsResult>> response) {
                        if (!response.isSuccessful()) {
                            return;
                        }

                        Gson gson = new Gson();
                        String json = gson.toJson(response.body());

                        Map<String, VariantsResult> resp = response.body();

                        Log.d("MENTY", resp.toString());
                        OrygooVariantsResult ovr = new OrygooVariantsResult(resp);

                        try {
                            manager.saveItem("variants", MAPPER.writeValueAsString(ovr));
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        customCallback.onSuccess(ovr);
                    }

                    @Override
                    public void onFailure(Call<Map<String, VariantsResult>> call, Throwable t) {
                        customCallback.onFailure("error");
                        Log.e("TAG", "Got error : " + t.getLocalizedMessage());
                    }
                });
            }
        } else {
            customCallback.onFailure("Initialize First");
        }
    }

    public void trackEvent(TrackEventOrygooClient trackEventPayload, GetTrackEvent customCallback) {

        // Context object is require to create its object.
        SharedPrefManager manager = new SharedPrefManager(getContext());

        String token = manager.getItem("sessionToken");

        EventTrackModel eventTrack = new EventTrackModel(
                trackEventPayload.getEventName(),
                trackEventPayload.getMetadata(),
                trackEventPayload.getMonetaryValue(),
                trackEventPayload.getValue(),
                System.currentTimeMillis()
        );

        if(!token.isEmpty()){
            Call<EventTrackModel> call = orygooService.trackEvent(eventTrack, getClientKey(), getSecretKey(), manager.getItem("sessionToken"));

            call.enqueue(new Callback<EventTrackModel>() {
                @Override
                public void onResponse(Call<EventTrackModel> call, Response<EventTrackModel> response) {
                    if (!response.isSuccessful()) {
                        customCallback.onFailure("Failed to track event");
                    }

                    EventTrackModel trackEventResponse = response.body();

                    customCallback.onSuccess("success to track event");

                    Log.d("Orygoo track event :", String.valueOf(trackEventResponse));
                }

                @Override
                public void onFailure(Call<EventTrackModel> call, Throwable t) {
                    Log.e("TAG", "Got error : " + t.getLocalizedMessage());
                }
            });
        } else {
            customCallback.onFailure("Initialize First");
        }
    }

    public void reset() {

        // Context object is require to create its object.
        SharedPrefManager manager = new SharedPrefManager(getContext());

        manager.reset();
    }

}