package com.tyxo.testbaseframe;

import android.util.Log;

import com.tyxo.baseframelib.BaseRecyclerActivity;

/**
 * Created by LY on 2016/10/14 11: 59.
 * Mail      tyxo842@163.com
 * Describe :
 */

public class TestBaseFrame extends BaseRecyclerActivity<TestBean> {

    //首先设置id
    @Override
    protected void setIds() {
        this.setLayoutId(R.layout.activity_test_baseframe);
        this.setRefreshLId(R.id.swipRefreshL);
        this.setRecyclerVId(R.id.recyclerView);
    }

    //处理返回数据
    @Override
    protected void handleData(TestBean beanB) {
        Log.v("tyxo","TestBaseFrame handleDta ");
    }

    @Override
    protected void requestNet() {
        super.requestNet();
        //做网络请求操作
    }
}
