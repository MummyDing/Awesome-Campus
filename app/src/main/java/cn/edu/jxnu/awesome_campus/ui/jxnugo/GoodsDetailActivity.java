package cn.edu.jxnu.awesome_campus.ui.jxnugo;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.GoodsModel;
import cn.edu.jxnu.awesome_campus.ui.base.BaseEventWebViewActivity;

/**
 * Created by KevinWu on 16-5-12.
 */
public class GoodsDetailActivity extends BaseEventWebViewActivity{
    public static final String TAG="GoodsDetailActivity";
    private GoodsModel model;
    private String goodLocation="地址：";
    private String goodBuyTime="购买时间：";
    private String goodPrice="价格：";
    private String goodQuality="成色：";
    private String goodContact="联系方式：";
    private TextView tvUserName;
    private TextView tvTime;



    @Override
    protected void init() {
        title="商品详情";
        layoutID= R.layout.activity_goods_details;
    }

    /**
     * 拼接数据
     */
    @Override
    protected void onDataRefresh() {
        tvTime.setText(model.getTimestamp());
        tvUserName.setText(model.getPostUserName());
        data="<div class=\"main-wrap content-wrap\">" +
                "<h1 class=\"question-title\">"
                +model.getGoodName() +"</h1>\n\n" +
                "<div class=\"answer\">"
                + "<div class=\"content\">\n" +
                "<p>" +goodPrice+model.getGoodPrice()+"</p>"+
                "<p>" +goodBuyTime+model.getGoodBuyTime()+"</p>"+
                "<p>" +goodQuality+model.getGoodQuality()+"</p>"+
                "<p>" +goodLocation+model.getGoodLocation()+"</p>"+
                "<p>" +goodContact+model.getContact()+"</p>"+
                "<p>" +model.getBody() + "</p>" ;
//        Log.d(TAG,"取得的图片数组大小"+model.getPhoto().length);
//        for(int i=0;i<model.getPhoto().length;i++)
//            data=data+"</p>\r\n<p><img class=\"content-image\" src=\""+model.getPhoto()[i]+"\" alt=\"\" /></p>\r\n<p>";
        data=data+"</div></div></div>";
    }

    @Override
    protected void onEventComing(EventModel eventModel) {
        if(eventModel.getEventCode()==EVENT.GOODS_DETAIL_INTENT){
            model=(GoodsModel)eventModel.getData();
            initView();
            onDataRefresh();
            onDataShow("Daily.css");
        }
    }

    private void initView() {
        tvUserName=(TextView)findViewById(R.id.username);
        tvTime=(TextView)findViewById(R.id.time);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_goodsdetail,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
