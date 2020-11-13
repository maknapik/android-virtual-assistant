package pl.edu.agh.virtualassistant;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import pl.edu.agh.virtualassistant.service.WeatherService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        TextView temp = findViewById(R.id.Temp);

        WeatherService.getTemperatureForCity(this, "Kraków", val -> {
            temp.setText(String.valueOf(val));
        });
    }

}