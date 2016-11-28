package com.tyxo.baseframelib;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tyxo.baseframelib.net.TaskHelp;
import com.tyxo.baseframelib.net.volley.VolleyCallBack;
import com.tyxo.baseframelib.net.volley.VolleyErrorResult;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by tyxo on 2016/8/25 10: 38.
 * Mail      1577441454@qq.com
 * Describe :
 */
/*
<T> 限定数据 bean 的类型,
 *            子类使用的时候: public class GirlsActivity extends BaseRecyclerActivity<BeanGirls>{....}.
 *            参考 GirlsActivity 代码:
 *            重写 requestNet ,task.网络请求.
 */
public abstract class BaseRecyclerActivity<T extends Object> extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    /*
    public static <T> String arrayToString(ArrayList<T> list) {
    Gson g = new Gson();
    return g.toJson(list);
    }
    public static <T> ArrayList<T> stringToArray(String s) {
    Gson g = new Gson();
    Type listType = new TypeToken<ArrayList<T>>(){}.getType();
    ArrayList<T> list = g.fromJson(s, listType);
    return list;
    }

    正确:
    public static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
    T[] arr = new Gson().fromJson(s, clazz);
    return Arrays.asList(arr); //or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
    }

    public <T> List<T> deserializeList(String json) {
    Gson gson = new Gson();
    Type type = (new TypeToken<List<T>>() {}).getType();
    return  gson.fromJson(json, type);
    }

    正确:
    public <T> List<T> deserializeList(String json, Type type) {
    Gson gson = new Gson();
    return  gson.fromJson(json, type);
    }
     */

    protected RecyclerView mRecyclerView;
    protected SwipeRefreshLayout mRefreshLayout;
    protected BaseRecyclerAdapter.OnItemClickLitener itemClickLitener;

    protected TaskHelp taskHelp;
    protected VolleyCallBack<JSONObject> callback;
    private GridLayoutManager mLayoutManager;
    private int lastVisibleItem;
    private MyHandler handler;

    protected boolean isRefresh = true;            //是否是下拉刷新
    protected boolean isLoadMore = false;         //是否是上拉加载
    protected int pageSize;
    protected int pageIndex;
    protected int rand;

    private int layoutId;
    private int refreshLayoutId;
    private int recyclerVId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setIds();       //设置布局布局等等id

        initView();		// 初始化 view
        initListener();	// 初始化 监听
        initData();		// 初始化 数据
    }

    /**设置布局,refreshLayoutId和recyclerVId的 id.*/
    protected abstract void setIds();

    protected void initView() {
        setContentView(layoutId);

        mRecyclerView = (RecyclerView) findViewById(recyclerVId);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(refreshLayoutId);
        //mRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        mRefreshLayout.setColorSchemeColors(0xFF00FF00,0xff2b5fc5,0xFF888888);
        mRefreshLayout.setOnRefreshListener(this);
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnScrollListener(mScrollListener);
    }

    protected void initListener() {
        // 具体可以由子类去实现 itemClickLitener 的click内容.
        itemClickLitener = new BaseRecyclerAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, Object bean, int position) {
                //ToastUtil.showToastS(getApplicationContext(),"条目%1$.2f点击了"+position);
                Toast.makeText(getApplicationContext(),
                        String.format("%1$d点击了,这是第%2$d个条目",position,position),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, Object bean, int position) {

            }
        };
    }

    protected void initData() {
        pageSize = 10;
        handler = new MyHandler();

        requestNet();
    }

    /** 处理数据 */
    protected abstract void handleData(T beanB);
        /** 子类建议做如下判断, 打印size,便于调试接口,按自己情况去写 */
        //Type type = new TypeToken<BeanGirls>() {}.getType();
        //BeanGirls bean = new Gson().fromJson(response.toString(), type);

        /*if(bean.getData()!=null && bean.getData().size() > 0){
            HLog.i("tyxo", " response size(): " + bean.getData().size());
        }else {
            HLog.i("tyxo", " size<=0 返回信息: " + ConstValues.SERVER_RESPONSE_EMPTY);
        }*/


    /** 网络请求 重写此方法,然后可参考继承TaskHelp,发起网络请求*/
    protected void requestNet(){

        callback =  new VolleyCallBack<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("tyxo", " response : " + response.toString());
                try {
                    if (!TextUtils.isEmpty(response.toString())) {
                        handleData(parseObject(response.toString()));  //处理数据

                    } else {
                        stopLoading();
                        Log.i("tyxo", " response为空 返回信息: " + "服务器返回结果为空");
                    }
                } catch (Exception e) {
                    stopLoading();
                    e.printStackTrace();
                    Log.i("tyxo", " 解析数据错误" + e.getMessage());
                }
            }

            @Override
            public void onErrorResponse(VolleyErrorResult result) {
                stopLoading();
                Log.i("tyxo", " 请求失败: " + result.toString());
            }
        };

        //taskHelp = new TaskHelp();
    }

    private RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            // TODO: 2016/11/2 在基类中传adapter使用,需再看看,并加入判断!!
            if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mAdapter.getItemCount()) {
                mRefreshLayout.setRefreshing(true);

                    /* 此处换成网络请求  上拉加载
                    ......
                    * */
                Message msg = new Message();
                msg.what = 0;
                handler.sendMessageDelayed(msg, 1000);
                Log.v("tyxo", "上拉加载");
            }

            /*boolean reachBottom = mLayoutManager.findLastCompletelyVisibleItemPosition()
                    >= mLayoutManager.getItemCount() - 1;
            if(newState == RecyclerView.SCROLL_STATE_IDLE && !isLoadMore && reachBottom) {
                isLoadMore = true;
                onLoadMore();
            }*/
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();



            /*boolean reachBottom = mLayoutManager.findLastCompletelyVisibleItemPosition()
                    >= mLayoutManager.getItemCount() - 1;
            if(!isLoadMore && reachBottom) {
                onLoadMore();
            }*/
        }
    };

    // 下拉刷新
    @Override
    public void onRefresh() {
        pageIndex = 1;
        rand = 0;
        isRefresh = true;
        isLoadMore = false;

        Message msg = new Message();
        msg.what = 1;
        handler.sendMessageDelayed(msg, 1000);
    }

    // 加载更多
    protected void onLoadMore() {
        isLoadMore = true;
        isRefresh = false;
    }

    protected void stopLoading(){
        isLoadMore = false;
        mRefreshLayout.setRefreshing(false);
    }

    /**解析 返回数据*/
    protected T parseObject(String response) throws Exception {
        try {
            return new Gson().fromJson(response, getClazz());
        }catch (Exception e){
            Log.e("tyxo","BaseRecyclerActivity parseObject 数据解析失败"+e);
            throw new Exception(e);
        }
    }
    public Class<T> getClazz() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType p = (ParameterizedType) t;
        Class<T> c = (Class<T>) p.getActualTypeArguments()[0];
        return c;
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mRefreshLayout.setRefreshing(false);
                    break;
                case 1:
                    mRefreshLayout.setRefreshing(false);
                    break;
                case 2:
                    break;
            }
        }
    }

    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public int getRefreshLId() {
        return refreshLayoutId;
    }

    public void setRefreshLId(int refreshLayoutId) {
        this.refreshLayoutId = refreshLayoutId;
    }

    public void setRecyclerVId(int recyclerVId){
        this.recyclerVId = recyclerVId;
    }

    public int getRecyclerVId(){
        return recyclerVId;
    }

    /** activity的返回结果码 */
    private int resultCode;

    /** activity的返回结果数据 */
    private Intent intentData;

    /**  获得返回结果数据 */
    public Intent getIntentData() {
        return intentData;
    }

    /** 设置Activity返回结果 */
    public void setIntentData(Intent intentData) {
        this.intentData = intentData;
    }

    /** 获得Activity返回结果码 */
    public int getResultCode() {
        return resultCode;
    }

    /** 设置Activity返回结果码 */
    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    /** 结束Activity时，设置返回结果 */
    public void finishWithResult(){
        this.setResult(getResultCode(), getIntentData());
        finish();
    }

    /** 结束Activity */
    @Override
    public void finish() {
        super.finish();
        // 设置切换动画，从右边进入，右边退出
        this.overridePendingTransition(android.R.anim.fade_in,android.R.anim.slide_out_right);
        //this.overridePendingTransition(0,0);//去除动画效果
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        // 设置切换动画，从右边进入，右边退出
        this.overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.fade_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menu!=null) {
            if (menu.getClass()== MenuBuilder.class) {
                try{
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }
}

/*
public class ActivityGirls extends BaseRecyclerActivity<BeanGirls>{

    private ArrayList<BeanGirls.ShowapiResBodyBean.NewslistBean> beanList; // 返回数据集合
    private GirlsAdapterMy mAdapter;
    private LoadMoreView mLoadMore;

    @Override
    protected void initView(View contentView) {
        super.initView(contentView);
        mToolbarTitle.setText("Girls");

        mLoadMore = (LoadMoreView) LayoutInflater.from(this).inflate(R.layout.base_load_more, mRecyclerView, false);
    }

    @Override
    protected void initListener() {
        super.initListener();
        itemClickLitener = new BaseRecyclerStaggeredAdapter.OnItemClickLitener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(View view, Object bean, int position) {
                Intent intent = new Intent(ActivityGirls.this, ActivityGirl.class);
                Bundle bun = new Bundle();
                bun.putSerializable("girls",beanList);
                bun.putInt("current",position);
                intent.putExtras(bun);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(
                        view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
                startActivity(intent,options.toBundle());
            }

            @Override
            public void onItemLongClick(View view, Object bean, int position) {
            }
        };
    }

    @Override
    protected void initData() {
        super.initData();
        beanList = new ArrayList<>();

        requestNet();

        mAdapter = new GirlsAdapterMy(this, beanList, R.layout.base_recycler_item);
        mAdapter.setOnItemClickLitener(itemClickLitener);
        HeaderViewRecyclerAdapter adapter = new HeaderViewRecyclerAdapter(mAdapter);
        adapter.setLoadingView(mLoadMore);
        mRecyclerView.setAdapter(adapter);
    }

    //处理返回的数据
    @Override
    protected void handleData(BeanGirls beanB) {
        HLog.i("tyxo", "BeanGirls beanB 返回解析: " + beanB.toString());
        BeanGirls.ShowapiResBodyBean beanBody = beanB.getShowapi_res_body();

        if(beanBody.getNewslist()!=null && beanBody.getNewslist().size() > 0){

            ArrayList<BeanGirls.ShowapiResBodyBean.NewslistBean> resList = (ArrayList<BeanGirls.ShowapiResBodyBean.NewslistBean>) beanB.getShowapi_res_body().getNewslist();
            if (isLoadMore) {
                beanList.addAll(resList);
            } else {
                beanList = resList;
            }
            mAdapter.substituteDatas(beanList);
            mAdapter.notifyDataSetChanged();
            stopLoading();

        }else {
            ToastUtil.showToastS(this,"无更多数据");
            HLog.i("tyxo", "BeanGirls size<=0 返回信息: " + ConstValues.SERVER_RESPONSE_EMPTY);
        }
    }

    @Override
    protected void requestNet() {
        super.requestNet();
        taskHelp.getGirls(this,pageSize,pageIndex,rand,callback);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        requestNet();
    }

    @Override
    protected void onLoadMore() {
        super.onLoadMore();
        pageIndex++;
        mLoadMore.setStatus(LoadMoreView.STATUS_LOADING);
        requestNet();
    }

    @Override
    protected void stopLoading() {
        super.stopLoading();
    }
}
 */
