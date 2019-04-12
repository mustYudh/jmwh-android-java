package com.qsd.jmwh.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qsd.jmwh.R;

@SuppressLint("Recycle") public class NormaFormItemVIew extends LinearLayout {

  private TextView rightBtn;
  private EditText mEdit;
  private String mRightBtnText;

  public NormaFormItemVIew(Context context) {
    super(context, null);
  }

  public NormaFormItemVIew(Context context, AttributeSet attrs) {
    super(context, attrs, 0);
    initView(context, attrs);
  }

  public NormaFormItemVIew(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  private void initView(Context context, AttributeSet attrs) {
    LayoutInflater.from(context).inflate(R.layout.inflate_normal_item_layout, this, true);
    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NormaFormItemVIew);
    View topLine = findViewById(R.id.top_line);
    View bottomLine = findViewById(R.id.bottom_line);
    TextView title = findViewById(R.id.title);
    mEdit = findViewById(R.id.edit);
    TextView content = findViewById(R.id.content);
    rightBtn = findViewById(R.id.right_btn);
    String hint = typedArray.getString(R.styleable.NormaFormItemVIew_content_hint);
    String titleText = typedArray.getString(R.styleable.NormaFormItemVIew_left_text);
    String text = typedArray.getString(R.styleable.NormaFormItemVIew_content_text);
    mRightBtnText = typedArray.getString(R.styleable.NormaFormItemVIew_right_button_hint);
    boolean showTopLine = typedArray.getBoolean(R.styleable.NormaFormItemVIew_show_top_line, true);
    boolean showBottomLine =
        typedArray.getBoolean(R.styleable.NormaFormItemVIew_show_bottom_line, true);
    boolean onlyRead = typedArray.getBoolean(R.styleable.NormaFormItemVIew_only_read, false);
    topLine.setVisibility(showTopLine ? View.VISIBLE : View.GONE);
    bottomLine.setVisibility(showBottomLine ? View.VISIBLE : View.GONE);
    if (!TextUtils.isEmpty(titleText)) {
      title.setText(titleText);
    }
    if (!TextUtils.isEmpty(hint)) {
      mEdit.setHint(hint);
      mEdit.setVisibility(VISIBLE);
    } else {
      mEdit.setVisibility(GONE);
    }
    if (onlyRead) {
      mEdit.setVisibility(GONE);
      content.setVisibility(VISIBLE);
    } else {
      mEdit.setVisibility(VISIBLE);
      content.setVisibility(GONE);
    }

    if (!TextUtils.isEmpty(text)) {
      mEdit.setText(text);
      content.setText(text);
    }
    if (!TextUtils.isEmpty(mRightBtnText)) {
      rightBtn.setText(mRightBtnText);
      rightBtn.setVisibility(VISIBLE);
    }
  }


  public void setRightHint(String hint) {
    if (!TextUtils.isEmpty(hint)) {
      rightBtn.setText(hint);
    }
  }

  public void setRightButtonEnable(boolean enable) {
    rightBtn.setEnabled(enable);
  }

  public String getEditText() {
    String text = mEdit.getText().toString().trim();
    return !TextUtils.isEmpty(text) ? text : "";
  }

  public void setRightButtonListener(View.OnClickListener listener) {
    rightBtn.setOnClickListener(listener);
  }
}
