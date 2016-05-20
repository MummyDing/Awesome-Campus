package cn.edu.jxnu.awesome_campus.support.adapter.jxnugo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ramotion.foldingcell.FoldingCell;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.api.JxnuGoApi;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.GoodsModel;
import cn.edu.jxnu.awesome_campus.model.leisure.DailyModel;
import cn.edu.jxnu.awesome_campus.support.adapter.BaseListAdapter;
import cn.edu.jxnu.awesome_campus.ui.jxnugo.GoodsDetailActivity;

/**
 * Created by KevinWu on 16-5-12.
 */
public class GoodsListAdapter extends RecyclerView.Adapter {
    private LayoutInflater mLayoutInflater;
    private List<GoodsModel> mList;
    private Context mContext;
    public GoodsListAdapter(Context context,List<GoodsModel> mList){
        this.mContext=context;
        mLayoutInflater = LayoutInflater.from(context);
        this.mList=mList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(mLayoutInflater.inflate(R.layout.card_goods_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final GoodsModel model=mList.get(position);
        VH vh=(VH)holder;
        vh.time.setText(model.getTimestamp());
        vh.goodName.setText(model.getGoodName());
        vh.goodPrice.setText(model.getGoodPrice()+"");
        if(model.getPhotos()!=null&&model.getPhotos().length>0)
            vh.goodFirstImg.setImageURI(Uri.parse(JxnuGoApi.BasePicUrl+model.getPhotos()[0].getKey()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new EventModel<GoodsModel>(EVENT.GOODS_DETAIL_INTENT,model));
                Intent intent=new Intent();
                intent.setClass(mContext, GoodsDetailActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


//    @Override
//    public void onBindViewHolder(VH holder, int position) {
//        final GoodsModel model=getItem(position);
//        holder.time.setText(model.getTimestamp());
//        holder.goodName.setText(model.getGoodName());
//        holder.goodPrice.setText(model.getGoodPrice()+"");
//        if(model.getPhoto()!=null&&model.getPhoto().length>0)
//        holder.goodFirstImg.setImageURI(Uri.parse(model.getPhoto()[0]));
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EventBus.getDefault().postSticky(new EventModel<GoodsModel>(EVENT.GOODS_DETAIL_INTENT,model));
//                Intent intent=new Intent();
//                intent.setClass(mContext, GoodsDetailActivity.class);
//                mContext.startActivity(intent);
//            }
//        });
//
//    }
//
   static class VH extends RecyclerView.ViewHolder {
        View itemView;
        TextView time;
        TextView goodName;
        TextView goodPrice;
        SimpleDraweeView goodFirstImg;
        public VH(View itemView) {
            super(itemView);
            this.itemView = itemView;
            time=(TextView)itemView.findViewById(R.id.goods_time);
            goodName=(TextView)itemView.findViewById(R.id.goods_title);
            goodPrice=(TextView)itemView.findViewById(R.id.goods_price);
            goodFirstImg=(SimpleDraweeView)itemView.findViewById(R.id.goods_image);
        }
    }

}
