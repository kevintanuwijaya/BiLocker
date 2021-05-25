    package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import com.blikoon.qrcodescanner.QrCodeActivity;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_QR_SCAN = 101;
    Button scan,returning;

    boolean isput=false,isreturn=false;
    TextView login,curruser,logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GlobalClass globalClass = (GlobalClass) getApplicationContext();

        scan = findViewById(R.id.btn1);
        login = findViewById(R.id.login);
        logout = findViewById(R.id.logout);
        curruser = findViewById(R.id.currentUser);
        returning = findViewById(R.id.btn2);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isput = true;
                Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        if(globalClass.getName().equals("")){
                            Toast.makeText(MainActivity.this,"Please Login First",Toast.LENGTH_SHORT).show();
                        }else{
                            Intent i = new Intent(MainActivity.this,QrCodeActivity.class);
                            startActivityForResult( i,REQUEST_CODE_QR_SCAN);
                        }

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        permissionDeniedResponse.getRequestedPermission();

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                    }
                }).check();
            }
        });
        returning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isreturn=true;
                Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        if (globalClass.getName().equals("")) {
                            Toast.makeText(MainActivity.this, "Please Login First", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent i = new Intent(MainActivity.this, QrCodeActivity.class);
                            startActivityForResult(i, REQUEST_CODE_QR_SCAN);
                        }
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        permissionDeniedResponse.getRequestedPermission();

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                    }
                }).check();
            }
        });


        if(globalClass.getName().equals("")){
            login.setVisibility(View.VISIBLE);
            logout.setVisibility(View.GONE);
            String login_text = "Login";
            SpannableString ss = new SpannableString(login_text);

            ClickableSpan cclogin = new ClickableSpan() {
                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(Color.BLUE);
                    ds.setUnderlineText(false);
                }

                @Override
                public void onClick(@NonNull View widget) {
                    Intent intent = new Intent(MainActivity.this,Login.class);
                    startActivity(intent);

                }
            };

            ss.setSpan(cclogin,0,5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            login.setText(ss);
            login.setMovementMethod(LinkMovementMethod.getInstance());
        }else{
            curruser.setText(globalClass.getName());
            logout.setVisibility(View.VISIBLE);
            login.setVisibility(View.INVISIBLE);
            String login_text = "Logout";
            SpannableString ss = new SpannableString(login_text);

            ClickableSpan cclogin = new ClickableSpan() {
                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(Color.BLUE);
                    ds.setUnderlineText(false);
                }

                @Override
                public void onClick(@NonNull View widget) {
                    Toast.makeText(MainActivity.this,"Logout Succesfully",Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this,"See you again " + globalClass.getName(),Toast.LENGTH_SHORT).show();
                    globalClass.setNameNull();
                    globalClass.setUsernameNull();
                    curruser.setText("");
                    Intent intent = new Intent(MainActivity.this,MainActivity.class);
                    startActivity(intent);

                }
            };

            ss.setSpan(cclogin,0,6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            logout.setText(ss);
            logout.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            if (data == null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
            if (result != null) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Scan Error");
                alertDialog.setMessage("QR Code could not be scanned");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
            return;

        }
        if (requestCode == REQUEST_CODE_QR_SCAN) {
            if (data == null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
            if(isput){
                isput=false;
                openPenitipan(result);
            }else
                if(isreturn){
                    isreturn=false;
                    openPengembalian(result);
                }
        }
    }

    private void openPenitipan(String result){
        Intent intent = new Intent(MainActivity.this,Penitipan.class);
        intent.putExtra("NUMBER",result);
        startActivity(intent);
    }

    private void openPengembalian(String result){
        Intent intent = new Intent(MainActivity.this,Pengembalian.class);
        intent.putExtra("NUMBER",result);
        startActivity(intent);
    }
}