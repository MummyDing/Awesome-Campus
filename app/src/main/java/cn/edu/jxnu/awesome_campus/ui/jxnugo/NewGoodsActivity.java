package cn.edu.jxnu.awesome_campus.ui.jxnugo;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.squareup.okhttp.Headers;
import com.tendcloud.tenddata.TCAgent;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.model.jxnugo.GoodsPhotoModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.PublishGoodsBean;
import cn.edu.jxnu.awesome_campus.support.adapter.jxnugo.ChoosePicAdapter;
import cn.edu.jxnu.awesome_campus.support.loader.FrescoImageLoader;
import cn.edu.jxnu.awesome_campus.support.utils.common.TextUtil;
import cn.edu.jxnu.awesome_campus.support.utils.jxnugo.UploadGoodsUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonEntityCallback;
import cn.edu.jxnu.awesome_campus.support.utils.net.qiniuservice.IUploadService;
import cn.edu.jxnu.awesome_campus.support.utils.net.request.PostJsonRequest;
import cn.edu.jxnu.awesome_campus.ui.base.BaseToolbarActivity;
import cn.edu.jxnu.awesome_campus.view.widget.goodtagspinner.GoodTagSpinnerWrapper;
import cn.edu.jxnu.awesome_campus.view.widget.goodtagspinner.OnGoodTagChangedLister;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.galleryfinal.widget.HorizontalListView;

/**
 * Created by KevinWu on 16-5-13.
 */
public class NewGoodsActivity extends BaseToolbarActivity implements View.OnClickListener {
    public static final String TAG = "NewGoodsActivity";
    private static final String title = InitApp.AppContext.getString(R.string.jxnugo_newgoods_postnewgood);
    private final int REQUEST_CODE_GALLERY = 1001;
    private int goodTag = 4;//默认其它
    private List<GoodsPhotoModel> mPhotoList;
    private ArrayList<String> photoKeys = new ArrayList<>();
    private ChoosePicAdapter adapter;
    private HorizontalListView picListView;
    private AppCompatButton addPicButton;
    private EditText goodNameET;
    private EditText yearET;
    private EditText monthET;
    private EditText dayET;
    private EditText priceET;
    private EditText amountET;
    private EditText positionET;
    private EditText contactET;
    private EditText discribtionET;
    private IUploadService.OnUploadListener uploadListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TCAgent.onPageStart(InitApp.AppContext, TAG);
        setContentView(R.layout.activity_jxnugo_new_goods);
        swipeEnabled = false;
        initToolbar();
        initView();
        bindAdapter();
        addTagSpinner();
        setToolbarTitle(title);
    }

    private void addTagSpinner() {
        GoodTagSpinnerWrapper spinnerWrapper = new GoodTagSpinnerWrapper();
        spinnerWrapper.setOnTagChangeedListener(new OnGoodTagChangedLister() {
            @Override
            public void onTagChanged(int tag) {
                goodTag = tag;
            }
        });
        spinnerWrapper.build((MaterialSpinner) findViewById(R.id.spinner));
    }

    private void initView() {
        picListView = (HorizontalListView) findViewById(R.id.lv_photo);
        addPicButton = (AppCompatButton) findViewById(R.id.addPic);
        goodNameET=(EditText)findViewById(R.id.title);
        yearET=(EditText)findViewById(R.id.year);
        monthET=(EditText)findViewById(R.id.month);
        dayET=(EditText)findViewById(R.id.day);
        priceET=(EditText)findViewById(R.id.price);
        amountET=(EditText)findViewById(R.id.count);
        positionET=(EditText)findViewById(R.id.pos);
        contactET=(EditText)findViewById(R.id.contact);
        discribtionET=(EditText)findViewById(R.id.description);
        addPicButton.setOnClickListener(this);
    }

    private void bindAdapter() {
        mPhotoList = new ArrayList<>();
        adapter = new ChoosePicAdapter(NewGoodsActivity.this, mPhotoList);
        picListView.setAdapter(adapter);
    }

    private void callImageSelector() {
        ThemeConfig themeConfig = setImageSelectorTheme();
        FunctionConfig.Builder functionConfigBuilder = new FunctionConfig.Builder();
        cn.finalteam.galleryfinal.ImageLoader imageLoader;
        imageLoader = new FrescoImageLoader(NewGoodsActivity.this);
        functionConfigBuilder.setMutiSelectMaxSize(6);
        if(mPhotoList.size()>0){
            List <PhotoInfo> tempPhotolist=new ArrayList<>();
            for(int i=0;i<mPhotoList.size();i++){
                PhotoInfo p=new PhotoInfo();
                p.setHeight(mPhotoList.get(i).getHeight());
                p.setWidth(mPhotoList.get(i).getWidth());
                p.setPhotoId(mPhotoList.get(i).getPhotoId());
                p.setPhotoPath(mPhotoList.get(i).getPhotoPath());
                tempPhotolist.add(p);
            }
            functionConfigBuilder.setSelected(tempPhotolist);
        }
        functionConfigBuilder.setEnableEdit(false);
        functionConfigBuilder.setEnableCamera(true);
        functionConfigBuilder.setEnablePreview(true);
//        functionConfigBuilder.setEnableRotate(true);
        final FunctionConfig functionConfig = functionConfigBuilder.build();
        File fileDir = new File("/sdcard/DICM/AwesomeCampus/");
        CoreConfig coreConfig = new CoreConfig.Builder(NewGoodsActivity.this, imageLoader, themeConfig)
                .setFunctionConfig(functionConfig)
                .setTakePhotoFolder(fileDir)
                .setNoAnimcation(true)
                .build();
        GalleryFinal.init(coreConfig);
        GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);
        initFresco();
    }

    /**
     * 判斷是否輸入完成了必要信息
     * @return
     */
    private boolean completeInput(){
        if(!TextUtil.isNull(goodNameET.getText().toString())
                &&!TextUtil.isNull(priceET.getText().toString())
                &&!TextUtil.isNull(amountET.getText().toString())
                &&!TextUtil.isNull(positionET.getText().toString())
                &&!TextUtil.isNull(contactET.getText().toString())
                &&!TextUtil.isNull(discribtionET.getText().toString()))return true;
        return false;
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
            theme = theme.DARK;
        }
        return theme;
    }


    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                mPhotoList.clear();
                for (int i = 0; i < resultList.size(); i++) {
                    mPhotoList.add(new GoodsPhotoModel(resultList.get(i).getPhotoId(),
                            resultList.get(i).getPhotoPath(),
                            resultList.get(i).getWidth(),
                            resultList.get(i).getHeight()));
                }
                adapter.notifyDataSetChanged();
