package com.qsd.jmwh.module.home.radio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarFragment;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.module.home.radio.adapter.HomeRadioRvAdapter;
import com.qsd.jmwh.module.home.radio.bean.GetRadioConfigListBean;
import com.qsd.jmwh.module.home.radio.bean.HomeRadioListBean;
import com.qsd.jmwh.module.home.radio.bean.LocalHomeRadioListBean;
import com.qsd.jmwh.module.home.radio.dialog.RadioItemPop;
import com.qsd.jmwh.module.home.radio.presenter.RadioPresenter;
import com.qsd.jmwh.module.home.radio.presenter.RadioViewer;
import com.qsd.jmwh.module.register.DateRangeActivity;
import com.qsd.jmwh.module.register.bean.RangeData;
import com.qsd.jmwh.utils.DialogUtils;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.common.ui.DelayClickImageView;
import com.yu.common.ui.DelayClickTextView;

import java.util.ArrayList;
import java.util.List;

import static com.qsd.jmwh.module.register.EditUserDataActivity.DATE_RANGE_REQUEST_CODE;

/**
 * @author yudneghao
 * @date 2018/6/12
 */

public class RadioFragment extends BaseBarFragment implements RadioViewer {
    private List<LocalHomeRadioListBean> dataList = new ArrayList<>();
    @PresenterLifeCycle
    RadioPresenter mPresenter = new RadioPresenter(this);
    private RecyclerView rv_radio;
    private LinearLayout ll_empty;
    private DialogUtils typeDialog;
    private int sexType = 0;
    private int disType = 0;
    private DelayClickTextView right_menu;
    private DelayClickTextView left_menu;
    private TextView tv_left;
    private HomeRadioRvAdapter adapter;

    @Override
    protected int getActionBarLayoutId() {
        return R.layout.rang_bar_layout;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.radio_fragment;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        initView();
    }

