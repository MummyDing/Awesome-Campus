package cn.edu.jxnu.awesome_campus.support.utils.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;

import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.okhttp.Headers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import cn.edu.jxnu.awesome_campus.support.CONSTANT;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.InputStreamCallback;

/**
 * Created by MummyDing on 16-2-10.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class ImageUtil {
    public static int getImageColor(Bitmap bitmap){
        Palette palette = Palette.from(bitmap).generate();
        if(palette == null || palette.getDarkMutedSwatch() == null){
            return Color.LTGRAY;
        }
        return palette.getDarkMutedSwatch().getRgb();
    }

    public static Bitmap getBitmap(SimpleDraweeView imageView){
        Bitmap bitmap;
        if (imageView.getDrawable() instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            Drawable d = imageView.getDrawable();
            bitmap = Bitmap.createBitmap(d.getIntrinsicWidth(), d.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            d.draw(canvas);
        }
        return bitmap;
    }
}
