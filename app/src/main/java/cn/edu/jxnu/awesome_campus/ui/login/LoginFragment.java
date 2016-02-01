package cn.edu.jxnu.awesome_campus.ui.login;

import java.util.ArrayList;

import cn.edu.jxnu.awesome_campus.ui.base.TopNavigationFragment;
import cn.edu.jxnu.awesome_campus.ui.library.LibraryFragment;

/**
 * Created by MummyDing on 16-2-1.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class LoginFragment extends TopNavigationFragment{

    protected static void addChildFragments() {
        if(fragments == null) {
            fragments = new ArrayList<>();
        }else if(fragments.size()>0){
            fragments.clear();
        }
        fragments.add(new EducationLoginFragment());
        fragments.add(new LibraryLoginFragment());
        fragments.add(new StudyLoginFragment());
    }

    public static LibraryFragment newInstance(){
        addChildFragments();
        return new LibraryFragment();
    }

}
