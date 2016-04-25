package cn.edu.jxnu.awesome_campus.support.theme;

import android.support.v4.content.ContextCompat;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;

/**
 * Created by kevinwu on 16-2-24.
 */
public class ThemeConfig {
    //颜色选择器颜色值
    public static final int themeColor[]={
            ContextCompat.getColor(InitApp.AppContext,R.color.colorPrimary),
            ContextCompat.getColor(InitApp.AppContext,R.color.nightColorPrimary),
            ContextCompat.getColor(InitApp.AppContext,R.color.brownColorPrimary),
            ContextCompat.getColor(InitApp.AppContext,R.color.cyanColorPrimary),
            ContextCompat.getColor(InitApp.AppContext,R.color.tealColorPrimary),
            ContextCompat.getColor(InitApp.AppContext,R.color.lightGreenColorPrimary),
            ContextCompat.getColor(InitApp.AppContext,R.color.blueColorPrimary),
            ContextCompat.getColor(InitApp.AppContext,R.color.blueGreyColorPrimary),
            ContextCompat.getColor(InitApp.AppContext,R.color.purpleColorPrimary),
            ContextCompat.getColor(InitApp.AppContext,R.color.orangeColorPrimary),
            ContextCompat.getColor(InitApp.AppContext,R.color.mklColorPrimary),
            ContextCompat.getColor(InitApp.AppContext,R.color.bgColorPrimary)
    };
    //主题样式值
    public static final int themeStyle[]={
            R.style.AppTheme,
            R.style.DarkTheme,
            R.style.BrownTheme,
            R.style.CyanTheme,
            R.style.TealTheme,
            R.style.LightgreenTheme,
            R.style.BlueTheme,
            R.style.BlueGreyTheme,
            R.style.PurpleTheme,
            R.style.OrangeTheme,
            R.style.MklTheme,
            R.style.BgTheme
    };
    //主题对话框样式值
    public static final int themeDialogStyle[]={
            R.style.DialogTheme,
            R.style.DarkDialogTheme,
            R.style.BrownDialogTheme,
            R.style.CyanDialogTheme,
            R.style.TealDialogTheme,
            R.style.LightgreenDialogTheme,
            R.style.BlueDialogTheme,
            R.style.BlueGreyDialogTheme,
            R.style.PurpleDialogTheme,
            R.style.OrangeDialogTheme,
            R.style.MklDialogTheme,
            R.style.BgDialogTheme
    };
}
