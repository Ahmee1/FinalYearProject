package bike.smartcycling.smartcycling;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {

    UserSession session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView txt_name = (TextView) findViewById(R.id.txt_Name);
        TextView username = (TextView) findViewById(R.id.username);
        EditText phone = (EditText) findViewById(R.id.phone);
        EditText email = (EditText) findViewById(R.id.email);

        session =  new UserSession(getApplicationContext());
        HashMap<String, String> information = session.getUserDetails();
        txt_name.setText(information.get(UserSession.KEY_FNAME) + " " + information.get(UserSession.KEY_LNAME));
        username.setText(information.get(UserSession.KEY_USERNAME));
        phone.setText(information.get(UserSession.KEY_PHONE));
        email.setText(information.get(UserSession.KEY_EMAIL));
    }
}