////                mChoosePhotoListAdapter.notifyDataSetChanged();
//                Log.d(TAG,"取得的图片的数量为："+resultList.size());
//                Log.d(TAG,"目标图片数量为："+mPhotoList.size());
//                Log.d(TAG,"第一个图片路劲为"+mPhotoList.get(0).getPhotoPath());
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TCAgent.onPageEnd(InitApp.AppContext, TAG);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addPic:
                callImageSelector();
                break;
        }
    }


    private void initFresco() {
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setBitmapsConfig(Bitmap.Config.ARGB_8888)
                .build();
        Fresco.initialize(this, config);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_newgoods, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_new_goods_done:
                if(!completeInput()){
                    Snackbar.make(getCurrentFocus(), R.string.jxnugo_newgoods_inputillegal,Snackbar.LENGTH_SHORT).show();
                }
                else{
                    //这里上传帖子信息，用Eventbus发送消息到外部处理
                    UploadGoodsUtil.onUploadImages(this, mPhotoList);

                    final PublishGoodsBean bean = new PublishGoodsBean();
                    bean.setBody(discribtionET.getText().toString());
                    bean.setContact(contactET.getText().toString());
                    if (!TextUtil.isNull(yearET.getText().toString())
                            && !TextUtil.isNull(monthET.getText().toString())
                            && !TextUtil.isNull(dayET.getText().toString()))
                        bean.setGoodBuyTime(yearET + "-" + monthET + "-" + dayET);
                    bean.setGoodLocation(positionET.getText().toString());
                    bean.setGoodName(goodNameET.getText().toString());
                    bean.setGoodPrice(Float.valueOf(priceET.getText().toString()));
                    bean.setGoodTag(goodTag);
                    bean.setGoodNum(Integer.valueOf(amountET.getText().toString()));
                    bean.setGoodQuality("");

                    UploadGoodsUtil.onUploadJson(bean);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
