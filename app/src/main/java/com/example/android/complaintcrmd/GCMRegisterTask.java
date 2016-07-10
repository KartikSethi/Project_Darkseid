package com.example.android.complaintcrmd;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.complaintcrmd.data.DBHelper;
import com.google.android.gcm.GCMRegistrar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.example.android.complaintcrmd.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.example.android.complaintcrmd.CommonUtilities.SENDER_ID;

/**
 * Created by Dell on 10/07/2016.
 */
public class GCMRegisterTask extends AsyncTask<String,Void,String> {
    Context contx;
    Activity activity;
   ConnectionDetector cd;
    AsyncTask<Void, Void, Void> mRegisterTask;
    String id;
    AlertDialogManager alert = new AlertDialogManager();

    AlertDialog.Builder builder;  // to alert the user
    ProgressDialog progressDialog;  // to show the progress

    public GCMRegisterTask(Context contx) {
        this.contx = contx;
        activity = (Activity) contx;
    }


    @Override
    protected String doInBackground(String... params) {
        id = RegisterActivity.userid_fin;
        GCMRegistrar.checkDevice(contx);
        final String regId = GCMRegistrar.getRegistrationId(contx);
        if (regId.equals("")) {
            // Registration is not present, register now with GCM
            GCMRegistrar.register(contx, SENDER_ID);
            return "Registered with GCM";
        }
        else
        {
            // Device is already registered on GCM
            if (GCMRegistrar.isRegisteredOnServer(contx)) {
                // Skips registration

                return "Already Registered";
//                ServerUtilities.unregister(context,regId);
            } else {
                // Try to register again, but not in the UI thread.
                // It's also necessary to cancel the thread onDestroy(),
                // hence the use of AsyncTask instead of a raw thread.

                ServerUtilities.register(contx, regId, id);
                return "Register try succeeded";

            }
        }
    }

    @Override
    protected void onPreExecute() {  // to initialise the progress dialog

        //TelephonyManager tm=(TelephonyManager) contx.getSystemService(Context.TELEPHONY_SERVICE);
        //imei=tm.getDeviceId();
        cd = new ConnectionDetector(contx);

        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(activity,
                    "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    /*
     *@param here the parameter is some jason data
     * this method is used to decode that jason data(object)
     */
    @Override
    protected void onPostExecute(String output) {
        // Log.v("sdfsdf","Back");
        showDialog("GCM",output,"GCMreg");
    }



    /*
     * To analyse the message and to display the alert message
     */
    public void showDialog(String title, String message, String code) {
        builder.setTitle(title);
        if (code.equals("reg_true")) {

            builder.setMessage(message);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    Intent intent = new Intent(activity, Main2Activity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }
            });

        } else if (code.equals("reg_false")) {
            // if loginn fails then we need to empty the username and password fields
            builder.setMessage(message);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    activity.finish();
                }
            });
        } else if (code.equals("login_false")) {
            // if loginn fails then we need to empty the username and password fields
            builder.setMessage(message);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    EditText username, password;
                    username = (EditText) activity.findViewById(R.id.username_login);
                    password = (EditText) activity.findViewById(R.id.password_login);
                    username.setText("");
                    password.setText("");
                    dialogInterface.dismiss();
                }
            });
        } else if (code.equals("report_false")) {
            // if loginn fails then we need to empty the username and password fields
            builder.setMessage(message);
            builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    dialogInterface.dismiss();
                }
            });
        } else if (code.equals("report_true")) {
            // if loginn fails then we need to empty the username and password fields
            builder.setMessage(message);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    activity.finish();
                }
            });
        } else if (code.equals("submit_report_false")) {
            // if loginn fails then we need to empty the username and password fields
            builder.setMessage(message);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    dialogInterface.dismiss();
                }
            });
        } else if (code.equals("submit_report_true")) {
            // if loginn fails then we need to empty the username and password fields
            builder.setMessage(message);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    dialogInterface.dismiss();
                    activity.finish();
                }
            });
        }
        else
        {
            builder.setMessage(message);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    dialogInterface.dismiss();
                }
            });
        }

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
