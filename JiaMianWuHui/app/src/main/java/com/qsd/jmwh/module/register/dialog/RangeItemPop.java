package com.qsd.jmwh.module.register.dialog;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qsd.jmwh.R;
import com.qsd.jmwh.module.register.adapter.DateRangeProjectAdapter;
import com.qsd.jmwh.module.register.bean.ProjectBean;
import com.yu.common.windown.BasePopupWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RangeItemPop extends BasePopupWindow {
    private Map<Integer, String> selectedProject = new HashMap<>();

    private SelectedProjectListener selectedProjectListener;

    public interface SelectedProjectListener {
        void onSelected(Map<Integer, String> selected);
    }

    public RangeItemPop setOnSelectedProjectListener(SelectedProjectListener selectedProjectListener) {
        this.selectedProjectListener = selectedProjectListener;
        return this;
    }


    public RangeItemPop(Context context) {
        super(context, LayoutInflater.from(context).inflate(R.layout.rang_item_layout, null),
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        initView();
    }

    private void initView() {
        bindView(R.id.cancel, v -> dismiss());
        bindView(R.id.ok, v -> {
            if (selectedProjectListener != null) {
                selectedProjectListener.onSelected(selectedProject);
            }
            dismiss();
        });
    }

    public RangeItemPop setData(List<String> sDateProList) {
        assert sDateProList != null;
        List<ProjectBean> datas = new ArrayList<>();
        for (String data : sDateProList) {
            ProjectBean projectBean = new ProjectBean();
            projectBean.name = data;
            projectBean.selected = false;
            datas.add(projectBean);
        }
        RecyclerView recyclerView = bindView(R.id.list_data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DateRangeProjectAdapter adapter = new DateRangeProjectAdapter(R.layout.item_date_range_project_layout, datas);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            List<ProjectBean> data = adapter1.getData();
            data.get(position).selected = !data.get(position).selected;
            adapter1.notifyDataSetChanged();
            ProjectBean project = (ProjectBean) adapter1.getData().get(position);
            if (project.selected) {
                selectedProject.put(position, project.name);
            }
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
