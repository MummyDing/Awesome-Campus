package cn.edu.jxnu.awesome_campus.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.view.IView;

/**
 * Created by MummyDing on 16-1-29.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public abstract class BaseFragment extends Fragment {

    protected View parentView = null;

    private void loadConfig(){

    }

    protected abstract void init();
    public abstract String getTitle();
    public abstract void onEventComing(EventModel eventModel);

    protected abstract int getLayoutID();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = View.inflate(getContext(),getLayoutID(),null);
        init();
        loadConfig();
        return parentView;
    }


    public void onEventMainThread(EventModel eventModel){
        if(eventModel != null){
           onEventComing(eventModel);
        }
    }
}
