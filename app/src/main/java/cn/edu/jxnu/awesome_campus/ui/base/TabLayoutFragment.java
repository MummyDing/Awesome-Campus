package cn.edu.jxnu.awesome_campus.ui.base;


import android.view.View;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import cn.edu.jxnu.awesome_campus.R;

/**
 * Created by MummyDing on 16-1-30.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public abstract class TabLayoutFragment extends BaseFragment{

    protected SmartTabLayout smartTabLayout;
    @Override
    public void initView() {
        smartTabLayout = (SmartTabLayout) getActivity().findViewById(R.id.tab_layout);
        smartTabLayout.setVisibility(View.VISIBLE);
    }
}
