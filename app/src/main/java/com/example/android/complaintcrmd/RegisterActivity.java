package com.example.android.complaintcrmd;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.example.android.complaintcrmd.CommonUtilities.SENDER_ID;
import static com.example.android.complaintcrmd.CommonUtilities.SERVER_URL;

public class RegisterActivity extends AppCompatActivity {

    private EditText Username, Pass, ConPass, Name, Email;
    protected static String name_fin, email_fin;
    private Button reg_button;
    private AlertDialog.Builder builder;
    AlertDialogManager alert = new AlertDialogManager();
    ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Name = (EditText) findViewById(R.id.name_reg);
        Email = (EditText) findViewById(R.id.email_reg);
        Username = (EditText) findViewById(R.id.username_reg);
        Pass = (EditText) findViewById(R.id.password_reg);
        ConPass = (EditText) findViewById(R.id.con_password_reg);
        reg_button = (Button) findViewById(R.id.register_button);

        cd = new ConnectionDetector(getApplicationContext());

        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(RegisterActivity.this,
                    "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }

        // Check if GCM configuration is set
        if (SERVER_URL == null || SENDER_ID == null || SERVER_URL.length() == 0
                || SENDER_ID.length() == 0) {
            // GCM sernder id / server url is missing
            alert.showAlertDialog(RegisterActivity.this, "Configuration Error!",
                    "Please set your Server URL and GCM Sender ID", false);
            // stop executing code by return
            return;
        }

        /*
         * code to set up font for text views
         */
        //     TextView register_header = (TextView) findViewById(R.id.register_header);
        // Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/Roboto-BlackItalic.ttf");

        //  register_header.setTypeface(typeFace);



        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Name.getText().toString().equals("") || Email.getText().toString().equals("") || Username.getText().toString().equals("") || Pass.getText().toString().equals("")) {
                    builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("Something went wrong");
                    builder.setMessage("Please fill all the fields");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else if (!(Pass.getText().toString().equals(ConPass.getText().toString()))) {
                    builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("Something went wrong");
                    builder.setMessage("Your password fields are not matching");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            Pass.setText("");
                            ConPass.setText("");
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else if (!isValidEmail(Email.getText().toString().trim())) {
                    builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("Something went wrong");
                    builder.setMessage("Invalid Email");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
//                    TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//                    String imei = tm.getDeviceId();
                    BackgroundTask backgroundTask = new BackgroundTask(RegisterActivity.this);
                    /*
                     * AsyncTask has the form AsyncTask<Param, Progress, Return> with execute using the Param type.
                     * so here we are sending the @Params to the Background task with all the parameters as strings
                     */
                    name_fin = Name.getText().toString();
                    email_fin = Email.getText().toString();
                    backgroundTask.execute("register", Name.getText().toString(), Email.getText().toString(), Username.getText().toString(), Pass.getText().toString());
                    /*
                     * here register is arg[0], Name.getText().toString() is arg[1] and so on.
                     */


                }

            }
        });
    }

    public final static boolean isValidEmail(String target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

}
