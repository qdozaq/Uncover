package com.example.doza.uncover;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;


public class Holla extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{
    String location = "";
    String sent="";
    private GoogleApiClient c = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holla);
        getIntent().getData();
        if (c == null) {
            c = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }
    @Override
    protected void onStart(){
        c.connect();
        super.onStart();
    }
    @Override
    protected void onStop(){
        c.disconnect();
        super.onStop();
    }
    public void findFriend(View v){
        String input = ((EditText)findViewById(R.id.locField)).getText().toString();
        //String lat = input.substring(0,input.indexOf(","));
        //String lon = input.substring(input.indexOf(",")+1,input.length());
        Uri gmmIntentUri = Uri.parse("geo:0,0?q="+input+"(Frand)");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
//        Intent X = new Intent(this, MapActivity.class);
//
//        startActivity(X);

    }



    public void toMain(View v){
        Intent X = new Intent(this, MainActivity.class);

        startActivity(X);
    }
    public void sendLoc(View v){
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("sms:"));
        sendIntent.putExtra("sms_body", location);
        startActivity(sendIntent);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        try {
            Location loc = LocationServices.FusedLocationApi.getLastLocation(c);
            location = loc.getLatitude() + ", " + loc.getLongitude();
            ((TextView) findViewById(R.id.statText)).setText("location ready to send");

        }catch (SecurityException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
