package cn.edu.jxnu.awesome_campus.ui.jxnugo;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;

import org.greenrobot.eventbus.Subscribe;

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
import cn.edu.jxnu.awesome_campus.view.widget.circleview.AvatarImageView;

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
    private MenuItem favorite, favorite_select, comment;
    private SimpleDraweeView avatarImageView;

    @Override
    protected void init() {
        title = "商品详情";
        layoutID = R.layout.activity_goods_details;
    }

    /**
     * 拼接数据
     */
    @Override
    protected void onDataRefresh() {
        tvTime.setText(model.getTimestamp());
        tvUserName.setText(model.getPostUserName());
        if (model.getPostUserAvator() != null) {
            avatarImageView.setImageURI(Uri.parse(model.getPostUserAvator()));
            Log.d(TAG, "取得的帖子发布者头像信息" + model.getPostUserAvator());
        }
        data = "<div class=\"main-wrap content-wrap\">" +
                "<h1 class=\"question-title\">"
                + model.getGoodName() + "</h1>\n\n" +
                "<div class=\"answer\">"
                + "<div class=\"content\">\n" +
                "<p>" + goodPrice + model.getGoodPrice() + "</p>" +
                "<p>" + goodBuyTime + model.getGoodBuyTime() + "</p>" +
                "<p>" + goodQuality + model.getGoodQuality() + "</p>" +
                "<p>" + goodLocation + model.getGoodLocation() + "</p>" +
                "<p>" + goodContact + model.getContact() + "</p>" +
                "<p>" + model.getBody() + "</p>";
//        Log.d(TAG,"取得的图片数组大小"+model.getPhoto().length);
        if (model.getPhoto() != null)
            for (int i = 0; i < model.getPhoto().length; i++)
                data = data + "\r\n<p><img class=\"content-image\" src=\"" + JxnuGoApi.BasePicUrl+ model.getPhoto()[i] + "\" alt=\"\" /></p>\r\n";
        data = data + "</div></div></div>";
        onDataShow("JianShu.css");
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "创建菜单");
        getMenuInflater().inflate(R.menu.menu_goodsdetail, menu);
        favorite = menu.findItem(R.id.menu_favorite);
        favorite_select = menu.findItem(R.id.menu_favorite_select);
        comment = menu.findItem(R.id.menu_comment);
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
        }
        return super.onOptionsItemSelected(item);
    }

    private void setFavorite(boolean b) {
        SPUtil spu = new SPUtil(InitApp.AppContext);
        int userId = spu.getIntSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.USERID);
        CollectBean bean = new CollectBean(userId + "", model.getPostId() + "");
        Log.d(TAG, "取得userId：" + userId);
        Log.d(TAG, "取得postId：" + model.getPostId());
        UploadCollectingStatusUtil.onUploadJson(b, bean);
    }
}
