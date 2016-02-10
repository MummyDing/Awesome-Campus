package cn.edu.jxnu.awesome_campus.support.utils.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import cn.edu.jxnu.awesome_campus.support.CONSTANT;

/**
 * Created by MummyDing on 16-2-10.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class ImageUtil {

    /**
     * Get Bitmap From image url
     *
     * @param url
     * @return
     */
    private static Bitmap bitmap = null;
    public static Bitmap GetLocalOrNetBitmap(final String url)
    {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream in = null;
                BufferedOutputStream out = null;
                try
                {
                    in = new BufferedInputStream(new URL(url).openStream(), CONSTANT.IO_BUFFER_SIZE);
                    final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
                    out = new BufferedOutputStream(dataStream, CONSTANT.IO_BUFFER_SIZE);
                    copy(in, out);
                    out.flush();
                    byte[] data = dataStream.toByteArray();
                    bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    data = null;
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    bitmap =  null;
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    private static void copy(InputStream in, OutputStream out)
            throws IOException {
        byte[] b = new byte[CONSTANT.IO_BUFFER_SIZE];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }
}
