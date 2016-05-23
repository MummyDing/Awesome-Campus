package cn.edu.jxnu.awesome_campus;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.WindowManager;

import com.github.paolorotolo.appintro.AppIntro2;

import cn.edu.jxnu.awesome_campus.view.guide.Slide;

public class AppGuideActivity extends AppIntro2 {


    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        addSlide(Slide.newInstance(R.layout.intro1));
        addSlide(Slide.newInstance(R.layout.intro2));
        addSlide(Slide.newInstance(R.layout.intro3));
        addSlide(Slide.newInstance(R.layout.intro4));
        addSlide(Slide.newInstance(R.layout.intro5));
        addSlide(Slide.newInstance(R.layout.intro6));

    }

//    @Override
//    public void onSkipPressed() {
//        finish();
//    }


    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed() {
        finish();
    }

    @Override
    public void onSlideChanged() {

    }
}
