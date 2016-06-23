package com.example.android.complaintcrmd;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FormActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    private String[] arraySpinner1;
    Spinner spinner1, spinner2;
    private String[] arraySpinner2;
    CoordinatorLayout coordinatorLayout;
    EditText comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        comment = (EditText) findViewById(R.id.comment_box);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);

        this.arraySpinner1 = new String[]{
                "None", "Telecom", "Automatic Fare Collection (AFC)"
        };

        ArrayAdapter<String> adapter_spinner1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner1);
        spinner1.setAdapter(adapter_spinner1);
        spinner1.setOnItemSelectedListener(this);

    }

//        if (spinner1_text.equals("Telecom")) {
//            this.arraySpinner2 = new String[]{
//                    "Telephone System", "Train Radio", "Tetra System", "Station Radio System", "Other (Please Specify in Comments)"
//            };
//
//            ArrayAdapter<String> adapter_spinner2 = new ArrayAdapter<String>(this, // second constructor ArrayAdapter(Context context, int resource, int textViewResourceId)
//                    android.R.layout.simple_spinner_item, arraySpinner2);
//            spinner1.setAdapter(adapter_spinner2);
//        } else if (spinner1_text.equals("Automatic Fare Collection (AFC)")) {
//            this.arraySpinner2 = new String[]{
//                    "Add Value Machine", "GATE", "Ticket Operating Machine (TOM)", "Ticket Operation Machine (TOM)", "Other (Please Specify in Comments)"
//            };
//
//            ArrayAdapter<String> adapter_spinner2 = new ArrayAdapter<String>(this, // second constructor ArrayAdapter(Context context, int resource, int textViewResourceId)
//                    android.R.layout.simple_spinner_item, arraySpinner2);
//            spinner1.setAdapter(adapter_spinner2);
//        }
//
//
//        while (spinner1_text.equals("None")) {
//                Snackbar snackbar = Snackbar.make(coordinatorLayout, "Fault Type Empty", Snackbar.LENGTH_INDEFINITE);
//                snackbar.show();
//            }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub
        String spinner1_text = String.valueOf(spinner1.getSelectedItem());
        Toast.makeText(this, spinner1_text, Toast.LENGTH_SHORT).show();
        if (spinner1_text.contentEquals("Telecom")) {
            Log.v("TEsting", "here");
            List<String> list = new ArrayList<String>();
            list.add("Telephone System");
            list.add("Train Radio");
            list.add("Tetra System");
            list.add("Station Radio System");
            list.add("Train Radio");
            list.add("Others(Please Specify in Comments)");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            spinner2.setAdapter(dataAdapter);
        }
        if (spinner1_text.contentEquals("Automatic Fare Collection (AFC)")) {
            List<String> list = new ArrayList<String>();
            list.add("Add Value Machine");
            list.add("GATE");
            list.add("Ticket Operating Machine (TOM)");
            list.add("Ticket Vending Machine (TVM)");
            list.add("Other(Please Specify in Comments)");
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            spinner2.setAdapter(dataAdapter2);
        }

        if (spinner1_text.contentEquals("None")) {
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Please select a type", Snackbar.LENGTH_LONG);
            snackbar.show();
            List<String> list = new ArrayList<String>();
            list.add("None");
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            spinner2.setAdapter(dataAdapter2);
        }

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void clickable(View view) {
        finish();
        System.exit(0);
    }
}

