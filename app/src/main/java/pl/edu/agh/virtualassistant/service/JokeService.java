package pl.edu.agh.virtualassistant.service;

import android.content.Context;

import com.android.volley.VolleyError;

import java.util.function.Consumer;
import java.util.function.Function;

import pl.edu.agh.virtualassistant.converter.JokeConverter;
import pl.edu.agh.virtualassistant.infrastructure.JokeClient;

public class JokeService {

    public static void getRandomJoke(Context context, Consumer<String> onResponseCallback, Consumer<VolleyError> onErrorCallback) {
        JokeClient.getRandomJoke(context, getOnResponseCallback(onResponseCallback, JokeConverter::getJoke), onErrorCallback);
    }

    private static <T, K> Consumer<T> getOnResponseCallback(Consumer<K> onResponseCallback,
                                                            Function<T, K> getValue) {
        return response -> onResponseCallback.accept(getValue.apply(response));
    }

}
