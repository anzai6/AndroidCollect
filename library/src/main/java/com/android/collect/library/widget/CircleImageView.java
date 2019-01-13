package com.android.collect.library.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class CircleImageView extends ImageView {

	private int mWidth;
	private int mRadius;
	private Matrix mMatrix;
	private Paint mBitmapPaint;
	private int mBorderRadius;
	private int type;
	private BitmapShader mBitmapShader;
	private int TYPE_CIRCLE = 0;
	private int TYPE_ROUND = 1;
	private RectF mRoundRect;
	private Context context;

	public CircleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context= context;

		mMatrix = new Matrix();
		mBitmapPaint = new Paint();
		mBitmapPaint.setAntiAlias(true);

		mBorderRadius = 10;
		type = 0;
	}

	private void setUpShader() {
		Drawable drawable = getDrawable();
		if (drawable == null) {
			return;
		}

		Bitmap bmp = drawableToBitamp(drawable);
		// 将bmp作为着色器，就是在指定区域内绘制bmp
		mBitmapShader = new BitmapShader(bmp, TileMode.CLAMP, TileMode.CLAMP);
		float scale = 1.0f;
		if (type == TYPE_CIRCLE) {
			// 拿到bitmap宽或高的小值
			int bSize = Math.min(bmp.getWidth(), bmp.getHeight());
			scale = mWidth * 1.0f / bSize;

		} else if (type == TYPE_ROUND) {
			// 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；
			scale = Math.max(getWidth() * 1.0f / bmp.getWidth(), getHeight()
					* 1.0f / bmp.getHeight());
		}
		// shader的变换矩阵，我们这里主要用于放大或者缩小
		mMatrix.setScale(scale, scale);
		// 设置变换矩阵
		mBitmapShader.setLocalMatrix(mMatrix);
		// 设置shader
		mBitmapPaint.setShader(mBitmapShader);
	}

	private Bitmap drawableToBitamp(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			BitmapDrawable bd = (BitmapDrawable) drawable;
			return bd.getBitmap();
		}
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		drawable.draw(canvas);
		return bitmap;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		// 设置成圆形
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
//		mWidth = this.getLayoutParams().height;
		mRadius = mWidth / 2;
		setMeasuredDimension(mWidth, mWidth);
	}

	protected void onDraw(Canvas canvas) {
		if (getDrawable() == null) {
			return;
		}
		setUpShader();

		if (type == TYPE_ROUND) {
			canvas.drawRoundRect(mRoundRect, mBorderRadius, mBorderRadius,
					mBitmapPaint);
		} else {
			canvas.drawCircle(mRadius, mRadius, mRadius, mBitmapPaint);
			// drawSomeThing(canvas);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Activity ac = (Activity) context;
//		Class c = ac.getClass();
//		Class cc = ContactManagerActivity.class;
//		// 判断是不是ContactManagerActivity,再处理圆头像的点击事件
//		if (c.equals(cc)) {
//			if (event.getAction() == MotionEvent.ACTION_DOWN) { // 自己消费这个点击事件
//				Util.L("----切换头像");
//				return true;
//			}
//		}
		return super.onTouchEvent(event);
	}

}
