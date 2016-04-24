package cn.edu.jxnu.awesome_campus;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class AppGuideActivity extends AppIntro {


    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        /**
         * 测使用==-=
         */
        addSlide(AppIntroFragment.newInstance("课程表","今天上什么课?",R.drawable.intro2,Color.RED));
        addSlide(AppIntroFragment.newInstance("校内新闻","学校发生了什么?",R.drawable.intro3,Color.RED));
        addSlide(AppIntroFragment.newInstance("图书查询","大家在看什么书?",R.drawable.intro4,Color.RED));
        addSlide(AppIntroFragment.newInstance("校内快递","找不到快递店怎么办?",R.drawable.intro5,Color.RED));
        addSlide(AppIntroFragment.newInstance("师大+","欢迎使用师大人自己的App!",R.drawable.intro1,Color.RED));
        setBarColor(Color.TRANSPARENT);

    }

    @Override
    public void onSkipPressed() {
        finish();
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
