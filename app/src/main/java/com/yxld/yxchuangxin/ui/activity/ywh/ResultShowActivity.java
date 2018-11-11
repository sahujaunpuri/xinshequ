package com.yxld.yxchuangxin.ui.activity.ywh;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.application.AppConfig;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.ui.activity.ywh.component.DaggerResultShowComponent;
import com.yxld.yxchuangxin.ui.activity.ywh.contract.ResultShowContract;
import com.yxld.yxchuangxin.ui.activity.ywh.module.ResultShowModule;
import com.yxld.yxchuangxin.ui.activity.ywh.presenter.ResultShowPresenter;
import com.zhy.autolayout.AutoLinearLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author William
 * @Package com.yxld.yxchuangxin.ui.activity.wuye
 * @Description: 业主大会结果公示
 * @date 2018/11/07 20:09:47
 */

public class ResultShowActivity extends BaseActivity implements ResultShowContract.View {

    @Inject
    ResultShowPresenter mPresenter;
    @BindView(R.id.title_recommend_member)
    TextView titleRecommendMember;
    @BindView(R.id.tv_send_time)
    TextView tvSendTime;
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.tv_click_name1)
    TextView tvClickName1;
    @BindView(R.id.tv_click_name2)
    TextView tvClickName2;
    @BindView(R.id.autoll)
    AutoLinearLayout autoll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_ywh_checknotice);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvMenu.setVisibility(View.VISIBLE);
        tvMenu.setText("意见反馈");
        tvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(FkyjActivity.class);
            }
        });

    }

    @Override
    protected void initData() {
        //网络请求 获得数据
//        Map<String, String> map = new HashMap<>();
//        map.put("uuid", Contains.uuid);
//        mPresenter.getData(map);
    }


    @Override
    public void setData(BaseEntity baseEntity) {

    }

    @Override
    protected void onDestroy() {
        mPresenter.unsubscribe();
        super.onDestroy();
    }

    @Override
    protected void setupActivityComponent() {
        DaggerResultShowComponent
                .builder()
                .appComponent(((AppConfig) getApplication()).getApplicationComponent())
                .resultShowModule(new ResultShowModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void setPresenter(ResultShowContract.ResultShowContractPresenter presenter) {
        mPresenter = (ResultShowPresenter) presenter;
    }

    @Override
    public void showProgressDialog() {
        progressDialog.show();
    }

    @Override
    public void closeProgressDialog() {
        progressDialog.hide();
    }

    @OnClick({R.id.tv_click_name1, R.id.tv_click_name2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_click_name1:
                Intent intent = new Intent(this, YwhMemberShowActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_click_name2:
                break;
        }
    }
}