package cn.edu.jxnu.awesome_campus.support.utils.jxnugo;

import android.webkit.MimeTypeMap;

/**
 * Created by zpauly on 16-5-19.
 */
public class ImageUtil {
    public static String getMimeTypeFromUrl(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }
}
