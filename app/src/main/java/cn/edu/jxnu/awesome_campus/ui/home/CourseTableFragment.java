package cn.edu.jxnu.awesome_campus.ui.home;

import android.view.View;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.ui.base.BaseListFragment;
import cn.edu.jxnu.awesome_campus.view.widget.ColorButton;

/**
 * Created by MummyDing on 16-1-31.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CourseTableFragment extends BaseListFragment{

    @Override
    public String getTitle() {
        return InitApp.AppContext.getString(R.string.course_table);
    }

    @Override
    public void cardViewTransition() {

    }

    @Override
    public void bindAdapter() {

    }

    @Override
    public void initView() {
        final ColorButton colorButton = (ColorButton) parentView.findViewById(R.id.colorBtn);
        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorButton.setChecked(!colorButton.isChecked());
            }
        });
    }

    @Override
    protected void onNetworkBtnClick() {

    }
}
