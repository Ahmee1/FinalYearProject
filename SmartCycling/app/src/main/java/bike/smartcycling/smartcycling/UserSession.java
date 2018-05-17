package bike.smartcycling.smartcycling;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserSession {
    // Shared Preferences reference
    SharedPreferences pref;

    // Editor reference for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared preferences mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    public static final String PREFER_NAME = "session";

    // All Shared Preferences Keys
    public static final String IS_USER_LOGIN = "IsUserLoggedIn";

    public static final String KEY_ID = "ID";

    // First name (make variable public to access from outside)
    public static final String KEY_FNAME = "FName";

    // Last name (make variable public to access from outside)
    public static final String KEY_LNAME = "LName";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "Email";

    // Phone Number (make variable public to access from outside)
    public static final String KEY_PHONE = "Phone";

    // Login Name (make variable public to access from outside)
    public static final String KEY_USERNAME = "Username";

    // password
    public static final String KEY_PASSWORD = "txtPassword";

    // Family Login Name (make variable public to access from outside)
    public static final String KEY_FUSERNAME = "FamilyUsername";

    // Family password
    public static final String KEY_FPASSWORD = "FamilytxtPassword";

    // Constructor
    public UserSession(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create login session
    public void createUserLoginSession(String id, String uUsername, String uPassword, String uFName, String uLName, String uEmail, String uPhone, String uFUsername, String uFPassword) {
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        editor.putString(KEY_ID, id);

        // Storing user login name in preferences
        editor.putString(KEY_USERNAME, uUsername);

        // Storing user password in preferences
        editor.putString(KEY_PASSWORD, uPassword);

        // Storing name in preferences
        editor.putString(KEY_FNAME, uFName);
        editor.putString(KEY_LNAME, uLName);

        // Storing email in preferences
        editor.putString(KEY_EMAIL, uEmail);

        // Storing mobile phone in preferences
        editor.putString(KEY_PHONE, uPhone);

        // Storing family login name in preferences
        editor.putString(KEY_FUSERNAME, uFUsername);

        // Storing family password in preferences
        editor.putString(KEY_FPASSWORD, uFPassword);

        // commit changes
        editor.commit();
    }

    public void updateFamilyAccount(String uFUsername, String uFPassword) {
        // Storing family login name in preferences
        editor.putString(KEY_FUSERNAME, uFUsername);

        // Storing family password in preferences
        editor.putString(KEY_FPASSWORD, uFPassword);
        // commit changes
        editor.commit();
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     */
    public boolean checkLogin() {
        // Check login status
        if (!this.isUserLoggedIn()) {

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

            return true;
        }
        return false;
    }


    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_ID, pref.getString(KEY_ID, null));

        // user name
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));

        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));

        // user account information
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        user.put(KEY_FNAME, pref.getString(KEY_FNAME, null));

        user.put(KEY_LNAME, pref.getString(KEY_LNAME, null));

        user.put(KEY_PHONE, pref.getString(KEY_PHONE, null));

        user.put(KEY_FUSERNAME, pref.getString(KEY_FUSERNAME, null));

        user.put(KEY_FPASSWORD, pref.getString(KEY_FPASSWORD, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to MainActivity
        Intent i = new Intent(_context, MainActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }


    // Check for login
    public boolean isUserLoggedIn() {
        return pref.getBoolean(IS_USER_LOGIN, false);
    }
}