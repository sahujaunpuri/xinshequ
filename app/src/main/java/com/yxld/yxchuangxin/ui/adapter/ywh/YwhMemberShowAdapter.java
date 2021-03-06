package com.yxld.yxchuangxin.ui.adapter.ywh;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.entity.YwhMember;

/**
 * Created by William on 2018/11/8.
 */

public class YwhMemberShowAdapter extends BaseQuickAdapter<YwhMember.DataBean, BaseViewHolder> {

    private TextView tv_content;

    public YwhMemberShowAdapter() {
        super(R.layout.item_ywh_membershow_list);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, YwhMember.DataBean dataBean) {
        baseViewHolder.setText(R.id.tv_name, dataBean.getCfname()).setText(R.id.tv_house_message, dataBean.getExpect
                () + "期-" + dataBean.getBuilding() + "栋-" + dataBean.getUnit() + "单元-" + dataBean.getRoomNumber() +
                "").setText(R.id.tv_idcard, dataBean.getIdcard()).setText(R.id.tv_work_place, dataBean.getWorkUnit()).setText(R.id.tv_work, dataBean
                .getWorkPosition()).setText(R.id.tv_other,dataBean.getOtherWorks() );

//        .setText(R.id.tv_identity, dataBean.getWorkPosition
//                ())
        switch (dataBean.getType()) {//类型1:筹备组人员 2:业委会候选人 3:业委会人员 4:业委会主任 5:业委会副主任
            case 1:
                baseViewHolder.setText(R.id.tv_identity, "筹备组人员");
                break;
            case 2:
                baseViewHolder.setText(R.id.tv_identity, "业委会候选人");
                break;
            case 3:
                baseViewHolder.setText(R.id.tv_identity, "业委会人员");
                break;
            case 4:
                baseViewHolder.setText(R.id.tv_identity, "业委会主任");
                break;
            case 5:
                baseViewHolder.setText(R.id.tv_identity, "业委会副主任");
                break;
        }

        tv_content = baseViewHolder.getView(R.id.tv_content);

        if (!TextUtils.isEmpty(dataBean.getDriscipt())) {
            tv_content.setVisibility(View.VISIBLE);
            baseViewHolder.setText(R.id.tv_content, "简介：" + dataBean.getDriscipt());
        } else {
            tv_content.setVisibility(View.GONE);
        }
    }
}
