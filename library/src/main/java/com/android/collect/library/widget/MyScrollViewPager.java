package com.android.collect.library.widget;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;


public class MyScrollViewPager extends ViewPager {

	private Context mContext;
	private int startX;
	private int startY;
	private Handler handler_homepage;
	private int currentIndex;

	public MyScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		return super.onInterceptTouchEvent(arg0);
	}
	

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startX = (int) event.getX();
			getParent().requestDisallowInterceptTouchEvent(true);
//			handler_homepage.removeMessages(1);
			break;
			
		case MotionEvent.ACTION_MOVE:
			int newX = (int) event.getX();
			int dX = newX - startX;
			
			if(getCurrentItem()==0){
				if(dX>0){
					getParent().requestDisallowInterceptTouchEvent(false);
				}
			}else{
				getParent().requestDisallowInterceptTouchEvent(true);
			}
			
			break;
			
		case MotionEvent.ACTION_UP:
//			handler_homepage.sendEmptyMessageDelayed(1,5000);
			getParent().requestDisallowInterceptTouchEvent(false);
			break;
		}

		return super.onTouchEvent(event);
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		
		if(this.getCurrentItem()==0){
			
		}
		
		ViewParent viewParent = getParent();
		while(viewParent!=null){
			viewParent.requestDisallowInterceptTouchEvent(true);
			viewParent = viewParent.getParent();
		}
		getParent().requestDisallowInterceptTouchEvent(true);
		return super.dispatchTouchEvent(ev);
	}

	public void setHandler(Handler handler_homepage) {
		this.handler_homepage = handler_homepage;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}
	
	

}
