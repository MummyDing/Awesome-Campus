package cn.edu.jxnu.awesome_campus.support.recycle;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.edu.jxnu.awesome_campus.R;

/**
 * Created by yzr on 16/10/16.
 */

public abstract class NormalSwipeAdapter<T> extends BaseSwipeAdapter<T> {


    List<T> mDataList;

    public NormalSwipeAdapter(List<T> list) {
        this.mDataList = list;
    }

    public static final int ITEM_TYPE=1;

    @Override
    public int getNormalItemViewType(int position) {
        return ITEM_TYPE;
    }

    public T getItem(int position){
        return mDataList.get(position);
    }

    @Override
    public int getNormalItemCount() {
        return mDataList==null?0:mDataList.size();
    }


    @Override
    public RecyclerView.ViewHolder createFooterViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_footer,parent,false);
        return new FootViewHolder(view);
    }

    private static class FootViewHolder extends RecyclerView.ViewHolder{
        public FootViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public void bindFooterViewHolder(RecyclerView.ViewHolder holder) {

    }
}
