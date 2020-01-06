package com.electreca.tech.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatCheckBox;

import com.electreca.tech.R;
import com.electreca.tech.utils.HelperMethods;

public class CustomCheckBox extends AppCompatCheckBox {


    public CustomCheckBox(Context context) {
        super(context);
    }

    public CustomCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public CustomCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.CustomFont);
        String customFont = a.getString(R.styleable.CustomFont_myFont);
        setCustomFont(ctx, customFont);
        a.recycle();
    }

    public boolean setCustomFont(Context ctx, String customFont) {
        Typeface tf = null;
        try {
            tf = Typeface.createFromAsset(ctx.getAssets(), getResources().getString(R.string.FONT_REGULAR));
        } catch (Exception e) {
            return false;
        }

        if (HelperMethods.checkForValidString(customFont)) {
            if (customFont.equalsIgnoreCase(ctx.getString(R.string.REGULAR))) {
                tf = Typeface.createFromAsset(ctx.getAssets(), getResources().getString(R.string.FONT_REGULAR));
            } else if (customFont.equalsIgnoreCase(ctx.getString(R.string.LIGHT))) {
                tf = Typeface.createFromAsset(ctx.getAssets(), getResources().getString(R.string.FONT_LIGHT));
            } else if (customFont.equalsIgnoreCase(ctx.getString(R.string.MEDIUM))) {
                tf = Typeface.createFromAsset(ctx.getAssets(), getResources().getString(R.string.FONT_MEDIUM));
            } else if (customFont.equalsIgnoreCase(ctx.getString(R.string.BOLD))) {
                tf = Typeface.createFromAsset(ctx.getAssets(), getResources().getString(R.string.FONT_BOLD));
            } else if (customFont.equalsIgnoreCase(ctx.getString(R.string.SEMI_BOLD))) {
                tf = Typeface.createFromAsset(ctx.getAssets(), getResources().getString(R.string.FONT_SEMI_BOLD));
            } else if (customFont.equalsIgnoreCase(ctx.getString(R.string.THIN))) {
                tf = Typeface.createFromAsset(ctx.getAssets(), getResources().getString(R.string.FONT_THIN));
            }
        }
        setTypeface(tf);
        return true;
    }
}
