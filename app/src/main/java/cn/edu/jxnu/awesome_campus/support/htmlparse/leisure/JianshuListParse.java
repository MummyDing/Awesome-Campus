package cn.edu.jxnu.awesome_campus.support.htmlparse.leisure;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.support.utils.html.HtmlUtil;
import cn.edu.jxnu.awesome_campus.support.utils.html.NullHtmlStringException;

/**
 * 解析简书的返回list
 * Created by KevinWu on 2016/2/16.
 */
public class JianshuListParse {
    private static final String CUT_LEFT_STR="hide loader-tiny";//左边切割字符串
    private static final String CUT_RIGHT_STR="reject-collection-submission";//右边切割字符串
    private static final String ALL_ITEM_CSS="ul[class=article-list]";//所有列表选择css
    private static final String ITEM_CSS="li";
    private String parseStr;

    public List getEndList() {
        return endList;
    }

    private List endList;

    public JianshuListParse(String parseStr){
        super();
        this.parseStr=parseStr;
        endList=new ArrayList();
        parseData();
    }

    /**
    *解析数据
    *@author KevinWu
    *create at 2016/2/16 13:28
    */
    private void parseData() {
        try {
            HtmlUtil hu=new HtmlUtil(parseStr);
            List all_list=hu.parseRawString(ALL_ITEM_CSS);
            hu=new HtmlUtil(all_list.get(0).toString());
            endList=hu.parseString(ITEM_CSS);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NullHtmlStringException e) {
            e.printStackTrace();
        }
    }

}
