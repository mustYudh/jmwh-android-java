package com.qsd.jmwh.module.home.park.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.module.home.park.adapter.PersonRvAdapter;
import com.qsd.jmwh.module.home.park.bean.HomePersonListBean;
import com.qsd.jmwh.module.home.park.presenter.SearchPresenter;
import com.qsd.jmwh.module.home.park.presenter.SearchViewer;
import com.qsd.jmwh.utils.DialogUtils;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.ui.DelayClickImageView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseBarActivity implements SearchViewer {
    private RecyclerView rv_person;
    @PresenterLifeCycle
    SearchPresenter mPresenter = new SearchPresenter(this);
    private PersonRvAdapter adapter;
    private DialogUtils loveDialog;
    private List<HomePersonListBean.CdoListBean> list = new ArrayList<>();
    private LinearLayout ll_empty;
    private String sex;

    @Override
    protected int getActionBarLayoutId() {
        return R.layout.search_bar_layout;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);

    }

    @Override
    protected void loadData() {
        sex = getIntent().getStringExtra("SEX");
        EditText edit = bindView(R.id.edit);
        LinearLayout ll_back = bindView(R.id.ll_back);
        ll_empty = bindView(R.id.ll_empty);
        rv_person = bindView(R.id.rv_person);
        rv_person.setLayoutManager(new LinearLayoutManager(getActivity()));


        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager inputMgr = (InputMethodManager) SearchActivity.this
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMgr.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
                    if (TextUtils.isEmpty(edit.getText().toString().trim())) {
                        ToastUtils.show("请输入搜索内容");
                    } else {
                        mPresenter.initPersonListData(UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), "3", edit.getText().toString() + "", "0", sex + "", UserProfile.getInstance().getHomeCityName());
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void getDataSuccess(HomePersonListBean homePersonListBean) {
        if (homePersonListBean != null) {
            if (homePersonListBean.cdoList != null && homePersonListBean.cdoList.size() != 0) {
                list = homePersonListBean.cdoList;
                if (adapter == null) {
                    adapter = new PersonRvAdapter(R.layout.item_person, homePersonListBean.cdoList, getActivity(), sex);
                    rv_person.setAdapter(adapter);
                } else {
                    adapter.setNewData(homePersonListBean.cdoList);
                }
                adapter.setOnItemClickListener((adapter, view, position) -> {
                    HomePersonListBean.CdoListBean cdoListBean = (HomePersonListBean.CdoListBean) adapter.getData().get(position);
                    getLaunchHelper().startActivity(LookUserInfoActivity.getIntent(getActivity(), cdoListBean.lUserId));
                });

                adapter.setOnPersonItemClickListener(new PersonRvAdapter.OnPersonItemClickListener() {
                    @Override
                    public void setOnPersonItemClick(DelayClickImageView iv_love, int position, boolean is_love, String lLoveUserId) {
                        if (is_love) {
                            showLoveDialog(lLoveUserId, iv_love, position);
                        } else {
                            mPresenter.initAddLoveUser(lLoveUserId, "0", position, iv_love);
                        }
                    }
                });
                rv_person.setVisibility(View.VISIBLE);
                ll_empty.setVisibility(View.GONE);
            } else {
                //空页面
                rv_person.setVisibility(View.GONE);
                ll_empty.setVisibility(View.VISIBLE);
            }
        }
    }


    //收藏成功
    @Override
    public void addLoveUserSuccess(int position, DelayClickImageView iv_love) {
        list.get(position).blove = true;
        iv_love.setImageResource(R.drawable.collect_select);
        ToastUtils.show("收藏成功");
    }

    @Override
    public void delLoveUserSuccess(int position, DelayClickImageView iv_love) {
        list.get(position).blove = false;
        iv_love.setImageResource(R.drawable.collect);
        ToastUtils.show("取消收藏成功");
    }


    /**
     * 取消收藏弹窗
     */
    private void showLoveDialog(String lLoveUserId, DelayClickImageView iv_love, int position) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.cancle:
                        if (loveDialog.isShowing()) {
                            loveDialog.dismiss();
                        }
                        break;
                    case R.id.down:
                        if (loveDialog.isShowing()) {
                            loveDialog.dismiss();
                        }
                        mPresenter.initdelLoveUser(lLoveUserId, "0", position, iv_love);
                        break;
                }
            }
        };

        loveDialog = new DialogUtils.Builder(getActivity())
                .view(R.layout.dialog_layout)
                .settext("确定要取消收藏吗？", R.id.dialog_content)
                .setLeftButton("取消", R.id.cancle)
                .setRightButton("确定", R.id.down)
                .gravity(Gravity.CENTER)
                .style(R.style.Dialog_NoAnimation)
                .cancelTouchout(false)
                .addViewOnclick(R.id.cancle, listener)
                .addViewOnclick(R.id.down, listener)
                .build();
        loveDialog.show();
    }
}
