package pl.edu.agh.virtualassistant;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;

import pl.edu.agh.virtualassistant.service.WeatherService;
import pl.edu.agh.virtualassistant.voice.VoiceControl;

public class MainActivity extends AppCompatActivity {

    DecimalFormat temperatureFormat = new DecimalFormat("0.#");
    private VoiceControl voiceControl;

    private TextView tempTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions();

        voiceControl = new VoiceControl(getApplicationContext());
        voiceControl.setUp(
                bundle -> tempTextView.setText("Say the name of the city to hear the temperature that is currently in it."),
                this::getTemperatureForCity);

        tempTextView = findViewById(R.id.Temp);

    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            // necessary permission for voice control functions
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        }
    }


    private void getTemperatureForCity(String city) {
        WeatherService.getTemperatureForCity(MainActivity.this, city,
                temperature -> {
                    String response = "Temperature in " + city + " is " + temperatureFormat.format(temperature) + " degrees Celsius.";
                    voiceControl.say(response);
                    tempTextView.setText(response);
                },
                error -> voiceControl.say("I cannot check the temperature for this place."));
    }
}