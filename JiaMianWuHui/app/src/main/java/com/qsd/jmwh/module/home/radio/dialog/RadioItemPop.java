package com.qsd.jmwh.module.home.radio.dialog;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.module.home.radio.activity.ReleaseAppointmentActivity;
import com.qsd.jmwh.module.home.radio.adapter.DateRadioListDialogAdapter;
import com.qsd.jmwh.module.home.radio.bean.GetRadioConfigListBean;
import com.yu.common.windown.BasePopupWindow;

import java.util.ArrayList;
import java.util.List;

public class RadioItemPop extends BasePopupWindow {
    private List<String> selectedProject = new ArrayList<>();

    private SelectedProjectListener selectedProjectListener;

    public interface SelectedProjectListener {
        void onSelected(List<String> selected);
    }

    public RadioItemPop setOnSelectedProjectListener(SelectedProjectListener selectedProjectListener) {
        this.selectedProjectListener = selectedProjectListener;
        return this;
    }
    public RadioItemPop setTitle(String titleName) {
        TextView title = bindView(R.id.title);
        if (!TextUtils.isEmpty(titleName) && title != null) {
            title.setText(titleName);
        }
        return this;
    }

    public RadioItemPop(Context context) {
        super(context, LayoutInflater.from(context).inflate(R.layout.rang_item_layout, null),
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        initView();
    }

    private void initView() {
        bindView(R.id.ll_bottom).setVisibility(View.GONE);
        bindView(R.id.cancel, v -> dismiss());
        bindView(R.id.rl_root, v -> dismiss());
        bindView(R.id.ok, v -> {
            if (selectedProjectListener != null) {
                selectedProjectListener.onSelected(selectedProject);
            }
        });
    }

    public RadioItemPop setData(List<GetRadioConfigListBean.CdoListBean> sDateProList) {
        assert sDateProList != null;

        RecyclerView recyclerView = bindView(R.id.list_data);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
        DateRadioListDialogAdapter adapter = new DateRadioListDialogAdapter(R.layout.item_date_radio_layout, sDateProList,getContext());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            getContext().startActivity(new Intent(getContext(),ReleaseAppointmentActivity.class).putExtra("activity_title",sDateProList.get(position).sDatingConfigName));
        });
        return this;
    }

    @Override
    public void dismiss() {
        selectedProject.clear();
        super.dismiss();
    }

    @Override
    protected View getBackgroundShadow() {
        return null;
    }

    @Override
    protected View getContainer() {
        return null;
    }
}
