package com.yxld.yxchuangxin.ui.activity.mine.presenter;
import android.support.annotation.NonNull;
import com.yxld.yxchuangxin.data.api.HttpAPIWrapper;
import com.yxld.yxchuangxin.ui.activity.mine.contract.PatternProveContract;
import com.yxld.yxchuangxin.ui.activity.mine.PatternProveActivity;
import javax.inject.Inject;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * @author xlei
 * @Package com.yxld.yxchuangxin.ui.activity.mine
 * @Description: presenter of PatternProveActivity
 * @date 2018/04/04 11:25:15
 */
public class PatternProvePresenter implements PatternProveContract.PatternProveContractPresenter{

    HttpAPIWrapper httpAPIWrapper;
    private final PatternProveContract.View mView;
    private CompositeDisposable mCompositeDisposable;
    private PatternProveActivity mActivity;

    @Inject
    public PatternProvePresenter(@NonNull HttpAPIWrapper httpAPIWrapper, @NonNull PatternProveContract.View view, PatternProveActivity activity) {
        mView = view;
        this.httpAPIWrapper = httpAPIWrapper;
        mCompositeDisposable = new CompositeDisposable();
        this.mActivity = activity;
    }
    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        if (!mCompositeDisposable.isDisposed()) {
             mCompositeDisposable.dispose();
        }
    }

//    @Override
//    public void getUser(HashMap map) {
//        //mView.showProgressDialog();
//        Disposable disposable = httpAPIWrapper.getUser(map)
//                .subscribe(new Consumer<User>() {
//                    @Override
//                    public void accept(User user) throws Exception {
//                        //isSuccesse
//                        KLog.i("onSuccesse");
//                        mView.setText(user);
//                      //mView.closeProgressDialog();
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        //onError
//                        KLog.i("onError");
//                        throwable.printStackTrace();
//                      //mView.closeProgressDialog();
//                      //ToastUtil.show(mActivity, mActivity.getString(R.string.loading_failed_1));
//                    }
//                }, new Action() {
//                    @Override
//                    public void run() throws Exception {
//                        //onComplete
//                        KLog.i("onComplete");
//                    }
//                });
//        mCompositeDisposable.add(disposable);
//    }
}