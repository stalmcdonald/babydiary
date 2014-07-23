package com.cm.babydiary;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

class EditList extends ListView
{
	interface OnHorizontalSwipeListener {
		public void OnSwipe( int deltaX, int position );
	}
	
	private float mOrigX, mOrigY, mDiffX, mDiffY, mLastX, mLastY;
	private boolean fired;
	private OnHorizontalSwipeListener swipeListener = null;
	
	public EditList( Context c ) {
		super(c);
	}
	
	public EditList( Context c, AttributeSet attrs) {
		super(c,attrs);
	}
	
	public EditList( Context c, AttributeSet attrs, int defStyle ) {
		super(c,attrs,defStyle);
	}
	
	public void setSwipeListener( OnHorizontalSwipeListener l ) {
		swipeListener = l;
	}
	
	@Override public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_MOVE:
			final float curX = ev.getX();
	        final float curY = ev.getY();
	        mDiffX += Math.abs(curX - mLastX);
	        mDiffY += Math.abs(curY - mLastY);
	        mLastX = curX;
	        mLastY = curY;
	        
	        if ( swipeListener!=null ) {
                final int deltaX = (int)(mLastX-mOrigX); 
                if (  deltaX<-200 && Math.abs(mLastY-mOrigY)<100 && !fired ) {
                	fired = true;
                	new Handler().post(new Runnable() {
						@Override
						public void run() {
							swipeListener.OnSwipe(deltaX, pointToPosition((int)curX, (int)curY));
						}
					});
                }
            }
		}
		return super.onTouchEvent(ev);
	}

	@Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
            	fired = false;
                // reset difference values
                mDiffX = 0;
                mDiffY = 0;

                mOrigX = mLastX = ev.getX();
                mOrigY = mLastY = ev.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();
                mDiffX += Math.abs(curX - mLastX);
                mDiffY += Math.abs(curY - mLastY);
                mLastX = curX;
                mLastY = curY;

                // keeps from running into each other
                if (mDiffX > mDiffY) {
                    return false;      
                    }
            default:
            	break;
        }

        return super.onInterceptTouchEvent(ev);
    }
	
	public boolean getSwipeOccured() {
		return fired;
	}
}