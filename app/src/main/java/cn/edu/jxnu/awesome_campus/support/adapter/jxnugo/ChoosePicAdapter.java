package cn.edu.jxnu.awesome_campus.support.adapter.jxnugo;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import java.util.List;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.model.jxnugo.GoodsPhotoModel;
import cn.edu.jxnu.awesome_campus.support.loader.FrescoImageLoader;
import cn.finalteam.galleryfinal.widget.GFImageView;
import cn.finalteam.toolsfinal.DeviceUtils;

/**
 * Created by KevinWu on 16-5-14.
 */
public class ChoosePicAdapter extends BaseAdapter {
    private List<GoodsPhotoModel> mList;
    private LayoutInflater mInflater;
    private int mScreenWidth;
    private Context mContext;

    public ChoosePicAdapter(Activity activity, List<GoodsPhotoModel> list){
        this.mList = list;
        this.mContext=activity;
        this.mInflater = LayoutInflater.from(activity);
        this.mScreenWidth = DeviceUtils.getScreenPix(activity).widthPixels;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GFImageView iv=(GFImageView)mInflater.inflate(R.layout.card_choose_photolist, null);
        setHeight(iv);
        FrescoImageLoader fl=new FrescoImageLoader(mContext);
        fl.displayImage((Activity)mContext,
                mList.get(position).getPhotoPath(),
                iv,
                ContextCompat.getDrawable(mContext,R.drawable.ic_gf_default_photo),
                100,
                100);
        return iv;

    }
    private void setHeight(final View convertView) {
        int height = mScreenWidth / 3 - 8;
        convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
    }
}
