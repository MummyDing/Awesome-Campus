package cn.edu.jxnu.awesome_campus.ui.education;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tendcloud.tenddata.TCAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.edu.jxnu.awesome_campus.Config;
import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.education.CourseScoreModel;
import cn.edu.jxnu.awesome_campus.model.home.CourseInfoModel;
import cn.edu.jxnu.awesome_campus.support.theme.ThemeConfig;
import cn.edu.jxnu.awesome_campus.support.utils.common.TermUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.TextUtil;

public class ScoreDetailsDialog extends Activity {
    private static final String TAG="ScoreDetailsDialog";

    private TextView courseName;
    private TextView courseCredit;
    private TextView courseScore;
    private TextView standardScore;
    private ImageButton closeBtn;

    private CourseScoreModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TCAgent.onPageStart(InitApp.AppContext, TAG);
        Log.d("当前选择的颜色下标为：",Config.themeSelected+"");
        setTheme(ThemeConfig.themeDialogStyle[Config.themeSelected]);
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
        // 这里补考成绩有大坑，没有的时候是返回一个空格！！而不是空字符串
        if(TextUtil.isNull(model.getAgainScore())){
            courseScore.setText(model.getCourseScore());
        }else {
            courseScore.setText(model.getCourseScore() + "(" + model.getAgainScore() + ")");
        }
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

        TCAgent.onPageEnd(InitApp.AppContext, TAG);
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
