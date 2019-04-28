package com.qsd.jmwh.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class NoscrollerGridView extends GridView {
    public NoscrollerGridView(Context context) {
        super(context,null);
    }

    public NoscrollerGridView(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public NoscrollerGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
