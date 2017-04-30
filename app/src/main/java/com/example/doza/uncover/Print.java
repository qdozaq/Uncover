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
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class Print extends AppCompatActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    Printer[] printerLocations = new Printer[27];
    double currLat, currLong;
    boolean locFound = false;
    private GoogleApiClient c = null;
    Printer closestPrinter;
    private LocationRequest locationRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);
        if (c == null) {
            c = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        printerLocations[0] = new Printer(35.913433, -79.051007, "Alumni Hall", "Outside Room 203");
        printerLocations[1] = new Printer(35.907289, -79.053425, "Beard Hall", "Inside Room 104");
        printerLocations[2] = new Printer(35.906583, -79.051672, "Berryhill Hall", "1st Floor Lounge (Access restricted to Med students)");
        printerLocations[3] = new Printer(35.91162, -79.051239, "Campus Y", "1st Floor");
        printerLocations[4] = new Printer(35.910985, -79.051314, "Career Services", "Hanes Hall 2nd Floor");
        printerLocations[5] = new Printer(35.906987, -79.051887, "Carrington Hall", "Inside Room 205; and 450");
        printerLocations[6] = new Printer(35.911309, -79.048044, "Davis", "1st Floor Info Commons and 2nd Floor");
        printerLocations[7] = new Printer(35.908244, -79.054111, "Global Education Center", "Coffee Shop");
        printerLocations[8] = new Printer(35.912616, -79.054372, "Sloane Art Library", "102 Hanes Art Center");
        printerLocations[9] = new Printer(35.905979, -79.052828, "Health Sciences Library", "1st Floor, Copy Room");
        printerLocations[10] = new Printer(35.9, -79.046242, "Kenan-Flagler Business School/McColl Building", "1st Flr Study Rms; 3rd Flr Atrium");
        printerLocations[11] = new Printer(35.909034, -79.052367, "Kenan Science Library", "G301A Venable");
        printerLocations[12] = new Printer(35.908996, -79.042263, "Law Library", "Back of Library by stack");
        printerLocations[13] = new Printer(35.909623, -79.049723, "Music Library (Wilson Library)", "302 Wilson Library");
        printerLocations[14] = new Printer(35.912955, -79.050294, "New East", "Inside Room 209");
        printerLocations[15] = new Printer(35.910467, -79.051884, "Park Library", "Carroll Hall 2nd floor");
        printerLocations[16] = new Printer(35.910756, -79.053544, "Peabody Hall", "Outside of room 204");
        printerLocations[17] = new Printer(35.911987, -79.051068, "Student & Academic Services Building", "1st Floor South Building");
        printerLocations[18] = new Printer(35.90479, -79.05368, "School of Dentistry", "G401 Koury Oral Health Sciences Building");
        printerLocations[19] = new Printer(35.910408, -79.041939, "School of Government", "3rd Floor MPA Wing Knapp-Sanders Building");
        printerLocations[20] = new Printer(35.911528, -79.049243, "School of Information and Library Science", "117 Manning Hall");
        printerLocations[21] = new Printer(35.910467, -79.051827, "School of Media and Journalism", "outside 111 Carroll Hall");
        printerLocations[22] = new Printer(35.907352, -79.0542, "School of Social Work", "501 Tate-Turner Kuralt");
        printerLocations[23] = new Printer(35.906006, -79.054057, "School of Public Health", "201 Rosenau Hall");
        printerLocations[24] = new Printer(35.908246, -79.050116, "Stone Center Library", "310 Stone Center");
        printerLocations[25] = new Printer(35.924755, -79.047363, "Student Union", "Lower Level Underground Lounge");
        printerLocations[26] = new Printer(35.909996, -79.049048, "UL - R.B. House Undergraduate Library", "Entry Copier Rm; Rm 037");
    }

    public void toMain(View v) {
        Intent X = new Intent(this, MainActivity.class);

        startActivity(X);
    }

    public void findPrint(View v) {
        if (locFound) {
            closestPrinter = findPrinter();
            ((TextView) findViewById(R.id.printer)).setText(closestPrinter.getLocation());
            ((TextView) findViewById(R.id.note)).setText(closestPrinter.getNote());
            ((Button) findViewById(R.id.navTo)).setVisibility(View.VISIBLE);
            findViewById(R.id.printerfield).setVisibility(View.VISIBLE);
        }


    }

    public void navTo(View v) {
        if (closestPrinter != null) {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + closestPrinter.getLat() + "," + closestPrinter.getLon() + "(" + closestPrinter.getLocation() + " Printer)");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }

    }

    public Printer findPrinter() {

        double lat = currLat;
        double lon = currLong;
        Printer closest = null;
        boolean set = false;
        double shortestDist = 100000;
        for (Printer p : printerLocations) {
            double dist = Math.sqrt((lat - p.getLat()) * (lat - p.getLat()) + (lon - p.getLon()) * (lon - p.getLon()));
            if (!set) {
                closest = p;
                shortestDist = dist;
                set = true;
            } else if (dist < shortestDist) {
                closest = p;
                shortestDist = dist;
            }
        }
        return closest;

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
    protected void onStart() {
        c.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        c.disconnect();
        super.onStop();
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
                currLat = loc.getLatitude();
                currLong = loc.getLongitude();
                locFound = true;
                ((TextView) findViewById(R.id.status)).setText("Location found");
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
