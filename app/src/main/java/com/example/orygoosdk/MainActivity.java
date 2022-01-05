package com.example.orygoosdk;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.orygoolibrary.OrygooManager;
import com.example.orygoolibrary.OrygooVariantsResult;
import com.example.orygoolibrary.model.InitOrygooClient;
import com.example.orygoolibrary.model.TrackEventOrygooClient;
import com.example.orygoolibrary.model.VariantsModel;
import com.example.orygoolibrary.rest.GetTrackEvent;
import com.example.orygoolibrary.rest.GetVariants;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button b1, b2, b3;
    TextView t1;
    EditText e1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    OrygooManager orygooManager = OrygooManager.builder()
            .widthSDKKey("C-1ecb498e3f32476a9b43ba29da9c9d03", "STG-1fca401d5b4d49a3ac098de5462c6213c5061d8c7708406db865e4ceb0a1bf1b")
            .build(MainActivity.this);

    public void init_button(View view) {
        e1 = (EditText) findViewById(R.id.editTextTextPersonName);

        InitOrygooClient initPayload = new InitOrygooClient(
                e1.getText().toString(),
                "1",
                "xdigoxinx@gmail.com",
                "081344559903",
                "1",
                "en",
                "id"
        );

        orygooManager.initOrygoo(initPayload);
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
                b1 = (Button) findViewById(R.id.button_send2);
                t1 = (TextView) findViewById(R.id.textView);

                Log.d("Orygoo main activity :", variants.getValue("Button", "Color", "#FFFF00"));

                String color = variants.getValue("Button", "Color", "#FFFF00");
                t1.setTextColor(Color.parseColor(color));
            }

            @Override
            public void onFailure(String throwableError) {
                Log.d("Get Variants ERROR", throwableError);

            }
        });
    }

    public void reset_button(View view) {
        orygooManager.reset();
        t1 = (TextView) findViewById(R.id.textView);
        t1.setTextColor(Color.parseColor("#000000"));
    }

    public void get_track2(View view) {
        TrackEventOrygooClient trackEventClient = new TrackEventOrygooClient("track_2", "", "", "1");

        orygooManager.trackEvent(trackEventClient, new GetTrackEvent() {
            @Override
            public void onSuccess(String status) {
                Log.d("Success Track Event :", status);
            }

            @Override
            public void onFailure(String throwableError) {
                Log.d("Failed Track Event", throwableError);
            }
        });
    }

    public void get_track1(View view) {
        TrackEventOrygooClient trackEvent = new TrackEventOrygooClient("track_1", "", "", "1");

        orygooManager.trackEvent(trackEvent, new GetTrackEvent() {
            @Override
            public void onSuccess(String status) {
                Log.d("Success Track Event :", status);
            }

            @Override
            public void onFailure(String throwableError) {
                Log.d("Failed Track Event", throwableError);
            }
        });
    }
}