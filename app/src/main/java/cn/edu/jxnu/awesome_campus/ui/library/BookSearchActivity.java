package cn.edu.jxnu.awesome_campus.ui.library;

import android.app.SearchManager;
import android.content.Intent;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.support.provider.SuggestionProvider;

public class BookSearchActivity extends AppCompatActivity {

    private String keyword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        /**
         * 测使用　非正式代码　　---- By MummyDing
         */
        Intent intent = getIntent();
        if( intent.ACTION_SEARCH.equals(intent.getAction())){
            keyword = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, SuggestionProvider.AUTHORITY,SuggestionProvider.MODE);
            suggestions.saveRecentQuery(keyword,null);
            Log.d("got",keyword);
        }


    }
}
