package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Pengembalian extends AppCompatActivity {

    private static final String url = "https://bilocker.000webhostapp.com/Take.php";
    TextView locker,put_time,take_time,duration,price,flazz;
    Button pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengembalian);

        final GlobalClass globalClass = (GlobalClass) getApplicationContext();
        String locker_number = getIntent().getStringExtra("NUMBER");

        locker = findViewById(R.id.textLocker);
        put_time = findViewById(R.id.textPutTime);
        take_time = findViewById(R.id.textTakeTime);
        duration = findViewById(R.id.textDuration);
        price = findViewById(R.id.textPrice);
        flazz = findViewById(R.id.lblFlazz);
        pay = findViewById(R.id.btnPay);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Date put = null;
                try{
                    put = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(response);
                }catch (Exception e){}
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                String take = simpleDateFormat.format(date);

                long hours;
                long datediff = Math.abs(put.getTime()-date.getTime());
                if(datediff%3600000==0) hours = datediff/3600000; else hours=(datediff/3600000)+1;

                long total_price = hours*2000;

                locker.setText(locker_number);
                put_time.setText(response);
                take_time.setText(take);
                duration.setText(hours+" Hours");
                price.setText("Rp. " + total_price);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Pengembalian.this,"This is not your locker, please scan another one",Toast.LENGTH_SHORT).show();
            }
        }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("username",globalClass.getUsername());
                params.put("locker",locker_number);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Pengembalian.this);
        requestQueue.add(stringRequest);

    pay.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Pengembalian.this,PaymentNoNFC.class);
            intent.putExtra("PAYMENT",flazz.getText());
            intent.putExtra("TAKETIME",take_time.getText());
            intent.putExtra("PRICE",price.getText());
            intent.putExtra("LOCKER",locker.getText());
            startActivity(intent);
        }
    });


    }
}