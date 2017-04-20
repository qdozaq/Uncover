package com.example.doza.uncover;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Holla extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holla);
        getIntent().getData();
    }
    public void toMain(View v){
        Intent X = new Intent(this, MainActivity.class);

        startActivity(X);
    }

}
