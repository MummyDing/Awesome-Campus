package cn.edu.jxnu.awesome_campus.support.boardcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.database.DatabaseHelper;
import cn.edu.jxnu.awesome_campus.database.table.about.NotifyTable;
import cn.edu.jxnu.awesome_campus.ui.about.NotifyActivity;
import cn.edu.jxnu.awesome_campus.ui.about.NotifyListActivity;

public class NotifyClickReceiver extends BroadcastReceiver {
    public NotifyClickReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        // 标记已读
        DatabaseHelper.exeSQL(NotifyTable.UPDATE_READED,"1",intent.getStringExtra(context.getString(R.string.id_title)));

      //  Intent newIntent = new Intent(context, NotifyActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(context, NotifyActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
