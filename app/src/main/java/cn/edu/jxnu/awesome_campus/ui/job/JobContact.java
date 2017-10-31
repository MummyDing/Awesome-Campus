package cn.edu.jxnu.awesome_campus.ui.job;

import java.util.List;

import cn.edu.jxnu.awesome_campus.model.job.Post;

/**
 * Created by yzr on 16/10/17.
 */

public class JobContact {


    public interface View{
        void onMoreJobData(List<Post>list);
        void onRefreshJobData(List<Post>list);
        void onError(String err);
    }
   public interface Presenter{
        void moreJobData();
        void refreshJobData();
    }
}
