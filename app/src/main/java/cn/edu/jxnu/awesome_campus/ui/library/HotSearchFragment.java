package cn.edu.jxnu.awesome_campus.ui.library;


import android.util.Log;
import android.widget.ProgressBar;

import com.moxun.tagcloudlib.view.TagCloudView;
import java.util.ArrayList;
import java.util.List;
import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.library.HotSearchModel;
import cn.edu.jxnu.awesome_campus.support.adapter.library.HotSearchAdapter;
import cn.edu.jxnu.awesome_campus.ui.base.BaseListFragment;

/**
 * Created by MummyDing on 16-2-1.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class HotSearchFragment extends BaseListFragment {

    private TagCloudView tagCloudView;
    private HotSearchModel model = new HotSearchModel();
    private HotSearchAdapter searchAdapter;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_hot_search;
    }


    @Override
    protected void init() {
        tagCloudView = (TagCloudView) parentView.findViewById(R.id.tagView);
        progressBar = (ProgressBar) parentView.findViewById(R.id.progressBar);
        searchAdapter = new HotSearchAdapter(getActivity());
        tagCloudView.setAdapter(searchAdapter);
        model.loadFromCache();
    }

    @Override
    public String getTitle() {
        return InitApp.AppContext.getString(R.string.hot_search);
    }


    @Override
    public void onDataRefresh() {
        model.loadFromNet();
    }

    @Override
    public void bindAdapter() {
    }

    @Override
    public void addHeader() {
    }

    @Override
    public void initView() {

    }

    @Override
    public void onEventComing(EventModel eventModel) {
        super.onEventComing(eventModel);
        switch (eventModel.getEventCode()) {
            case EVENT.HOT_SEARCH_REFRESH_SUCCESS:
                Log.d("标签列表大小", eventModel.getDataList().size() + "");
                searchAdapter.newTags(eventModel.getDataList());
                hideLoading();
                break;
            case EVENT.HOT_SEARCH_REFRESH_FAILURE:
                hideLoading();
                break;
            case EVENT.HOT_SEARCH_LOAD_CACHE_SUCCESS:
                searchAdapter.newTags(eventModel.getDataList());
                hideLoading();
                onDataRefresh();
                break;
            case EVENT.HOT_SEARCH_LOAD_CACHE_FAILURE:
                onDataRefresh();
                break;
        }
    }
}
