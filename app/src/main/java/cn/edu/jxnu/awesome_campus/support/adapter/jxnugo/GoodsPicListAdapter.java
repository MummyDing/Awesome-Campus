package cn.edu.jxnu.awesome_campus.support.adapter.jxnugo;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.model.IModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.GoodsPhotoModel;
import cn.edu.jxnu.awesome_campus.support.adapter.BaseListAdapter;
import cn.edu.jxnu.awesome_campus.support.loader.FrescoImageLoader;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.galleryfinal.widget.GFImageView;

/**
 * Created by KevinWu on 16-5-14.
 */
public class GoodsPicListAdapter extends BaseListAdapter<GoodsPhotoModel,GoodsPicListAdapter.VH> {

    public GoodsPicListAdapter(Context mContext, GoodsPhotoModel model) {
        super(mContext, model);
    }

    @Override
    protected void updateView() {
notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_selected_pic, parent, false);
        VH vh = new VH(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        GoodsPhotoModel model=getItem(position);
        Log.d("设置图片","--");

//        holder.showPic.setImageURI(Uri.parse("file:/" + model.getPhotoPath()));
        FrescoImageLoader fl=new FrescoImageLoader(mContext);
        fl.displayImage((Activity)mContext,
                model.getPhotoPath(),
                holder.showPic,
                ContextCompat.getDrawable(mContext,R.drawable.ic_gf_default_photo),
                100,
                100);
    }

    class VH extends RecyclerView.ViewHolder {
        View itemView;
        ImageView deletePic;
        GFImageView showPic;

        public VH(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.deletePic=(ImageView)itemView.findViewById(R.id.delete);
            this.showPic=(GFImageView)itemView.findViewById(R.id.addPic);

        }
    }
}
