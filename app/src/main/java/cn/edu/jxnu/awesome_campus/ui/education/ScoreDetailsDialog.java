package cn.edu.jxnu.awesome_campus.ui.education;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.education.CourseScoreModel;
import cn.edu.jxnu.awesome_campus.model.home.CourseInfoModel;

public class ScoreDetailsDialog extends AppCompatActivity {

/*
    course_id

    13-物二-吴启东 2016-2-17 13:30:26
    course_name

    13-物二-吴启东 2016-2-17 13:30:35
    course_credit

    13-物二-吴启东 2016-2-17 13:30:43
    course_score

    13-物二-吴启东 2016-2-17 13:30:54
    standard_score*/

    private TextView courseName;
    private TextView courseCredit;
    private TextView courseScore;
    private TextView standardScore;
    private ImageButton closeBtn;

    private CourseScoreModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_course_score);
        EventBus.getDefault().register(this);
    }

    private void initView(){
        courseName = (TextView) findViewById(R.id.course_name);
        courseCredit = (TextView)findViewById(R.id.course_credit);
        courseScore = (TextView)findViewById(R.id.course_score);
        standardScore = (TextView)findViewById(R.id.standard_score);
        closeBtn = (ImageButton) findViewById(R.id.btn_close);

        courseName.setText(model.getCourseName());
        courseCredit.setText(model.getCourseCredit());
        courseScore.setText(model.getCourseScore()+"("+model.getAgainScore()+")");
        standardScore.setText(model.getStandardScore());

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 1)
    public void onEventMainThread(EventModel eventModel) {
        if(eventModel.getEventCode() == EVENT.SEND_MODEL_DETAIL){
            model = (CourseScoreModel) eventModel.getData();
            initView();
        }
    }


}
