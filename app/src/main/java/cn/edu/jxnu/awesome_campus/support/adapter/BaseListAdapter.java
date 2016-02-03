package cn.edu.jxnu.awesome_campus.support.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * Created by MummyDing on 16-2-3.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public abstract class BaseListAdapter<M extends IModel,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected List<M> mItems;
    protected Context mContext;

    public BaseListAdapter(Context mContext,M model) {
        this.mContext = mContext;
        model.loadFromCache(mItems);
    }
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    protected M getItem(int position){
        return mItems.get(position);
    }
}