    @Override
    protected void loadData() {
        mPresenter.initRadioDataAll("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), "", "1", "0");
    }

    private void initView() {
        tv_left = bindView(R.id.tv_left);
        TextView tv_right = bindView(R.id.tv_right);
        right_menu = bindView(R.id.right_menu);
        left_menu = bindView(R.id.left_menu);
        rv_radio = bindView(R.id.rv_radio);
        ll_empty = bindView(R.id.ll_empty);
        rv_radio.setLayoutManager(new LinearLayoutManager(getActivity()));
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLaunchHelper().startActivityForResult(DateRangeActivity.getIntent(getActivity(), 1, UserProfile.getInstance().getAppToken(), UserProfile.getInstance().getAppAccount(), "约会范围"), DATE_RANGE_REQUEST_CODE);
            }
        });
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.initRadioConfigData("0");
            }
        });

        left_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTypeDialog(1);
            }
        });

        right_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTypeDialog(2);
            }
        });
    }


    @Override
    public void getDataSuccess(HomeRadioListBean homeRadioListBean) {
        if (homeRadioListBean != null) {
            if (homeRadioListBean.cdoList != null && homeRadioListBean.cdoList.size() != 0) {
                dataList.clear();
                for (int i = 0; i < homeRadioListBean.cdoList.size(); i++) {
                    HomeRadioListBean.CdoListBean cdoListBean = homeRadioListBean.cdoList.get(i);
                    LocalHomeRadioListBean localHomeRadioListTitleBean = new LocalHomeRadioListBean();
                    if (cdoListBean.cdoUserData != null) {
                        localHomeRadioListTitleBean.headImg = cdoListBean.cdoUserData.sUserHeadPic;
                        localHomeRadioListTitleBean.userName = cdoListBean.cdoUserData.sNickName;
                    }
                    localHomeRadioListTitleBean.sDatingRange = cdoListBean.sDatingRange;
                    localHomeRadioListTitleBean.sDatingTime = cdoListBean.sDatingTime;
                    localHomeRadioListTitleBean.sContent = cdoListBean.sContent;
                    localHomeRadioListTitleBean.sDatingTitle = cdoListBean.sDatingTitle;
                    localHomeRadioListTitleBean.sex = cdoListBean.nSex;
                    localHomeRadioListTitleBean.cTime = cdoListBean.dCreateTime;
                    localHomeRadioListTitleBean.itemType = 0;
                    dataList.add(localHomeRadioListTitleBean);

                    LocalHomeRadioListBean localHomeRadioListPicBean = new LocalHomeRadioListBean();
                    localHomeRadioListPicBean.picList = cdoListBean.sImg;
                    localHomeRadioListPicBean.itemType = 1;
                    dataList.add(localHomeRadioListPicBean);

                    LocalHomeRadioListBean localHomeRadioListBottomBean = new LocalHomeRadioListBean();
                    localHomeRadioListBottomBean.is_like = cdoListBean.bLove;
                    localHomeRadioListBottomBean.like_count = cdoListBean.nLikeCount;
                    localHomeRadioListBottomBean.count_num = cdoListBean.nCommentCount;
                    localHomeRadioListBottomBean.is_apply = cdoListBean.bApply;
                    localHomeRadioListBottomBean.lDatingId = cdoListBean.lDatingId;
                    localHomeRadioListBottomBean.lUserId = cdoListBean.lUserId;
                    localHomeRadioListBottomBean.nApplyCount = cdoListBean.nApplyCount;
                    localHomeRadioListBottomBean.itemType = 2;
                    dataList.add(localHomeRadioListBottomBean);
                }

                if (adapter == null) {
                    adapter = new HomeRadioRvAdapter(dataList, getActivity());
                    rv_radio.setAdapter(adapter);
                } else {
                    adapter.setNewData(dataList);
                }
                adapter.setOnPersonItemClickListener(new HomeRadioRvAdapter.OnRadioItemClickListener() {
                    @Override
                    public void setOnRadioItemClick(DelayClickImageView iv_like, DelayClickTextView tv_like, int position, int is_like, String lDatingId, String lJoinerId, String lInitiatorId) {
                        mPresenter.addDatingLikeCount(lDatingId, lJoinerId, lInitiatorId, iv_like, tv_like, position, is_like);
                    }
                });
                ll_empty.setVisibility(View.GONE);
            } else {
                ll_empty.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public void getConfigDataSuccess(GetRadioConfigListBean configListBean) {
        if (configListBean != null && configListBean.cdoList != null && configListBean.cdoList.size() != 0) {
            RadioItemPop infoPop = new RadioItemPop(getActivity());
            infoPop.setOutsideTouchable(true);
            infoPop.setTitle("发布约会广播").setData(configListBean.cdoList).showPopupWindow();
        }
    }

    @Override
    public void addDatingLikeCountSuccess(DelayClickImageView iv_like, DelayClickTextView tv_like, int position, int is_like) {
        if (is_like == 1) {
            dataList.get(position).is_like = 0;
            dataList.get(position).like_count = dataList.get(position).like_count - 1;
            tv_like.setText("赞(" + dataList.get(position).like_count + ")");
            iv_like.setImageResource(R.drawable.zan);
            ToastUtils.show("取消点赞成功");
        } else {
            dataList.get(position).is_like = 1;
            dataList.get(position).like_count = dataList.get(position).like_count + 1;
            tv_like.setText("赞(" + dataList.get(position).like_count + ")");
            iv_like.setImageResource(R.drawable.zan_select);
            ToastUtils.show("点赞成功");
        }
    }


    /**
     * 弹窗
     */
    private void showTypeDialog(int type) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ll_top:
                        if (typeDialog.isShowing()) {
                            typeDialog.dismiss();
                        }
                        if (type == 1) {
                            disType = 0;
                            left_menu.setText("最新发布");
                            switch (sexType) {
                                case 0:
                                    mPresenter.initRadioDataAll("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), "", "1", "0");
                                    break;
                                case 1:
                                    mPresenter.initRadioData("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), "", "1", "0", "0");
                                    break;
                                case 2:
                                    mPresenter.initRadioData("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), "", "1", "0", "1");
                                    break;
                            }
                        } else {
                            sexType = 0;
                            right_menu.setText("全部");
                            switch (disType) {
                                case 0:
                                    mPresenter.initRadioDataAll("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), "", "1", "0");
                                    break;
                                case 1:
                                    mPresenter.initRadioDataAll("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), "", "2", "0");
                                    break;
                            }

                        }
                        break;
                    case R.id.ll_bottom:
                        if (typeDialog.isShowing()) {
                            typeDialog.dismiss();
                        }
                        disType = 1;
                        if (type == 1) {
                            switch (sexType) {
                                case 0:
                                    mPresenter.initRadioDataAll("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), "", "2", "0");
                                    break;
                                case 1:
                                    mPresenter.initRadioData("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), "", "2", "0", "0");
                                    break;
                                case 2:
                                    mPresenter.initRadioData("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), "", "2", "0", "1");
                                    break;
                            }
                            left_menu.setText("最近约会");
                        } else {
                            sexType = 1;
                            right_menu.setText("女士");
                            switch (disType) {
                                case 0:
                                    mPresenter.initRadioData("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), "", "1", "0", "0");
                                    break;
                                case 1:
                                    mPresenter.initRadioData("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), "", "2", "0", "0");
                                    break;
                            }

                        }

                        break;

                    case R.id.ll_other:
                        if (typeDialog.isShowing()) {
                            typeDialog.dismiss();
                        }
                        sexType = 2;
                        right_menu.setText("男士");
                        switch (disType) {
                            case 0:
                                mPresenter.initRadioData("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), "", "1", "0", "1");
                                break;
                            case 1:
                                mPresenter.initRadioData("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), "", "2", "0", "1");
                                break;
                        }

                        break;
                }
            }
        };

        typeDialog = new DialogUtils.Builder(getActivity())
                .view(R.layout.dialog_radio_type)
                .settext(type == 1 ? "选择排序条件" : "选择过滤条件", R.id.tv_title)
                .setLeftButton(type == 1 ? "最新发布" : "全部", R.id.tv_top)
                .setRightButton(type == 1 ? "最近约会" : "女士", R.id.tv_bottom)
                .gravity(Gravity.BOTTOM)
                .style(R.style.Dialog)
                .cancelTouchout(true)
                .addViewOnclick(R.id.ll_bottom, listener)
                .addViewOnclick(R.id.ll_top, listener)
                .addViewOnclick(R.id.ll_other, listener)
                .build();
        typeDialog.show();
        if (type == 1) {
            typeDialog.findViewById(R.id.ll_other).setVisibility(View.GONE);
        } else {
            typeDialog.findViewById(R.id.ll_other).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getBundleExtra(DateRangeActivity.RANGE_RESULT);
            RangeData.Range range = (RangeData.Range) bundle.getSerializable(DateRangeActivity.RANGE_RESULT);
            if (range != null) {
                if (requestCode == DATE_RANGE_REQUEST_CODE) {
                    tv_left.setText(range.sName);
                    switch (sexType) {
                        case 0:
                            mPresenter.initRadioDataAll("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), range.sName + "", (disType == 0 ? 1 : 2) + "", "0");
                            break;
                        case 1:
                            mPresenter.initRadioData("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), range.sName + "", (disType == 0 ? 1 : 2) + "", "0", "0");
                            break;
                        case 2:
                            mPresenter.initRadioData("", UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), range.sName + "", (disType == 0 ? 1 : 2) + "", "0", "1");
                            break;
                    }

                }
            }
        }
    }
}
