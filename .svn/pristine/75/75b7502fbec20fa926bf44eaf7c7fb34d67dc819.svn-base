package com.fsti.fjdicClient.component;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.ImageView;

public class CustomFlingOnceGallery extends Gallery {

	private Camera mCamera = new Camera();
    private int mMaxRotationAngle = 60;//60
    private int mMaxZoom = -95;//-120
    private int mCoveflowCenter;
    private Context myContext;
    boolean mDoTransition=false;
    private ImageView imageView;
	
    public CustomFlingOnceGallery(Context context) {
            super(context);
            this.myContext = context;
            this.setStaticTransformationsEnabled(true);
    }

    public CustomFlingOnceGallery(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.myContext = context;
            this.setStaticTransformationsEnabled(true);
    }

    public CustomFlingOnceGallery(Context context, AttributeSet attrs, int defStyle) {
    		
            super(context, attrs, defStyle);
            this.myContext = context;
            this.setStaticTransformationsEnabled(true);
    }

    public int getMaxRotationAngle() {
            return mMaxRotationAngle;
    }

    public void setMaxRotationAngle(int maxRotationAngle) {
            mMaxRotationAngle = maxRotationAngle;
    }

    public int getMaxZoom() {
    	
            return mMaxZoom;
    }
    
    public void setMaxZoom(int maxZoom) {
            mMaxZoom = maxZoom;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCoveflowCenter = (w - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
    }
    
    

    
    
    
    public void setDoTransition(boolean doTransition){
        mDoTransition= doTransition;
    }

    public boolean getDoTransition(){
        return mDoTransition;
    }
    
	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
		return e2.getX() > e1.getX();
	}
	

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		int keyCode;
		if (isScrollingLeft(e1, e2)) {
			keyCode = KeyEvent.KEYCODE_DPAD_LEFT;
		} else {
			keyCode = KeyEvent.KEYCODE_DPAD_RIGHT;
		}
		onKeyDown(keyCode, null);

		
 
////开始执行动画
//arg1.startAnimation(animation);
		
		
		return true;
//		return false;
	}
	

	
	
}
