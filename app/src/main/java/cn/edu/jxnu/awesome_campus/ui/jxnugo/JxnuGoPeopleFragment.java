package cn.edu.jxnu.awesome_campus.ui.jxnugo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.jxnugo.JxnuGoPeopleDao;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoLoginBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoPeopleBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoPeopleListBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoPeopleModel;
import cn.edu.jxnu.awesome_campus.support.adapter.jxnugo.JxnuGoPeopleAdapter;
import cn.edu.jxnu.awesome_campus.ui.base.BaseListFragment;

/**
 * Created by Thereisnospon on 16/5/14.
 * 显示用户关注的人的信息或者被关注的信息
 */
public class JxnuGoPeopleFragment extends BaseListFragment {


    private  String title="关注";

    private  String TAG="JxnuGoPeopleFragments";

    JxnuGoPeopleDao.MODE mode= JxnuGoPeopleDao.MODE.FOLLOWED;


    JxnuGoPeopleDao dao;
    public  static  JxnuGoPeopleFragment  newInstance(JxnuGoPeopleDao.MODE mode,int id){
        JxnuGoPeopleFragment fragment=new JxnuGoPeopleFragment();
        fragment.dao=new JxnuGoPeopleDao(mode,id);
        fragment.mode=mode;
        return  fragment;
    }


    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public void bindAdapter() {
        if(dao!=null){
            Log.d(TAG,"BIND ADAPTER sxx");
            adapter=new JxnuGoPeopleAdapter(getContext(),null);
            recyclerView.setAdapter(adapter);
            dao.loadFromNet();

            displayLoading();
        }
    }


    @Override
    public void addHeader() {


    }

    @Override
    public void initView() {
            Log.d(TAG,"init");
    }

    List<JxnuGoPeopleModel> getPeopleList(JxnuGoPeopleListBean bean){
        Log.d(TAG,"GET LIST");
        if(mode== JxnuGoPeopleDao.MODE.FOLLOWED) {
            Log.d(TAG,"LOAD FOLLOWED");
            return Arrays.asList(bean.getFollowed());
        }else{
            Log.d(TAG,"LOAD FOLLOWERS");
            return Arrays.asList(bean.getFollowers());
        }
    }

    void  loadPeopleInfo(EventModel eventModel){
        JxnuGoPeopleListBean bean=(JxnuGoPeopleListBean)eventModel.getData();
        List<JxnuGoPeopleModel>list=getPeopleList(bean);
        Log.d(TAG,bean.getFollowed().length+"");
        Log.d(TAG,"size x"+list.size());
        adapter.newList(list);
        Log.d(TAG,"LOAD PEOPLEE INFO");
    }

    @Override
    public void onEventComing(EventModel eventModel) {
        switch (eventModel.getEventCode())
        {
            case EVENT.JXNUGO_LOAD_PEOPLELIST_SUCCESS:
                loadPeopleInfo(eventModel);
                Log.d(TAG,"LOAD SUCCESS");
                hideLoading();
                break;
            case EVENT.JXNUGO_LOAD_PEOPLELIST_FAILURE:
                Log.d(TAG,"LOAD FAILURE");
                hideLoading();
                break;
        }
    }


}
