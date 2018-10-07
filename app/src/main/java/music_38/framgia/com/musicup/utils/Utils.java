package music_38.framgia.com.musicup.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.concurrent.TimeUnit;

public class Utils {

    private static final int IMAGE_RESIZE_WIDTH = 50;
    private static final int IMAGE_RESIZE_HEIGHT = 50;
    private static final int IMAGE_BLUR = 20;
    private static final String ACTION_CONVERT_TIME = "%02d:%02d";

    public static void blurImageWithFresco(SimpleDraweeView imageView, Uri uri) {

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setPostprocessor(new IterativeBoxBlurPostProcessor(IMAGE_BLUR))
                .setResizeOptions(new ResizeOptions(IMAGE_RESIZE_WIDTH, IMAGE_RESIZE_HEIGHT))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(imageView.getController())
                .build();
        imageView.setController(controller);
    }

    public static void roundImageWithFresco(Context context, SimpleDraweeView imageView, Uri uri) {
        RoundingParams roundingParams = RoundingParams.asCircle();
        imageView.setHierarchy(new GenericDraweeHierarchyBuilder(context.getResources())
                .setRoundingParams(roundingParams)
                .build());
        imageView.setImageURI(uri);
    }

    @SuppressLint("DefaultLocale")
    public static String convertTime(int time) {
        return String.format(ACTION_CONVERT_TIME, TimeUnit.MILLISECONDS.toMinutes(time),
                TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)));
    }
}
