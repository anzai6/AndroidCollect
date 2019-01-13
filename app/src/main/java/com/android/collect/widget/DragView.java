package com.android.collect.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.android.collect.library.util.DpPxUtils;

import java.io.InputStream;

/**
 * Created by qinshunan on 2017/11/8.
 */

public class DragView extends View {

    private Context mContext;

    private int WIDTH = 160;//拖动色块的大小

    /**
     * DragView的宽高
     */
    private int heigh, width;
    private Rect rect;
    private int deltaX, deltaY;//点击位置和图形边界的偏移量
    private static Paint paint = new Paint();//画笔
    private Bitmap mBitmap = null;
    private OnClickListener mClickListener;
    private int mStartX, mStartY;

    /**
     * 是否吸附左右边边
     */
    private boolean isEdge = false;

    public DragView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        WIDTH = DpPxUtils.dip2px(mContext, 40);
        rect = new Rect(0, 0, WIDTH, WIDTH);//绘制矩形的区域
        paint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(widthSize, heightSize);
        width = widthSize;
        heigh = heightSize;
        // 默认位置
//        rect = new Rect(width-WIDTH, heigh/2-WIDTH/2, width, heigh/2+WIDTH/2);//绘制矩形的区域
        rect = new Rect(width / 2 - WIDTH / 2, heigh - WIDTH - DpPxUtils.dip2px(mContext, 16),
                width / 2 + WIDTH / 2, heigh - DpPxUtils.dip2px(mContext, 16));//绘制矩形的区域
    }

    /**
     * Implement this to do your drawing.
     *
     * @param canvas the canvas on which the background will be drawn
     */
    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap != null) {
            Rect rectF = new Rect();
            rectF.set(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
            canvas.drawBitmap(mBitmap, rectF, rect, paint);
        } else {
            paint.setColor(Color.RED);//填充红色
            canvas.drawRect(rect, paint);//画矩形
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!rect.contains(x, y)) {
                    return false;//没有在矩形上点击，不处理触摸消息
                }
                mStartX = x;
                mStartY = y;
                deltaX = x - rect.left;
                deltaY = y - rect.top;
                break;
            case MotionEvent.ACTION_MOVE:
                Rect old = new Rect(rect);
                //更新矩形的位置
                rect.left = x - deltaX;
                if (rect.left < 0) {
                    rect.left = 0;
                }
                rect.top = y - deltaY;
                if (rect.top < 0) {
                    rect.top = 0;
                }
                rect.right = rect.left + WIDTH;
                if (rect.right > width) {
                    rect.right = width;
                    rect.left = width - WIDTH;
                }
                rect.bottom = rect.top + WIDTH;
                if (rect.bottom > heigh) {
                    rect.bottom = heigh;
                    rect.top = heigh - WIDTH;
                }
                old.union(rect);//要刷新的区域，求新矩形区域与旧矩形区域的并集
                invalidate(old);//出于效率考虑，设定脏区域，只进行局部刷新，不是刷新整个view
                break;
            case MotionEvent.ACTION_UP:
                Rect oldl = new Rect(rect);

                if (isEdge) {
                    //更新矩形的位置
                    if (rect.left + WIDTH / 2 < width / 2) { // 吸左边
                        rect.left = 0;
                    } else { // 吸右边
                        rect.left = width - WIDTH;
                    }
                }

                rect.top = y - deltaY;
                if (rect.top < 0) {
                    rect.top = 0;
                }
                rect.right = rect.left + WIDTH;
                rect.bottom = rect.top + WIDTH;
                if (rect.bottom > heigh) {
                    rect.bottom = heigh;
                    rect.top = heigh - WIDTH;
                }
                oldl.union(rect);
                invalidate(oldl);
                if (Math.abs(mStartX - x) < 10
                        && Math.abs(y - mStartY) < 10) {//捕捉点击事件
                    if (mClickListener != null) {
                        mClickListener.onClick(this);
                    }
                }
                break;
        }
        return true;//处理了触摸消息，消息不再传递
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        this.mClickListener = l;
    }

    /***
     * 加载资源图片
     *
     * @param resId
     */
    public void setImageResource(int resId) {
        InputStream is = getContext().getResources().openRawResource(resId);
        Bitmap bmp = BitmapFactory.decodeStream(is);
        //图片重新裁剪，原图从中心点按显示大小裁剪
        int bw = bmp.getWidth(), bh = bmp.getHeight();
        int w = WIDTH, h = WIDTH;
        if (bw / w >= bh / h) {
            mBitmap = Bitmap.createBitmap(bmp, (bw - bh) / 2, 0, w * bh / h, bh);
        } else {
            mBitmap = Bitmap.createBitmap(bmp, 0, (bh - bw) / 2, bw, h * bw / w);
        }
        invalidate();
    }

}
