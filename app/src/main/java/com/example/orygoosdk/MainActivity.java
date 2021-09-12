package com.example.orygoosdk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.orygoolibrary.OrygooManager;
import com.example.orygoolibrary.OrygooVariantsResult;
import com.example.orygoolibrary.model.SessionModel;
import com.example.orygoolibrary.model.VariantsModel;
import com.example.orygoolibrary.model.VariantsResult;
import com.example.orygoolibrary.rest.GetVariants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    OrygooManager orygooManager = OrygooManager.builder()
            .widthSDKKey("C-1ecb498e3f32476a9b43ba29da9c9d03", "STG-1fca401d5b4d49a3ac098de5462c6213c5061d8c7708406db865e4ceb0a1bf1b")
            .build(MainActivity.this);

    public void init_button(View view) {

        SessionModel session = new SessionModel(
                "",
                "1",
                "1",
                "deviceId",
                "xdigoxinx@gmail.com",
                "081344559903",
                "ANDROID",
                "",
                "",
                "1",
                "en",
                "id"
        );

        orygooManager.initOrygoo(session);
    }

    public void get_variants(View view) {

        List<String> namespace = new ArrayList<String>() {
            {
                add("Button");
            }
        };

        List<String> defaultValue = new ArrayList<String>() {
            {
                add("1");
            }
        };

        VariantsModel varReq = new VariantsModel(
                namespace
        );

        orygooManager.getVariants(varReq, defaultValue, new GetVariants() {
            @Override
            public void onSuccess(OrygooVariantsResult variants) {
                Log.d("Get Variants SUCCESS", variants.getValue("Button", "Color", "Blue"));
            }

            @Override
            public void onFailure(String throwableError) {
                Log.d("Get Variants ERROR", throwableError);

            }
        });
    }

    public void reset_button(View view) {
        orygooManager.reset();
    }
}