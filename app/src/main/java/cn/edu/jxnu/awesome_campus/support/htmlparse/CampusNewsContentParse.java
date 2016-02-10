package cn.edu.jxnu.awesome_campus.support.htmlparse;

import android.util.Log;

/**
 * 校内新闻详情解析页
 * 使用，传入html字符串，自动提取出内容，调用getEndStr获取截取后内容
 * Created by KevinWu on 2016/2/10.
 */
public class CampusNewsContentParse {
    private static final String STR_CUT_LEFT="<div class=\"bottom\">";//分割左边界
    private static final String STR_CUT_RIGHT="<script>";//分割右边界
    private String praseStr=null;//待解析字符串

    public String getEndStr() {
        return endStr;
    }

    private String endStr=null;//解析后字符串

    public CampusNewsContentParse(String praseStr){
        super();
        this.praseStr=praseStr;
        praseData();
    }

    /**
    *解析数据，分割出有效内容
    *@author KevinWu
    *create at 2016/2/10 13:43
    */
    private void praseData() {
        String first_cut[]=praseStr.split(STR_CUT_LEFT);
        if(first_cut.length>1){
            String second_cut[]=first_cut[1].split(STR_CUT_RIGHT);
            if(second_cut.length>1){
                endStr=second_cut[0];
            }else{
                Log.e("ERROR","第二次解析出错，未找到有效分割元素");
            }
        }else{
            Log.e("ERROR","第一次解析出错，未找到有效分割元素");
        }
    }


}
