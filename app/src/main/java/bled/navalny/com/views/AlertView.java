package bled.navalny.com.views;

import android.content.Context;
import android.util.AttributeSet;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

/**
 * Created by svyatozar on 13.08.17.
 */

public class AlertView extends PulsatorLayout
{
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
}
