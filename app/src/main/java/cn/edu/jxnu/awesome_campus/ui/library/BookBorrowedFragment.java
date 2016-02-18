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
        onDataRefresh();
        displayLoading();
        Log.d("Onrefresh","gogo");
    }

    @Override
    public void addHeader() {

    }

    @Override
    public void initView() {
        setOnLineLayout(LibraryLoginUtil.isLogin());
    }

    @Override
    public void onEventComing(EventModel eventModel) {
        super.onEventComing(eventModel);
        switch (eventModel.getEventCode()){
            case EVENT.BOOK_BORROWED_REFRESH_SUCCESS:
                adapter.newList(eventModel.getDataList());
                hideLoading();
                Log.d("Success","好了"+eventModel.getDataList().size());
                break;
            case EVENT.BOOK_BORROWED_REFRESH_FAILURE:
                hideLoading();
                Log.d("Failed","好了");
                break;
        }
    }
}
