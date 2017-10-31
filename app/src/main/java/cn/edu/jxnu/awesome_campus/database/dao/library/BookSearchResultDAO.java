package cn.edu.jxnu.awesome_campus.database.dao.library;

import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.support.spkey.LibraryStaticKey;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.library.BookSearchResultModel;
import cn.edu.jxnu.awesome_campus.support.htmlparse.libary.BookSearchResultParse;
import cn.edu.jxnu.awesome_campus.support.config.Urlconfig;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCallback;

/**
 * Created by MummyDing on 16-2-19.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class BookSearchResultDAO implements DAO<BookSearchResultModel> {

    public static final String TAG = "BookSearchResultDAO";

    private String keyword;

    @Override
    public boolean cacheAll(List<BookSearchResultModel> list) {
        return false;
    }

    @Override
    public boolean clearCache() {
        return false;
    }

    @Override
    public void loadFromCache() {

    }


    public BookSearchResultDAO(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void loadFromNet() {
        final Handler handler = new Handler(Looper.getMainLooper());
        SPUtil spu = new SPUtil(InitApp.AppContext);
        String cookies = spu.getStringSP(LibraryStaticKey.SP_FILE_NAME, LibraryStaticKey.COOKIE);

        //未登录的情况，防止cookies为空报错
        if(cookies==null)cookies="---";

        NetManageUtil.get(Urlconfig.Library_Book_Search_URL)
                .addHeader("Cookie", cookies)
                .addParams("strSearchType","title")
                .addParams("historyCount","1")
                .addParams("strText", keyword)
                .addParams("x", "0")
                .addParams("y","0")
                .addParams("doctype","ALL")
                .addParams("match_flag","forward")
                .addParams("displaypg","200000")
                .addParams("sort","CATA_DATE")
                .addParams("orderby","desc")
                .addParams("showmode","list")
                .addParams("dept","ALL")
                .addTag(TAG).enqueue(new StringCallback() {
            @Override
            public void onSuccess(String result, Headers headers) {
                BookSearchResultParse myParse = new BookSearchResultParse(result);
                final List list = myParse.getEndList();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (list != null) {
                            // 缓存数据
                            cacheAll(list);
                            //发送获取成功消息
                            EventBus.getDefault().post(new EventModel<BookSearchResultModel>(EVENT.BOOK_SEARCH_REFRESH_SUCCESS, list));
                        } else {
                            //发送获取失败消息
                            EventBus.getDefault().post(new EventModel<BookSearchResultModel>(EVENT.BOOK_SEARCH_REFRESH_FAILURE));
                        }
                    }
                });
            }

            @Override
            public void onFailure(String error) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new EventModel<BookSearchResultModel>(EVENT.BOOK_SEARCH_REFRESH_FAILURE));
                    }
                });
            }
        });

    }
}
