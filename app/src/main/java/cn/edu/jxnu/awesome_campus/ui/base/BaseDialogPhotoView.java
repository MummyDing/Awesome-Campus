package cn.edu.jxnu.awesome_campus.ui.base;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.okhttp.Headers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.support.utils.common.TimeUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.InputStreamCallback;
import cn.finalteam.galleryfinal.widget.zoonview.PhotoViewAttacher;

/**
 * Created by KevinWu on 16-6-4.
 * KevinWu.cn
 */
public class BaseDialogPhotoView extends BaseToolbarActivity {
    private String title = "查看大图";
    private ImageView imageView;
    private String url;
    private PhotoViewAttacher mAttacher;
    private ProgressBar progressBar;
    private Bitmap bitmap;
    private String PATH = "/sdcard/DCIM/AwesomeCampus/";
    private String message = "保存失败";
    private Handler handle = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_big_img);
        swipeEnabled = false;
        url = getIntent().getStringExtra("url");
        initView();
        initToolbar();
        setToolbarTitle(title);
        onDataRefresh();
        showProgressBar();
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
    }


    private void initView() {
        imageView = (ImageView) findViewById(R.id.photoView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
//        Drawable bitmap = getResources().getDrawable(R.drawable.logo);
//        imageView.setImageDrawable(bitmap);
//        imageView.setImageURI(Uri.parse(url));
//        mAttacher = new PhotoViewAttacher(imageView);
    }

    private void onDataRefresh() {
        NetManageUtil.get(url)
                .enqueue(new InputStreamCallback() {
                    @Override
                    public void onSuccess(InputStream result, Headers headers) {
                        bitmap = BitmapFactory.decodeStream(result);
                        float width=bitmap.getWidth();
                        float height=bitmap.getHeight();
                        while(width>2200||height>2200){
//                            Log.d(TAG,"上传的图片大小 宽："+width+" 高："+height);
                            float scaleWidth=0;
                            float scaleHeight=0;
                            scaleWidth= (width/2) /width;
                            scaleHeight= (height/2) /height;
                            Matrix matrix=new Matrix();
                            matrix.postScale(scaleWidth,scaleHeight);
                            bitmap=Bitmap.createBitmap(bitmap,0,0,(int)width,(int)height,matrix,true);
                            width=bitmap.getWidth();
                            height=bitmap.getHeight();
                        }
                        handle.post(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(bitmap);
                                mAttacher = new PhotoViewAttacher(imageView);
                                hideProgressBar();
                            }
                        });
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                });
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_photoview, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_download:
                saveFile(bitmap, TimeUtil.getTimestamp() + ".jpeg");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveFile(Bitmap bm, String fileName) {
        File dirFile = new File(PATH);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(PATH + fileName);
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            message = "成功保存到" + PATH+ fileName;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            Uri contentUri = Uri.fromFile(new File(PATH + fileName));
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,contentUri);
            InitApp.AppContext.sendBroadcast(mediaScanIntent);
        }
    }


}
