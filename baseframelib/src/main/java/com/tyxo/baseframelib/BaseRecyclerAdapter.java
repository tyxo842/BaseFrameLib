package com.tyxo.baseframelib;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
* author tyxo
* created at 2016/8/25 16:11
* des : recyclerView 的基类adapter
*/
/*
用法:
 *        class GAdapter extends BaseRecyclerAdapter <MyHolder,NewslistBean>{...}
 *        参考 GAdaper 内的adapter用法.
 */
public abstract class BaseRecyclerAdapter<T extends RecyclerView.ViewHolder,E> extends
        RecyclerView.Adapter<T> {

    private Context context;
    protected ArrayList<E> mDatas = new ArrayList<>();
    protected LayoutInflater mInflater;
    protected int layoutItemId;
    protected List<Integer> mHeights;                 //随机item的高度
    protected OnItemClickLitener mOnItemClickLitener;

    public interface OnItemClickLitener<E> {
        void onItemClick(View view, E bean, int position);
        void onItemLongClick(View view, E bean, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public BaseRecyclerAdapter(Context context, ArrayList<E> datas, int layoutItemId) {
        this.context = context;
        //mInflater = LayoutInflater.from(context);
        mDatas = datas;
        this.layoutItemId = layoutItemId;
    }

    public void substituteDatas(ArrayList<E> datas){
        mDatas = datas;
    }//置换集合数据
    public void clearDatas(){
        mDatas.clear();
    }//清空集合数据
    public void addDatas(ArrayList<E> datas){
        mDatas.addAll(datas);
    }//添加集合数据

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, layoutItemId, null);
        //MyViewHolder holder = new MyViewHolder(mInflater.inflate(layoutItemId, parent, false));
        return getViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final T holder, final int position) {

        final E bean = mDatas.get(position);
        initItemView(holder,bean,position);

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView,bean,pos);
                }
            });

            holder.itemView.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView,bean,pos);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mDatas != null && mDatas.size() >= 0) {
            return mDatas.size();
        } else {
            return 0;
        }
    }

    /** 返回 viewHolder */
    protected abstract T getViewHolder(View itemView);

    /** 设置数据 */
    protected abstract void initItemView(T holder,E bean,int position);

    public void addData(int position,E bean) {
        mDatas.add(position, bean);
        mHeights.add((int) (100 + Math.random() * 300));
        notifyItemInserted(position);
    }

    public void removeData(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }
}

/*
public class GAdapter extends BaseRecyclerAdapter<GAdapter.MyHolder,BeanGirls.ShowapiResBodyBean.NewslistBean> {

    protected List<Integer> mHeights;//随机item的高度
    private Context context;

    public GAdapter(Context context, ArrayList <BeanGirls.ShowapiResBodyBean.NewslistBean> datas, int layoutItemId) {
        super(context, datas, layoutItemId);
        this.context = context;

        mHeights = new ArrayList<>();
        for (int i = 0; i < mDatas.size(); i++) {
            mHeights.add((int) (100 + Math.random() * 300));
        }
    }

    @Override
    protected MyHolder getViewHolder(View itemView) {
        return new MyHolder(itemView);
    }

    @Override
    protected void initItemView(MyHolder holder, BeanGirls.ShowapiResBodyBean.NewslistBean bean,int position) {
        //int position = holder.getLayoutPosition();

        // 设置随机高度 ---> 报空了...
        //ViewGroup.LayoutParams lp = holder.mGirlImage.getLayoutParams();
        //lp.height = mHeights.get(position);
        //holder.mGirlImage.setLayoutParams(lp);

        holder.itemView.setTag(position);//设置item的 view内容数据
        Glide.with(context)
                .load(bean.getPicUrl())
                .centerCrop()
                .placeholder(R.color.global_background)
                .into(holder.mGirlImage);
    }

    class MyHolder extends RecyclerView.ViewHolder{
        RatioImageView mGirlImage;
        public MyHolder(View itemView) {
            super(itemView);
            mGirlImage = (RatioImageView) itemView.findViewById(R.id.girl_image_item);
            mGirlImage.setRatio(0.618f);// set the ratio to golden ratio.
        }
    }
}
*/