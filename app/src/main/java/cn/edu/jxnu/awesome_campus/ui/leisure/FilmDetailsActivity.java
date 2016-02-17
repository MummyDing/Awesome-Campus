package cn.edu.jxnu.awesome_campus.ui.leisure;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.leisure.FilmModel;
import cn.edu.jxnu.awesome_campus.ui.base.BaseDetailsActivity;

/**
 * Created by MummyDing on 16-2-17.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class FilmDetailsActivity extends BaseDetailsActivity {
    public static final String TAG = "FilmDetailsActivity";
    private FilmModel model = new FilmModel();
    @Override
    protected void onDataRefresh() {
        // 这里请求详情
    }

    @Override
    protected void onEventComing(EventModel eventModel) {
        switch (eventModel.getEventCode()){
            case EVENT.SEND_MODEL_DETAIL:
                model = (FilmModel) eventModel.getData();
                initView();
                break;
            case EVENT.FILM_DETAILS_REFRESH_SUCCESS:
                hideLoading();
                break;
            case EVENT.FILM_DETAILS_REFRESH_FAILURE:
                hideLoading();
                displayNetworkError();
                break;
        }
    }

    @Override
    protected String getShareInfo() {
        return "["+model.getTitle()+"]:"+model.getUrl()+" ( "+getString(R.string.text_share_from)+getString(R.string.app_name)+")";

    }
}
