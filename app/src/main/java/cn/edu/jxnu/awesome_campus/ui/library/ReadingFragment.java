package cn.edu.jxnu.awesome_campus.ui.library;

import android.app.SearchManager;
import android.content.Context;
import android.view.TextureView;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.ui.base.BaseListFragment;

/**
 * Created by MummyDing on 16-2-1.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class ReadingFragment extends BaseListFragment {
    @Override
    public String getTitle() {
        return InitApp.AppContext.getString(R.string.reading);
    }

    @Override
    public void cardViewTransition() {

    }

    @Override
    public void bindAdapter() {

    }

    @Override
    public void addHeader() {

        /**
         * 测使用　非正式代码！！！！！　－－－－By MummyDing
         */
        final SearchView searchView = new SearchView(getContext());
        searchView.setQueryHint("点击搜索");
        searchView.setFilterTouchesWhenObscured(true);
        searchView.setSubmitButtonEnabled(true);
        SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });

        headerLayout.addView(searchView);

    }

    @Override
    public void initView() {

    }

    @Override
    protected void onNetworkBtnClick() {

    }
}
