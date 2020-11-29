package pl.edu.agh.virtualassistant.avatar.animation;

import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;

import pl.edu.agh.virtualassistant.avatar.lipsync.ExpressionExtractor;

public class WinkAnimation {

    public static AnimationDrawable getWinkAnimation(Resources resources) {
        AnimationDrawable anim = new AnimationDrawable();

        anim.addFrame(ExpressionExtractor.getAnimationFrame(resources, 1), 100);
        anim.addFrame(ExpressionExtractor.getAnimationFrame(resources, 0), 150);

        return anim;
    }

}
