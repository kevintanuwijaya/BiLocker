package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class PaymentNoNFC extends AppCompatActivity {

    private static final String url = "https://bilocker.000webhostapp.com/PaymentNoNFC.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_no_n_f_c);


        String take_time = getIntent().getStringExtra("TAKETIME");
        String payment = getIntent().getStringExtra("PAYMENT");
        String price = getIntent().getStringExtra("PRICE");
        String locker = getIntent().getStringExtra("LOCKER");

        final GlobalClass globalClass = (GlobalClass) getApplicationContext();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(PaymentNoNFC.this,response,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PaymentNoNFC.this,MainActivity.class);
                startActivity(intent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PaymentNoNFC.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PaymentNoNFC.this,Pengembalian.class);
                startActivity(intent);
            }
        }


        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("username",globalClass.getUsername());
                params.put("locker",locker);
                params.put("take_time",take_time);
                params.put("payment",payment);
                params.put("price",price);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(PaymentNoNFC.this);
        requestQueue.add(stringRequest);

    }
}