package cn.edu.jxnu.awesome_campus.database.dao.library;

import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.database.spkey.LibraryStaticKey;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.library.BookBorrowedModel;
import cn.edu.jxnu.awesome_campus.support.htmlparse.Library.BookBorrowedParse;
import cn.edu.jxnu.awesome_campus.support.urlconfig.Urlconfig;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCallback;

/**
 * Created by MummyDing on 16-2-2.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class BookBorrowedDAO implements DAO<BookBorrowedModel> {
    public static final String TAG="BookBorrowedDAO";
    @Override
    public boolean cacheAll(List<BookBorrowedModel> list) {
        return false;
    }

    @Override
    public boolean clearCache() {
        return false;
    }

    @Override
    public void loadFromCache() {

    }

    @Override
    public void loadFromNet() {
        final Handler handler = new Handler(Looper.getMainLooper());
        SPUtil spu = new SPUtil(InitApp.AppContext);
        String cookies = spu.getStringSP(LibraryStaticKey.SP_FILE_NAME, LibraryStaticKey.COOKIE);
        NetManageUtil.get(Urlconfig.ExamTime_URL)
                .addHeader("Cookie", cookies)
                .addTag(TAG).enqueue(new StringCallback() {
            @Override
            public void onSuccess(String result, Headers headers) {
                BookBorrowedParse myPrase = new BookBorrowedParse(result);
                final List list = myPrase.getEndList();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (list != null) {
                            // 缓存数据
                            cacheAll(list);
                            //发送获取成功消息
                            EventBus.getDefault().post(new EventModel<BookBorrowedModel>(EVENT.BOOK_BORROWED_REFRESH_SUCCESS, list));
                        } else {
                            //发送获取失败消息
                            EventBus.getDefault().post(new EventModel<BookBorrowedModel>(EVENT.BOOK_BORROWED_REFRESH_FAILURE));
                        }
                    }
                });
            }

            @Override
            public void onFailure(String error) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new EventModel<BookBorrowedModel>(EVENT.BOOK_BORROWED_REFRESH_FAILURE));
                    }
                });
            }
        });

    }
}
