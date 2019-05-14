package com.qsd.jmwh.module.home.user.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseFragment;
import com.qsd.jmwh.dialog.SelectHintPop;
import com.qsd.jmwh.module.home.user.adapter.WithdrawalAdapter;
import com.qsd.jmwh.module.home.user.bean.AccountBalance;
import com.qsd.jmwh.module.home.user.bean.MaskBallCoinBean;
import com.qsd.jmwh.module.home.user.dialog.PayPop;
import com.qsd.jmwh.module.home.user.dialog.SelePayTypePop;
import com.qsd.jmwh.module.home.user.presenter.JiaMianCoinPresenter;
import com.qsd.jmwh.module.home.user.presenter.JiaMianCoinViewer;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;

public class JiaMianCoinFragment extends BaseFragment implements View.OnClickListener, JiaMianCoinViewer {
    @PresenterLifeCycle
    private JiaMianCoinPresenter mPresenter = new JiaMianCoinPresenter(this);

    private int pageIndex;
    private RecyclerView recyclerView;

    @Override
    protected int getContentViewId() {
        return R.layout.jia_mian_coin_fragment;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
        bindView(R.id.withdrawal, this);
        bindView(R.id.top_up, this);
        bindView(R.id.ll_withdrawal, this);
        SmartRefreshLayout layout = bindView(R.id.refresh);
        layout.setOnRefreshListener(refreshLayout -> {
            pageIndex = 0;
            mPresenter.getInfo(pageIndex);
        });
        layout.setOnLoadMoreListener(refreshLayout -> {
            pageIndex++;
            mPresenter.getInfo(pageIndex);
        });
        recyclerView = bindView(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPresenter.getInfo(pageIndex);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_withdrawal:
                PayPop payPop = new PayPop(getActivity());
                payPop.setPositiveButton((account, userName) -> {
                    mPresenter.modifyAliPay(account, userName);
                    payPop.dismiss();
                }).setNegativeButton(v1 -> payPop.dismiss()).showPopupWindow();
                break;
            case R.id.withdrawal:
                SelectHintPop selectHintPop = new SelectHintPop(getActivity());
                selectHintPop.
                        setTitle("现金提现金额")
                        .setMessage("提现金额必须是10元的整数倍，如10、20、50、100 …")
                        .setEditButton("确定", input -> {
                            if (TextUtils.isEmpty(input)) {
                                ToastUtils.show("输入金额不能为空");
                            } else if (Integer.parseInt(input) % 10 != 0) {
                                ToastUtils.show("输入正确类型");
                            } else {
                                mPresenter.getCoinConvertMoney(Integer.parseInt(input));
                                selectHintPop.dismiss();
                            }
                        }).setBottomButton("取消", v12 -> {
                    selectHintPop.dismiss();
                });
                selectHintPop.showPopupWindow();
                break;
            case R.id.top_up:
                SelePayTypePop selePayTypePop = new SelePayTypePop(getActivity());
                selePayTypePop.showPopupWindow();
                break;

        }
    }

    @Override
    public void getInfo(AccountBalance accountBalance) {
        AccountBalance.CdoUserAccountListBean listBean = accountBalance.cdoUserAccountList;
        bindText(R.id.left_text, listBean.nMaskBallCoin + "");
        bindText(R.id.right_text, listBean.nRealMaskBallCoin + "");
        if (!TextUtils.isEmpty(listBean.sAliPayAccount) && !TextUtils.isEmpty(listBean.sAliPayName)) {
            bindText(R.id.ali_pay_hint, listBean.sAliPayAccount + "(" + listBean.sAliPayName + "）");
        }
        WithdrawalAdapter adapter = new WithdrawalAdapter(R.layout.item_withdrawal_layout, accountBalance.cdoAccountBalanceList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void coinConvertMoney(MaskBallCoinBean maskBallCoinBean) {
        SelectHintPop selectHintPop = new SelectHintPop(getActivity());
        selectHintPop.setMessage("你有" + maskBallCoinBean.nPayFee + "个假面币可以兑换成人民币" + maskBallCoinBean.nMoney + "元，确定申请兑换吗？")
                .setTitle("提现申请").setPositiveButton("确定", v -> {
            mPresenter.withdrawMaskBallCoin(maskBallCoinBean.nPayFee);
            selectHintPop.dismiss();
        }).setNegativeButton("取消", v -> selectHintPop.dismiss()).showPopupWindow();

    }
}
