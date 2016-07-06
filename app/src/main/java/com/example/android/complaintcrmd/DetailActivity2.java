package com.example.android.complaintcrmd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.android.complaintcrmd.data.DBHelper;

public class DetailActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail2);
        Intent intent = this.getIntent();
        if(intent!=null&&intent.hasExtra("details")){
            String detailsText= intent.getStringExtra("details");
            int position=intent.getIntExtra("position",0);
            TextView tv=(TextView)findViewById(R.id.detailsView);
            tv.setText(detailsText+position);
        }
    }
    public void deleteEntry(View v){
        DBHelper db;
        db = new DBHelper(this);
        Intent intent = this.getIntent();
        int position=intent.getIntExtra("position",0);
        db.insertCompleted(intent.getStringExtra("details"),"abc");
        db.deletePending(position);
    }
}
