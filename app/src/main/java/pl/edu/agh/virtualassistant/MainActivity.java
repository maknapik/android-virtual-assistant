package pl.edu.agh.virtualassistant;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import pl.edu.agh.virtualassistant.service.WeatherService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        WeatherService.getCurrentWeatherForCity(this, "Prague");
    }

}