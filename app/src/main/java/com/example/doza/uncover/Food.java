package com.example.doza.uncover;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

public class Food extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "A0OSFhztiC2b5YBodx3URraw8";
    private static final String TWITTER_SECRET = "lilg33TymSkVocqq2odImJBtjmclwJJCaMtnMPdVYk33oleuuq";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_food);
    }
    public void toMain(View v){
        Intent X = new Intent(this, MainActivity.class);

        startActivity(X);
    }
}
