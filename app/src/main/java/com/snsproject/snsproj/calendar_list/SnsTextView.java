package com.snsproject.snsproj.calendar_list;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.snsproject.snsproj.R;

public class SnsTextView extends TextView {
    public static final int Light = 123123123;
    private int TextStyle = 0;
    private Context contxt;

    public int getTextStyle() {
        return TextStyle;
    }

    public void setTextStyle(int textStyle) {
        TextStyle = textStyle;
    }

    public SnsTextView(Context context) {
        super(context);
        this.contxt = context;
    }

    public SnsTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        this.contxt = context;
    }

    public SnsTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.contxt = context;
        TypedArray a = contxt.obtainStyledAttributes(attrs, R.styleable.SnsTextView, defStyleAttr, 0);
        TextStyle = a.getInteger(R.styleable.SnsTextView_textFontStyle, 0);
        a.recycle();
        if(TextStyle==Light){
            super.setTypeface(FontManager.getTypeFace(context));
            super.setIncludeFontPadding(false);
        }
    }

}
