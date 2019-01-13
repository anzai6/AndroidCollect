package com.android.collect.library.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.android.collect.library.R;
import com.android.collect.library.listener.ClickSpannable;

public class TextViewUtil {

    /**
     * 实现部分文字变色和点击，从startIndex到endIndex（不包含）
     *
     * @param context
     * @param tv
     * @param startIndex
     * @param endIndex（不包含）
     * @param colorId（部分字体颜色）
     * @param onClickListener
     */
    public static void setTextViewPartClick(Context context, TextView tv,
                                            int startIndex, int endIndex, int colorId,
                                            View.OnClickListener onClickListener) {
        // 去掉点击后的背景
        tv.setHighlightColor(ContextCompat.getColor(context, R.color.transparent));
        SpannableStringBuilder builder = new SpannableStringBuilder(tv
                .getText().toString().trim());

        // 设置文本点击事件
        builder.setSpan(new ClickSpannable(onClickListener, ContextCompat.getColor(context, colorId)
                ), startIndex, endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        tv.setMovementMethod(LinkMovementMethod.getInstance());
        tv.setText(builder);
    }

    /**
     * 改变部分字体颜色，从startIndex到endIndex（不包含）
     *
     * @param startIndex
     * @param endIndex
     * @param colorId（部分字体颜色）
     * @param tv
     */
    public static void setTextPartColor(Context context, int startIndex,
                                         int endIndex, int colorId, TextView tv) {
        SpannableStringBuilder builder = new SpannableStringBuilder(tv
                .getText().toString());
        ForegroundColorSpan span = new ForegroundColorSpan(ContextCompat.getColor(context, colorId));
        builder.setSpan(span, startIndex, endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(builder);
    }

    /**
     * 改变部分字体颜色，从startIndex1到endIndex1（不包含）,从startIndex2到endIndex2（不包含）
     *
     * @param context
     * @param startIndex1
     * @param endIndex1
     * @param colorId1
     * @param startIndex2
     * @param endIndex2
     * @param colorId2
     * @param tv
     */
    public static void setTextPartColorTwo(Context context, int startIndex1,
                                         int endIndex1, int colorId1, int startIndex2, int endIndex2,
                                         int colorId2, TextView tv) {
        SpannableStringBuilder builder = new SpannableStringBuilder(tv
                .getText().toString());
        ForegroundColorSpan span1 = new ForegroundColorSpan(ContextCompat.getColor(context, colorId1));
        ForegroundColorSpan span2 = new ForegroundColorSpan(ContextCompat.getColor(context, colorId2));
        builder.setSpan(span1, startIndex1, endIndex1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(span2, startIndex2, endIndex2,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(builder);
    }

    /**
     * 改变部分文字字体大小，从startIndex到endIndex（不包含）
     *
     * @param context
     * @param startIndex
     * @param endIndex
     * @param textSize（改变字体的大小）
     * @param tv
     */
    public static void setTextPartSize(Context context, int startIndex,
                                       int endIndex, int textSize, TextView tv) {
        SpannableStringBuilder builder = new SpannableStringBuilder(tv.getText().toString());
        builder.setSpan(new AbsoluteSizeSpan(DpPxUtils.sp2px(context, textSize)),
                startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); // 字号
        tv.setText(builder);
    }

    /**
     * 改变部分文字字体大小,颜色，从startIndex到endIndex（不包含）
     *
     * @param context
     * @param startIndex
     * @param endIndex
     * @param textSize   传：12,14,16....
     * @param colorId    传：R.coror...
     * @param tv
     */
    public static void setTextPartSizeAndColor(Context context, int startIndex,
                                               int endIndex, int textSize, int colorId, TextView tv) {
        Spannable spannable = new SpannableString(tv.getText());
        spannable.setSpan(new AbsoluteSizeSpan(DpPxUtils.sp2px(context, textSize)), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, colorId)), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(spannable);
    }

    /**
     * 改变部分文字字体大小,颜色,背景，从startIndex到endIndex（不包含）
     *
     * @param context
     * @param startIndex
     * @param endIndex
     * @param textSize   传：12,14,16....
     * @param colorId    传：R.coror...
     * @param colorId    传：R.coror...
     * @param bgColorId  传：R.coror...
     * @param tv
     */
    public static void setTextPartSizeAndColorBg(Context context, int startIndex,
                                                 int endIndex, int textSize, int colorId, int bgColorId, TextView tv) {
        Spannable spannable = new SpannableString(tv.getText());
        ForegroundColorSpan fSpan = new ForegroundColorSpan(ContextCompat.getColor(context, colorId));
        spannable.setSpan(new AbsoluteSizeSpan(DpPxUtils.sp2px(context, textSize)), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, colorId)), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new BackgroundColorSpan(ContextCompat.getColor(context, bgColorId)), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(spannable);
    }

}
