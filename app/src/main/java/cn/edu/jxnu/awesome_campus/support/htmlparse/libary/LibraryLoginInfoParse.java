package cn.edu.jxnu.awesome_campus.support.htmlparse.libary;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.support.utils.html.HtmlUtil;
import cn.edu.jxnu.awesome_campus.support.utils.html.NullHtmlStringException;

/**
 * 通过endList的第一个元素获取姓名
 * Created by KevinWu on 2016/2/20.
 */
public class LibraryLoginInfoParse {
    private static final String ITEM_CSS="div#menu";//选择css
    private static final String ITEM_CSS2="div";
    private static final String CUT_LEFT="我的图书馆";
    private static final String CUT_RIGHT="注销";
    private String parseStr = null;//待解析字符串

    public List<String> getEndList() {
        return endList;
    }

    private List<String> endList = null;//解析后的列表


    public LibraryLoginInfoParse(String parseStr){
        super();
        this.endList=new ArrayList<>();
        this.parseStr=parseStr;
        parseData();
    }


    /**
    *解析数据
    *@author KevinWu
    *create at 2016/2/21 15:18
    */
    private void parseData() {
        HtmlUtil hu = null;
        try {
            hu = new HtmlUtil(parseStr);
            List tempList = hu.parseRawString(ITEM_CSS);

            HtmlUtil hu2=new HtmlUtil(tempList.get(0).toString());
            tempList.clear();
            tempList=hu2.parseString(ITEM_CSS2);
            Log.d("图书馆登录解析取得的数据列表大小为：","--"+tempList.size());
            String tempParseStr=tempList.get(0).toString();
            Log.d("解析出来的用户名字段字符串为：","--"+tempParseStr);
            String first_cut[]=tempParseStr.split(CUT_LEFT);
            if(first_cut.length>1){
                String second_cut[]=first_cut[1].split(CUT_RIGHT);
                endList.add(second_cut[0].trim());
            }else{
                endList.add("");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NullHtmlStringException e) {
            e.printStackTrace();
        }

    }
}
