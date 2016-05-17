package cn.edu.jxnu.awesome_campus.support.adapter.jxnugo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import cn.edu.jxnu.awesome_campus.R;
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
    public CLGoodsListAdapter(Context mContext, GoodsModel model) {
        super(mContext, model);
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
        holder.goodName.setText(model.getGoodName());
        holder.goodPrice.setText(model.getGoodPrice()+"");
        if(model.getPhoto()!=null&&model.getPhoto().length>0)
            holder.goodFirstImg.setImageURI(Uri.parse(model.getPhoto()[0]));

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

    class VH extends RecyclerView.ViewHolder {
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
