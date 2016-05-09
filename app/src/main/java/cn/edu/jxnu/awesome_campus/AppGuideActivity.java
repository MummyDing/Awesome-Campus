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
//        /**
//         * 测使用==-=
//         */
//        addSlide(AppIntroFragment.newInstance("师大+","欢迎使用师大人自己的App!",R.drawable.intro_1,Color.parseColor("#2196F3")));
//
//        addSlide(AppIntroFragment.newInstance("课程表","今天上什么课?",R.drawable.intro_2,Color.parseColor("#2196F3")));
//        addSlide(AppIntroFragment.newInstance("校内新闻","学校发生了什么?",R.drawable.intro_3,Color.parseColor("#2196F3")));
//        addSlide(AppIntroFragment.newInstance("图书查询","大家在看什么书?",R.drawable.intro_4,Color.parseColor("#2196F3")));
//        addSlide(AppIntroFragment.newInstance("校内快递","找不到快递店怎么办?",R.drawable.logo,Color.parseColor("#2196F3")));
//        setZoomAnimation();
        addSlide(Slide.newInstance(R.layout.intro1));
        addSlide(Slide.newInstance(R.layout.intro2));
        addSlide(Slide.newInstance(R.layout.intro3));
        addSlide(Slide.newInstance(R.layout.intro4));
        addSlide(Slide.newInstance(R.layout.intro5));

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
