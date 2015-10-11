package com.veaer.gank.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Veaer on 15/8/17.
 */
public class LocalDisplay {
    public static int SCREEN_WIDTH_PIXELS;
    public static int SCREEN_HEIGHT_PIXELS;
    public static float SCREEN_DENSITY;
    public static int SCREEN_WIDTH_DP;
    public static int SCREEN_HEIGHT_DP;
    private static boolean sInitialed;

    public LocalDisplay() {}

    public static void init(Context context) {
        if(!sInitialed && context != null) {
            sInitialed = true;
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager wm = (WindowManager)context.getSystemService("window");
            wm.getDefaultDisplay().getMetrics(dm);
            SCREEN_WIDTH_PIXELS = dm.widthPixels;
            SCREEN_HEIGHT_PIXELS = dm.heightPixels;
            SCREEN_DENSITY = dm.density;
            SCREEN_WIDTH_DP = (int)((float)SCREEN_WIDTH_PIXELS / dm.density);
            SCREEN_HEIGHT_DP = (int)((float)SCREEN_HEIGHT_PIXELS / dm.density);
        }
    }

    public static int dp2px(float dp) {
        float scale = SCREEN_DENSITY;
        return (int)(dp * scale + 0.5F);
    }

    public static int designedDP2px(float designedDp) {
        if(SCREEN_WIDTH_DP != 320) {
            designedDp = designedDp * (float)SCREEN_WIDTH_DP / 320.0F;
        }

        return dp2px(designedDp);
    }

    public static void setPadding(View view, float left, float top, float right, float bottom) {
        view.setPadding(designedDP2px(left), dp2px(top), designedDP2px(right), dp2px(bottom));
    }
}
