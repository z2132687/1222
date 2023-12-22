package com.example.lab1222;

import android.app.DownloadManager;
import android.os.Bundle;
import android.telecom.Call;
import android.view.Window;
import android.view.textclassifier.TextLinks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NonNls;

import java.io.IOException;
import java.net.ResponseCache;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UnsafeOkHttpClient {
    public static OkHttpClient getUnsafeOkHttpClient(){
        try{

            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager(){
                        @Override
                        public void checkClientTrusted(X509Certificate[] x509Certificates,String s){}

                        @Override
                        public void checkServerTrusted(X509Certificate[] x509Certificates,String s){}

                        @Override
                        public X509Certificate[] getAcceptedIssuers(){
                            return new X509Certificate[]{};
                        }
                    }
            };


            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null,trustAllCerts,new SecureRandom());
            return new OkHttpClient
                    .Builder()
                    .sslSocketFactory(sslContext.getSocketFactory(),(X509TrustManager)trustAllCerts[0])
                    .hostnameVerifier((hostname,session) -> true)
                    .build();
        } catch (Exception e){
            throw new RuntimeException();
        }
    }

    public class MainActivity extends AppCompatActivity {
        class Data {
            Result result;

            class Result {
                Result[] results;

                class Results {
                    String Station;
                    String Destination;
                }
            }
        }


        @Override
        protected void onCreate(Bundle savedInatanceState) {
            super.onCreate(savedInatanceState);
            setContentView(R.layout.activity_main);

            findViewById(R.id.btn_search).setOnClickListener(v -> {
                String URL = "https://tools-api.italkutalk.com/java/lab12";
                Request request = new Request.Builder().url(URL).build();
                OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
                okHttpClient.newCall(request).enqueue(new Callback() {


                    @Override
                    public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {

                    }

                    @Override
                    public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {

                    }
                });
            });
        }
    }
}
