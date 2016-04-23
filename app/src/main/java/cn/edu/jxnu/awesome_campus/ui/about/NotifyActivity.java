package cn.edu.jxnu.awesome_campus.ui.about;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.ui.base.BaseWebViewActivity;

/**
 * Created by MummyDing on 16-4-23.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class NotifyActivity extends BaseWebViewActivity {

    private String type;
    private String data;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected String getLink() {
        if (type == null){
            type = getIntent().getStringExtra(getString(R.string.id_type));
            data = getIntent().getStringExtra(getString(R.string.id_data));
        }
        if (type.equals(getString(R.string.link)))
        return data;
        return null;
    }

    @Override
    protected String getData() {
        if (type == null){
            type = getIntent().getStringExtra(getString(R.string.id_type));
            data = getIntent().getStringExtra(getString(R.string.id_data));
        }
        if (type.equals(getString(R.string.data)))
            return data;
        return null;
    }
}
