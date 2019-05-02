package com.qsd.jmwh.view;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.yu.common.utils.DensityUtil;

import java.lang.reflect.Field;


public class ProxyDrawable extends Drawable {
  private View view;
  private Paint paint;

  private int indicatorPaddingLeft;
  private int indicatorPaddingRight;
  private int indicatorPaddingTop;
  private int indicatorHeight;
  private int radius;
  private RectF drawRectF;

  public ProxyDrawable(@NonNull View view, int indicatorHeight) {
    this.view = view;
    this.indicatorHeight = indicatorHeight;
    paint = new Paint();
    drawRectF = new RectF();
  }

  public void setIndicatorPaddingLeft(int indicatorPaddingLeft) {
    this.indicatorPaddingLeft = indicatorPaddingLeft;
  }


  public void setIndicatorPaddingTop(int indicatorPaddingTop) {
    this.indicatorPaddingTop = indicatorPaddingTop;
  }

  public void setIndicatorPaddingRight(int indicatorPaddingRight) {
    this.indicatorPaddingRight = indicatorPaddingRight;
  }

  public void setIndicatorColor(int color) {
    paint.setColor(color);
  }

  @Override public void draw(@NonNull Canvas canvas) {
    //这里通过反射获取SlidingTabStrip的两个变量，源代码画的是下划线，我们现在画的是带圆角的矩形
    int mIndicatorLeft = getIntValue("indicatorLeft") + indicatorPaddingLeft;
    int mIndicatorRight = getIntValue("indicatorRight") - indicatorPaddingRight;
    int mSelectedIndicatorHeight = getIntValue("selectedIndicatorHeight");
    int height = view.getHeight();
    if (mIndicatorLeft >= 0 && mIndicatorRight > mIndicatorLeft) {
      drawRectF.set(mIndicatorLeft, height - mSelectedIndicatorHeight - DensityUtil.dip2px(indicatorHeight) + indicatorPaddingTop, mIndicatorRight,
          height - DensityUtil.dip2px(indicatorHeight) + indicatorPaddingTop);
      canvas.drawRoundRect(drawRectF, radius, radius, paint);
    }
  }


  public void setRadius(int radius) {
    this.radius = radius;
  }


  private int getIntValue(String name) {
    try {
      Field f = view.getClass().getDeclaredField(name);
      f.setAccessible(true);
      Object obj = f.get(view);
      return (Integer) obj;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }

  @Override public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

  }

  @Override public void setColorFilter(@Nullable ColorFilter colorFilter) {

  }

  @Override public int getOpacity() {
    return PixelFormat.UNKNOWN;
  }
}