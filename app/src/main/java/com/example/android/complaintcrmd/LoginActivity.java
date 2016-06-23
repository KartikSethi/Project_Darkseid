package com.example.android.complaintcrmd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


public class LoginActivity extends AppCompatActivity {

    private TextView signup_text;
    private EditText Username, Pass;
    private Button login_button;
    private AlertDialog.Builder builder;
    private String imei = "";
    private String username, password;
    //    private Button ok;
//    private EditText editTextUsername,editTextPassword;
    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signup_text = (TextView) findViewById(R.id.sign_up);
        signup_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ScrollingActivity.class));
            }
        });

        Username = (EditText) findViewById(R.id.username_login);
        Pass = (EditText) findViewById(R.id.password_login);
        login_button = (Button) findViewById(R.id.login_button);

//        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        saveLoginCheckBox = (CheckBox) findViewById(R.id.saveLoginCheckBox);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            Username.setText(loginPreferences.getString("username", ""));
            Pass.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
        }
    }

//    public void onClick(View view) {
//        if (view == login_button) {
//
//
//        }
//    }

//        login_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (Username.getText().toString().equals("") || Pass.getText().toString().equals("")) {
//                    builder = new AlertDialog.Builder(LoginActivity.this);
//                    builder.setTitle("Something went wrong");
//                    builder.setMessage("Please fill both the fields");
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            dialogInterface.dismiss();
//                        }
//                    });
//                    AlertDialog alertDialog = builder.create();
//                    alertDialog.show();
//                } else {
////                    BackgroundTask backgroundTask = new BackgroundTask(LoginActivity.this);
//                    TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//                    imei = tm.getDeviceId();
//                    /*
//                     * AsyncTask has the form AsyncTask<Param, Progress, Return> with execute using the Param type.
//                     * so here we are sending the @Params to the Background task with all the parameters as strings
//                     */
//
////                    backgroundTask.execute("login", Username.getText().toString(), Pass.getText().toString(), imei);
//                    /*
//                     * here login is arg[0], Username.getText().toString() is arg[1] and so on.
//                     */
//
//                }
//
//            }
//        });
//
//
//        final AlertDialog.Builder builder =
//                new AlertDialog.Builder(this);
//        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
//        final String message = "Enable Location";
//
//        builder.setMessage(message)
//                .setPositiveButton("OK",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface d, int id) {
////                                this.startActivity(new Intent(action));
//                                Intent i = new Intent(action);
//                                startActivity(i);
//                                d.dismiss();
//                            }
//                        })
//                .setNegativeButton("Cancel",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface d, int id) {
//                                d.cancel();
//                            }
//                        });
//        builder.create().show();
//
//
//    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

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

    private void attemptLogin() {


        // Reset errors.
        Username.setError(null);
        Pass.setError(null);

        // Store values at the time of the login attempt.
        String username = Username.getText().toString();
        String password = Pass.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            Pass.setError(getString(R.string.error_invalid_password));
            focusView = Pass;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            Username.setError(getString(R.string.error_field_required));
            focusView = Username;
            cancel = true;
        }
//        else if (!isEmailValid(username)) {
//            Username.setError(getString(R.string.error_invalid_email));
//            focusView = Username;
//            cancel = true;
//        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(Username.getWindowToken(), 0);

            username = Username.getText().toString();
            password = Pass.getText().toString();

            if (saveLoginCheckBox.isChecked()) {
                loginPrefsEditor.putBoolean("saveLogin", true);
                loginPrefsEditor.putString("username", username);
                loginPrefsEditor.putString("password", password);
                loginPrefsEditor.commit();
            } else {
                loginPrefsEditor.clear();
                loginPrefsEditor.commit();
            }
            Intent i = new Intent(LoginActivity.this, FormActivity.class);
            startActivity(i);
//            finish();
//            System.exit(0);
        }
// else {
//            // Show a progress spinner, and kick off a background task to
//            // perform the user login attempt.
//            showProgress(true);
//            mAuthTask = new UserLoginTask(email, password);
//            mAuthTask.execute((Void) null);
//        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

}


