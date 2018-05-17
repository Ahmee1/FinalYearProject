package bike.smartcycling.smartcycling;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FamilyActivity extends AppCompatActivity {

    UserSession session;
    String ServerURL = "http://10.0.2.2/Mobile_familyAccountInsert.php" ;
    //String ServerURL = "http://web.smartcycling.bike/Mobile_familyAccountInsert.php" ;

    Boolean insert = false;
    TextView opd;
    TextView npd;
    TextView cpd;
    @BindView(R.id.F_username) EditText etfamilyac;
    @BindView(R.id.F_password) EditText etpassword;
    @BindView(R.id.F_password_n) EditText etpassword_n;
    @BindView(R.id.F_password_c) EditText etpassword_c;

    HashMap<String, String> information;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
         opd = (TextView) findViewById(R.id.txt_F_password);
         npd = (TextView) findViewById(R.id.txt_F_password_n);
         cpd = (TextView) findViewById(R.id.txt_F_password_c);
         btn = (Button) findViewById(R.id.button_create);

        session =  new UserSession(getApplicationContext());
        information = session.getUserDetails();
        if (information.get(UserSession.KEY_FUSERNAME).equalsIgnoreCase("n")) {
            insert = true;
        }

        if (insert) {
            npd.setVisibility(View.INVISIBLE);
            cpd.setVisibility(View.INVISIBLE);
            etpassword_n.setVisibility(View.INVISIBLE);
            etpassword_c.setVisibility(View.INVISIBLE);
            opd.setText("Password");
            btn.setText("Create Family Account");
        } else {
            etfamilyac.setFocusable(false);
            etfamilyac.setText(information.get(UserSession.KEY_FUSERNAME));
        }

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!insert) {
                    validate_2();
                    insertToDatabase(etfamilyac.getText().toString(), etpassword_n.getText().toString(), information.get(UserSession.KEY_ID));
                    session.updateFamilyAccount(etfamilyac.getText().toString(), etpassword_n.getText().toString());
                } else {
                    validate();
                    insertToDatabase(etfamilyac.getText().toString(), etpassword.getText().toString(), information.get(UserSession.KEY_ID));
                    session.updateFamilyAccount(etfamilyac.getText().toString(), etpassword.getText().toString());
                }
                Toast.makeText(FamilyActivity.this, "Your Family Account is updated", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }


    public void insertToDatabase(String facc, String password, String userid){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                Log.d(params[0]+", " + params[1] + ", " + params[2], "doInBackground: ");

                String result = "";
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("familyaccname", params[0]));
                nameValuePairs.add(new BasicNameValuePair("familyaccpassword", params[1]));
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

        sendPostReqAsyncTask.execute(facc, password, userid);
    }

    public boolean validate() {
        boolean valid = true;

        String password = etpassword.getText().toString();
        String username = etfamilyac.getText().toString();

        if (username.isEmpty() || username.length() < 6) {
            etfamilyac.setError("at least 6 characters");
            valid = false;
        } else {
            etfamilyac.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            etpassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            etpassword.setError(null);
        }



        return valid;
    }

    public boolean validate_2 () {
        boolean valid = true;

        String password = etpassword.getText().toString();
        String username = etfamilyac.getText().toString();
        String newpassword = etpassword_n.getText().toString();
        String reEnterPassword = etpassword_c.getText().toString();

            if (password.isEmpty() || password.length() < 4 || password.length() > 10 || !(password.equals(information.get(UserSession.KEY_FPASSWORD)))) {
                etpassword.setError("Password Do not match");
                valid = false;
            } else {
                etpassword.setError(null);
            }

            if (newpassword.isEmpty() || newpassword.length() < 4 || newpassword.length() > 10) {
                etpassword_n.setError("between 4 and 10 alphanumeric characters");
                valid = false;
            } else {
                etpassword_n.setError(null);
            }

            if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(newpassword))) {
                etpassword_c.setError("Password Do not match");
                valid = false;
            } else {
                etpassword_c.setError(null);
            }
            return valid;

    }
}
