package com.snsproject.snsproj.calendar_list;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by waynezu on 16/6/24.
 */
public class FontManager {
    private static Typeface textLight = null;
    public static synchronized Typeface getTypeFace(Context context){
        if(textLight != null){
            return textLight;
        }else {
            Typeface tf = Typeface.createFromAsset(context.getResources().getAssets(), "SourceHanSansCN-Light.TTF");
            return textLight = tf;
        }
    }
}
