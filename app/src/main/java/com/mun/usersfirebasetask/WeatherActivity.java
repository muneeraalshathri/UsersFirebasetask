package com.mun.usersfirebasetask;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherActivity extends AppCompatActivity {

    EditText etCityName;
    Button btnGetWeather;
    TextView tvWeatherData;

    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        etCityName = findViewById(R.id.et_city_name);
        btnGetWeather = findViewById(R.id.btn_get_weather);
        btnGetWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWeatherData();
            }
        });
        tvWeatherData = findViewById(R.id.tv_weather_data);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait.");


        getWeatherData();
    }

    void getWeatherData() {

        WeatherTask weatherTask = new WeatherTask();
        weatherTask.execute();

    }


    private class WeatherTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            String weatherUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + etCityName.getText().toString() + "&appid=62fc5df4cb5170d9281036ed0673b124&units=metric";

            try {

                URL url = new URL(weatherUrl);

                // Open url connection
                HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();

                // set request method
                httpConnection.setRequestMethod("GET");


                // open input stream and read server response data
                InputStream inputStream = httpConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String result = bufferedReader.readLine();


                httpConnection.disconnect();

                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            dialog.dismiss();

            if (s == null) {
                tvWeatherData.setText("Error in City Name");
                return;
            }

            // parsing json
            try {
                JSONObject rootJSON = new JSONObject(s);

                String weather = "Temperature : " + rootJSON.getJSONObject("main").getDouble("temp") +
                        "\n" +
                        "Humidity : " + rootJSON.getJSONObject("main").getDouble("humidity");

                tvWeatherData.setText(weather);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}