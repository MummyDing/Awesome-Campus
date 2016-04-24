package cn.edu.jxnu.awesome_campus.support.adapter.library;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import cn.edu.jxnu.awesome_campus.model.library.SelfStudySeatLeftModel;
import cn.edu.jxnu.awesome_campus.support.adapter.BaseListAdapter;

/**
 * Created by MummyDing on 16-4-24.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class SelfStudySeatsAdapter extends BaseListAdapter<SelfStudySeatLeftModel,SelfStudySeatsAdapter.VH>{


    public SelfStudySeatsAdapter(Context mContext, SelfStudySeatLeftModel model) {
        super(mContext, model);
    }

    @Override
    protected void updateView() {

    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {

    }

    static class VH extends RecyclerView.ViewHolder{

        public VH(View itemView) {
            super(itemView);
        }
    }
}
