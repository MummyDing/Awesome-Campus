package cn.edu.jxnu.awesome_campus.support.htmlparse.libary;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.model.library.SelfStudySeatLeftModel;
import cn.edu.jxnu.awesome_campus.support.utils.html.HtmlUtil;
import cn.edu.jxnu.awesome_campus.support.utils.html.NullHtmlStringException;

/**
 * Created by KevinWu on 16-4-24.
 */
public class SelfStudySeatLeftParse {
    private static final String ITEM_CSS="li[data-theme=c]";//选择css
    private String parseStr = null;//待解析字符串
    private List<String> resultList;//结果列表

    public List<SelfStudySeatLeftModel> getEndList() {
        return endList;
    }

    private List<SelfStudySeatLeftModel> endList;//最终模型列表

    public SelfStudySeatLeftParse(String parseStr){
        this.parseStr=parseStr;
        resultList=new ArrayList<>();
        endList=new ArrayList<>();
        parseData();
    }

    /**
     * 数据解析
     */
    private void parseData() {
        try {
            System.out.println(parseStr);//使用sysout是为了更方便junit测试
            HtmlUtil hu=new HtmlUtil(parseStr);
            resultList=hu.parseString(ITEM_CSS);
            System.out.println("获取到的结果列表大小"+resultList.size());
            for(int i=0;i<resultList.size();i++){
                System.out.println();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NullHtmlStringException e) {
            e.printStackTrace();
        }
    }

}
