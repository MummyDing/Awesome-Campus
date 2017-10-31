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
import cn.edu.jxnu.awesome_campus.support.config.Urlconfig;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCallback;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCodeCallback;

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
                if (list!=null&&!list.isEmpty()){
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
        SPUtil spu = new SPUtil(InitApp.AppContext);
        String id=spu.getStringSP(LibraryStaticKey.SP_FILE_NAME,LibraryStaticKey.ID);
        String password=spu.getStringSP(LibraryStaticKey.SP_FILE_NAME,LibraryStaticKey.PASSWORD);
        NetManageUtil.post(Urlconfig.Library_Login_URL)
                .addTag(TAG)
                .addParams("number", id)
                .addParams("passwd", password)
                .addParams("select", "cert_no")
                .addParams("returnUrl", "")
                .enqueue(new StringCodeCallback() {
                    @Override
                    public void onSuccess(String result,int code, Headers headers) {
                        System.out.println("获取到的状态码为：" + code);

                        String cookies = null;
                        for (int i = 0; i < headers.size(); i++) {
                            if (headers.name(i).equals("Set-Cookie")) {
                                cookies = headers.value(i);
                                break;
                            }
                        }
//                        System.out.println(cookies);
                        //账号密码正确，执行重定向
                        if(code==302){
//                            final String finalCookies = cookies;
//                            new Handler(Looper.getMainLooper()).post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    EventBus.getDefault().post(new EventModel<String>(EVENT.LIBRARY_GET_COOKIE_SUCCESS, finalCookies));
//                                }
//                            });
                            toFollowRedirects(cookies);
                        }else{
                            //密码错误
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    EventBus.getDefault().post(new EventModel<String>(EVENT.LIBRARY_LOGIN_FAILURE));
                                }
                            });
                        }

                    }

                    @Override
                    public void onFailure(String error) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new EventModel<String>(EVENT.LIBRARY_LOGIN_FAILURE_NETWORKERROR));
                            }
                        });
                    }
                });
    }
    private void toFollowRedirects(final String cookie){
        final Handler handler = new Handler(Looper.getMainLooper());
        NetManageUtil.get(Urlconfig.Library_Book_Borrowed_URL)
                .addHeader("Cookie", cookie)
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
//                            把cookies保存一下
                            SPUtil sp = new SPUtil(InitApp.AppContext);
                            sp.putStringSP(LibraryStaticKey.SP_FILE_NAME,LibraryStaticKey.COOKIE,cookie);
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
