package cn.edu.jxnu.awesome_campus.support.htmlparse.libary;

import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.model.library.SelfStudySeatLeftModel;

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

    }

}
