package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private static final String url = "https://bilocker.000webhostapp.com/Login.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final GlobalClass globalClass = (GlobalClass) getApplicationContext();

        final TextView Sign_btn = findViewById(R.id.Signup);
        final Button login_btn = findViewById(R.id.btnlogin);
        final TextView user_name = findViewById(R.id.txtuser);
        final TextView pass = findViewById(R.id.txtpass);

        String sign = "Signup";
        SpannableString ss = new SpannableString(sign);

        ClickableSpan button_sign = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(Login.this,Signup.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };

        ss.setSpan(button_sign,0,6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        Sign_btn.setText(ss);
        Sign_btn.setMovementMethod(LinkMovementMethod.getInstance());

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Username = user_name.getText().toString();
                final String Password= pass.getText().toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("user not found")){
                            user_name.setText("");
                            pass.setText("");
                            Toast.makeText(Login.this,"No user Found, Please Register new Account",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            Toast.makeText(Login.this, "Welcome to BiLocker " + response, Toast.LENGTH_SHORT).show();
                            globalClass.setName(response);
                            globalClass.setUsername(Username);
                            startActivity(intent);
                        }
                    }
                },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this,"No user Found, Please Register new Account", Toast.LENGTH_SHORT).show();
                    }
                }

                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String,String>();
                        params.put("username",Username);
                        params.put("password",Password);
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
                requestQueue.add(stringRequest);
            }
        });

    }


}