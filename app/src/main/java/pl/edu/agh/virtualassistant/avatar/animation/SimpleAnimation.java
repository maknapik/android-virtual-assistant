package pl.edu.agh.virtualassistant.avatar.animation;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import pl.edu.agh.virtualassistant.R;

public class SimpleAnimation {
    public static Drawable getAnimationFrame(Resources resources, int frameNumber) {
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
