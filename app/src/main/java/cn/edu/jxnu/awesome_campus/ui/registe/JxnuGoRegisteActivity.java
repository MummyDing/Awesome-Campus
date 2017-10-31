package cn.edu.jxnu.awesome_campus.ui.registe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.GoodsModel;
import cn.edu.jxnu.awesome_campus.support.utils.login.JxnuGoRegisteUtil;
import cn.edu.jxnu.awesome_campus.ui.base.BaseToolbarActivity;
import cn.edu.jxnu.awesome_campus.ui.jxnugo.NoticeActivity;

/**
 * Created by zpauly on 16-5-11.
 */
public class JxnuGoRegisteActivity extends BaseToolbarActivity {

    private EditText mUsernameEt;
    private EditText mEmailEt;
    private EditText mPasswordEt;
    private EditText mVerityPasswordEt;
    private Button mRegisteBtn;
    private TextView noticeTV;
    private CheckBox agreeCB;
    private AppCompatButton mBackButton;
    private LinearLayout contentLayout, finishLayout;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.layout_registe_jxnugo);
        initViews();
    }

    private void initViews() {
        mUsernameEt = (EditText) findViewById(R.id.et_username);
        mEmailEt = (EditText) findViewById(R.id.et_email);
        mPasswordEt = (EditText) findViewById(R.id.et_password);
        mVerityPasswordEt = (EditText) findViewById(R.id.et_verifyPassword);
        mRegisteBtn = (Button) findViewById(R.id.registeBtn);
        mBackButton = (AppCompatButton) findViewById(R.id.back_button);
        contentLayout = (LinearLayout) findViewById(R.id.content_layout);
        finishLayout = (LinearLayout) findViewById(R.id.finish_layout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        noticeTV=(TextView)findViewById(R.id.noticeText);
        agreeCB=(CheckBox)findViewById(R.id.agreeCB);
        setupToolbar();
//        setupEditTexts();
        setupButton();
    }

    private void setupToolbar() {
        initToolbar();
        setToolbarTitle("注册JxnuGo账号");
    }

//    private void setupEditTexts() {
//        TextWatcher watcher = new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (mUsernameEt.getText().toString().equals("") || null == mUsernameEt.getText()
//                        || mEmailEt.getText().toString().equals("")|| null == mEmailEt.getText()
//                        || mPasswordEt.getText().toString().equals("") || null == mPasswordEt.getText()
//                        || mVerityPasswordEt.getText().toString().equals("") || null == mVerityPasswordEt.getText()) {
//                    mRegisteBtn.setEnabled(false);
//                }
//                else{
//                    mRegisteBtn.setEnabled(true);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        };
//        mUsernameEt.addTextChangedListener(watcher);
//        mEmailEt.addTextChangedListener(watcher);
//        mPasswordEt.addTextChangedListener(watcher);
//        mVerityPasswordEt.addTextChangedListener(watcher);
//    }


    private void setupButton() {
//        mRegisteBtn.setEnabled(false);
        mRegisteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUsernameEt.getText().toString().equals("") || null == mUsernameEt.getText()
                        || mEmailEt.getText().toString().equals("") || null == mEmailEt.getText()
                        || mPasswordEt.getText().toString().equals("") || null == mPasswordEt.getText()
                        || mVerityPasswordEt.getText().toString().equals("") || null == mVerityPasswordEt.getText()) {
                    Snackbar.make(getCurrentFocus(), "请输入完整信息！", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(!agreeCB.isChecked()){
                    Snackbar.make(getCurrentFocus(), "请您先仔细阅读协议并勾选同意！", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (!JxnuGoRegisteUtil.verifyEmail(mEmailEt)) {
                    Snackbar.make(getCurrentFocus(), "邮箱不合法，请重新输入！", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (!JxnuGoRegisteUtil.verifyPassword(mPasswordEt, mVerityPasswordEt)) {
                    Snackbar.make(getCurrentFocus(), "两次密码不一致，请重新输入！", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                Log.d("开始注册", "--");
                mRegisteBtn.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                JxnuGoRegisteUtil.onRegiste(mUsernameEt.getText().toString(),
                        mEmailEt.getText().toString(),
                        mPasswordEt.getText().toString());
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        noticeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(JxnuGoRegisteActivity.this, NoticeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Subscribe
    public void onEventMainThread(EventModel eventModel) {
        switch (eventModel.getEventCode()) {
            case EVENT.JXNUGO_REGISTER_SUCCESS:
                contentLayout.setVisibility(View.GONE);
                finishLayout.setVisibility(View.VISIBLE);
                break;
            case EVENT.JXNUGO_REGISTER_FAILURE:
                Snackbar.make(getCurrentFocus(), "注册失败，请稍后再试！", Snackbar.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                mRegisteBtn.setVisibility(View.VISIBLE);
                break;
            case EVENT.JXNUGO_REGISTER_FAILURE_SAME_NAME:
                progressBar.setVisibility(View.GONE);
                mRegisteBtn.setVisibility(View.VISIBLE);
                Snackbar.make(getCurrentFocus(), "用户名已被注册，请更换后重试", Snackbar.LENGTH_SHORT).show();
                break;
            case EVENT.JXNUGO_REGISTER_FAILURE_SAME_EMAIL:
                progressBar.setVisibility(View.GONE);
                mRegisteBtn.setVisibility(View.VISIBLE);
                Snackbar.make(getCurrentFocus(), "邮箱已被注册，请更换后重试", Snackbar.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
