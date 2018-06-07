package cn.edu.jxnu.awesome_campus.ui.jxnugo;

import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.squareup.okhttp.Headers;
import com.tendcloud.tenddata.TCAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.api.JxnuGoApi;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.GoodsPhotoModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoPeopleBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoPeopleModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoUserBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.PhotokeyBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.UpdateInfoBean;
import cn.edu.jxnu.awesome_campus.support.Settings;
import cn.edu.jxnu.awesome_campus.support.loader.FrescoImageLoader;
import cn.edu.jxnu.awesome_campus.support.spkey.JxnuGoStaticKey;
import cn.edu.jxnu.awesome_campus.support.utils.common.ImageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.jxnugo.UploadImageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.jxnugo.UploadUserETInfoUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonEntityCallback;
import cn.edu.jxnu.awesome_campus.ui.base.BaseToolbarActivity;
import cn.edu.jxnu.awesome_campus.view.widget.sexspinner.OnSexChangedListener;
import cn.edu.jxnu.awesome_campus.view.widget.sexspinner.SexList;
import cn.edu.jxnu.awesome_campus.view.widget.sexspinner.SexSpinnerWrapper;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * 个人信息修改界面
 * Created by KevinWu on 16-5-18.
 */
public class JxnuGoUserInfoETActivity extends BaseToolbarActivity{
    public static final String TAG="JxnuGoUserInfoETActivity";
    private static final String title="修改个人信息";
    private MenuItem doneMenu;
    private final int REQUEST_CODE_GALLERY = 1001;
    private EditText nicknameET,locationET,contactET,aboutmeET;
    private SimpleDraweeView avatarImageView;
    private JxnuGoUserBean bean;
    private boolean hasSpinnerSelected;
    private String sexSelected= SexList.sex[0];
    private String rawImgString;//原头像地址（用于判断是否需要上传新头像）
    private Handler handler=new Handler(Looper.getMainLooper());
    private MaterialSpinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        TCAgent.onPageStart(InitApp.AppContext, TAG);
        setContentView(R.layout.activity_edit_jxnugo_userinfo);
        initToolbar();
        setToolbarTitle(title);
        initView();
        addSexSpinner();
        loadDataFromNet();
    }

    //添加性别选择spinner
    private void addSexSpinner() {
        SexSpinnerWrapper spinner=new SexSpinnerWrapper();
        spinner.setOnSexChangedListener(new OnSexChangedListener() {
            @Override
            public void onSexChanged(String sex) {
                sexSelected=sex;
                hasSpinnerSelected=true;
            }
        });
        mSpinner=spinner.build((MaterialSpinner) findViewById(R.id.sexspinner));
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
        ThemeConfig themeConfig = setImageSelectorTheme();
        FunctionConfig.Builder functionConfigBuilder = new FunctionConfig.Builder();
        FrescoImageLoader imageLoader;
        imageLoader = new FrescoImageLoader(JxnuGoUserInfoETActivity.this);
        functionConfigBuilder.setEnableRotate(true);
        functionConfigBuilder.setEnableEdit(true);
        functionConfigBuilder.setEnableCamera(true);
        functionConfigBuilder.setEnablePreview(true);
        functionConfigBuilder.setCropSquare(true);
        functionConfigBuilder.setEnableCrop(true);
        functionConfigBuilder.setForceCrop(true);
        functionConfigBuilder.setEnableRotate(true);
        final FunctionConfig functionConfig = functionConfigBuilder.build();
        File fileDir = new File("/sdcard/DCIM/AwesomeCampus/");
        CoreConfig coreConfig = new CoreConfig.Builder(JxnuGoUserInfoETActivity.this, imageLoader, themeConfig)
                .setFunctionConfig(functionConfig)
                .setTakePhotoFolder(fileDir)
                .setEditPhotoCacheFolder(fileDir)
                .setNoAnimcation(true)
                .build();
        GalleryFinal.init(coreConfig);
        GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);
    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                bean.setAvatar(resultList.get(0).getPhotoPath());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        fillData();
                    }
                });
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {

        }
    };

    private void fillData() {
        Log.d("取得的联系方式为","--"+bean.getContactMe());
        if(bean.getAbout_me()!=null&&aboutmeET.getText().toString().equals("")){
            aboutmeET.setText(bean.getAbout_me());
        }
        if(bean.getAvatar()!=null){
            if(!rawImgString.equals(bean.getAvatar()))
            avatarImageView.setImageURI(Uri.parse("file://"+bean.getAvatar()));
            else
                avatarImageView.setImageURI(Uri.parse(bean.getAvatar()));
        }
        if(bean.getContactMe()!=null&&contactET.getText().toString().equals(""))
            contactET.setText(bean.getContactMe());
        if(bean.getName()!=null&&nicknameET.getText().toString().equals(""))
            nicknameET.setText(bean.getName());
        if(bean.getLocation()!=null&&locationET.getText().toString().equals(""))
            locationET.setText(bean.getLocation());
        if(bean.getSex()!=null&&!hasSpinnerSelected){
            for(int i=0;i<SexList.sex.length;i++){
                if(SexList.sex[i].equals(bean.getSex().trim())){
                    mSpinner.setSelectedIndex(i);
                }
            }
        }
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
        doneMenu=menu.findItem(R.id.menu_new_goods_done);
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
                        if(entity!=null){
                            bean=entity;
                            rawImgString=entity.getAvatar();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    fillData();
                                }
                            });
                        }


                    }
                });
    }

    /**
     * 设置多图选择器的主题
     * @return
     */
    private ThemeConfig setImageSelectorTheme() {
        ThemeConfig.Builder thBuilder = new ThemeConfig.Builder();
        ThemeConfig theme = null;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            TypedArray array = getTheme().obtainStyledAttributes(new int[]{
                    android.R.attr.colorPrimary, android.R.attr.colorPrimaryDark
            });
            theme = thBuilder.setTitleBarBgColor(array.getColor(0, 0x000000))
                    .setFabNornalColor(array.getColor(0, 0x000000))
                    .setFabPressedColor(array.getColor(array.getIndex(1), 0x000000))
                    .setCheckSelectedColor(array.getColor(0, 0x000000))
                    .setCropControlColor(array.getColor(0, 0x000000)).build();
            array.recycle();
        } else {
            theme = ThemeConfig.DARK;
        }
        return theme;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_new_goods_done:
                if (!bean.getAvatar().equals(rawImgString)){
                    ArrayList<String> paths=new ArrayList<>();
                    paths.add(bean.getAvatar());
                    UploadImageUtil.onUploadImages(this,paths);
                }else{
                    upLoadData();
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void upLoadData() {
        Log.d("头像地址为",bean.getAvatar());
        UpdateInfoBean updateInfoBean=new UpdateInfoBean(bean.getUserId()+"",nicknameET.getText().toString()
                ,locationET.getText().toString()
                ,sexSelected
                ,contactET.getText().toString()
                ,aboutmeET.getText().toString()
                ,bean.getAvatar()
        , Settings.getJxnugoAuthToken());
        UploadUserETInfoUtil.uploadData(this,updateInfoBean);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventModel eventModel) {
        switch (eventModel.getEventCode()) {
            case EVENT.IMAGES_UPLOAD_SUCCESS:
                List<PhotokeyBean> keys = (ArrayList<PhotokeyBean>) eventModel.getData();
                Log.d("取得的key",keys.get(0).getKey()+" ");
                if(keys!=null&&keys.size()>0)
                    bean.setAvatar(JxnuGoApi.BasePicUrl+keys.get(0).getKey());
                upLoadData();
                break;
            case EVENT.IMAGES_UPLOAD_FAILURE:
                break;
            case EVENT.UPDATE_USER_INFO_SUCCESS:
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new EventModel<Void>(EVENT.JXNUGO_REFRESH_USERINFO_TRIGGER));
                    }
                });
                this.finish();
                break;
            case EVENT.UPDATE_USER_INFO_FAILURE:
                break;
        }
    }
}
