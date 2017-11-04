package cn.edu.jxnu.awesome_campus.view.widget.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.support.utils.common.DisplayUtil;

/**
 * Created by MummyDing on 2016/9/7.
 */
public class AppUpdateDialog extends AlertDialog {

    private AlertDialog mDialog;

    public AppUpdateDialog(Context context,String newVersion) {
        super(context);
        init(context, newVersion);
    }

    public void show() {
        if (mDialog != null) {
            mDialog.show();
        }
    }

    private void init(final Context context, String newVersion) {
        if (context != null && context instanceof Activity) {
            mDialog = new AlertDialog.Builder(context)
                    .setIcon(R.drawable.logo)
                    .setTitle(InitApp.AppContext.getString(R.string.new_version) + newVersion)
                    .setMessage(InitApp.AppContext.getString(R.string.notify_new_version))
                    .setNegativeButton(InitApp.AppContext.getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            Uri content_url = Uri.parse("https://coding.net/u/MummyDing/p/Awesome-Campus-Data/git/raw/master/download/AwesomeCampus.apk");
                            intent.setData(content_url);
                            context.startActivity(intent);
                        }
                    })
                    .setNeutralButton(InitApp.AppContext.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create();
            mDialog.getWindow().setLayout(3* DisplayUtil.getScreenWidth(context)/4,-2);
        }
    }

}
