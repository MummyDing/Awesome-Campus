package cn.edu.jxnu.awesome_campus.ui.library;

import android.util.Log;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.library.BookBorrowedModel;
import cn.edu.jxnu.awesome_campus.support.adapter.library.BookBorrowedAdapter;
import cn.edu.jxnu.awesome_campus.support.utils.login.EducationLoginUtil;
import cn.edu.jxnu.awesome_campus.support.utils.login.LibraryLoginUtil;
import cn.edu.jxnu.awesome_campus.ui.base.BaseListFragment;

/**
 * Created by MummyDing on 16-2-1.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class BookBorrowedFragment extends BaseListFragment{

    private BookBorrowedModel model;

    @Override
    public String getTitle() {
        return InitApp.AppContext.getString(R.string.book_borrowed);
    }


    @Override
    public void onDataRefresh() {
        model.loadFromNet();
    }

    @Override
    public void bindAdapter() {
        model = new BookBorrowedModel();
        adapter = new BookBorrowedAdapter(getActivity(),model);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void addHeader() {

    }

    @Override
    public void initView() {
        setOnLineLayout(LibraryLoginUtil.isLogin());
        if (LibraryLoginUtil.isLogin()) {
            model.loadFromCache();
        }
    }

    @Override
    public void onEventComing(EventModel eventModel) {
        super.onEventComing(eventModel);
        switch (eventModel.getEventCode()){
            case EVENT.BOOK_BORROWED_LOAD_CACHE_SUCCESS:
            case EVENT.BOOK_BORROWED_REFRESH_SUCCESS:
                Log.d("sis000e",eventModel.getDataList().size()+"");
                adapter.newList(eventModel.getDataList());
                hideLoading();
                break;
            case EVENT.BOOK_BORROWED_REFRESH_FAILURE:
                hideLoading();
                break;
            case EVENT.BOOK_BORROWED_LOAD_CACHE_FAILURE:
                onDataRefresh();
                break;
        }
    }
}
