package cn.edu.jxnu.awesome_campus.ui.registe;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.support.utils.common.DisplayUtil;
import cn.edu.jxnu.awesome_campus.ui.base.BaseToolbarActivity;

/**
 * Created by zpauly on 16-5-11.
 */
public class JxnuGoRegisteActivity extends BaseToolbarActivity {
    private EditText mUsernameEt;
    private EditText mEmailEt;
    private EditText mPasswordEt;
    private EditText mVerityPasswordEt;
    private Button mRegisteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_registe_market);

        initViews();
    }

    private void initViews() {
        mUsernameEt = (EditText) findViewById(R.id.et_username);
        mEmailEt = (EditText) findViewById(R.id.et_email);
        mPasswordEt = (EditText) findViewById(R.id.et_password);
        mVerityPasswordEt = (EditText) findViewById(R.id.et_verifyPassword);
        mRegisteBtn = (Button) findViewById(R.id.registeBtn);

        setupToolbar();

        setupEditTexts();

        setupButton();
    }

    private void setupToolbar() {
        initToolbar();
        setToolbarTitle("注册JxnuGo");
    }

    private void setupEditTexts() {
        TextWatcher watcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mUsernameEt.getText().toString() == "" || null == mUsernameEt.getText()
                        || mEmailEt.getText().toString() == "" || null == mEmailEt.getText()
                        || mPasswordEt.getText().toString() == "" || null == mPasswordEt.getText()
                        || mVerityPasswordEt.getText().toString() == "" || null == mVerityPasswordEt.getText()) {
                    mRegisteBtn.setEnabled(false);
                } else {
                    mRegisteBtn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        mUsernameEt.addTextChangedListener(watcher);
        mEmailEt.addTextChangedListener(watcher);
        mPasswordEt.addTextChangedListener(watcher);
        mVerityPasswordEt.addTextChangedListener(watcher);
    }

    private void setupButton() {
        mRegisteBtn.setEnabled(false);
        mRegisteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mRegisteBtn.isEnabled())
                    return;
                if (mPasswordEt.getText().toString().length() < 6) {
                    DisplayUtil.Snack(mPasswordEt, "密码长度不得小于6位");
                    mPasswordEt.setText("");
                    mVerityPasswordEt.setText("");
                    return;
                }
                if (!verifyEmail(mEmailEt.getText().toString())) {
                    DisplayUtil.Snack(mEmailEt, "邮件格式错误");
                    mEmailEt.setText("");
                    return;
                }
                if (!mPasswordEt.getText().toString().equals(mVerityPasswordEt.getText().toString())) {
                    DisplayUtil.Snack(mRegisteBtn, "两次输入密码不一致");
                    mPasswordEt.setText("");
                    mVerityPasswordEt.setText("");
                    return;
                }
            }
        });
    }

    private boolean verifyEmail(String email) {
        String regex = "\\w+@\\w+\\.(com\\.cn)|\\w+@\\w+\\.(com|cn)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }
}
