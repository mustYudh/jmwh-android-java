package com.yu.common.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.yu.common.R;

/**
 * @author chenwei
 * @date 2017/8/17
 */
public class BarIconContainer extends RelativeLayout {

  public BarIconContainer(Context context) {
    this(context, null);
  }

  public BarIconContainer(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public BarIconContainer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    LayoutInflater.from(context).inflate(R.layout.layout_bar_icon_container, this, true);

    ImageView barIcon = (ImageView) findViewById(R.id.bar_icon);
    View barUnRead = findViewById(R.id.bar_unread);

    if (attrs != null) {
      TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.BarIconContainer);

      if (a.getResourceId(R.styleable.BarIconContainer_bar_icon, 0) != 0) {
        barIcon.setImageResource(a.getResourceId(R.styleable.BarIconContainer_bar_icon, 0));
      }
      if (a.getResourceId(R.styleable.BarIconContainer_bar_unread_icon, 0) != 0) {
        barUnRead.setBackgroundResource(
            a.getResourceId(R.styleable.BarIconContainer_bar_unread_icon, 0));
      }
      a.recycle();
    }
  }

  public void showUnRead(int count) {
    TextView barUnread = (TextView) findViewById(R.id.bar_unread);
    if (barUnread == null) {
      return;
    }
    if (count <= 0) {
      barUnread.setVisibility(GONE);
    } else {
      barUnread.setVisibility(VISIBLE);
      barUnread.setText(count < 10 ? String.valueOf(count) : "···");
    }
  }
}