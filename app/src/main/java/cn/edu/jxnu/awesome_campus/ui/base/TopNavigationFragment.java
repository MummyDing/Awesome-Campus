package cn.edu.jxnu.awesome_campus.ui.base;


import android.support.v4.view.ViewPager;
import android.view.View;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.List;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.support.adapter.BasePagerAdapter;

/**
 * Created by MummyDing on 16-1-30.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public abstract class TopNavigationFragment extends BaseFragment{

    protected SmartTabLayout smartTabLayout;
    protected ViewPager viewPager;
    protected static List<BaseFragment> fragments;
    protected BasePagerAdapter pagerAdapter;


    @Override
    protected int getLayoutID() {
        return R.layout.fragment_top_navigation;
    }

    @Override
    public void init() {
        pagerAdapter = new BasePagerAdapter(getChildFragmentManager(),fragments);
        viewPager = (ViewPager) parentView.findViewById(R.id.inner_viewpager);
        viewPager.setAdapter(pagerAdapter);
        smartTabLayout = (SmartTabLayout) getActivity().findViewById(R.id.tab_layout);
        smartTabLayout.setVisibility(View.VISIBLE);
        smartTabLayout.setViewPager(viewPager);
    }

    @Override
    public String getTitle() {
        return null;
    }

    public static void clearChildFragments() {
        if(fragments != null){
            fragments.clear();
        }
    }


}
