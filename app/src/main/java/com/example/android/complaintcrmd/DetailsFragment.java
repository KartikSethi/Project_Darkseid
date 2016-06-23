package com.example.android.complaintcrmd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsFragment extends Fragment {

    public DetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_details, container, false);
        Intent intent = getActivity().getIntent();
        if(intent!=null&&intent.hasExtra("details")){
            String detailsText= intent.getStringExtra("details");
            TextView tv=(TextView)rootView.findViewById(R.id.detailsView);
            tv.setText(detailsText);
        }


        return rootView;
    }
}
