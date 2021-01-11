package pl.edu.agh.virtualassistant.interaction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import pl.edu.agh.virtualassistant.MainActivity;
import pl.edu.agh.virtualassistant.chat.ChatBot;
import pl.edu.agh.virtualassistant.voice.VoiceControl;

import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest(InteractionFactory.class)
public class ResponseResolverTest {
    private static final String USER_UTTERANCE = "some fancy command";

    @Mock
    private VoiceControl voiceControlMock;
    @Mock
    private MainActivity mainActivityMock;
    @Mock
    private ChatBot chatBotMock;
    @Mock
    InteractionFactory interactionFactory;

    @InjectMocks
    private ResponseResolver responseResolver;

    @Test
    public void shouldRespondOnUserUtterance() throws Exception {
        // given

        // when
        responseResolver.respond(USER_UTTERANCE);

        // then
        verify(interactionFactory, Mockito.times(1));
    }

}