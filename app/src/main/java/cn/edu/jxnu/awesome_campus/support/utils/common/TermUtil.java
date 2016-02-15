package cn.edu.jxnu.awesome_campus.support.utils.common;

/**
 * 获取当前学期类
 * Created by KevinWu on 2016/2/15.
 */
public class TermUtil {

    public static String getNowTerm(){
        String year=TimeUtil.getYear_xxxx();
        int month=Integer.parseInt(TimeUtil.getMonth());
        month = (month >= 6 ? 9 : 3);
        String term = year + "/" + month + "/1+0:00:00";
        return term;
    }
}
