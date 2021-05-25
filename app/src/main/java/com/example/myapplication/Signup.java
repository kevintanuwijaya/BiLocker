package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {

    private static final String url = "https://bilocker.000webhostapp.com/Register.php";
    private static final String LOG = Signup.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        EditText username = (EditText) findViewById(R.id.txtuser);
        final EditText password = findViewById(R.id.txtpass);
        final EditText name = findViewById(R.id.txtName);
        final Button bRegist = findViewById(R.id.bregist);

        bRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Username = username.getText().toString();
                final String Password= password.getText().toString();
                final String Name = name.getText().toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Signup.this, response, Toast.LENGTH_SHORT).show();
                        Log.i(LOG,"SUCCESS");
                        if(response.equals("registered succesfully")){
                            Intent intent = new Intent(Signup.this, Login.class);
                            startActivity(intent);
                        }
                    }
                },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        username.setText("");
                        password.setText("");
                        name.setText("");
                        Toast.makeText(Signup.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String,String>();
                        params.put("username",Username);
                        params.put("password",Password);
                        params.put("name",Name);
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(Signup.this);
                requestQueue.add(stringRequest);
            }

        });
    }
}