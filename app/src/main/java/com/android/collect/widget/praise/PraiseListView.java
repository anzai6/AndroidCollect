package com.android.collect.widget.praise;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.android.collect.R;

import java.util.List;

/**
 * 点赞控件
 *
 * @author anzai
 */
public class PraiseListView extends TextView {

    private int itemColor;
    private int itemSelectorColor;
    private int firstBitmapId = R.drawable.ic_praise;
    private List<String> datas;
    private OnItemClickListener onItemClickListener;

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public PraiseListView(Context context) {
        super(context);
    }

    public PraiseListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
    }

    public PraiseListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(
                attrs, R.styleable.PraiseListView, 0, 0);
        try {
            // textview的默认颜色
            itemColor = typedArray.getColor(
                    R.styleable.PraiseListView_item_color,
                    getContext().getResources().getColor(
                            R.color.praise_item_default));
            itemSelectorColor = typedArray.getColor(
                    R.styleable.PraiseListView_item_selector_color,
                    getContext().getResources().getColor(
                            R.color.praise_item_selector_default));

        } finally {
            typedArray.recycle();
        }
    }

    public List<String> getDatas() {
        return datas;
    }

    public void setDatas(List<String> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    /**
     * 设置每一项Text字体颜色
     *
     * @param colorId
     */
    public void setItemTextColor(int colorId) {
        itemColor = getContext().getResources().getColor(colorId);
    }

    /**
     * 设置每一项Text点击背景色
     *
     * @param colorId
     */
    public void setItemSelectorBgColor(int colorId) {
        itemSelectorColor = getContext().getResources().getColor(colorId);
    }

    /**
     * 设置每一项Text点击背景色
     *
     * @param resId
     */
    public void setFirstBitmapRes(int resId) {
        firstBitmapId = resId;
    }

    public void notifyDataSetChanged() {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        if (datas != null && datas.size() > 0) {
            // 添加点赞图标
            builder.append(setImageSpan());
            String item = null;
            for (int i = 0; i < datas.size(); i++) {
                item = datas.get(i);
                if (item != null) {
                    builder.append(setClickableSpan(item, i));
                    if (i != datas.size() - 1) {
                        builder.append(", ");
                    }
                }
            }
        }
        builder.append(setLastSpan());
        setText(builder);
        setMovementMethod(new CircleMovementMethod(getContext(),
                itemSelectorColor));
    }

    private SpannableString setImageSpan() {
        String text = "  ";
        SpannableString imgSpanText = new SpannableString(text);
        CustomImageSpan icon = new CustomImageSpan(getContext(),
                firstBitmapId);
        imgSpanText.setSpan(icon, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return imgSpanText;

    }

    /**
     * 添加透明防止触摸影响最后一个的BUG
     *
     * @return
     */
    private SpannableString setLastSpan() {
        SpannableString subjectSpanText = new SpannableString(" ");

        ForegroundColorSpan span = new ForegroundColorSpan(Color.TRANSPARENT);
        subjectSpanText.setSpan(span, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return subjectSpanText;
    }

    private SpannableString setClickableSpan(String textStr, final int position) {
        SpannableString subjectSpanText = new SpannableString(textStr);
        subjectSpanText.setSpan(new SpannableClickable(itemColor) {
            @Override
            public void onClick(View widget) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(position);
                }
            }
        }, 0, subjectSpanText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return subjectSpanText;
    }

    public static interface OnItemClickListener {
        public void onClick(int position);
    }
}
