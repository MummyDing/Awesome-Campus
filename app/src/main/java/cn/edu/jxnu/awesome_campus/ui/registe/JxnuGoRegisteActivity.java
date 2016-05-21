package cn.edu.jxnu.awesome_campus.ui.registe;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.support.utils.login.JxnuGoRegisteUtil;
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
        setContentView(R.layout.layout_registe_jxnugo);

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
        setToolbarTitle("注册JxnuGo账号");
    }

    private void setupEditTexts() {
        TextWatcher watcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mUsernameEt.getText().toString().equals("") || null == mUsernameEt.getText()
                        || mEmailEt.getText().toString().equals("")|| null == mEmailEt.getText()
                        || mPasswordEt.getText().toString().equals("") || null == mPasswordEt.getText()
                        || mVerityPasswordEt.getText().toString().equals("") || null == mVerityPasswordEt.getText()) {
                    mRegisteBtn.setEnabled(false);
                }
//                else if(!isOKEmail(mEmailEt.getText().toString())) {
//                    mRegisteBtn.setEnabled(false);
//                }
                else{
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

//    /**
//     * 判断邮箱是否合法
//     * @param s
//     * @return
//     */
//    private boolean isOKEmail(String s) {
//        String regex="\\w+@\\w+(\\.\\w{2,3})*\\.\\w{2,3}";
//        if(s.matches(regex))return true;
//        return false;
//    }

    private void setupButton() {
        mRegisteBtn.setEnabled(false);
        mRegisteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mRegisteBtn.isEnabled())
                    return;
                if (!JxnuGoRegisteUtil.verifyEmail(mEmailEt)) {
                    return;
                }
                if (!JxnuGoRegisteUtil.verifyPassword(mPasswordEt, mVerityPasswordEt)) {
                    return;
                }
                /*UploadGoodsUtil.simpleUploadByPath("/system/media/Pre-loaded/Pictures/Picture_03_Eiffel.jpg",
                        new IUploadService.OnUploadListener() {
                            @Override
                            public void onCompleted(String key, ResponseInfo info, JSONObject res) {
                                Toast.makeText(JxnuGoRegisteActivity.this, key + ",\r\n " + info + ",\r\n " + res, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onProcessing(String key, double percent) {

                            }

                            @Override
                            public boolean onCancelled() {
                                return false;
                            }
                        });*/
                Log.d("开始注册","--");
                JxnuGoRegisteUtil.onRegiste(mUsernameEt.getText().toString(),
                        mEmailEt.getText().toString(),
                        mPasswordEt.getText().toString());
            }
        });
    }
}
