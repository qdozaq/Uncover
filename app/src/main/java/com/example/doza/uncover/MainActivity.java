package com.example.doza.uncover;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!checkPermission(this)) {
            askPermission();
        }

    }

    public void toHolla(View v) {
        Intent X = new Intent(this, Holla.class);

        startActivity(X);
    }

    public void toPrint(View v) {
        Intent X = new Intent(this, Print.class);

        startActivity(X);
    }

    public void toFood(View v) {
        Intent X = new Intent(this, Food.class);

        startActivity(X);
    }

    private final int REQ_PERMISSION = 999;

    // Check for permission to access Location
    static boolean checkPermission(Context context) {
        Log.d("tag", "checkPermission()");
        // Ask for permission if it wasn't granted yet
        return (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED);
    }


    // Asks for permission
    private void askPermission() {
        Log.d("tag", "askPermission()");
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQ_PERMISSION
        );
    }

    // Verify user's response of the permission requested
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("tag", "onRequestPermissionsResult()");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // Permission denied
                    permissionsDenied();
                }
                break;
            }
        }
    }

    // App cannot work without the permissions
    private void permissionsDenied() {
        Toast toast = Toast.makeText(getApplicationContext(), "This application needs location services to work", Toast.LENGTH_SHORT);
        toast.show();
        Log.w("tag", "permissionsDenied()");
    }
}
