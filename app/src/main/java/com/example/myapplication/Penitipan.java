package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
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

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.sql.Timestamp;

public class Penitipan extends AppCompatActivity {

    private static String url = "https://bilocker.000webhostapp.com/Put.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penitipan);

        final GlobalClass globalClass = (GlobalClass) getApplicationContext();

        TextView lock = (TextView) findViewById(R.id.locker);
        TextView date_t = (TextView) findViewById(R.id.date_time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String put_time = simpleDateFormat.format(date);
        String locker = getIntent().getStringExtra("NUMBER");
        lock.setText(locker);
        date_t.setText(put_time);


        Button btn_oke = findViewById(R.id.btnOke);
        btn_oke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Penitipan.this,response,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Penitipan.this,MainActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Penitipan.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }

                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String,String>();
                        params.put("username",globalClass.getUsername());
                        params.put("locker",locker);
                        params.put("put_time",put_time);
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(Penitipan.this);
                requestQueue.add(stringRequest);
            }
        });

    }
}