package cn.edu.jxnu.awesome_campus.ui.about;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.database.dao.about.NotifyDAO;
import cn.edu.jxnu.awesome_campus.model.about.NotifyModel;
import cn.edu.jxnu.awesome_campus.ui.base.BaseToolbarActivity;
import cn.edu.jxnu.awesome_campus.ui.base.SwipeBackActivity;

public class AboutActivity extends BaseToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initToolbar();
        setToolbarTitle(InitApp.AppContext.getString(R.string.about));
        getFragmentManager().beginTransaction().replace(R.id.framelayout,new AboutFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notify, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_notify){
            Intent intent = new Intent(this,NotifyActivity.class);
            NotifyDAO dao = new NotifyDAO();
            NotifyModel model = dao.getCache();
            if (model == null){
                intent.putExtra(getString(R.string.id_type),getString(R.string.id_none));
            }else {
                intent.putExtra(getString(R.string.id_type),model.getType());
                intent.putExtra(getString(R.string.id_data),model.getData());
            }
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
