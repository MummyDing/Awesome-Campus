package cn.edu.jxnu.awesome_campus;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.tendcloud.tenddata.TCAgent;

import cn.edu.jxnu.awesome_campus.support.Settings;
import cn.edu.jxnu.awesome_campus.support.utils.common.ImageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.ProcessUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.SystemUtil;
import cn.edu.jxnu.awesome_campus.support.utils.login.EducationLoginUtil;

/**
 * Created by MummyDing on 16-1-25.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class InitApp extends Application{
    public static Context AppContext;
    public static final String PACK_NAME="cn.edu.jxnu.awesome_campus";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Application创建","--");
        AppContext = getApplicationContext();
        String processName= ProcessUtil.getProcessName(AppContext,android.os.Process.myPid());
        Log.d("--", "进程名称"+processName);
        if(processName != null){
            boolean defaultProcess = processName
                    .equals(PACK_NAME);
            if (defaultProcess) {
                Fresco.initialize(AppContext);
                AppConfig.updateConfig();

                TCAgent.LOG_ON=true;
                // App ID: 在TalkingData创建应用后，进入数据报表页中，在“系统设置”-“编辑应用”页面里查看App ID。
                // 渠道 ID: 是渠道标识符，可通过不同渠道单独追踪数据。
                TCAgent.init(this, "AA659C3528C62C870A16860692AD6325", "00001");
                TCAgent.setReportUncaughtExceptions(true);


                /**
                 * 此处用于加载jxnugo的auth_token
                 */
                Settings.setJxnugoAuthToken();


                /**
                 * 此处用于加载头像 使用了Fresco --- By MummyDing
                 */
                DrawerImageLoader.init(new DrawerImageLoader.IDrawerImageLoader() {
                    @Override
                    public void set(final ImageView imageView, Uri uri, final Drawable placeholder) {
                        DataSource<CloseableReference<CloseableImage>> dataSource = Fresco.getImagePipeline().fetchDecodedImage(ImageRequest.fromUri(uri), getApplicationContext());

                        dataSource.subscribe(new BaseBitmapDataSubscriber() {
                                                 @Override
                                                 public void onNewResultImpl(@Nullable final Bitmap bitmap) {
                                                     imageView.post(new Runnable() {
                                                         public void run() {
                                                             imageView.setImageBitmap(ImageUtil.bitmapToCircle(bitmap));
                                                         }
                                                     });
                                                 }

                                                 @Override
                                                 public void onFailureImpl(DataSource dataSource) {
                                                     imageView.setImageDrawable(placeholder);
                                                 }
                                             },
                                CallerThreadExecutor.getInstance());
                    }

                    @Override
                    public void cancel(final ImageView imageView) {
                        if (EducationLoginUtil.getAvatorUrl() != null) {
                            DataSource<CloseableReference<CloseableImage>> dataSource = Fresco.getImagePipeline().fetchDecodedImage(ImageRequest.fromUri(EducationLoginUtil.getAvatorUrl()), getApplicationContext());
                            dataSource.subscribe(new BaseBitmapDataSubscriber() {
                                                     @Override
                                                     public void onNewResultImpl(@Nullable final Bitmap bitmap) {
                                                         imageView.post(new Runnable() {
                                                             public void run() {
                                                                 imageView.setImageBitmap(ImageUtil.bitmapToCircle(bitmap));
                                                             }
                                                         });
                                                     }

                                                     @Override
                                                     public void onFailureImpl(DataSource dataSource) {
                                                         imageView.setImageDrawable(ContextCompat.getDrawable(InitApp.AppContext, R.drawable.logo));
                                                     }
                                                 },
                                    CallerThreadExecutor.getInstance());
                        }
                    }

                    @Override
                    public Drawable placeholder(Context ctx) {
                        return ContextCompat.getDrawable(ctx, R.drawable.logo);
                    }

                    @Override
                    public Drawable placeholder(Context ctx, String tag) {
                        return ContextCompat.getDrawable(ctx, R.drawable.logo);
                    }
                });

            }
        }

    }
}
