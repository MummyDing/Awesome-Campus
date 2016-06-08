package cn.edu.jxnu.awesome_campus.support.adapter.jxnugo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.api.JxnuGoApi;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.GoodsModel;
import cn.edu.jxnu.awesome_campus.support.adapter.BaseListAdapter;
import cn.edu.jxnu.awesome_campus.ui.jxnugo.GoodsDetailActivity;

/**
 * 固定商品列表（相对可变的GoodsListAdapter来说）
 * Created by KevinWu on 16-5-17.
 */
public class CLGoodsListAdapter extends BaseListAdapter<GoodsModel,CLGoodsListAdapter.VH> {
    public static final String TAG="CLGoodsListAdapter";
    private boolean hasLogin=false;//为真时为自身用户
    public CLGoodsListAdapter(Context mContext, GoodsModel model) {
        super(mContext, model);
    }
    public CLGoodsListAdapter(Context mContext, GoodsModel model,boolean hasLogin) {
        super(mContext, model);
        this.hasLogin=hasLogin;
    }
    @Override
    protected void updateView() {
        notifyDataSetChanged();
    }


    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_goods_list, parent, false);
        VH vh = new VH(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        final GoodsModel model=getItem(position);
        holder.time.setText(model.getTimestamp());
        holder.goodName.setText(model.getGoodsName());
        holder.goodPrice.setText(model.getGoodsPrice()+"");
        if(model.getPhotos()!=null&&model.getPhotos().length>0){
            holder.goodFirstImg.setImageURI(Uri.parse(JxnuGoApi.BasePicUrl+model.getPhotos()[0].getKey()+"?imageMogr2/thumbnail/2000x2000"));
        }
        else{
            holder.goodFirstImg.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new EventModel<GoodsModel>(EVENT.GOODS_DETAIL_INTENT,model));
                Intent intent=new Intent();
                intent.setClass(mContext, GoodsDetailActivity.class);
                mContext.startActivity(intent);
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new EventModel<Integer>(EVENT.JXNUGO_TRIGGER_DELETE_POST,model.getPostId()));
                    }
                });
            }
        });

    }

    class VH extends RecyclerView.ViewHolder {
        View itemView;
        TextView time;
        TextView goodName;
        TextView goodPrice;
        SimpleDraweeView goodFirstImg;
        LinearLayout deleteLayout;
        ImageButton deleteButton;

        public VH(View itemView) {
            super(itemView);
            this.itemView = itemView;
            time=(TextView)itemView.findViewById(R.id.goods_time);
            goodName=(TextView)itemView.findViewById(R.id.goods_title);
            goodPrice=(TextView)itemView.findViewById(R.id.goods_price);
            goodFirstImg=(SimpleDraweeView)itemView.findViewById(R.id.goods_image);
            deleteLayout=(LinearLayout)itemView.findViewById(R.id.delete_layout);
            deleteButton=(ImageButton) itemView.findViewById(R.id.delete_button);
            if(hasLogin){
                deleteLayout.setVisibility(View.VISIBLE);
            }
        }
    }
}
