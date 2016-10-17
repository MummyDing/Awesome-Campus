package cn.edu.jxnu.awesome_campus.ui.job;

/**
 * Created by yzr on 16/10/17.
 */

public class JobDataPresenter implements  JobContact.Presenter {


    JobContact.View view;
    JobContact.Model model;

    public JobDataPresenter(JobContact.View view) {
        this.view = view;
    }

    @Override
    public void moreJobData() {

    }

    @Override
    public void refreshJobData() {

    }
}
