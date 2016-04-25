package cn.edu.jxnu.awesome_campus;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class AppGuideActivity extends AppIntro2 {


    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        /**
         * 测使用==-=
         */
        addSlide(AppIntroFragment.newInstance("师大+","欢迎使用师大人自己的App!",R.drawable.intro_1,Color.parseColor("#2196F3")));

        addSlide(AppIntroFragment.newInstance("课程表","今天上什么课?",R.drawable.intro_2,Color.parseColor("#2196F3")));
        addSlide(AppIntroFragment.newInstance("校内新闻","学校发生了什么?",R.drawable.intro_3,Color.parseColor("#2196F3")));
        addSlide(AppIntroFragment.newInstance("图书查询","大家在看什么书?",R.drawable.intro_4,Color.parseColor("#2196F3")));
        addSlide(AppIntroFragment.newInstance("校内快递","找不到快递店怎么办?",R.drawable.logo,Color.parseColor("#2196F3")));
        setZoomAnimation();

    }


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
