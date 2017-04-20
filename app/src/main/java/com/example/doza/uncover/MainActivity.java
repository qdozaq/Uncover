package com.example.doza.uncover;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void toHolla(View v){
        Intent X = new Intent(this, Holla.class);

        startActivity(X);
    }
    public void toPrint(View v){
        Intent X = new Intent(this, Print.class);

        startActivity(X);
    }
    public void toFood(View v){
        Intent X = new Intent(this, Food.class);

        startActivity(X);
    }
}
