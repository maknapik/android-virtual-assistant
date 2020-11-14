package pl.edu.agh.virtualassistant;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.function.Consumer;

import pl.edu.agh.virtualassistant.service.WeatherService;
import pl.edu.agh.virtualassistant.voice.RecognitionListenerImpl;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech textToSpeech;
    private SpeechRecognizer speechRecognizer;
    private final Locale locale = Locale.ENGLISH;
    private Intent speechRecognizerIntent;
    DecimalFormat temperatureFormat = new DecimalFormat("#.#");

    private TextView tempTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        requestPermissions();
        boolean voiceControlSetUpSucceeded = setUpVoiceControl(
                bundle -> tempTextView.setText("Say the name of the city to hear the temperature that is currently in it."),
                this::getTemperatureForCity);

        tempTextView = findViewById(R.id.Temp);
        if (!voiceControlSetUpSucceeded) {
            tempTextView.setText("Voice control isn't available on this device. The application will not work.");
        }

    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            // necessary permission for voice control functions
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        }
    }

    /**
     * Prepares the application for receiving voice commands and answering them.
     *
     * @return true if set up succeeded, false otherwise - for example when speech recognition isn't available on the device.
     */
    private boolean setUpVoiceControl(Consumer<Bundle> readyForSpeech, Consumer<String> onRecognitionResult) {
        textToSpeech = new TextToSpeech(getApplicationContext(), status -> {
            if (status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(locale);
            } else {
                System.out.println("Error on setting up text to speech.");
            }
        });
        if (!SpeechRecognizer.isRecognitionAvailable(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "Speech recognition is not available on this device. The application will not work", Toast.LENGTH_LONG).show();
            System.out.println("Speech recognition is not available on this device. The application will not work");
            return false;
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
        speechRecognizer.setRecognitionListener(new RecognitionListenerImpl() {
            @Override
            public void onError(int error) {
                if (error == SpeechRecognizer.ERROR_NO_MATCH) {
                    // ignore noise
                    speechRecognizer.stopListening();
                    startListening();
                } else {
                    System.out.println("Error in speech recognition. Code: " + error);
                }
            }

            @Override
            public void onReadyForSpeech(Bundle params) {
                readyForSpeech.accept(params);
            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                onRecognitionResult.accept(data.get(0));
            }
        });

        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);

        startListening();
        return true;
    }

    private void getTemperatureForCity(String city) {
        WeatherService.getTemperatureForCity(MainActivity.this, city,
                temperature -> {
                    String response = "Temperature in " + city + " is " + temperatureFormat.format(temperature) + " degrees Celsius.";
                    speak(response);
                    tempTextView.setText(response);
                },
                error -> speak("I cannot check the temperature for this place."));
    }

    private void speak(String textToSpeak) {
        speechRecognizer.stopListening();
        textToSpeech.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, Long.toString(System.currentTimeMillis()));
        startListening();
    }

    private void startListening() {
        speechRecognizer.startListening(speechRecognizerIntent);
    }
}