package cn.edu.jxnu.awesome_campus.ui.library;

import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.moxun.tagcloudlib.view.TagCloudView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.support.adapter.library.HotSearchAdapter;
import cn.edu.jxnu.awesome_campus.ui.base.BaseListFragment;

/**
 * Created by MummyDing on 16-2-1.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class HotSearchFragment extends BaseListFragment {

    private TagCloudView tagCloudView;
    @Override
    protected int getLayoutID() {
        return R.layout.fragment_hot_search;
    }

    @Override
    protected void init() {
        tagCloudView = (TagCloudView) parentView.findViewById(R.id.tagView);

        List<String> tags = new ArrayList<>();
        for (int i=0 ;i<50;i++){
            tags.add("Android");
        }
        HotSearchAdapter adapter = new HotSearchAdapter(tags,getActivity());
        tagCloudView.setAdapter(adapter);
    }

    @Override
    public String getTitle() {
        return InitApp.AppContext.getString(R.string.hot_search);
    }



    @Override
    public void onDataRefresh() {

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

}
