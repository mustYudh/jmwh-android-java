package com.qsd.jmwh.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * Created by win7 on 2017/4/22.
 *
 * 自定义不可上下滑动的GridView
 */

public class NoSlidingGridView extends GridView {
    public NoSlidingGridView(Context context) {
        super(context);
    }

    public NoSlidingGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoSlidingGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //true:禁止滚动
        return ev.getAction() == MotionEvent.ACTION_MOVE || super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
