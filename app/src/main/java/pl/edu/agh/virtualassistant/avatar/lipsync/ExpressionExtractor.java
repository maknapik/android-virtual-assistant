package pl.edu.agh.virtualassistant.avatar.lipsync;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import pl.edu.agh.virtualassistant.R;

public class ExpressionExtractor {
        @SuppressLint("UseCompatLoadingForDrawables")
        public static Drawable getAnimationFrame(Resources resources, int frameNumber) {
            switch (frameNumber) {
                case 0:
                    return resources.getDrawable(R.drawable.mouth_1a);
                case 1:
                    return resources.getDrawable(R.drawable.mouth_1b);
                case 2:
                    return resources.getDrawable(R.drawable.mouth_2a);
                case 3:
                    return resources.getDrawable(R.drawable.mouth_2b);
                case 4:
                    return resources.getDrawable(R.drawable.mouth_3);
                case 5:
                    return resources.getDrawable(R.drawable.mouth_4);
                default:
                    throw new UnsupportedOperationException("Unsupported frame provided.");
            }
        }
}
