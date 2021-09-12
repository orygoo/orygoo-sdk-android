//package com.example.orygoolibrary;
//
//import android.content.Context;
//import android.util.Log;
//
//import com.example.orygoolibrary.model.Session;
//import com.example.orygoolibrary.rest.OrygooApiService;
//import com.example.orygoolibrary.rest.RestClient;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class Orygoo {
//
//    private static String clientKey;
//    private static String secretKey;
//    private static Context context;
//
//    public static String getClientKey() {
//        return clientKey;
//    }
//
//    public static String getSecretKey() {
//        return secretKey;
//    }
//
//    public static Context getContext() {
//        return context;
//    }
//
//    public static void createInstance(String clientKey, String secretKey, Context context) {
//        Orygoo.clientKey = clientKey;
//        Orygoo.secretKey = secretKey;
//        Orygoo.context = context;
//    }
//
//    public static String checkSession(){
//        SharedPrefManager manager = new SharedPrefManager(getContext());
//
//        String sessionId = manager.getItem("sessionId");
//
//        return sessionId;
//    }
//
//    public static void initOrygoo(
//            String clientSessionId,
//            String userId,
//            String deviceId,
//            String userEmail,
//            String userPhoneNumber,
//            String clientInterface,
//            String deviceModel,
//            String deviceOsVersion,
//            String clientVersion,
//            String language,
//            String country
//    ) {
//        Session session = new Session(
//                clientSessionId,
//                userId,
//                deviceId,
//                userEmail,
//                userPhoneNumber,
//                clientInterface,
//                deviceModel,
//                deviceOsVersion,
//                clientVersion,
//                language,
//                country
//        );
//
//        SharedPrefManager manager = new SharedPrefManager(getContext());
//
//        String sessionId = manager.getItem("sessionId");
//
////        manager.removeItem("sessionId");
//
//        Log.d("Session Id saved :", sessionId);
//
//        Call<Session> call = RestClient.getClient().create(OrygooApiService.class).initialize(session, getClientKey(), getSecretKey());
//
//        call.enqueue(new Callback<Session>() {
//            @Override
//            public void onResponse(Call<Session> call, Response<Session> response) {
//                if (!response.isSuccessful()) {
//                    return;
//                }
//
//                Session sessionResponse = response.body();
//                String content = "";
//                content += "SessionId: " + sessionResponse.getSessionId() + "\n";
//
//                //Context object is require to create its object.
//                SharedPrefManager manager = new SharedPrefManager(getContext());
//
//                manager.saveSession("sessionId", sessionResponse.getSessionId());
//
//                Log.d("Orygoo Initialize :", content);
//            }
//
//            @Override
//            public void onFailure(Call<Session> call, Throwable t) {
//                Log.e("TAG", "Got error : " + t.getLocalizedMessage());
//            }
//        });
//    }
//
//    public static void getVariants(
//            ArrayList<String> nameSpacesArr,
//            ArrayList<String> defaultValue
//    ) {
//
////        if(checkSession().length() > 0){
////            Log.d("have session", Arrays.toString(nameSpacesArr));
////        }
////
////        Log.d("have session", Arrays.toString(defaultValue));
//    }
//}