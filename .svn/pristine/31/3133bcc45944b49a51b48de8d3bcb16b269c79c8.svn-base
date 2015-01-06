package com.fsti.fjdicClient.component;

import com.fsti.fjdicClient.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TabHost;

/** 锟教筹拷 TabHost 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷谐锟斤拷幕锟斤拷锟斤拷锟斤拷锟叫э拷锟�*/
public class AnimationTabHost extends TabHost {
	private Animation slideLeftIn;
	private Animation slideLeftOut;
	private Animation slideRightIn;
	private Animation slideRightOut;

	/** 锟斤拷录锟角凤拷蚩锟斤拷锟叫э拷锟�*/
	private boolean isOpenAnimation;
	/** 锟斤拷录锟斤拷前锟斤拷签页锟斤拷锟斤拷锟斤拷 */
	private int mTabCount;

	public AnimationTabHost(Context context, AttributeSet attrs) {
		super(context, attrs);

		slideLeftIn = AnimationUtils.loadAnimation(context,
				R.anim.slide_left_in);
		slideLeftOut = AnimationUtils.loadAnimation(context,
				R.anim.slide_left_out);
		slideRightIn = AnimationUtils.loadAnimation(context,
				R.anim.slide_right_in);
		slideRightOut = AnimationUtils.loadAnimation(context,
				R.anim.slide_right_out);

		isOpenAnimation = false;
	}

	/**
	 * 锟斤拷锟斤拷锟角凤拷蚩锟斤拷锟叫э拷锟�
	 * 
	 * @param isOpenAnimation
	 *            true锟斤拷锟斤拷
	 */
	public void setOpenAnimation(boolean isOpenAnimation) {
		this.isOpenAnimation = isOpenAnimation;
	}

	/**
	 * 锟斤拷锟矫憋拷签锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷<br>
	 * 锟斤拷锟斤拷顺锟斤拷为锟斤拷锟斤拷锟�锟斤拷锟斤拷锟斤拷锟�锟揭斤拷>锟斤拷锟斤拷锟�
	 * 
	 * @param animationResIDs
	 *            锟斤拷锟斤拷锟斤拷锟斤拷源锟侥硷拷ID
	 * @return true锟斤拷锟侥革拷锟斤拷锟斤拷锟侥硷拷;<br>
	 *         false锟斤拷锟斤拷锟侥革拷锟斤拷锟斤拷锟侥硷拷锟斤拷锟睫凤拷匹锟戒，锟斤拷锟斤拷默锟较讹拷锟斤拷锟斤拷
	 */
	public boolean setTabAnimation(int[] animationResIDs) {
		if (3 == animationResIDs.length) {
			slideLeftIn = AnimationUtils.loadAnimation(getContext(),
					animationResIDs[0]);
			slideLeftOut = AnimationUtils.loadAnimation(getContext(),
					animationResIDs[1]);
			slideRightIn = AnimationUtils.loadAnimation(getContext(),
					animationResIDs[2]);
			slideRightOut = AnimationUtils.loadAnimation(getContext(),
					animationResIDs[3]);

			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return 锟斤拷锟截碉拷前锟斤拷签页锟斤拷锟斤拷锟斤拷
	 */
	public int getTabCount() {
		return mTabCount;
	}

	@Override
	public void addTab(TabSpec tabSpec) {
		mTabCount++;
		super.addTab(tabSpec);
	}

	@Override
	public void setCurrentTab(int index) {
		int mCurrentTabID = getCurrentTab();

		if (null != getCurrentView()) {
			// 锟斤拷一锟斤拷锟斤拷锟斤拷 Tab 时锟斤拷锟斤拷值为 null锟斤拷

			if (isOpenAnimation) {
				if (mCurrentTabID == (mTabCount - 1) && index == 0) {
					getCurrentView().startAnimation(slideLeftOut);
				} else if (mCurrentTabID == 0 && index == (mTabCount - 1)) {
					getCurrentView().startAnimation(slideRightOut);
				} else if (index > mCurrentTabID) {
					getCurrentView().startAnimation(slideLeftOut);
				} else if (index < mCurrentTabID) {
					getCurrentView().startAnimation(slideRightOut);
				}
			}
		}

		super.setCurrentTab(index);

		if (isOpenAnimation) {
			if (mCurrentTabID == (mTabCount - 1) && index == 0) {
				getCurrentView().startAnimation(slideLeftIn);
			} else if (mCurrentTabID == 0 && index == (mTabCount - 1)) {
				getCurrentView().startAnimation(slideRightIn);
			} else if (index > mCurrentTabID) {
				getCurrentView().startAnimation(slideLeftIn);
			} else if (index < mCurrentTabID) {
				getCurrentView().startAnimation(slideRightIn);
			}
		}
	}
}
