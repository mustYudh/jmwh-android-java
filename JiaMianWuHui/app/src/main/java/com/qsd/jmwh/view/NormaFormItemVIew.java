package com.qsd.jmwh.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qsd.jmwh.R;

@SuppressLint("Recycle")
public class NormaFormItemVIew extends LinearLayout {

    private TextView rightBtn;
    private EditText mEdit;
    private String mRightBtnText;
    private String textLength;
    private TextView content;
    private View rootView;

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
        rootView = LayoutInflater.from(context).inflate(R.layout.inflate_normal_item_layout, this, true);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NormaFormItemVIew);
        View topLine = findViewById(R.id.top_line);
        View bottomLine = findViewById(R.id.bottom_line);
        TextView title = findViewById(R.id.title);
        mEdit = findViewById(R.id.edit);
        content = findViewById(R.id.content);
        rightBtn = findViewById(R.id.right_btn);
        String hint = typedArray.getString(R.styleable.NormaFormItemVIew_content_hint);
        String titleText = typedArray.getString(R.styleable.NormaFormItemVIew_left_text);
        String text = typedArray.getString(R.styleable.NormaFormItemVIew_content_text);
        int hintColor = typedArray.getColor(R.styleable.NormaFormItemVIew_hint_color, getResources().getColor(R.color.color_999999));
        int textColor = typedArray.getColor(R.styleable.NormaFormItemVIew_content_color, getResources().getColor(R.color.color_999999));
        String inputType = typedArray.getString(R.styleable.NormaFormItemVIew_input_type);
        textLength = typedArray.getString(R.styleable.NormaFormItemVIew_length);
        if (!TextUtils.isEmpty(inputType)) {
            if (inputType.equals("password")) {
                mEdit.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
            } else if (inputType.equals("number")) {
                mEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
            } else {
                mEdit.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        }
        mEdit.setHintTextColor(hintColor);
        content.setHintTextColor(hintColor);
        content.setTextColor(textColor);
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
            content.setHint(hint);
            content.setTextColor(hintColor);
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
        if (textLength != null) {
            mEdit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String str = s.toString();
                    if (str.length() > Integer.parseInt(textLength)) {
                        mEdit.setText(str.substring(0, Integer.parseInt(textLength)));
                        mEdit.requestFocus();
                        mEdit.setSelection(mEdit.getText().length());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }


    }


    public void setRightHint(String hint) {
        if (!TextUtils.isEmpty(hint)) {
            rightBtn.setText(hint);
        }
    }

    public void setOnClickSelectedItem(View.OnClickListener onClickSeletedItem) {
        content.setOnClickListener(onClickSeletedItem);
    }

    public void setContentText(CharSequence contentText) {
        if (!TextUtils.isEmpty(contentText)) {
            content.setText(contentText);
            content.setTextColor(getContext().getResources().getColor(R.color.color_222222));
        } else {
            rootView.setVisibility(GONE);
        }
    }

    public void setContent(CharSequence contentText) {
        if (!TextUtils.isEmpty(contentText)) {
            content.setText(contentText);
        } else {
            rootView.setVisibility(GONE);
        }
    }

    public void setRightButtonEnable(boolean enable) {
        rightBtn.setEnabled(enable);
    }

    public String getText() {
        String text = mEdit.getText().toString().trim();
        if (!TextUtils.isEmpty(text)) {
            return text;
        } else {
            return content.getText().toString().trim();
        }

    }

    public void setRightButtonListener(View.OnClickListener listener) {
        rightBtn.setOnClickListener(listener);
    }
}
