package cn.edu.jxnu.awesome_campus.ui.job;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import cn.edu.jxnu.awesome_campus.model.job.JobBean;
import cn.edu.jxnu.awesome_campus.support.recycle.BaseSwipeAdapter;
import cn.edu.jxnu.awesome_campus.support.recycle.BaseSwipeFragment;

/**
 * Created by yzr on 16/10/16.
 */

public class JobFragment extends BaseSwipeFragment<JobBean> {

    @Override
    public BaseSwipeAdapter<JobBean> createItemAdapter(List<JobBean> list) {
        return null;
    }

    @Override
    public RecyclerView.LayoutManager createLayoutManager() {
        return null;
    }

    @Override
    public void loadMore() {

    }

    @Override
    public void refresh() {

    }
}
