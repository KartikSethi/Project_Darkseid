package com.example.android.complaintcrmd;


import android.content.Intent;
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
public class PendingFragment extends Fragment {

    ArrayAdapter<String> pendingAdapter;
    DBHelper db;

    public PendingFragment() {
        // Required empty public constructor
    }

    public void updateList(){
//////////////////////////////////////////////////////////////////////////////
//        FetchListTask task=new FetchListTask();
//        task.execute();
        Toast.makeText(getActivity(),"Updating List",Toast.LENGTH_SHORT).show();

    }
    public void onStart(){
        super.onStart();
        updateList();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        String[] complaints= {
                "Complaint1",
                "complaint2",
                "Complaint3",
                "Complaint4"
        };
        db = new DBHelper(this.getContext());
        // Inflate the layout for this fragment

         db.insertPending("Cm1");

        Cursor cr = db.getListPending();

        List<String> complaintList= getTableRows(cr);
        //List<String> complaintList=new ArrayList<String>(Arrays.asList(complaints));
        pendingAdapter= new ArrayAdapter<String>(getActivity(),R.layout.list_view_item,R.id.textView,complaintList);

        View rootView=inflater.inflate(R.layout.fragment_pending, container, false);



        ListView listView = (ListView) rootView.findViewById(R.id.pendingList);
        listView.setAdapter(pendingAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getActivity(),"Entry number"+position,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getActivity(),DetailsActivity.class).putExtra("details","bla bla bla");
                startActivity(intent);
            }
        });
        return rootView;
    }
    public List<String> getTableRows(Cursor cursor) {
        List<String> result = new ArrayList<String>();

        //   cursor.moveToFirst();
        while (cursor.moveToNext()) {
            String columnValue = cursor.getString(0);
            result.add(columnValue);

        }


        cursor.close();
        return result;
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
