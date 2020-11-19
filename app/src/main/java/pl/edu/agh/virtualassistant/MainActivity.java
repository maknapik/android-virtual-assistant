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

import pl.edu.agh.virtualassistant.service.WeatherService;
import pl.edu.agh.virtualassistant.voice.VoiceControl;

import static pl.edu.agh.virtualassistant.avatar.animation.SimpleAnimation.getSimpleAnimation;

public class MainActivity extends AppCompatActivity {
    private static final DecimalFormat temperatureFormat = new DecimalFormat("0.#");
    private VoiceControl voiceControl;
    private RequestResolver requestResolver;
    private ImageView imageView;
    private TextView tempTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions();

        voiceControl = new VoiceControl(getApplicationContext());
        requestResolver = new RequestResolver(voiceControl, this);
        voiceControl.setUp(
                bundle -> tempTextView.setText("Say the name of the city to hear the temperature that is currently in it."),
                requestResolver::respondToRequest);

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


    public void startAvatarAnimation() {
        String output = tempTextView.getText().toString();
        AnimationDrawable anim = getSimpleAnimation(getResources(), output);

        anim.setOneShot(true);
        imageView.setImageDrawable(anim);
        anim.start();
    }
}