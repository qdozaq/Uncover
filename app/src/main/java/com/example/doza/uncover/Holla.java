package com.example.doza.uncover;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class Holla extends AppCompatActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    String location = "";
    private GoogleApiClient c = null;
    private LocationRequest locationRequest;

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
    protected void onStart() {
        c.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        c.disconnect();
        super.onStop();
    }

    public void findFriend(View v) {
        String input = ((EditText) findViewById(R.id.locField)).getText().toString();
        if (input.equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please enter coordinates first", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + input + "(Frand)");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }


    public void toMain(View v) {
        Intent X = new Intent(this, MainActivity.class);

        startActivity(X);
    }

    public void sendLoc(View v) {
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("sms:"));
        sendIntent.putExtra("sms_body", location);
        startActivity(sendIntent);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getLocation(null);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        getLocation(location);
    }

    private void getLocation(Location l) {
        Location loc = l;
        try {
            if (loc == null) {
                loc = LocationServices.FusedLocationApi.getLastLocation(c);
            }

            if (loc != null) {
                location = loc.getLatitude() + ", " + loc.getLongitude();
                ((TextView) findViewById(R.id.statText)).setText("location ready to send");
            } else {
                startLocationUpdates();
            }

        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
    }

    private void startLocationUpdates() {
        Log.i("tag", "startLocationUpdates()");
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000);

        if (MainActivity.checkPermission(this))
            LocationServices.FusedLocationApi.requestLocationUpdates(c, locationRequest, this);

        getLocation(null);
    }

}
