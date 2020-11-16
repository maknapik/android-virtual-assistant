package pl.edu.agh.virtualassistant;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.util.List;

import pl.edu.agh.virtualassistant.avatar.animation.SimpleAnimation;
import pl.edu.agh.virtualassistant.avatar.utils.AvatarHelpers;
import pl.edu.agh.virtualassistant.service.WeatherService;
import pl.edu.agh.virtualassistant.voice.VoiceControl;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;

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

        imageView = findViewById(R.id.avatarImage);
        imageView.setBackgroundResource(R.drawable.mouth_1);
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
                    onStartAnimation();
                    tempTextView.setText(response);
                },
                error -> voiceControl.say("I cannot check the temperature for this place."));
    }

    private void onStartAnimation() {
        String output = tempTextView.getText().toString();
        AnimationDrawable anim = new AnimationDrawable();
        List<Integer> expressions = AvatarHelpers.getExpressions(output);
        for(Integer expression : expressions) {
            anim.addFrame(SimpleAnimation.getAnimationFrame(getResources(), expression), 200);
        }

        // wink anim
        anim.addFrame(SimpleAnimation.getAnimationFrame(getResources(), 1), 1000);
        anim.addFrame(SimpleAnimation.getAnimationFrame(getResources(),0), 150);
        anim.addFrame(SimpleAnimation.getAnimationFrame(getResources(),1), 100);

        anim.setOneShot(true);
        imageView.setImageDrawable(anim);
        anim.start();
    }
}