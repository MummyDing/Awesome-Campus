package cn.edu.jxnu.awesome_campus.database.dao.library;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.database.DatabaseHelper;
import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.database.table.library.BookBorrowedTable;
import cn.edu.jxnu.awesome_campus.support.spkey.LibraryStaticKey;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.library.BookBorrowedModel;
import cn.edu.jxnu.awesome_campus.support.htmlparse.libary.BookBorrowedParse;
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

        if (list == null || list.isEmpty()){
            return false;
        }

        clearCache();

        for (int i=0 ; i<list.size() ; i++){
            BookBorrowedModel borrowedModel = list.get(i);
            ContentValues values = new ContentValues();
            values.put(BookBorrowedTable.BOOK_CODE,borrowedModel.getBookCode());
            values.put(BookBorrowedTable.BOOK_TITLE,borrowedModel.getBookTitle());
            values.put(BookBorrowedTable.AUTHOR,borrowedModel.getAuthor());
            values.put(BookBorrowedTable.BORROW_TIME,borrowedModel.getBorrowTime());
            values.put(BookBorrowedTable.SHOULD_BACK_TIME,borrowedModel.getShouldBackTime());
            values.put(BookBorrowedTable.AGAIN_TIMES,borrowedModel.getAgainTimes());
            values.put(BookBorrowedTable.BOOK_LOCATION,borrowedModel.getBookLocation());
            DatabaseHelper.insert(BookBorrowedTable.NAME,values);
        }
        return true;
    }

    @Override
    public boolean clearCache() {
        DatabaseHelper.clearTable(BookBorrowedTable.NAME);
        return true;
    }

    @Override
    public void loadFromCache() {
        final Handler handler = new Handler(Looper.getMainLooper());
        Cursor cursor = DatabaseHelper.selectAll(BookBorrowedTable.NAME);

        final List<BookBorrowedModel> list = new ArrayList<>();
        while (cursor.moveToNext()){
            BookBorrowedModel model = new BookBorrowedModel();
            model.setBookCode(cursor.getString(BookBorrowedTable.ID_BOOK_CODE));
            model.setBookTitle(cursor.getString(BookBorrowedTable.ID_BOOK_TITLE));
            model.setAuthor(cursor.getString(BookBorrowedTable.ID_AUTHOR));
            model.setBorrowTime(cursor.getString(BookBorrowedTable.ID_BORROW_TIME));
            model.setShouldBackTime(cursor.getString(BookBorrowedTable.ID_SHOULD_BACK_TIME));
            model.setAgainTimes(cursor.getString(BookBorrowedTable.ID_AGAIN_TIMES));
            model.setBookLocation(cursor.getString(BookBorrowedTable.ID_BOOK_LOCATION));
            list.add(model);
        }

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (!list.isEmpty()){
                    // 从缓存获取成功　发送消息
                    EventBus.getDefault().post(new EventModel<BookBorrowedModel>(EVENT.BOOK_BORROWED_LOAD_CACHE_SUCCESS,list));

                }else {
                    //从缓存获取失败
                    EventBus.getDefault().post(new EventModel<BookBorrowedModel>(EVENT.BOOK_BORROWED_LOAD_CACHE_FAILURE));
                }
            }
        });
    }

    @Override
    public void loadFromNet() {
        final Handler handler = new Handler(Looper.getMainLooper());
        SPUtil spu = new SPUtil(InitApp.AppContext);
        String cookies = spu.getStringSP(LibraryStaticKey.SP_FILE_NAME, LibraryStaticKey.COOKIE);
        NetManageUtil.get(Urlconfig.Library_Book_Borrowed_URL)
                .addHeader("Cookie", cookies)
                .addTag(TAG).enqueue(new StringCallback() {
            @Override
            public void onSuccess(String result, Headers headers) {
                BookBorrowedParse myPrase = new BookBorrowedParse(result);
                final List list = myPrase.getEndList();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (list != null && !list.isEmpty()) {
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
