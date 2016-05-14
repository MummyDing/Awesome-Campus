package cn.edu.jxnu.awesome_campus.ui.jxnugo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.model.jxnugo.GoodsPhotoModel;
import cn.edu.jxnu.awesome_campus.support.adapter.jxnugo.ChoosePicAdapter;
import cn.edu.jxnu.awesome_campus.support.loader.FrescoImageLoader;
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
public class NewGoodsActivity extends BaseToolbarActivity implements View.OnClickListener{
    public static final String TAG="NewGoodsActivity";
    private static final String title="发布二手商品";
    private final int REQUEST_CODE_GALLERY = 1001;
    private int goodTag=4;//默认其它
    private List<GoodsPhotoModel> mPhotoList;
    private ChoosePicAdapter adapter;
    private HorizontalListView picListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TCAgent.onPageStart(InitApp.AppContext, TAG);
        setContentView(R.layout.activity_jxnugo_new_goods);
        swipeEnabled=false;
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
                goodTag=tag;
            }
        });
        spinnerWrapper.build((MaterialSpinner)findViewById(R.id.spinner));
    }

    private void initView() {
        picListView=(HorizontalListView)findViewById(R.id.lv_photo);
    }
    private void bindAdapter() {
        mPhotoList = new ArrayList<>();
        adapter=new ChoosePicAdapter(NewGoodsActivity.this,mPhotoList);
        picListView.setAdapter(adapter);
    }
    private void callImageSelector() {

        ThemeConfig themeConfig = null;
        themeConfig = ThemeConfig.DARK;
        FunctionConfig.Builder functionConfigBuilder = new FunctionConfig.Builder();
        cn.finalteam.galleryfinal.ImageLoader imageLoader;
        imageLoader = new FrescoImageLoader(NewGoodsActivity.this);
        functionConfigBuilder.setMutiSelectMaxSize(6);
        functionConfigBuilder.setEnableEdit(false);
        functionConfigBuilder.setEnableCamera(true);
        functionConfigBuilder.setEnablePreview(true);
        final FunctionConfig functionConfig = functionConfigBuilder.build();
        CoreConfig coreConfig = new CoreConfig.Builder(NewGoodsActivity.this, imageLoader, themeConfig)
                .setFunctionConfig(functionConfig)
//                .setPauseOnScrollListener(pauseOnScrollListener)
                .setNoAnimcation(true)
                .build();
        GalleryFinal.init(coreConfig);
        GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);
        initFresco();
    }


    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                for(int i=0;i<resultList.size();i++){
                    mPhotoList.add(new GoodsPhotoModel(resultList.get(i).getPhotoId(),
                            resultList.get(i).getPhotoPath(),
                            resultList.get(i).getWidth(),
                            resultList.get(i).getHeight()));
                }
                adapter.notifyDataSetChanged();
//                mChoosePhotoListAdapter.notifyDataSetChanged();
                Log.d(TAG,"取得的图片的数量为："+resultList.size());
                Log.d(TAG,"目标图片数量为："+mPhotoList.size());
                Log.d(TAG,"第一个图片路劲为"+mPhotoList.get(0).getPhotoPath());
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
        switch (v.getId()){
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

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
