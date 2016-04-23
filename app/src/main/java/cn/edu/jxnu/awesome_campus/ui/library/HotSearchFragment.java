package cn.edu.jxnu.awesome_campus.ui.library;

import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.ui.base.BaseListFragment;
import me.next.tagview.TagCloudView;

/**
 * Created by MummyDing on 16-2-1.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class HotSearchFragment extends BaseListFragment {

    private TagCloudView tagTop;
    private TagCloudView tagBottom;
    private LinearLayout layout;
    private ScrollView scrollView;
    private final Handler mHandler = new Handler();
    @Override
    protected int getLayoutID() {
        return R.layout.fragment_hot_search;
    }

    @Override
    protected void init() {
        tagTop = (TagCloudView) parentView.findViewById(R.id.tag_top);
        tagBottom = (TagCloudView) parentView.findViewById(R.id.tag_bottom);
        layout = (LinearLayout) parentView.findViewById(R.id.layout);
        scrollView = (ScrollView) parentView.findViewById(R.id.scrollView);

        List<String> tags = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            tags.add("标签" + i);
        }
        tagTop.setTags(tags);
        tagBottom.setTags(tags);
        mHandler.post(ScrollRunnable);
    }

    @Override
    public String getTitle() {
        return InitApp.AppContext.getString(R.string.reading);
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
    private Runnable ScrollRunnable= new Runnable() {
        @Override
        public void run() {
            int off = layout.getMeasuredHeight() - scrollView.getHeight();//判断高度
            if (off > -50000) {
                scrollView.scrollBy(0, 10);
                if (scrollView.getScrollY() == off) {
                    Thread.currentThread().interrupt();
                    scrollView.scrollTo(0,0);
                    mHandler.post(ScrollRunnable);
                } else {
                    mHandler.postDelayed(this, 5000);
                }
            }
        }
    };
}
