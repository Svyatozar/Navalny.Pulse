package bled.navalny.com.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

/**
 * Created by svyatozar on 13.08.17.
 */

public class AlertView extends PulsatorLayout
{
	private int CLICK_ACTION_THRESHOLD = 200;
	private float startX;
	private float startY;

	OnClickListener onClickListener = null;

	public AlertView(Context context)
	{
		super(context);
	}

	public AlertView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public AlertView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
	}

	@Override
	public void setOnClickListener(OnClickListener onClickListener)
	{
		this.onClickListener = onClickListener;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev)
	{
		onMarkerTouch(this, ev);
		return true;
	}

	public void onMarkerTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				startX = event.getX();
				startY = event.getY();
				break;
			case MotionEvent.ACTION_UP:
				float endX = event.getX();
				float endY = event.getY();
				if (isAClick(startX, endX, startY, endY)) {
					if (onClickListener != null) {
						onClickListener.onClick(v);
					}
				}
				break;
		}
	}

	private boolean isAClick(float startX, float endX, float startY, float endY) {
		float differenceX = Math.abs(startX - endX);
		float differenceY = Math.abs(startY - endY);
		return !(differenceX > CLICK_ACTION_THRESHOLD/* =5 */ || differenceY > CLICK_ACTION_THRESHOLD);
	}
}
