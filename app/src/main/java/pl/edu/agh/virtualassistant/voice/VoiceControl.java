package pl.edu.agh.virtualassistant.voice;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.ViewUtils;

import com.google.android.material.chip.Chip;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import pl.edu.agh.virtualassistant.MainActivity;
import pl.edu.agh.virtualassistant.R;

public class VoiceControl {

    private TextToSpeech textToSpeech;
    private SpeechRecognizer speechRecognizer;
    private final Locale locale;
    private Intent speechRecognizerIntent;
    private final MainActivity mainActivity;
    private final AtomicBoolean initialized = new AtomicBoolean(false);
    private TextView utterance;
    Consumer<Bundle> readyForSpeech;
    Consumer<String> onRecognitionResult;

    public VoiceControl(MainActivity mainActivity) {
        locale = Locale.ENGLISH;
        this.mainActivity = mainActivity;
    }

    /**
     * Prepares text-to-speech and speech-to-text services. If successful, {@link #isInitialized()} will return true.
     *
     * @param readyForSpeech      function executed once speech-to-text service is ready to receive input.
     * @param onRecognitionResult function executed when speech-to-text recognizes a complete utterance (a word or sentence followed by a short pause).
     *                            Recognized text is given as an argument.
     */
    public void setUp(Consumer<Bundle> readyForSpeech, Consumer<String> onRecognitionResult,
                      TextView textView, TextView utterance) {
        this.utterance = utterance;
        this.readyForSpeech = readyForSpeech;
        this.onRecognitionResult = onRecognitionResult;
        assert readyForSpeech != null && onRecognitionResult != null;

        textToSpeech = new TextToSpeech(mainActivity, status -> {
            if (status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(locale);
                textToSpeech.setOnUtteranceProgressListener(new OnUtteranceProgressListener(textView, utterance));
            } else {
                System.out.println("Error on setting up text to speech.");
            }
        });
        if (!SpeechRecognizer.isRecognitionAvailable(mainActivity)) {
            Toast.makeText(mainActivity, "Speech recognition is not available on this device. The application will not work", Toast.LENGTH_LONG).show();
            System.out.println("Speech recognition is not available on this device. The application will not work");
            initialized.set(false);
            return;
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(mainActivity);
        setSpeechRecognitionCallbacks(readyForSpeech, onRecognitionResult);

        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, locale.getLanguage());
        speechRecognizerIntent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{locale.getLanguage()});

        Chip startButton = mainActivity.findViewById(R.id.startButton);
        startButton.setOnClickListener(v -> startListening());

        initialized.set(true);
    }

    /**
     * Sets given speech recognition callbacks {@see #setUp}. Callback that do not need to be changed should be set to null.
     *
     * @param readyForSpeech      function executed once speech-to-text service is ready to receive input.
     * @param onRecognitionResult function executed when speech-to-text recognizes a complete utterance (a word or sentence followed by a short pause).
     *                            Recognized text is given as an argument.
     */
    public void setSpeechRecognitionCallbacks(Consumer<Bundle> readyForSpeech, Consumer<String> onRecognitionResult) {
        if (readyForSpeech != null) {
            this.readyForSpeech = readyForSpeech;
        }
        if (onRecognitionResult != null) {
            this.onRecognitionResult = onRecognitionResult;
        }
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
                VoiceControl.this.readyForSpeech.accept(params);
            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                System.out.println(data);
                VoiceControl.this.onRecognitionResult.accept(data.get(0));
            }
        });
    }

    /**
     * Stop listening, say supplied text and start listening again. Works only if {@link #isInitialized()} returns true.
     **/
    public void say(String textToSpeak) {
        utterance.setText(textToSpeak);
        speechRecognizer.stopListening();
        mainActivity.runInMainThread(() -> mainActivity.startAvatarAnimation(textToSpeak));
        textToSpeech.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, Long.toString(System.currentTimeMillis()));
    }

    private void startListening() {
        speechRecognizer.startListening(speechRecognizerIntent);
    }

    public boolean isInitialized() {
        return initialized.get();
    }

    private class OnUtteranceProgressListener extends UtteranceProgressListener {

        private TextView textView;
        private TextView textView2;

        public OnUtteranceProgressListener(TextView textView, TextView textView2) {
            this.textView = textView;
            this.textView2 = textView2;
        }

        @Override
        public void onStart(String utteranceId) {
            textView.setText("Processing");
        }

        @Override
        public void onDone(String utteranceId) {
            mainActivity.runInMainThread(mainActivity::stopAvatarAnimation);
            textView.setText("Tap the button and speak");
            textView2.setText("");
        }

        @Override
        public void onError(String utteranceId) {
            textView.setText("Error occurred");
            textView2.setText("");
        }
    }
}
