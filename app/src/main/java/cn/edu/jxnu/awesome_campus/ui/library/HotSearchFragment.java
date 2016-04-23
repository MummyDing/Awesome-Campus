package cn.edu.jxnu.awesome_campus.ui.library;


import android.util.Log;
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
    private HotSearchModel model;
    private List<HotSearchModel> modelList;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_hot_search;
    }

    @Override
    protected void init() {
        model=new HotSearchModel();
        model.loadFromNet();
        tagCloudView = (TagCloudView) parentView.findViewById(R.id.tagView);

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

        Log.d("绑定adapter","--");
        List<String> tags = new ArrayList<>();
        for (int i = 0; i < modelList.size(); i++) {
            tags.add(modelList.get(i).getTag());
        }
        HotSearchAdapter adapter = new HotSearchAdapter(tags, getActivity());
        tagCloudView.setAdapter(adapter);
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
            case EVENT.BOOK_HOT_SEARCH_REFRESH_SUCCESS:
                Log.d("标签列表大小", eventModel.getDataList().size() + "");
                modelList = eventModel.getDataList();
                hideLoading();
                break;
            case EVENT.BOOK_HOT_SEARCH_REFRESH_FAILURE:
                hideLoading();
                break;
        }
    }
}
