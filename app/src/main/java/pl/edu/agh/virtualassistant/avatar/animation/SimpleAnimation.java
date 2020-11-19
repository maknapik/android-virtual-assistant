package pl.edu.agh.virtualassistant.avatar.animation;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;

import java.util.List;

import pl.edu.agh.virtualassistant.R;
import pl.edu.agh.virtualassistant.avatar.utils.AvatarHelpers;

public class SimpleAnimation {
    public static AnimationDrawable getSimpleAnimation(Resources resources, String output) {
        AnimationDrawable anim = new AnimationDrawable();
        List<Integer> expressions = AvatarHelpers.getExpressions(output);
        for (Integer expression : expressions) {
            anim.addFrame(SimpleAnimation.getAnimationFrame(resources, expression), 200);
        }

        // wink anim
        anim.addFrame(SimpleAnimation.getAnimationFrame(resources, 1), 1000);
        anim.addFrame(SimpleAnimation.getAnimationFrame(resources, 0), 150);
        anim.addFrame(SimpleAnimation.getAnimationFrame(resources, 1), 100);

        return anim;
    }

    public static AnimationDrawable getSimpleTalkingAnimation(Resources resources) {
        AnimationDrawable anim = new AnimationDrawable();
        int[] expressions = new int[] { 2, 3 };
        for (Integer expression : expressions) {
            anim.addFrame(SimpleAnimation.getAnimationFrame(resources, expression), 200);
        }
        return anim;
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private static Drawable getAnimationFrame(Resources resources, int frameNumber) {
        switch (frameNumber) {
            case 0:
                return resources.getDrawable(R.drawable.mouth_1a);
            case 1:
                return resources.getDrawable(R.drawable.mouth_1);
            case 2:
                return resources.getDrawable(R.drawable.mouth_2);
            case 3:
                return resources.getDrawable(R.drawable.mouth_3);
            case 4:
                return resources.getDrawable(R.drawable.mouth_4);
            case 5:
                return resources.getDrawable(R.drawable.mouth_5);
            case 6:
                return resources.getDrawable(R.drawable.mouth_6);
            case 7:
                return resources.getDrawable(R.drawable.mouth_7);
            case 8:
                return resources.getDrawable(R.drawable.mouth_8);
            default:
                throw new UnsupportedOperationException("Unsupported frame provided.");
        }
    }
}
