package pl.edu.agh.virtualassistant;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;

import pl.edu.agh.virtualassistant.voice.VoiceControl;

import static pl.edu.agh.virtualassistant.avatar.animation.SimpleAnimation.getSimpleTalkingAnimation;

public class MainActivity extends AppCompatActivity {
    private static final DecimalFormat temperatureFormat = new DecimalFormat("0.#");
    private VoiceControl voiceControl;
    private RequestResolver requestResolver;
    private ImageView imageView;
    private TextView tempTextView;
    private Handler handler;
    private AnimationDrawable avatarAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler();
        requestPermissions();

        voiceControl = new VoiceControl(this);
        requestResolver = new RequestResolver(voiceControl, this);
        voiceControl.setUp(
                bundle -> tempTextView.setText("Ask for temperature or humidity in a city of your choosing."),
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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // necessary permission for voice control functions
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    public void startAvatarAnimation() {
        avatarAnimation = getSimpleTalkingAnimation(getResources());

        avatarAnimation.setOneShot(false);
        imageView.setImageDrawable(avatarAnimation);
        avatarAnimation.start();
    }

    public void stopAvatarAnimation() {
        if (avatarAnimation != null) {
            avatarAnimation.stop();
        }
        imageView.setImageResource(R.drawable.mouth_1);
    }

    public void runInMainThread(Runnable runnable) {
        handler.post(runnable);
    }
}