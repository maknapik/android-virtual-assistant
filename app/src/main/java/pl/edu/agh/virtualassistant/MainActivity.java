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
import pl.edu.agh.virtualassistant.chat.AssetsResolver;
import pl.edu.agh.virtualassistant.chat.ChatBot;
import pl.edu.agh.virtualassistant.interaction.ResponseResolver;
import pl.edu.agh.virtualassistant.voice.VoiceControl;

import static pl.edu.agh.virtualassistant.avatar.animation.SimpleAnimation.getSimpleTalkingAnimation;

public class MainActivity extends AppCompatActivity {

    private VoiceControl voiceControl;
    private ResponseResolver responseResolver;
    private ImageView imageView;
    private TextView tempTextView;
    private TextView tempTextView2;
    private Handler handler;
    private AnimationDrawable avatarAnimation;
    private ChatBot chatBot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler();

        tempTextView = findViewById(R.id.Temp);
        tempTextView2 = findViewById(R.id.Temp2);

        requestPermissions();

        chatBot = initAlice();
        voiceControl = new VoiceControl(this);
        responseResolver = new ResponseResolver(voiceControl, this, chatBot);
        voiceControl.setUp(
                bundle -> tempTextView.setText("Listening"),
                responseResolver::respond,
                tempTextView, tempTextView2);

        imageView = findViewById(R.id.avatarImage);
        imageView.setBackgroundResource(R.drawable.mouth_1a);

    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            // necessary permission for voice control functions
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // necessary permission for location-related functions
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    public void startAvatarAnimation(String textToSpeak) {
        avatarAnimation = getSimpleTalkingAnimation(getResources(), textToSpeak);

        avatarAnimation.setOneShot(false);
        imageView.setImageDrawable(avatarAnimation);
        avatarAnimation.start();
    }

    public void stopAvatarAnimation() {
        if (avatarAnimation != null) {
            avatarAnimation.stop();
        }
        imageView.setImageResource(R.drawable.mouth_1a);
    }

    public void runInMainThread(Runnable runnable) {
        handler.post(runnable);
    }

    private ChatBot initAlice() {
        String ms = AssetsResolver.init(getResources(), getCacheDir());
        return new ChatBot(ms);
    }
}