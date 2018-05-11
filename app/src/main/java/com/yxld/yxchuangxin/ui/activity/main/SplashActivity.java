package com.yxld.yxchuangxin.ui.activity.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.socks.library.KLog;
import com.umeng.analytics.MobclickAgent;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.application.AppConfig;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.ui.activity.login.LoginActivity;
import com.yxld.yxchuangxin.ui.activity.main.component.DaggerSplashComponent;
import com.yxld.yxchuangxin.ui.activity.main.contract.SplashContract;
import com.yxld.yxchuangxin.ui.activity.main.module.SplashModule;
import com.yxld.yxchuangxin.ui.activity.main.presenter.SplashPresenter;
import com.zhy.autolayout.AutoRelativeLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yxld.yxchuangxin.ui.activity.mine.FingerProveActivity.SP_FINGER_STATE;
import static com.yxld.yxchuangxin.ui.activity.mine.FingerProveActivity.SP_PATTERN_STATE;

/**
 * @author hu
 * @Package com.yxld.yxchuangxin.ui.activity.main
 * @Description: $description
 * @date 2017/06/05
 */

public class SplashActivity extends BaseActivity implements SplashContract.View {

    @Inject
    SplashPresenter mPresenter;
    @BindView(R.id.main)
    AutoRelativeLayout mMain;
    @BindView(R.id.img_bg)
    ImageView mImgBg;
    @BindView(R.id.tv_jump)
    TextView mTvJump;

    private android.support.v7.app.AlertDialog dialog;
    public static int LOCATION_FINISH = 65;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        KLog.i("Splash创建");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        Glide.with(this).load("https://timgsa.baidu" + "" + "" + "" + "" +
                ".com/timg?image&quality=80&size=b9999_10000&sec=1525959683754&di=5b45848a904c10b6e2b352a86251713a" +
                "&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu" + "" + "" + "" + "" +
                ".com%2Fimgad%2Fpic%2Fitem%2F4afbfbedab64034f788a1cd2a5c379310b551d9a.jpg").into(mImgBg);
        countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                KLog.i("--------" + (millisUntilFinished / 1000));
                mTvJump.setText("跳过(" + (millisUntilFinished / 1000) + ")");
            }

            @Override
            public void onFinish() {
                KLog.i("--------" + 0);
                mTvJump.setText("跳过(" + 0 + ")");
                jumpWhenCanClick();
            }
        };
        AppConfig.getInstance().mAppActivityManager.finishAllActivityWithoutThis();
        if (SplashPresenter.isXsqAvilible(this)) {
            showHasOldDialog();
        } else {

//            mPresenter.observeJump();
            countDownTimer.start();
            mPresenter.queryShipperInfo();
            mPresenter.getPermission();
            mPresenter.getLastVersion();
        }
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        setToorBar(false);
    }

//    private void checkFinger() {
//        if (!sp.getBoolean("openFinger", false)) {
//
//            dialog = new android.support.v7.app.AlertDialog.Builder(this)
//                    .setTitle("提示")
//                    .setMessage("\n   是否开启指纹登录")
//                    .setIcon(R.mipmap.finger_blue)
//                    .setCancelable(false)
//                    .setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
//
//                        @Override
//                        public void onClick(DialogInterface dialog, int i) {
//                            dialog.dismiss();
//                        }
//                    })
//                    .setPositiveButton("确认设置", null)
//                    .create();
//            dialog.show();
//
//            dialog.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View
// .OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    jsFingerUtils.startListening(SplashActivity.this);
//                }
//            });
//        }
//    }

    @Override
    protected void setupActivityComponent() {
        DaggerSplashComponent.builder().appComponent(((AppConfig) getApplication()).getApplicationComponent())
                .splashModule(new SplashModule(this)).build().inject(this);
    }

    @Override
    public void setPresenter(SplashContract.SplashContractPresenter presenter) {
        mPresenter = (SplashPresenter) presenter;
    }

    @Override
    public void loginSuccees() {
        if (sp.getBoolean(SP_FINGER_STATE, false) || sp.getBoolean(SP_PATTERN_STATE, false)) {
            startActivity(FingerLoginActivity.class);
            finish();
        } else {
            startActivity(HomeActivity.class);
            finish();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    @Override
    public void jumpToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("flag", "splash");
        startActivity(intent);
        finish();
    }

    @Override
    public void iumpToGuest() {
        startActivity(GuestActivity.class);
        finish();
    }

    private void showHasOldDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("存在老版本").setIcon(R.mipmap.logo).setMessage("请先卸载老版本的app!!!").setPositiveButton("确定", new
                DialogInterface.OnClickListener() {// 积极
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getAppDetailSettingIntent(SplashActivity.this);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {// 消极
            @Override
            public void onClick(DialogInterface dialog, int which) {
                countDownTimer.start();
                // mPresenter.observeJump();
                mPresenter.queryShipperInfo();
                mPresenter.getPermission();
                mPresenter.getLastVersion();
            }
        });
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    /**
     * 不可点击的开屏，使用该jump方法，而不是用jumpWhenCanClick
     */
    private void jump() {
        if (!hasJump) {
            countDownTimer.cancel();
            hasJump = true;
            mPresenter.observeJump();
        }
    }

    /**
     * 当设置开屏可点击时，需要等待跳转页面关闭后，再切换至您的主窗口。故此时需要增加canJumpImmediately判断。 另外，点击开屏还需要在onResume中调用jumpWhenCanClick接口。
     */
    public boolean canJumpImmediately = false;
    boolean hasJump = false;

    private void jumpWhenCanClick() {
        Log.d("test", "this.hasWindowFocus():" + this.hasWindowFocus() + "canJumpImmediately" + canJumpImmediately);
        if (!hasJump) {
            if (canJumpImmediately) {
                hasJump = true;
                countDownTimer.cancel();
                mPresenter.observeJump();
            } else {
                canJumpImmediately = true;
            }
        }
    }

    //        以下代码可以跳转到应用详情，可以通过应用详情跳转到权限界面(6.0系统测试可用)
    private void getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", "com.yxld.yxchuangxin", null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", "com.yxld.yxchuangxin");
        }
        startActivity(localIntent);
    }


//    public void onResume() {
//        super.onResume();
//        MobclickAgent.onResume(this);
//    }


    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);//统计时长
        canJumpImmediately = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);//统计时长
        if (canJumpImmediately) {
            jumpWhenCanClick();
        }
        canJumpImmediately = true;
    }

    private void toUrl(String url) {
        Intent intent = new Intent();
        intent.setData(Uri.parse(url));//Url 就是你要打开的网址
        intent.setAction(Intent.ACTION_VIEW);
        this.startActivity(intent); //启动浏览器
    }


    @OnClick({R.id.img_bg, R.id.tv_jump})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_bg:
                toUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1525959683754&di" +
                        "=5b45848a904c10b6e2b352a86251713a&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu" + "" + "" + "" +
                        "" + ".com%2Fimgad%2Fpic%2Fitem%2F4afbfbedab64034f788a1cd2a5c379310b551d9a.jpg");
                break;
            case R.id.tv_jump:
                hasJump = true;
                countDownTimer.cancel();
                mPresenter.observeJump();
                break;
            default:
                break;
        }
    }
}