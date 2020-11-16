package pl.edu.agh.virtualassistant.voice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class VoiceControl {

    private TextToSpeech textToSpeech;
    private SpeechRecognizer speechRecognizer;
    private final Locale locale;
    private Intent speechRecognizerIntent;
    private final Context context;
    private final AtomicBoolean initialized = new AtomicBoolean(false);

    public VoiceControl(Context context) {
        locale = Locale.ENGLISH;
        this.context = context;
    }

    /**
     * Prepares text-to-speech and speech-to-text services. If successful, {@link #isInitialized()} will return true.
     *
     * @param readyForSpeech      function executed once speech-to-text service is ready to receive input.
     * @param onRecognitionResult function executed when speech-to-text recognizes a complete utterance (a word or sentence followed by a short pause).
     *                            Recognized text is given as an argument.
     */
    public void setUp(Consumer<Bundle> readyForSpeech, Consumer<String> onRecognitionResult) {
        textToSpeech = new TextToSpeech(context, status -> {
            if (status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(locale);
            } else {
                System.out.println("Error on setting up text to speech.");
            }
        });
        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            Toast.makeText(context, "Speech recognition is not available on this device. The application will not work", Toast.LENGTH_LONG).show();
            System.out.println("Speech recognition is not available on this device. The application will not work");
            initialized.set(false);
            return;
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
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
                System.out.println(data);
                onRecognitionResult.accept(data.get(0));
            }
        });

        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, locale.getLanguage());
        speechRecognizerIntent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{locale.getLanguage()});

        startListening();
        initialized.set(true);
    }

    /**
     * Stop listening, say supplied text and start listening again. Works only if {@link #isInitialized()} returns true.
     **/
    public void say(String textToSpeak) {
        speechRecognizer.stopListening();
        textToSpeech.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, Long.toString(System.currentTimeMillis()));
        startListening();
    }

    private void startListening() {
        speechRecognizer.startListening(speechRecognizerIntent);
    }

    public boolean isInitialized() {
        return initialized.get();
    }
}
