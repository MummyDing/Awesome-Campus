package cn.edu.jxnu.awesome_campus.ui.jxnugo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.tendcloud.tenddata.TCAgent;

import org.greenrobot.eventbus.EventBus;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.api.JxnuGoApi;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.CollectBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.GoodsModel;
import cn.edu.jxnu.awesome_campus.support.spkey.JxnuGoStaticKey;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.jxnugo.UploadCollectingStatusUtil;
import cn.edu.jxnu.awesome_campus.ui.base.BaseEventWebViewActivity;

/**
 * Created by KevinWu on 16-5-12.
 */
public class GoodsDetailActivity extends BaseEventWebViewActivity {
    public static final String TAG = "GoodsDetailActivity";
    private GoodsModel model;
    private String goodLocation = "地址：";
    private String goodBuyTime = "购买时间：";
    private String goodPrice = "价格：";
    private String goodQuality = "成色：";
    private String goodContact = "联系方式：";
    private TextView tvUserName;
    private TextView tvTime;
    private MenuItem favorite, favorite_select, comment,share;
    private SimpleDraweeView avatarImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TCAgent.onPageStart(InitApp.AppContext, TAG);
    }

    @Override
    protected void init() {
        title = "商品详情";
        layoutID = R.layout.activity_goods_details;
        isShowBigPic=true;
    }

    /**
     * 拼接数据
     */
    @Override
    protected void onDataRefresh() {
        tvTime.setText(model.getTimestamp());
        tvUserName.setText(model.getPostNickName());
        if (model.getPostUserAvatar() != null) {
            avatarImageView.setImageURI(Uri.parse(model.getPostUserAvatar()));
            Log.d(TAG, "取得的帖子发布者头像信息" + model.getPostUserAvatar());
        }
        data = "<div class=\"main-wrap content-wrap\">" +
                "<h1 class=\"question-title\">"
                + model.getGoodsName() + "</h1>\n\n" +
                "<div class=\"answer\">"
                + "<div class=\"content\">\n" +
                "<p>" + goodPrice + model.getGoodsPrice() + "￥</p>" +
                "<p>" + goodBuyTime + model.getGoodsBuyTime() + "</p>" +
                "<p>" + goodQuality + model.getGoodsQuality() + "</p>" +
                "<p>" + goodLocation + model.getGoodsLocation() + "</p>" +
                "<p>" + goodContact + model.getContact() + "</p>" +
                "<p>" + model.getBody() + "</p>";
//        Log.d(TAG,"取得的图片数组大小"+model.getPhoto().length);
        if (model.getPhotos() != null){
            Log.d(TAG,"取得的图片数组大小"+model.getPhotos().length);
            for (int i = 0; i < model.getPhotos().length; i++)
                data = data + "\r\n<p><img class=\"content-image\" src=\"" + JxnuGoApi.BasePicUrl+ model.getPhotos()[i].getKey()+"?imageMogr2/thumbnail/2000x2000"+ "\" /></p>\r\n";
        }

        data = data + "</div></div></div>";
        onDataShow("Daily.css");
        SPUtil spu = new SPUtil(InitApp.AppContext);
        int userId = spu.getIntSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.USERID);
        CollectBean bean = new CollectBean(userId + "", model.getPostId() + "");
        UploadCollectingStatusUtil.getStatusJson(bean);
    }

    @Override
    protected void onEventComing(EventModel eventModel) {
        switch (eventModel.getEventCode()) {
            case EVENT.GOODS_DETAIL_INTENT:
                model = (GoodsModel) eventModel.getData();
                initView();
                onDataRefresh();
                break;
            case EVENT.POST_COLLECT_SUCCESS://成功评论
                setCollect(true);
                Snackbar.make(getCurrentFocus(), R.string.jxnugo_collect_success,Snackbar.LENGTH_SHORT).show();
                break;
            case EVENT.POST_COLLECT_FAILURE:
                setCollect(false);
                Snackbar.make(getCurrentFocus(), R.string.jxnugo_collect_failure,Snackbar.LENGTH_SHORT).show();
                break;
            case EVENT.JUDGE_COLLECT_TRUE:
                setCollect(true);
                break;
            case EVENT.JUDGE_COLLECT_FALSE:
                setCollect(false);
                break;
        }
    }

    private void setCollect(boolean b) {
        if(b){
            favorite.setVisible(false);
            favorite_select.setVisible(true);
        }else{
            favorite.setVisible(true);
            favorite_select.setVisible(false);
        }
    }

    private void setComment() {
        Log.d(TAG, "取得的评论数量为：" + model.getCommentsCount());
        if (model.getCommentsCount() > 0)
            ActionItemBadge.update(this, comment, ContextCompat.getDrawable(this, R.mipmap.ic_textsms_white), ActionItemBadge.BadgeStyles.YELLOW, model.getCommentsCount());
    }

    private void initView() {
        tvUserName = (TextView) findViewById(R.id.username);
        tvTime = (TextView) findViewById(R.id.time);
        avatarImageView = (SimpleDraweeView) findViewById(R.id.avatar);
        avatarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model!=null&&model.getAuthor_id()!=0){
                    EventBus.getDefault().postSticky(new EventModel<Integer>(EVENT.JXNUGO_USERINFO_LOAD_USER,model.getAuthor_id()));
                    Intent intent=new Intent(GoodsDetailActivity.this, JxnuGoUserinfoActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "创建菜单");
        getMenuInflater().inflate(R.menu.menu_goodsdetail, menu);
        favorite = menu.findItem(R.id.menu_favorite);
        favorite_select = menu.findItem(R.id.menu_favorite_select);
        comment = menu.findItem(R.id.menu_comment);
        share=menu.findItem(R.id.menu_share);
        setComment();
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_comment:
                Intent intent = new Intent();
                intent.setClass(this, GoodsCommentActivity.class);//测试

                intent.putExtra("id",model.getPostId());
//                intent.putExtra("id", 16);
                startActivity(intent);
                break;
            case R.id.menu_favorite:
                favorite.setVisible(false);
                favorite_select.setVisible(true);
                setFavorite(true);
                break;
            case R.id.menu_favorite_select:
                favorite.setVisible(true);
                favorite_select.setVisible(false);
                setFavorite(false);
                break;
            case R.id.menu_goods_detail_share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, getShareInfo());
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.hint_share_to)));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 拼接分享的信息的方法
     * @return
     */
    private String getShareInfo() {
        String info="我在江西师大的专属二手市场发现了件不错的商品——【"+model.getGoodsName()+"】，你也来看看吧：";
        info =info+JxnuGoApi.BaseTradeURL+model.getPostId();
        info = info+"(分享自 师大+)";
        return info;
    }

    private void setFavorite(boolean b) {
        SPUtil spu = new SPUtil(InitApp.AppContext);
        int userId = spu.getIntSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.USERID);
        CollectBean bean = new CollectBean(userId + "", model.getPostId() + "");
        Log.d(TAG, "取得userId：" + userId);
        Log.d(TAG, "取得postId：" + model.getPostId());
        UploadCollectingStatusUtil.onUploadJson(b, bean);
    }

    @Override
    protected void onDestroy() {
        TCAgent.onPageEnd(InitApp.AppContext, TAG);
        super.onDestroy();
    }
}
