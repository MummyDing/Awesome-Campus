package cn.edu.jxnu.awesome_campus.support.utils.common;

import java.util.Arrays;
import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.support.spkey.TermStaticKey;

/**
 * 获取当前学期类
 * Created by KevinWu on 2016/2/15.
 */
public class TermUtil {

    private static List<String> termList;
    public static String getNowTerm(){
        String year=TimeUtil.getYear_xxxx();
        int month=Integer.parseInt(TimeUtil.getMonth())-1;
        month = (month >= 6 ? 9 : 3);
        String term = year + "/" + month + "/1+0:00:00";
        return term;
    }

    /**
     * 获取学期列表
     * @return
     */
    public static List<String> getTermList(){

        if(termList !=null){
            return termList;
        }

        // 获取学期
        SPUtil sp=new SPUtil(InitApp.AppContext);
        String terms = sp.getStringSP(TermStaticKey.SP_FILE_NAME,TermStaticKey.ALL_TERM_LIST);

        if(TextUtil.isNull(terms)){
            return null;
        }
        termList = Arrays.asList(terms.split("@"));
        return termList;
    }
}
