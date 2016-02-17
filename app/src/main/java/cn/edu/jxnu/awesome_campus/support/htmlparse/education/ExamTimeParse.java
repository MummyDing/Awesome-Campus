package cn.edu.jxnu.awesome_campus.support.htmlparse.education;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import cn.edu.jxnu.awesome_campus.model.education.ExamTimeModel;
import cn.edu.jxnu.awesome_campus.support.utils.html.HtmlUtil;
import cn.edu.jxnu.awesome_campus.support.utils.html.NullHtmlStringException;

/**
 * 解析考试时间
 * Created by KevinWu on 2016/2/3.
 */
public class ExamTimeParse {
    private final static int GROUPSIZE = 7;//每组数据大小
    private final static String ITEM_CSS = "font[color=#330099]";//每个item选择css
    private String parseStr;
    private List<String> resultList;
    private List<ExamTimeModel> endList;

    public List<String> getResultList() {
        return resultList;
    }
    public List<ExamTimeModel> getEndList(){
        return endList;
    }

    /**
    *构造时执行解析，并填充结果集
    *@author KevinWu
    *create at 2016/2/4 19:29
    */
    public ExamTimeParse(String parseStr){
        super();
        this.parseStr=parseStr;
        resultList = new ArrayList<>();
        endList = new ArrayList<>();
        parseData();
    }

    /**
    *解析数据
    *@author KevinWu
    *create at 2016/2/4 19:29
    */
    private void parseData() {
        try {
            HtmlUtil hu=new HtmlUtil(parseStr);
            resultList=hu.parseString(ITEM_CSS);
            fillEndList();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NullHtmlStringException e) {
            e.printStackTrace();
        }
    }

    /**
    *填充最终模型
    *@author KevinWu
    *create at 2016/2/4 19:32
    */
    private void fillEndList() {
        Log.d("结果集大小", "--" + resultList.size());
        for(int i=0;i<=resultList.size()-GROUPSIZE;i=i+GROUPSIZE){
            endList.add(new ExamTimeModel(
                    resultList.get(i).toString(),
                    resultList.get(i+1).toString(),
                    resultList.get(i+3).toString(),
                    resultList.get(i+4).toString(),
                    resultList.get(i+5).toString(),
                    resultList.get(i+6).toString()
            ));
        }
    }

}
