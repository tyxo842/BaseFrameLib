package com.tyxo.testbaseframe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tyxo.baseframelib.BaseRecyclerAdapter;

import java.util.ArrayList;

/**
 * Created by LY on 2016/11/2 17: 33.
 * Mail      tyxo842@163.com
 * Describe :
 */

public class TestBaseFrameAdapter extends BaseRecyclerAdapter<TestBaseFrameAdapter.MyHolder,TestBean> {

    public TestBaseFrameAdapter(Context context, ArrayList datas, int layoutItemId) {
        super(context, datas, layoutItemId);
    }

    @Override
    protected MyHolder getViewHolder(View itemView) {
        return new MyHolder(itemView);
    }

    @Override
    protected void initItemView(MyHolder holder, TestBean bean, int position) {

    }

    class MyHolder extends RecyclerView.ViewHolder{
        //RatioImageView mGirlImage;
        public MyHolder(View itemView) {
            super(itemView);
            //mGirlImage = (RatioImageView) itemView.findViewById(R.id.girl_image_item);
            //mGirlImage.setRatio(0.618f);// set the ratio to golden ratio.
        }
    }
}
