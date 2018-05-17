package bike.smartcycling.smartcycling;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<LatLng> line;
    private List<Polyline> pl = new ArrayList<Polyline>();
    private LocationManager locationManager;
    private String provider;
    private Marker pMarker;
    private UserSession session;
    String ServerURL = "http://10.0.2.2/Mobile_recordInsert.php" ;
    //String ServerURL = "http://web.smartcycling.bike/Mobile_recordInsert.php" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        initLocationProvider();
    }

    @Override
    protected void onStart() {
        super.onStart();

        setUpMap();
        session = new UserSession(getApplicationContext());
        final Button btn_start = (Button) findViewById(R.id.btn_Start);
        final Button btn_Finish = (Button) findViewById(R.id.btn_Finish);
        builder = new AlertDialog.Builder(MapsActivity.this);
        btn_start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (initLocationProvider()) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    Location location = locationManager.getLastKnownLocation(provider);
                    if (location != null) {
                    }
                    int minTime = 1000;//ms
                    int minDist = 0;//meter
                    locationManager.requestLocationUpdates(provider, minTime, minDist, locationListener);
                }


                } else{
                    Toast.makeText(getApplicationContext(),"請開啟定位！", Toast.LENGTH_LONG).show();
                }
                btn_start.setVisibility(View.INVISIBLE);
                btn_Finish.setVisibility(View.VISIBLE);
            }
        });
        btn_Finish.setVisibility(View.INVISIBLE);
        btn_Finish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createBuilder();
                builder.show();
            }
        });

    }
    AlertDialog.Builder builder;

    public void createBuilder() {
        final EditText edittext = new EditText(this);
        builder.setMessage("Save Record?");
        builder.setTitle("Enter Your Record Name");

        builder.setView(edittext);


        builder.setPositiveButton(R.string.text_save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String recordName = edittext.getText().toString();
                        String route = "LINESTRING(";
                        for(int i=0; i<line.size();i++) {
                            if (i==0) {
                                route +=  line.get(i).latitude +" " + line.get(i).longitude;
                            } else {
                                route += ", " + line.get(i).latitude + " " + line.get(i).longitude;
                            }
                        }
                        route += ")";
                        String userid = session.getUserDetails().get(UserSession.KEY_ID);
                        insertToDatabase(route, userid, recordName);
                        locationManager.removeUpdates(locationListener);
                        Toast.makeText(MapsActivity.this, "Your Route is save", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        builder.setNegativeButton(R.string.text_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(MapsActivity.this, "Your action is cancel", Toast.LENGTH_SHORT).show();
                    }
                });
        builder.create();
    }

    public void insertToDatabase(String route, String userid, String name){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                Log.d(params[0]+", " + params[1] + ", " + params[2], "doInBackground: ");

                String result = "";
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("route", params[0]));
                nameValuePairs.add(new BasicNameValuePair("userid", params[1]));
                nameValuePairs.add(new BasicNameValuePair("name", params[2]));

                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(ServerURL);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse httpResponse = httpClient.execute(httpPost);

                    BufferedReader rd = new BufferedReader(
                            new InputStreamReader(
                                    httpResponse.getEntity().getContent()));
                    String line = "";
                    while ((line = rd.readLine()) != null) {
                        result += line;
                    }
                    rd.close();
                    //Log.d(result, "doInBackground: ");


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);

                //Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();

            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(route, userid, name);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMap();
    }

    @Override
    protected void onStop() {
        locationManager.removeUpdates(locationListener);
        super.onStop();
    }

    private void setUpMap() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            if (mMap != null) {
                //setUpMap();
            }
        }
    }

    private boolean initLocationProvider() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {

                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            1);
                }
            }
        }
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(22.27897, 114.172923), 12));
    }

    private void getLocation(Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        drawLine(lat, lng);
        setMarker(lat, lng);
        CameraPosition camPosition = new CameraPosition.Builder()
                .target(new LatLng(lat, lng))
                .zoom(12)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camPosition));
    }

    private void setMarker(double lat, double lng) {
        MarkerOptions markerOpt = new MarkerOptions();
        markerOpt.position(new LatLng(lat, lng));
        markerOpt.title("Your Location");

        if (pMarker != null) {
            pMarker.remove();
        }
        pMarker = mMap.addMarker(markerOpt);
    }

    private void drawLine(double lat, double lng) {
        if (line == null) {
            line = new ArrayList<LatLng>();
        }
        line.add(new LatLng(lat, lng));
        PolylineOptions polyLineOpt = new PolylineOptions();
        for (LatLng latlng : line) {
            polyLineOpt.add(latlng);
        }
        polyLineOpt.color(Color.BLUE);
        pl.add(mMap.addPolyline(polyLineOpt));
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            getLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider) {
            getLocation(null);
        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.OUT_OF_SERVICE:
                    //Log.v(TAG, "Status Changed: Out of Service");
                    Toast.makeText(MapsActivity.this, "Status Changed: Out of Service", Toast.LENGTH_SHORT).show();
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    //Log.v(TAG, "Status Changed: Temporarily Unavailable");
                    Toast.makeText(MapsActivity.this, "Status Changed: Temporarily Unavailable", Toast.LENGTH_SHORT).show();
                    break;
                case LocationProvider.AVAILABLE:
                    //Log.v(TAG, "Status Changed: Available");
                    Toast.makeText(MapsActivity.this, "Status Changed: Available", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    };
}
