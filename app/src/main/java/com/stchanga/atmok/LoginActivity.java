package com.stchanga.atmok;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText edUserid;
    private EditText edPasswd;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUserid=findViewById(R.id.userid);
        edPasswd=findViewById(R.id.passwd);
    }

    public void login(View view){
        String userid=edUserid.getText().toString();
        String passwd=edPasswd.getText().toString();
        if ("jack".equals(userid) && "1234".equals(passwd)){
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
