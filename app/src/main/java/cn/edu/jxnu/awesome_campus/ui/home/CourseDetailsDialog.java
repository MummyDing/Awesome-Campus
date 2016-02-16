package cn.edu.jxnu.awesome_campus.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Browser;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.database.spkey.EducationStaticKey;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.home.CourseInfoModel;
import cn.edu.jxnu.awesome_campus.support.urlconfig.Urlconfig;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;

public class CourseDetailsDialog extends Activity {

    private TextView courseName;
    private TextView courseID;
    private TextView courseTeacher;
    private TextView courseClass;
    private TextView classmateLink;
    private TextView classForumLink;


    private CourseInfoModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_course_info);
        EventBus.getDefault().register(this);
    }

    private void initView(){
        courseName = (TextView) findViewById(R.id.course_name);
        courseID = (TextView) findViewById(R.id.course_id);
        courseTeacher = (TextView) findViewById(R.id.course_teacher);
        courseClass = (TextView) findViewById(R.id.course_class);
        classmateLink = (TextView) findViewById(R.id.classmate_list_link);
        classForumLink = (TextView) findViewById(R.id.class_forum_link);

        courseName.setText(model.getCourseName());
        courseID.setText(model.getCourseID());
        courseTeacher.setText(model.getCourseTeacher());
        courseClass.setText(model.getCourseClass());
//        classmateLink.setText(buildHtmlLink("Classmates", Urlconfig.Education_Classmate_Base_URL+model.getClassmateListLink()));
//        classForumLink.setText(buildHtmlLink("Forum",Urlconfig.Education_CourseForum_Base_URL+model.getClassForumLink()));
//        classmateLink.setMovementMethod(LinkMovementMethod.getInstance());
//        classForumLink.setMovementMethod(LinkMovementMethod.getInstance());

        classmateLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(Urlconfig.Education_Classmate_Base_URL+model.getClassmateListLink()));
                Bundle bundle = new Bundle();
                SPUtil sp=new SPUtil(CourseDetailsDialog.this);
                bundle.putString("_ga", "GA1.3.609810117.1451115712");
                bundle.putString("ASP.NET_SessionId", sp.getStringSP(EducationStaticKey.SP_FILE_NAME, EducationStaticKey.BASE_COOKIE));
                bundle.putString("JwOAUserSettingNew", sp.getStringSP(EducationStaticKey.SP_FILE_NAME, EducationStaticKey.SPECIAL_COOKIE));
                i.putExtra(Browser.EXTRA_HEADERS, bundle);
                startActivity(i);
            }
        });
    }

    private Spanned buildHtmlLink(String text, String link){
        return Html.fromHtml("<a href='"+link+"'>"+text+"</a>");
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 1)
    public void onEventMainThread(EventModel eventModel) {
        if(eventModel.getEventCode() == EVENT.SEND_MODEL_DETAIL){
            model = (CourseInfoModel) eventModel.getData();
            initView();
        }
    }
}
