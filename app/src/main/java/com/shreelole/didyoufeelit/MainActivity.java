package com.shreelole.didyoufeelit;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-05-02&minfelt=50&minmagnitude=5";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Perform the HTTP request for earthquake data and process the response.

        new My_Asynk().execute(USGS_REQUEST_URL);
        // Update the information displayed to the user.

    }
    private void updateUi(Event earthquake) {
        TextView titleTextView = (TextView) findViewById(R.id.title);
        titleTextView.setText(earthquake.title);

        TextView tsunamiTextView = (TextView) findViewById(R.id.number_of_people);
        tsunamiTextView.setText(getString(R.string.num_people_felt_it, earthquake.numOfPeople));

        TextView magnitudeTextView = (TextView) findViewById(R.id.perceived_magnitude);
        magnitudeTextView.setText(earthquake.perceivedStrength);
    }
    private class My_Asynk extends AsyncTask<String , Void  , Event>
    {

        protected Event doInBackground(String... string) {
            if (string.length < 1 || string[0] == null) {
                return null;
            }

            Event earthquake = Utils.fetchEarthquakeData(string[0]);

            return earthquake;
        }

        @Override
        protected void onPostExecute(Event event) {
            updateUi(event);
        }
    }
}
