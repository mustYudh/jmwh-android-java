package com.qsd.jmwh.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qsd.jmwh.R;
import com.yu.common.windown.BasePopupWindow;

public class SelectGenderHintPop extends BasePopupWindow {
    private ItemClickListener itemClickListener;

    public interface  ItemClickListener {
        void onNext();
        void onCancel();
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public SelectGenderHintPop(Context context) {
        super(context, LayoutInflater.from(context).inflate(R.layout.select_gender_hint_pop_layout, null),
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        TextView ok = (TextView) findViewById(R.id.ok);
        TextView cancel = (TextView) findViewById(R.id.cancel);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onNext();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onCancel();
                }
            }
        });
    }

    @Override
    protected View getBackgroundShadow() {
         return findViewById(R.id.base_shadow);
    }

    @Override
    protected View getContainer() {
        return findViewById(R.id.base_container);
    }
}
