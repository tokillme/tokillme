package com.stchanga.atmok;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG=LoginActivity.class.getSimpleName();
    private static final int REQUEST_CODE_CAMERA = 5;
    private EditText edUserid;
    private EditText edPasswd;
    private CheckBox cbRemember;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        int permission=ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA);


        if(permission== PackageManager.PERMISSION_GRANTED){
            takephoto();
        }else{
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},REQUEST_CODE_CAMERA);
        }

        getSharedPreferences("atmok",MODE_PRIVATE)
                .edit()
                .putInt("LEVEL",3)
                .putString("NAME","Tom")
                .commit();
        int level=getSharedPreferences("atmok",MODE_PRIVATE).getInt("LEVEL",0);
        Log.d(TAG,"onCreate"+level);

        edUserid=findViewById(R.id.userid);
        edPasswd=findViewById(R.id.passwd);
        cbRemember=findViewById(R.id.cb_rem_userid);
        cbRemember.setChecked(getSharedPreferences("atmok",MODE_PRIVATE).getBoolean("REMEMBER_USERID",false));
        cbRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getSharedPreferences("atmok", MODE_PRIVATE)
                        .edit()
                        .putBoolean("REMEMBER_USERID",b)
                        .apply();

            }
        });
        String userid=getSharedPreferences("atmok",MODE_PRIVATE)
                .getString("USERID","");
        edUserid.setText(userid);
    }

    private void takephoto() {
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_CODE_CAMERA){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                takephoto();
            }
        }
    }

    public void login(View view){
        String userid=edUserid.getText().toString();
        String passwd=edPasswd.getText().toString();
        if ("jack".equals(userid) && "1234".equals(passwd)){
            boolean remember=getSharedPreferences("atmok",MODE_PRIVATE)
                    .getBoolean("REMEMBER_USERID",false);
            if(remember) {
               getSharedPreferences("atmok",MODE_PRIVATE)
                       .edit()
                       .putString("USERID",userid)
                       .apply();
            }
            setResult(RESULT_OK);
            finish();
        }else{
            new AlertDialog.Builder(this)
                    .setTitle("登入")
                    .setMessage("登入失敗")
                    .setPositiveButton("OK",null)
                    .show();
        }
    }
    public void cancel(View view){

    }
}
