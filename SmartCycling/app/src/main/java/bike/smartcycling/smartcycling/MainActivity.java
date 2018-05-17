package bike.smartcycling.smartcycling;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    UserSession session;
    NavigationView navigationView;
    View navHeader;
    private LocationManager mLocationManager;
    private String provider;
    //String ServerURL = "http://web.smartcycling.bike/Mobile_locationInsert.php" ;
    String ServerURL = "http://10.0.2.2/Mobile_locationInsert.php" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new UserSession(getApplicationContext());

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        navHeader = navigationView.getHeaderView(0);
        setUpButton();
        if (initLocationProvider()) {
            mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                mLocationManager.requestLocationUpdates(provider, 10000,
                        0, mLocationListener);
            } else {
                Toast.makeText(getApplicationContext(), "請開啟定位！", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean initLocationProvider() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
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

    public void insertToDatabase(String lat, String lng, String userid){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                Log.d(params[0]+", " + params[1] + ", " + params[2], "doInBackground: ");

                String result = "";
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("lat", params[0]));
                nameValuePairs.add(new BasicNameValuePair("lng", params[1]));
                nameValuePairs.add(new BasicNameValuePair("userid", params[2]));

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

        sendPostReqAsyncTask.execute(lat, lng, userid);
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();

            insertToDatabase(lat+"", lng+"", session.getUserDetails().get(UserSession.KEY_ID));
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private void setUpButton() {
        Button search = (Button) findViewById(R.id.btn_Search);
        search.setOnClickListener(this);
        Button nearby = (Button) findViewById(R.id.btn_Nearby);
        nearby.setOnClickListener(this);
        Button list = (Button) findViewById(R.id.btn_List);
        list.setOnClickListener(this);
        Button contest = (Button) findViewById(R.id.btn_Contest);
        contest.setOnClickListener(this);
        Button go = (Button) findViewById(R.id.btn_Go);
        go.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        session.checkLogin();
        HashMap<String, String> userInfo = session.getUserDetails();
        TextView txtUsername = (TextView) navHeader.findViewById(R.id.txtName);
        txtUsername.setText(userInfo.get(UserSession.KEY_LNAME) + " " + userInfo.get(UserSession.KEY_FNAME));
        TextView txtEmail = (TextView) navHeader.findViewById(R.id.txtEmail);
        txtEmail.setText(userInfo.get(UserSession.KEY_EMAIL));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {

        } else if (id == R.id.nav_home) {

        } else if (id == R.id.nav_notification) {

        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_family) {
            Intent intent = new Intent(getApplicationContext(), FamilyActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {
            String message = "Good Bye " + session.getUserDetails().get(UserSession.KEY_USERNAME);
            session.logoutUser();
            Toast.makeText(MainActivity.this, message , Toast.LENGTH_LONG).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        // Handle navigation view item clicks here.
        int id = view.getId();

        if (id == R.id.btn_Go) {
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_Search) {

        } else if (id == R.id.btn_Nearby) {

        } else if (id == R.id.btn_Contest) {

        } else if (id == R.id.btn_List) {

        }

    }
}
