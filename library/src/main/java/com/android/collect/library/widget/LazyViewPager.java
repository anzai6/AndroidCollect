package com.android.collect.library.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class LazyViewPager extends ViewPager {

	private boolean isCanScroll = false;
	
	public LazyViewPager(Context context) {
		super(context);
	}

	public LazyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		if (isCanScroll) {
			return super.onTouchEvent(arg0);
		} else {
			return false;
		}

	}
	
	@Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if (isCanScroll) {
			return super.onInterceptTouchEvent(arg0);
		} else {
			return false;
		}

	}
	

}
