package com.electreca.tech.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatEditText;

import com.electreca.tech.R;
import com.electreca.tech.utils.HelperMethods;

public class CustomEditText extends AppCompatEditText {
    private boolean disableEmoji; // disable emoji and some special symbol input.

    public CustomEditText(Context context) {
        super(context);
        setCapsLock();
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.CustomFont);
        String customFont = a.getString(R.styleable.CustomFont_myFont);
        setCustomFont(ctx, customFont);
        disableEmoji = a.getBoolean(R.styleable.CustomFont_disable_emoji, false);
        if (disableEmoji) {
            setFilters(new InputFilter[]{new EmojiExcludeFilter()});
        }
        a.recycle();
        setCapsLock();
    }

    public boolean setCustomFont(Context ctx, String customFont) {
        this.setImeOptions(TextUtils.CAP_MODE_SENTENCES);
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

    /**
     * set true to disable Emoji and special symbol
     *
     * @param disableEmoji true: disable emoji;
     *                     false: enable emoji
     */
    public void setDisableEmoji(boolean disableEmoji) {
        this.disableEmoji = disableEmoji;
        if (disableEmoji) {
            setFilters(new InputFilter[]{new EmojiExcludeFilter()});
        } else {
            setFilters(new InputFilter[0]);
        }
    }

    public void setCapsLock() {
        Log.i(this.getClass().getSimpleName(), this.getInputType() + "");
        if (this.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)) {
        } else if (this.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
        } else {
            this.setInputType(this.getInputType() | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        }
    }

    /**
     * disable emoji and special symbol input
     */
    private class EmojiExcludeFilter implements InputFilter {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                int type = Character.getType(source.charAt(i));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }
            }
            return null;
        }
    }

}
