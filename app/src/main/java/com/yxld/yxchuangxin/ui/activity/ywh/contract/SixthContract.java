package com.yxld.yxchuangxin.ui.activity.ywh.contract;

import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.ui.activity.base.BasePresenter;
import com.yxld.yxchuangxin.ui.activity.base.BaseView;
/**
 * @author William
 * @Package The contract for SixthFragment
 * @Description: $description
 * @date 2018/11/08 15:56:44
 */
public interface SixthContract {
    interface View extends BaseView<SixthContractPresenter> {
        /**
         *
         */
        void showProgressDialog();

        /**
         *
         */
        void closeProgressDialog();

        void setSixthData(BaseEntity baseEntity);
    }

    interface SixthContractPresenter extends BasePresenter {
         void getSixthData();
    }
}