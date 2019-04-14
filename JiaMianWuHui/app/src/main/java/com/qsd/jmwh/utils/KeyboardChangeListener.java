package com.qsd.jmwh.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;

import com.qsd.jmwh.R;


/**
 * 监听键盘弹起事件
 */
public class KeyboardChangeListener implements View.OnLayoutChangeListener {
  private View mContentView;
  private KeyBoardListener mKeyBoardListen;

  public interface KeyBoardListener {
    /**
     * call back
     *
     * @param isShow true is show else hidden
     * @param keyboardHeight keyboard height
     */
    void onKeyboardChange(boolean isShow, int keyboardHeight);
  }

  public void setKeyBoardListener(KeyBoardListener keyBoardListen) {
    this.mKeyBoardListen = keyBoardListen;
  }

  public KeyboardChangeListener(Activity contextObj) {
    if (contextObj == null) {
      return;
    }

    mContentView = findContentView(contextObj);
    if (mContentView != null) {
      addLayoutChangeListener();
    }
  }

  private View findContentView(Activity contextObj) {

    ViewGroup viewGroup = contextObj.findViewById(R.id.content_container);

    if (viewGroup == null) {
      viewGroup = contextObj.findViewById(android.R.id.content);
    }

    return viewGroup;
  }

  private void addLayoutChangeListener() {
    mContentView.addOnLayoutChangeListener(this);
  }

  @Override
  public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft,
      int oldTop, int oldRight, int oldBottom) {
    if (oldBottom != 0 && bottom != 0 && oldBottom != bottom) {
      if (Math.abs(oldBottom - bottom) > dip2px(80)) {


        if (mKeyBoardListen != null) {
          mKeyBoardListen.onKeyboardChange(oldBottom > bottom, Math.abs(oldBottom - bottom));
        }
      }
    }
  }

  private int dip2px(float dipValue) {
    float scale = Resources.getSystem().getDisplayMetrics().density;
    return (int) (dipValue * scale + 0.5F);
  }

  public void destroy() {
    if (mContentView != null) {
      mContentView.removeOnLayoutChangeListener(this);
      mContentView = null;
    }
  }
}