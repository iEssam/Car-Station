package com.example.login22_01_19_h1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.example.login22_01_19_h1.LoginSingup.Login_MySql;

//essam

//import android.content.Intent;

import com.example.login22_01_19_h1.LoginSingup.signUp_mysqlphp;
import com.example.login22_01_19_h1.Menu.Navigation_Main;

public class MainActivity extends AppCompatActivity  {
    Button login, Reg;
    Toolbar toolbar;
    ImageView logo;
    Drawable myDrawable;
    private static Context context;

    @Override
    public void onBackPressed() {
        MainActivity.this.finish();
    }
    public static Context getAppContext() {
        return MainActivity.context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Hello world");
        super.onCreate(savedInstanceState);
        MainActivity.context = getApplicationContext();
        setContentView(R.layout.activity_main);


        if(SharedPrefManger.getInstance(this).isLoggedIn()){
            finish();
            // -------------------*********************---------------
            startActivity(new Intent(this , Navigation_Main.class));
        return;
        }

        logo = findViewById(R.id.logo1);
        myDrawable = getResources().getDrawable(R.drawable.car_logo);
        logo.setImageDrawable(myDrawable);

        login = (Button) findViewById(R.id.btnLogin);
        toolbar = (Toolbar) findViewById(R.id.tool_main);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Login_MySql.class);
                startActivity(intent);
            }
        });
        Reg = findViewById(R.id.btnEnter);
        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, signUp_mysqlphp.class);
                startActivity(intent);
            }
        });

    }

}