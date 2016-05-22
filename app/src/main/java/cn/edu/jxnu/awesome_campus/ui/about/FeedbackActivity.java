package cn.edu.jxnu.awesome_campus.ui.about;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.tendcloud.tenddata.TCAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.FeedbackBean;
import cn.edu.jxnu.awesome_campus.support.utils.common.DeviceUtil;
import cn.edu.jxnu.awesome_campus.support.utils.feedback.FeedbackPostUtil;
import cn.edu.jxnu.awesome_campus.ui.base.BaseToolbarActivity;

/**
 * Created by KevinWu on 16-5-22.
 */
public class FeedbackActivity extends BaseToolbarActivity {
    public static final String TAG="FeedbackActivity";
    private static final String title= InitApp.AppContext.getString(R.string.feedback);
    private EditText respondentET;
    private EditText emailET;
    private EditText bodyET;
    private MenuItem sendMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TCAgent.onPageStart(InitApp.AppContext, TAG);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_feedback);
        initToolbar();
        setToolbarTitle(title);
        initView();
    }

    /**
     * 初始化布局中的元素
     */
    private void initView() {
        respondentET=(EditText)findViewById(R.id.respondent);
        emailET=(EditText)findViewById(R.id.email);
        bodyET=(EditText)findViewById(R.id.body);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        TCAgent.onPageEnd(InitApp.AppContext, TAG);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_newgoods, menu);
        sendMenu=menu.findItem(R.id.menu_new_goods_done);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_new_goods_done:
                if(!respondentET.getText().toString().equals("")
                        &&!bodyET.getText().toString().equals("")){
//                    拼接机型信息
                    String deviceInfo= "发行版本："+DeviceUtil.getVerName()+"  ||  版本号："+DeviceUtil.getVerCode()+"  ||  机型："+DeviceUtil.getPhoneModel()+"  ||  系统："+DeviceUtil.getOSVerson();
                    Log.d(TAG,deviceInfo);
                    sendMenu.setVisible(false);
                    FeedbackPostUtil.postFeedback(new FeedbackBean(respondentET.getText().toString(),
                            emailET.getText().toString(),bodyET.getText().toString()+"\n( 设备相关信息： "+deviceInfo+" )"));
                }else{
                    Snackbar.make(getCurrentFocus(), "请至少填入称呼与反馈信息",Snackbar.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventComming(EventModel eventModel) {
        switch (eventModel.getEventCode()) {
            case EVENT.FEEDBACK_SUCCESS:
                Snackbar.make(getCurrentFocus(), "反馈成功，2s后将退出反馈界面",Snackbar.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        finish();
                    }
                }, 2000);
                break;
            case EVENT.FEEDBACK_FAILURE:
                sendMenu.setVisible(true);
                Snackbar.make(getCurrentFocus(), "抱歉，反馈失败，请稍后再试",Snackbar.LENGTH_SHORT).show();
                break;
        }
    }

}
