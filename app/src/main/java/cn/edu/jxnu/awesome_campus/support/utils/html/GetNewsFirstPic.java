package cn.edu.jxnu.awesome_campus.support.utils.html;

/**
 * 获取师大新闻网的新闻的第一个图片工具类
 * Created by KevinWu on 2016/2/11.
 */
public class GetNewsFirstPic {
    private static String URL_CUT_LEFT="src=\"";
    private static String URL_CUT_RIGHT="\" />";
    /**
    *如果获取到则返回真实url，如果未获取到则返回null
    *@author KevinWu
    *create at 2016/2/11 21:12
    */
    public static String getPicURL(String htmlStr){
        if (htmlStr == null ) {
            return null;
        }
        String firstcut[]=htmlStr.split(URL_CUT_LEFT);
        if(firstcut.length>1){
            String secondcut[]=firstcut[1].split(URL_CUT_RIGHT);
            return secondcut[0];
        }
        return null;
    }
}
