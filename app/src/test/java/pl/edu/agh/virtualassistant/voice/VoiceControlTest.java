package pl.edu.agh.virtualassistant.voice;

import android.content.Intent;
import android.os.Bundle;
import android.speech.SpeechRecognizer;

import com.google.android.material.chip.Chip;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.function.Consumer;

import pl.edu.agh.virtualassistant.MainActivity;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;


@RunWith(PowerMockRunner.class)
@PrepareForTest({SpeechRecognizer.class, Intent.class})
public class VoiceControlTest {
    private static final String TEXT_TO_SPEAK = "some fancy text";

    @Mock
    private Consumer<Bundle> readyForSpeechMock;
    @Mock
    private Consumer<String> onRecognitionResultMock;
    @Mock
    private SpeechRecognizer speechRecognizerMock;
    @Mock
    private MainActivity mainActivityMock;
    @Mock
    private Chip chipMock;

    @InjectMocks
    private VoiceControl voiceControl;

    @Test
    public void test() throws Exception {
        // given
        PowerMockito.mockStatic(SpeechRecognizer.class);
        PowerMockito.when(SpeechRecognizer.class, "isRecognitionAvailable", mainActivityMock).thenReturn(true);
        PowerMockito.when(SpeechRecognizer.class, "createSpeechRecognizer", mainActivityMock).thenReturn(speechRecognizerMock);
        PowerMockito.when(mainActivityMock.findViewById(anyInt())).thenReturn(chipMock);
        voiceControl.setUp(readyForSpeechMock, onRecognitionResultMock);

        // when
        voiceControl.say(TEXT_TO_SPEAK);

        // then
        verify(speechRecognizerMock).stopListening();
    }


}