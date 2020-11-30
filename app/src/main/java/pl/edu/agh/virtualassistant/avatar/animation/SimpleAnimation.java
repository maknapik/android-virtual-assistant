package pl.edu.agh.virtualassistant.avatar.animation;

import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;

import java.util.List;

import pl.edu.agh.virtualassistant.avatar.lipsync.ExpressionExtractor;
import pl.edu.agh.virtualassistant.avatar.utils.AvatarHelpers;

public class SimpleAnimation {

    public static AnimationDrawable getSimpleTalkingAnimation(Resources resources, String output) {
        AnimationDrawable anim = new AnimationDrawable();
        List<Integer> expressions = AvatarHelpers.getExpressions(output);
        for (Integer expression : expressions) {
            anim.addFrame(ExpressionExtractor.getAnimationFrame(resources, expression), 150);
        }

        return anim;
    }

}
