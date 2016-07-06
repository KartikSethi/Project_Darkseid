package com.example.android.complaintcrmd;

/**
 * Created by Kartik Sethi on 21-Jun-16.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

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


public class BackgroundTask extends AsyncTask<String, Void, String> {
    // first argument tell us that parameter type is String
    Context contx;
    Activity activity;
    //String imei="";
    String form_retrieval_url = "http://2f1a2ffe.ngrok.io/ComplaintPortal/report/get_time_area_app";
    String form_submit_url = "http://2f1a2ffe.ngrok.io/ComplaintPortal/report/submit_report_app";
    String register_url = "http://2f1a2ffe.ngrok.io/ComplaintPortal/home/register";                        //"http://61.246.165.5/GPSAttendance/welcome/register"; // (name of the site) "http://192.168.X.X(ip of my comp or any other site)/directory name/php script
    String login_url = "http://2f1a2ffe.ngrok.io/ComplaintPortal/home/login_app";//"http://61.246.165.5/GPSAttendance/welcome/login";
    //    String gps_url = "http://192.168.252.50/GPSAttendance/welcome/report";//"http://61.246.165.5/GPSAttendance/welcome/report";
    AlertDialog.Builder builder;  // to alert the user
    ProgressDialog progressDialog;  // to show the progress

    public BackgroundTask(Context contx) {
        this.contx = contx;
        activity = (Activity) contx;
    }

    @Override
    protected void onPreExecute() {  // to initialise the progress dialog

        //TelephonyManager tm=(TelephonyManager) contx.getSystemService(Context.TELEPHONY_SERVICE);
        //imei=tm.getDeviceId();
        builder = new AlertDialog.Builder(activity); // alert dialog box for the context which has called the BackgroundTask.java (Register/Login)
        progressDialog = new ProgressDialog(contx);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Connecting to server....");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String method = params[0]; // determines whether the task is "register" or "login" as it will be the first parameter of the @param array. See register activity's last lines.
        if (method.equals("login")) { // if params[0] is equal to "login"
            /*
             *to send the username and password to the server and get the response from the server
             * if the response is positive we will transit to the home activity
             * otherwise we need to display an alertDialog
             */

            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String userid, password;
                userid = params[1];
                password = params[2];
                Log.v("checking:", "before sending");
                String data = URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(userid, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
//                        URLEncoder.encode("imei", "UTF-8") + "=" + URLEncoder.encode(params[3], "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();   // output stream has been used to send data to the server
                Log.v("checking:", "after writing");
                /*
                 * Now the response from the server will be in the form of json and we need to decode it
                 */
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";  // just a variable to read data from each line
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                httpURLConnection.disconnect();
                try {
                    Thread.sleep(500); // to give a pause for the "connecting to  the server" effect
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.v("checking:", "before retrieving message");
                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                Log.v("checking:", ",mal u");
                e.printStackTrace();
            } catch (ProtocolException e) {
                Log.v("checking:", "proto");
                e.printStackTrace();
            } catch (IOException e) {
                Log.v("checking:", "io");
                e.printStackTrace();
            }


        } else if (method.equals("register")) { // if params[0] is equal to "login"
            /*
             *to send the username and password to the server and get the response from the server
             * if the response is positive we will transit to the home activity
             * otherwise we need to display an alertDialog
             */

            try {
                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String name, email, userid, pass, type;
                Log.v("checking:", "before sending");
                String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8") + "&" +
                        URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(params[3], "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(params[4], "UTF-8") + "&" +
                        URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&" +
                        URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(params[5], "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();   // output stream has been used to send data to the server
                Log.v("checking:", "after writing");
                /*
                 * Now the response from the server will be in the form of json and we need to decode it
                 */
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";  // just a variable to read data from each line
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                httpURLConnection.disconnect();
                try {
                    Thread.sleep(500); // to give a pause for the "connecting to  the server" effect
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.v("checking:", "before retrieving message");
                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                Log.v("checking:", ",mal u");
                e.printStackTrace();
            } catch (ProtocolException e) {
                Log.v("checking:", "proto");
                e.printStackTrace();
            } catch (IOException e) {
                Log.v("checking:", "io");
                e.printStackTrace();
            }


        } else if (method.equals("form_retrieval")) {

            try {
                URL url = new URL(form_retrieval_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(LoginActivity.userid, "UTF-8");
//                        URLEncoder.encode("imei", "UTF-8") + "=" + URLEncoder.encode(params[3], "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();   // output stream has been used to send data to the server
                /*
                 * Now the response from the server will be in the form of json and we need to decode it
                 */
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";  // just a variable to read data from each line
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                httpURLConnection.disconnect();
                try {
                    Thread.sleep(500); // to give a pause for the "connecting to  the server" effect
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (method.equals("form_output")) { // if params[0] is equal to "login"
            /*
             *to send the username and password to the server and get the response from the server
             * if the response is positive we will transit to the home activity
             * otherwise we need to display an alertDialog
             */
            try {
                URL url = new URL(form_submit_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String userid, faulttype, subfault, comments;
                userid = LoginActivity.userid;
                faulttype = params[1];
                subfault = params[2];
                comments = params[3];
                String data = URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(userid, "UTF-8") + "&" +
                        URLEncoder.encode("comment", "UTF-8") + "=" + URLEncoder.encode(comments, "UTF-8") + "&" +
                        URLEncoder.encode("subfault", "UTF-8") + "=" + URLEncoder.encode(subfault, "UTF-8") + "&" +
                        URLEncoder.encode("faulttype", "UTF-8") + "=" + URLEncoder.encode(faulttype, "UTF-8");
//
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();   // output stream has been used to send data to the server
                /*
                 * Now the response from the server will be in the form of json and we need to decode it
                 */
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";  // just a variable to read data from each line
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                httpURLConnection.disconnect();
                try {
                    Thread.sleep(500); // to give a pause for the "connecting to  the server" effect
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
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
    protected void onPostExecute(String json) {
        // Log.v("sdfsdf","Back");
        if (json == null) {
            String error_message = "Some Error has occurred. Please check you internet connection.";
            //showDialog("Login failed", error_message, "net_fail");
            Toast.makeText(this.activity, error_message, Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        } else {
            try {
                // first we need to dismiss the
                //String str=json;
                JSONObject jsonObject = new JSONObject(json); // Output String parsed as a JSON object
                JSONArray jsonArray = jsonObject.getJSONArray("server_response"); // see welcome.php for server_response explanation
                // to get each of the json data from the json array. A JSON array can contain multiple JSON objects.

            /*
             * 0 is the index as our final JSON array which is being echoed from the php script
             * It has value either {"server_response" :{"code":"reg_true", "message":"Hurray!"}} or {"server_response" :{"code":"reg_false", "message":"Failed!"}}
             */
                JSONObject JO = jsonArray.getJSONObject(0);
                // this will give us the json object = {"code":"reg_true", "message":"Hurray!"} or {"code":"reg_false", "message":"Failed!"}
                // now we can read respective values from the key:value pairs for the above jason object
                // there are two data, code and message from the server as defined in our php script

                String code = JO.getString("code");  // code as the key, getString will return the value from the key:value pair
                // message as the key, getString will return the value from the key:value pair
                if (code.equals("reg_true")) // reg_true that means registration success, so corresponding message will be displayed: (here) it is a random string ("Hurray!") (to be changed)
                {
                    String message = JO.getString("message");
                    showDialog("Registration Success", message, code);
                } else if (code.equals("reg_false"))// reg_false that means registration failure, so corresponding message will be displayed: (here) it is a random string ("Failed!") (to be changed)
                {
                    String message = JO.getString("message");
                    showDialog("Registration Failed", message, code);
                    progressDialog.dismiss();
                } else if (code.equals("login_true")) {
                    progressDialog.dismiss();
                    String type = JO.getString("usertype");
                    Log.v("checkin:", type);
                    if (type.equals("Repair")) {
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    } else {
                        Intent intent = new Intent(activity, Drawer_Activity_Station.class);
                        activity.startActivity(intent);
                    }

//                    // to attach message to the intent from  the server
//                    intent.putExtra("message", message);
//                    activity.startActivity(intent);

                } else if (code.equals("login_false")) {
                    String message = JO.getString("message");
                    showDialog("Login Error", message, code);
                    progressDialog.dismiss();
//                } else if (code.equals("report_true")) {
//
//                    showDialog("Submission Successful!", message, code);
//                    progressDialog.dismiss();
//                } else if (code.equals("report_false")) {
//                    showDialog("Submission error!", message, code);
//                    progressDialog.dismiss();
                } else if (code.equals("time_area_true")) {
                    progressDialog.dismiss();
//                    String message = JO.getString("message");
                    String time = JO.getString("time");
                    String station = JO.getString("area");
                    String date = JO.getString("date");
//                    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
//                    sdfDate.setTimeZone(java.util.TimeZone.getTimeZone("IST"));
//                    SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm:ss");
//                    sdfTime.setTimeZone(java.util.TimeZone.getTimeZone("IST"));
//                    String dateStr = sdfDate.format(time);
//                    String timeStr = sdfTime.format(time);

//                  Write the code for extracting each of the three items
                    FormActivity.stationFill.setText(station);
                    FormActivity.timeFill.setText(time);
                    FormActivity.dateFill.setText(date);
                } else if (code.equals("submit_report_true")) {
                    Log.v("CODE", code);
                    progressDialog.dismiss();
                    String message = JO.getString("message");
                    showDialog("Complaint Reported", message, code);
                } else if (code.equals("submit_report_false")) {
                    progressDialog.dismiss();
                    String message = JO.getString("message");
                    showDialog("Error in reporting", message, code);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}


