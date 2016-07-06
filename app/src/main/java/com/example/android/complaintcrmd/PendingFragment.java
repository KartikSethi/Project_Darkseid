package com.example.android.complaintcrmd;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.complaintcrmd.data.DBHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PendingFragment extends Fragment {

     static ArrayAdapter<String> pendingAdapter;
     static DBHelper db;
    static ListView listView;
    static List<String> complaintList;
    static List<String> complaintList2;
    static  ArrayAdapter<String> pendingAdapter2;
    public PendingFragment() {
        // Required empty public constructor
    }
    public void fetchFormServer(){
        ConnectionDetector cd=new ConnectionDetector(this.getContext());
        if(cd.isConnectingToInternet()) {
            BackgroundTask backgroundTask = new BackgroundTask(this.getContext());
            String username = "DMRCIT009";
            backgroundTask.execute("fetch_pending", username);
        }
        else{
            Toast.makeText(this.getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateList(){
        //TODO: update fetchinfo from database and update the list adapter
        //TODO: update fetch
        //fetchFormServer();
        complaintList= getTableRows("AreaID");
        pendingAdapter= new ArrayAdapter<String>(getActivity(),R.layout.list_view_item,R.id.areaID,complaintList);
        complaintList2= getTableRows("FaultSubType");
        pendingAdapter2= new ArrayAdapter<String>(getActivity(),R.layout.list_view_item,R.id.faultSubType,complaintList2);
        Log.e("the string", complaintList.toString()+"\n\n\n\n" +
                "\n" +
                "\n"+complaintList2.toString());
        listView.setAdapter(pendingAdapter);
        //listView.setAdapter(pendingAdapter2);
//        pendingAdapter.notifyDataSetChanged();
//        pendingAdapter2.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Toast.makeText(getActivity(),"Entry number"+position,Toast.LENGTH_SHORT).show();
                String str=getListItem(position);
                Intent intent=new Intent(getActivity(),DetailActivity2.class).putExtra("details",str);
                intent.putExtra("position",position);
                startActivity(intent);

            }
        });
    }
    public void onStart(){
        super.onStart();
        updateList();
        Toast.makeText(getActivity(), "Pending Work", Toast.LENGTH_SHORT).show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //TODO Background activity start!!
        DBHelper db;
//        db = new DBHelper(this.getContext());
//        db.insertPending("abc");
//        db.insertPending("degghijkl");
//        db.insertPending("zcvzv");
        //fetchFormServer();

        Toast.makeText(getActivity(),"Updating List",Toast.LENGTH_SHORT).show();
        View rootView=inflater.inflate(R.layout.fragment_pending, container, false);

        listView = (ListView) rootView.findViewById(R.id.pendingList);

        return rootView;
    }

    public List<String> getTableRows(String s) {
        List<String> result = new ArrayList<String>();
        DBHelper db;
        db = new DBHelper(this.getContext());
        Cursor cursor = db.getListPending();
        //   cursor.moveToFirst();
        try
        {
        while (cursor.moveToNext()) {
            String columnValue = cursor.getString(0);
            JSONObject jsonObject = new JSONObject(columnValue);

            result.add(jsonObject.getString(s));
        }}catch (Exception e){}
        cursor.close();
        return result;
    }
    public String getListItem(int position){

        DBHelper db;
        db = new DBHelper(this.getContext());
        Cursor cursor = db.getListPending();
        cursor.moveToPosition(position);
        return cursor.getString(0);

    }

//    public class FetchListTask extends AsyncTask<Void, Void, String> {
//
//        private final String LOG_TAG = FetchListTask.class.getSimpleName();
//
//
//
//        @Override
//        protected String doInBackground(Void... params) {
//
//            // If there's no zip code, there's nothing to look up. Verify params.
//
//
//            // These two need to be declared outside the try/catch
//            // so that they can be closed in the finally block.
//            HttpURLConnection urlConnection = null;
//            BufferedReader reader = null;
//
//            // Will contain the raw JSON response as a string.
//            String forecastJsonStr = null;
//
//            String format = "json";
//            String units = "metric";
//            int numDays = 7;
//
//            try {
//                // Construct the URL for the OpenWeatherMap query
//                // Possible parameters are available at OWM's forecast API page, at
//                // http://openweathermap.org/API#forecast
//
//                String urlString="";
//                URL url = new URL(urlString);
//
//                Log.v(LOG_TAG, "Built URI: " + "");
//
//                // Create the request to OpenWeatherMap, and open the connection
//                urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setRequestMethod("GET");
//                urlConnection.connect();
//
//                // Read the input stream into a String
//                InputStream inputStream = urlConnection.getInputStream();
//                StringBuffer buffer = new StringBuffer();
//                if (inputStream == null) {
//                    // Nothing to do.
//                    return null;
//                }
//                reader = new BufferedReader(new InputStreamReader(inputStream));
//
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
//                    // But it does make debugging a *lot* easier if you print out the completed
//                    // buffer for debugging.
//                    buffer.append(line + "\n");
//                }
//
//                if (buffer.length() == 0) {
//                    // Stream was empty.  No point in parsing.
//                    return null;
//                }
//                forecastJsonStr = buffer.toString();
//
//                Log.v(LOG_TAG, "Forecast JSON String: " + forecastJsonStr);
//
//            } catch (Exception e) {
//                Log.e(LOG_TAG, "Error ", e);
//                // If the code didn't successfully get the weather data, there's no point in attempting
//                // to parse it.
//                return null;
//            } finally {
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//                if (reader != null) {
//                    try {
//                        reader.close();
//                    } catch (final Exception e) {
//                        Log.e(LOG_TAG, "Error closing stream", e);
//                    }
//                }
//            }
//
//            return forecastJsonStr;
//        }
//
//
//
//        @Override
//        protected void onPostExecute(String result) {
//            if (result != null) {
//                pendingAdapter.clear();
////                for (String dayForecastStr : result) {
////                    mForecastAdapter.add(dayForecastStr);
////                }
//                pendingAdapter.add(result);
//                pendingAdapter.notifyDataSetChanged();
//            }
//        }
//    }

}
