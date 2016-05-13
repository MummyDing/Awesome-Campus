package cn.edu.jxnu.awesome_campus.ui.jxnugo;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;

import org.greenrobot.eventbus.Subscribe;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.GoodsModel;
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
    private MenuItem favorite, favorite_select, comment;


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
//        for(int i=0;i<model.getPhoto().length;i++)
//            data=data+"</p>\r\n<p><img class=\"content-image\" src=\""+model.getPhoto()[i]+"\" alt=\"\" /></p>\r\n<p>";
        data = data + "</div></div></div>";


    }

    @Override
    protected void onEventComing(EventModel eventModel) {
        if (eventModel.getEventCode() == EVENT.GOODS_DETAIL_INTENT) {
            model = (GoodsModel) eventModel.getData();
            initView();
            onDataRefresh();
            onDataShow("Daily.css");
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG,"创建菜单");
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
                Intent intent=new Intent();
//                intent.setClass(this,GoodsCommentActivity.class);//测试
                intent.setClass(this,NewGoodsActivity.class);
                intent.putExtra("id",16);
                startActivity(intent);
                break;
            case R.id.menu_favorite:
                favorite.setVisible(false);
                favorite_select.setVisible(true);
                break;
            case R.id.menu_favorite_select:
                favorite.setVisible(true);
                favorite_select.setVisible(false);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
