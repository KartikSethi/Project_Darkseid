package com.example.android.complaintcrmd;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.complaintcrmd.data.DBHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompletedFragment extends Fragment {
    public static ArrayAdapter<String> pendingAdapter;
    DBHelper db;
    public ListView listView;
    public CompletedFragment() {
        // Required empty public constructor
    }
    public void updateList(){

        //TODO: update fetchinfo from database and update the list adapter
        //TODO: update fetch
        List<String> complaintList= getTableRows();
        pendingAdapter= new ArrayAdapter<String>(getActivity(),R.layout.list_view_item,R.id.areaID,complaintList);

        listView.setAdapter(pendingAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getActivity(),"Entry number"+position,Toast.LENGTH_SHORT).show();
//                String str=getListItem(position);
//                Intent intent=new Intent(getActivity(),DetailActivity2.class).putExtra("details",str);
//                intent.putExtra("position",position);
//                startActivity(intent);

            }
        });


    }
    public void onStart(){
        super.onStart();
        updateList();
        Toast.makeText(getActivity(), "Completed Work", Toast.LENGTH_SHORT).show();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Toast.makeText(getActivity(),"Updating List",Toast.LENGTH_SHORT).show();
        View rootView=inflater.inflate(R.layout.fragment_completed, container, false);
        listView = (ListView) rootView.findViewById(R.id.pendingList);

        return rootView;
    }
    public List<String> getTableRows() {
        List<String> result = new ArrayList<String>();
        DBHelper db;
        db = new DBHelper(this.getContext());
        Cursor cursor = db.getListCompleted();
        //   cursor.moveToFirst();
        while (cursor.moveToNext()) {
            String columnValue = cursor.getString(0);
            result.add(columnValue);
        }
        cursor.close();
        return result;
    }

}
