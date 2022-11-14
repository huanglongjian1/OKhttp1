package com.example.okhttp1;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    Button button;
    String TAG = MainActivity.class.getSimpleName();
    TextView tv;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //      tv.setText((String) msg.obj);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
        //    testOkHttp();
        button = findViewById(R.id.btn);
        button.setOnClickListener((v) -> {
                    tv.setText("");
                    TestRetrofit();
                }

        );

    }


    private void TestRetrofit() {
        String baseUrl = "http://apis.baidu.com/txapi/";


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetBaidu dayService = retrofit.create(GetBaidu.class);

        Call<String> call = dayService.get("1","10");

        call.enqueue(new Callback<String>() {


            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                tv.post(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(response.message());
                     //   Log.d(TAG,response.body().toString());
                    }
                });
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //   Log.d("黑龙江------------------------------------", t.toString());

            }
        });

    }
}
