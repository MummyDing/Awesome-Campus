package cn.edu.jxnu.awesome_campus.support.recycle;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

/**
 * Created by yzr on 16/10/16.
 */

public abstract class BaseSwipeAdapter<T>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int FOOTER_TYPE=1000;

    private boolean isShowFooter;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==FOOTER_TYPE){
            return createFooterViewHolder(parent);
        }else{
            return createNormalViewHolder(parent,viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(isShowFooter&&position==getItemCount()-1){
            if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
                params.setFullSpan(true);
            }
            bindFooterViewHolder(holder);
        }else {
            bindNormalViewHolder(holder,position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowFooter&&position==getItemCount()-1){
            return FOOTER_TYPE;
        }else{
            return getNormalItemViewType(position);
        }
    }

    @Override
    public int getItemCount() {
        return getNormalItemCount()+(isShowFooter?1:0);
    }

    public abstract int getNormalItemViewType(int position);
    public abstract int getNormalItemCount();


    public abstract RecyclerView.ViewHolder createFooterViewHolder(ViewGroup parent);


    public abstract void bindFooterViewHolder(RecyclerView.ViewHolder holder);

    public abstract RecyclerView.ViewHolder createNormalViewHolder(ViewGroup parent,int viewType);

    public abstract void bindNormalViewHolder(RecyclerView.ViewHolder viewHolder,int position);

    public boolean isFooter(int position){
        return isShowFooter&&position==getItemCount()-1;
    }

    public void onLoadMoreStateChange(boolean isShown) {
        this.isShowFooter= isShown;
        if (isShown) {
            notifyItemInserted(getItemCount());
        } else {
            notifyItemRemoved(getItemCount());
        }
    }

}