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
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.library.BookSearchResultModel;
import cn.edu.jxnu.awesome_campus.support.adapter.library.BookSearchResultAdapter;
import cn.edu.jxnu.awesome_campus.support.provider.SuggestionProvider;
import cn.edu.jxnu.awesome_campus.ui.base.SwipeBackActivity;

public class BookSearchActivity extends SwipeBackActivity {
    private Toolbar toolbar;
    private String keyword;
    private BookSearchResultModel model;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private BookSearchResultAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        EventBus.getDefault().register(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.search_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        TypedArray array = getTheme().obtainStyledAttributes(new int[] {
                android.R.attr.colorPrimary,
        });
        toolbar.setBackgroundColor(array.getColor(0,0xFFFFFF));
        array.recycle();


        /**
         * 测使用　非正式代码　　---- By MummyDing
         */
        Intent intent = getIntent();
        if( intent.ACTION_SEARCH.equals(intent.getAction())){
            keyword = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, SuggestionProvider.AUTHORITY,SuggestionProvider.MODE);
            suggestions.saveRecentQuery(keyword,null);
            model = new BookSearchResultModel(keyword);
            setTitle(keyword);
        }

        Log.d("keyword",keyword);
        layoutManager = new LinearLayoutManager(InitApp.AppContext);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onDataRefresh();
            }
        });

        if(model !=null){
            adapter = new BookSearchResultAdapter(this,model);
            adapter.addKeyword(keyword);
            recyclerView.setAdapter(adapter);
        }
        onDataRefresh();
    }

    private void onDataRefresh(){
        if(model !=null){
            if(refreshLayout.isRefreshing() == false){
                refreshLayout.setRefreshing(true);
            }
            model.loadFromNet();
        }
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEventMainThread(EventModel eventModel){
        switch (eventModel.getEventCode()){
            case EVENT.BOOK_SEARCH_REFRESH_SUCCESS:
                adapter.newList(eventModel.getDataList());
                Log.d("size",eventModel.getDataList().size()+"");
                refreshLayout.setRefreshing(false);
                break;
            case EVENT.BOOK_BORROWED_REFRESH_FAILURE:
                Log.d("没有找到","");
                break;
        }
    }



}
