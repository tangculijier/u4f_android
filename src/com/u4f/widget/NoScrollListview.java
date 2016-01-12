package com.u4f.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.ListView;


/**
 * 用于处理在scrollview嵌套中listview不滚动的情况
 * @author lizheHuang
 *
 */
public class NoScrollListview extends ListView
{

	public NoScrollListview(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * 设置不滚动
	 */
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);

	}

}