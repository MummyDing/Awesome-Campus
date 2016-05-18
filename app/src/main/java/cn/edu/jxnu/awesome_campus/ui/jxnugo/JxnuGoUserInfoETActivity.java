package cn.edu.jxnu.awesome_campus.ui.jxnugo;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.api.JxnuGoApi;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoPeopleBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoPeopleModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoUserBean;
import cn.edu.jxnu.awesome_campus.support.spkey.JxnuGoStaticKey;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonEntityCallback;
import cn.edu.jxnu.awesome_campus.ui.base.BaseToolbarActivity;

/**
 * 个人信息修改界面
 * Created by KevinWu on 16-5-18.
 */
public class JxnuGoUserInfoETActivity extends BaseToolbarActivity{
    public static final String TAG="JxnuGoUserInfoETActivity";
    private static final String title="修改个人信息";
    private MenuItem doneMenu;
    private EditText nicknameET,locationET,contactET,aboutmeET;
    private SimpleDraweeView avatarImageView;
    private JxnuGoUserBean bean;
    private MaterialSpinner sexSpinner;
    private Handler handler=new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_edit_jxnugo_userinfo);
        initToolbar();
        setToolbarTitle(title);
        initView();
        loadDataFromNet();
    }


    private void initView() {
        nicknameET=(EditText)findViewById(R.id.name);
        locationET=(EditText)findViewById(R.id.location);
        contactET=(EditText)findViewById(R.id.contact);
        aboutmeET=(EditText)findViewById(R.id.aboutme);
        avatarImageView=(SimpleDraweeView)findViewById(R.id.jxnugo_user_img);
        avatarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePic();
            }
        });
    }

    /**
     * 调用多图选择器
     */
    private void choosePic() {
    }



    private void fillData() {

//        nicknameET.setText();
//        contactET.setText();
        if(bean.getAbout_me()!=null){
            aboutmeET.setText(bean.getAbout_me());
        }
        if(bean.getAvatar()!=null){
            avatarImageView.setImageURI(Uri.parse(bean.getAvatar()));
        }
        if(bean.getContactMe()!=null)
            contactET.setText(bean.getContactMe());
        if(bean.getName()!=null)
            nicknameET.setText(bean.getName());
    }

    @Override
    protected void onDestroy() {
//        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    /**
     * 重网络加载数据
     */
    private void loadDataFromNet() {
        SPUtil sp=new SPUtil(this);
        int userId=sp.getIntSP(JxnuGoStaticKey.SP_FILE_NAME,JxnuGoStaticKey.USERID);
        String userName = sp.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.USERNAME);
        String password = sp.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.PASSWORD);
        NetManageUtil.getAuth(JxnuGoApi.BaseUserUrl+userId)
                .addUserName(userName)
                .addPassword(password)
                .addTag(TAG)
                .enqueue(new JsonEntityCallback<JxnuGoUserBean>() {
                    @Override
                    public void onFailure(IOException e) {

                    }

                    @Override
                    public void onSuccess(JxnuGoUserBean entity, Headers headers) {
                        bean=entity;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                fillData();
                            }
                        });

                    }
                });
    }
}
