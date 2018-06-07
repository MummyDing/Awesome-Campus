package cn.edu.jxnu.awesome_campus.support.utils.common;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;

/**
 * Created by MummyDing on 16-1-25.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class TextUtil {
    public static boolean isNull(String str){
        return str == null || str.replaceAll("\\s*", "").equals("");
    }
    public static void copyToClipboard(View view, String info) {
        ClipboardManager cm = (ClipboardManager) InitApp.AppContext.getSystemService(
                Context.CLIPBOARD_SERVICE);
        ClipData cd = ClipData.newPlainText("msg", info);
        cm.setPrimaryClip(cd);
        Snackbar.make(view, R.string.notify_info_copied,Snackbar.LENGTH_SHORT).show();
    }


}
