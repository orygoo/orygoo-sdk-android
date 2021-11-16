package com.example.orygoolibrary;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.orygoolibrary.model.SessionModel;
import com.example.orygoolibrary.model.TrackModel;
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

    public void initOrygoo(SessionModel session) {
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

        Call<SessionModel> call = orygooService.initialize(session, getClientKey(), getSecretKey());

        call.enqueue(new Callback<SessionModel>() {
            @Override
            public void onResponse(Call<SessionModel> call, Response<SessionModel> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                SessionModel sessionResponse = response.body();
                String content = "";
                content += "SessionToken: " + sessionResponse.getSessionToken() + "\n";

                // Context object is require to create its object.
                SharedPrefManager manager = new SharedPrefManager(getContext());

                manager.saveItem("sessionToken", sessionResponse.getSessionToken());

                Log.d("Orygoo Initialize :", content);
            }

            @Override
            public void onFailure(Call<SessionModel> call, Throwable t) {
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

    public void trackEvent(TrackModel trackModel, GetTrackEvent customCallback) {

        // Context object is require to create its object.
        SharedPrefManager manager = new SharedPrefManager(getContext());

        String token = manager.getItem("sessionToken");

//        Log.d("Orygoo token :", manager.getItem("variants"));

        if(!token.isEmpty()){
            Call<TrackModel> call = orygooService.trackEvent(trackModel, getClientKey(), getSecretKey(), manager.getItem("sessionToken"));

            call.enqueue(new Callback<TrackModel>() {
                @Override
                public void onResponse(Call<TrackModel> call, Response<TrackModel> response) {
                    if (!response.isSuccessful()) {
                        customCallback.onFailure("Failed to track event");
                    }

                    TrackModel trackEventResponse = response.body();

                    customCallback.onSuccess("success to track event");

                    Log.d("Orygoo track event :", String.valueOf(trackEventResponse));
                }

                @Override
                public void onFailure(Call<TrackModel> call, Throwable t) {
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