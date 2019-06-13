package com.example.ecdingdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        LoginParam loginParam = new LoginParam();
        loginParam.setServerUrl(" 10.117.80.11");
        loginParam.setServerPort(8011);
        loginParam.setUserName("UCTest1");
        loginParam.setPassword("AAaa1122");

        LoginService.getInstance().authorizeLogin(loginParam);
    }
}
