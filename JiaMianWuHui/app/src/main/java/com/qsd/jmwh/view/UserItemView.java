package com.qsd.jmwh.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qsd.jmwh.R;

import ch.ielse.view.SwitchView;

public class UserItemView extends LinearLayout {

    private SwitchView switchView;
    private ImageView selectBtn;

    private boolean buttonSelected;
    private TextView hintTextView;

    public UserItemView(Context context) {
        super(context, null);
    }

    public UserItemView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        initView(context, attrs);
    }


    public UserItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("Recycle")
    private void initView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UserItemView);
        View view = LayoutInflater.from(context).inflate(R.layout.inflate_user_item, this, true);
        TextView leftTextView = findViewById(R.id.left_text);
        switchView = findViewById(R.id.switch_button);
        selectBtn = findViewById(R.id.select_button);
        ImageView arrow = findViewById(R.id.arrow);
        hintTextView = findViewById(R.id.right_hint);
        View line = findViewById(R.id.bottom_line);
        String leftText = typedArray.getString(R.styleable.UserItemView_user_left_text);
        String hintText = typedArray.getString(R.styleable.UserItemView_user_hint_text);
        int leftTextColor = typedArray.getColor(R.styleable.UserItemView_left_text_color, -1);
        int hintTextColor = typedArray.getColor(R.styleable.UserItemView_hint_text_color, -1);
        boolean showLine = typedArray.getBoolean(R.styleable.UserItemView_show_line, true);
        boolean showArrow = typedArray.getBoolean(R.styleable.UserItemView_showArrow, true);
        String type = typedArray.getString(R.styleable.UserItemView_type);
        if (!TextUtils.isEmpty(type)) {
            switch (type) {
                case "switch":
                    arrow.setVisibility(GONE);
                    switchView.setVisibility(VISIBLE);
                    break;
                case "selected":
                    arrow.setVisibility(GONE);
                    selectBtn.setVisibility(VISIBLE);
                    selectBtn.setOnClickListener(v -> setButtonSelected(!buttonSelected));
                    break;
            }
        }
        line.setVisibility(showLine ? VISIBLE : GONE);
        arrow.setVisibility(showArrow ? VISIBLE : GONE);
        if (!TextUtils.isEmpty(leftText)) {
            leftTextView.setText(leftText);
        }
        if (!TextUtils.isEmpty(hintText)) {
            hintTextView.setText(hintText);
        }
        if (leftTextColor != -1) {
            leftTextView.setTextColor(leftTextColor);
        }
        if (hintTextColor != -1) {
            hintTextView.setTextColor(hintTextColor);
        }
    }

    public UserItemView setButtonSelected(boolean selected) {
        if (selectBtn != null) {
            selectBtn.setSelected(selected);
            buttonSelected = selected;
        }
        return this;
    }

    public void showTag(boolean show) {
        TextView textView = findViewById(R.id.vip_tag);
        textView.setVisibility(show ? VISIBLE : GONE);
    }


    public boolean isButtonSelected() {
        return buttonSelected;
    }

    public boolean getSwitched() {
        if (switchView != null) {
            return switchView.isOpened();
        }
        return false;
    }

    public UserItemView setSwichButton(boolean selected) {
        if (switchView != null) {
            switchView.toggleSwitch(selected);
        }
        return this;
    }

    public UserItemView setHint(String text) {
        if (hintTextView != null && !TextUtils.isEmpty(text)) {
            hintTextView.setText(text);
        }
        return this;
    }


    public UserItemView setSwichlistener(SwitchView.OnStateChangedListener swichlistener) {
        if (switchView != null && swichlistener != null) {
            switchView.setOnStateChangedListener(swichlistener);
        }
        return this;
    }


}
