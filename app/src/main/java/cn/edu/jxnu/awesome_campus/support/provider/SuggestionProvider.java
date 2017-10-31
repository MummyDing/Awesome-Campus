package cn.edu.jxnu.awesome_campus.support.provider;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by MummyDing on 16-2-6.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class SuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "cn.edu.jxnu.awesome_campus.support.provider.SuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public SuggestionProvider() {
        setupSuggestions(AUTHORITY,MODE);
    }
}
