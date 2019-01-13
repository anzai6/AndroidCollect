package com.android.collect.library.listener;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.View.OnClickListener;

public class ClickSpannable extends ClickableSpan implements OnClickListener {

    private OnClickListener onClickListener;
    private int colorId;

    public ClickSpannable(OnClickListener onClickListener, int colorId) {
        this.onClickListener = onClickListener;
        this.colorId = colorId;
    }

    @Override
    public void onClick(View arg0) {
        onClickListener.onClick(arg0);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(colorId);
        // 去掉下划线
        ds.setUnderlineText(false);
        // 去掉阴影
        ds.clearShadowLayer();
    }

}
