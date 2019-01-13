package com.android.collect.widget.praise;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * @author anzai
 */
public abstract class SpannableClickable extends ClickableSpan implements
        View.OnClickListener {

    /**
     * text颜色
     */
    private int textColor;

    public SpannableClickable(int textColor) {
        this.textColor = textColor;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(textColor);
        ds.setUnderlineText(false);
        ds.clearShadowLayer();
    }
}
