package cn.edu.jxnu.awesome_campus.ui.jxnugo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tendcloud.tenddata.TCAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.GoodsModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.UserCPListBean;
import cn.edu.jxnu.awesome_campus.support.adapter.BaseListAdapter;
import cn.edu.jxnu.awesome_campus.support.adapter.jxnugo.CLGoodsListAdapter;
import cn.edu.jxnu.awesome_campus.support.utils.jxnugo.LoadGoodsListUtil;
import cn.edu.jxnu.awesome_campus.ui.base.BaseToolbarActivity;

/**
 * Created by KevinWu on 16-5-20.
 */
public class JxnuGoGoodsListActivity extends BaseToolbarActivity{
    public static final String TAG="JxnuGoGoodsListActivity";
    private static final String CTITLE="他/她的收藏商品";
    private static final String PTITLE="他/她发布的商品";
    private static final String CTITLE_HASLOGIN="我的收藏商品";
    private static final String PTITLE_HASLOGIN="我发布的商品";
    private int userID;
    private int postID;
    private GoodsModel model;
    private int type=0;
    private String title[]=new String[]{
            CTITLE,PTITLE,CTITLE_HASLOGIN,PTITLE_HASLOGIN
    };
    private ProgressBar progressBar;
    private BaseListAdapter adapter;
    private RecyclerView recyclerView;
    private TextView tips;
    private boolean hasLogin=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jxnugo_goodlist);
        TCAgent.onPageStart(InitApp.AppContext, TAG);
        EventBus.getDefault().register(this);
        userID=getIntent().getIntExtra("userid",-1);
        type=getIntent().getIntExtra("type",0);
        hasLogin=getIntent().getBooleanExtra("haslogin",false);
        initView();
        initToolbar();
        if(hasLogin){
            setToolbarTitle(title[type+2]);
        }
        else{
            setToolbarTitle(title[type]);
        }
        if(type!=1){
            hasLogin=false;
        }
        bindAdapter();
        onDataRefresh();
    }

    private void onDataRefresh() {
        switch (type){
            case 0:
                LoadGoodsListUtil.getCGoodsList(this,userID);
                tips.setText("暂无收藏的商品");
                break;
            case 1:
                LoadGoodsListUtil.getPGoodsList(this,userID);
                tips.setText("暂无发布的商品");
                break;
        }
    }

    private void bindAdapter() {
        model=new GoodsModel();
        adapter=new CLGoodsListAdapter(this,model,hasLogin);
        recyclerView.setAdapter(adapter);
        displayLoading();
    }

    private void displayLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void initView() {
        progressBar=(ProgressBar)findViewById(R.id.progressbar);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tips=(TextView)findViewById(R.id.tips);
    }
    private void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        TCAgent.onPageEnd(InitApp.AppContext, TAG);
        super.onDestroy();
    }
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 1)
    public void onEventMainThread(EventModel eventModel) {
        switch (eventModel.getEventCode()){
            case EVENT.USERPOST_REFRESH_FAILURE:
                tips.setText("网络错误");
                tips.setVisibility(View.VISIBLE);
                break;
            case EVENT.USERPOST_REFRESH_SUCCESS:
            case EVENT.USERCOLLECT_REFRESH_SUCCESS:
                Log.d(TAG,"成功");
                List<UserCPListBean> list = (List<UserCPListBean>)eventModel.getDataList();
                if(type==0){
                    if(list.get(0).getCollectionPost().length>0)
                    adapter.newList(Arrays.asList(list.get(0).getCollectionPost()));
                    else
                        tips.setVisibility(View.VISIBLE);
                }
                else if(type==1){
                    if(list.get(0).getUserPosts().length>0)
                    adapter.newList(Arrays.asList(list.get(0).getUserPosts()));
                    else
                        tips.setVisibility(View.VISIBLE);
                }
                hideLoading();
                break;
            case EVENT.JXNUGO_TRIGGER_DELETE_POST:
                postID=(int)eventModel.getData();
                deleteDialog();
                break;
            case EVENT.JXNUGO_DELETE_POST_SUCCESS:
                Snackbar.make(getCurrentFocus(), "删除成功",Snackbar.LENGTH_SHORT).show();
                onDataRefresh();
                break;
            case EVENT.JXNUGO_DELETE_POST_FAILURE:
                Snackbar.make(getCurrentFocus(), "删除失败",Snackbar.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 删帖对话框
     */
    private void deleteDialog() {
        AlertDialog dialog =  new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("确定删除该商品么？")
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoadGoodsListUtil.deletePost(JxnuGoGoodsListActivity.this,userID,postID);
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }
}
