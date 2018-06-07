package cn.edu.jxnu.awesome_campus.ui.library;

import android.app.SearchManager;
import android.content.Intent;
import android.content.res.TypedArray;
import android.provider.SearchRecentSuggestions;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tendcloud.tenddata.TCAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.database.dao.library.BookSearchResultDAO;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.library.BookSearchResultModel;
import cn.edu.jxnu.awesome_campus.support.adapter.library.BookSearchResultAdapter;
import cn.edu.jxnu.awesome_campus.support.provider.SuggestionProvider;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.ui.base.BaseToolbarActivity;
import cn.edu.jxnu.awesome_campus.ui.base.SwipeBackActivity;

public class BookSearchActivity extends BaseToolbarActivity {
    private static final String TAG="BookSearchActivity";
    private String keyword;
    private BookSearchResultModel model;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private BookSearchResultAdapter adapter;

    private ProgressBar progressBar;
    private TextView notify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        EventBus.getDefault().register(this);
        TCAgent.onPageStart(InitApp.AppContext, TAG);
        initToolbar();


        /**
         * 测使用　非正式代码　　---- By MummyDing
         */
        Intent intent = getIntent();
        if( Intent.ACTION_SEARCH.equals(intent.getAction())){
            keyword = intent.getStringExtra(SearchManager.QUERY);
        }else {
            keyword = intent.getStringExtra(getString(R.string.id_search));
        }
        setToolbarTitle(InitApp.AppContext.getString(R.string.search_result)+":"+keyword);
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, SuggestionProvider.AUTHORITY,SuggestionProvider.MODE);
        suggestions.saveRecentQuery(keyword,null);
        model = new BookSearchResultModel(keyword);
        layoutManager = new LinearLayoutManager(InitApp.AppContext);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        notify = (TextView) findViewById(R.id.notify);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);


        if(model !=null){
            adapter = new BookSearchResultAdapter(this,model);
            adapter.addKeyword(keyword);
            recyclerView.setAdapter(adapter);
        }
        onDataRefresh();
    }

    private void onDataRefresh(){
        if(model !=null){
            model.loadFromNet();
        }
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        TCAgent.onPageEnd(InitApp.AppContext, TAG);
        super.onDestroy();
    }

    @Subscribe
    public void onEventMainThread(EventModel eventModel){
        switch (eventModel.getEventCode()){
            case EVENT.BOOK_SEARCH_REFRESH_SUCCESS:
                adapter.newList(eventModel.getDataList());
                progressBar.setVisibility(View.GONE);
                break;
            case EVENT.BOOK_SEARCH_REFRESH_FAILURE:
                progressBar.setVisibility(View.GONE);
                notify.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        NetManageUtil.cancelByTag(BookSearchResultDAO.TAG);
    }
}
