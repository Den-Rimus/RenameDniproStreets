package ua.dp.rename.dniprostreets.util;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.view.Surface;

public abstract class RotationUtil {

    public static void lockScreenOrientation(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
            return;
        }
        //
        // TODO : temporary commented, until move to single activity usage all over app
//        if (!mScreenOrientationLocked) {
            final int orientation = activity.getResources().getConfiguration().orientation;
            final int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
            //
            if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_90) {
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            }
            //
            else if (rotation == Surface.ROTATION_180 || rotation == Surface.ROTATION_270) {
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                }
                else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                }
            }
        //
//            mScreenOrientationLocked = true;
//        }
    }

    public static void unlockScreenOrientation(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
//        mScreenOrientationLocked = false;
    }
}
