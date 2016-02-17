package cn.edu.jxnu.awesome_campus.support.htmlparse.education;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.model.home.CourseInfoModel;
import cn.edu.jxnu.awesome_campus.model.home.CourseTableModel;
import cn.edu.jxnu.awesome_campus.support.utils.html.HtmlUtil;
import cn.edu.jxnu.awesome_campus.support.utils.html.NullHtmlStringException;

/**
 * 使用：通过传进来html后，执行getEndList()即可获取模型对象集
 * Created by KevinWu on 2016/2/5.
 */
public class CourseInfoParse {
    private final static int GROUPSIZE=6;
    private final static String ITEM_CSS="font[color=#330099]";
    //html左边界分割字符串
    private final static String LEFT_SP_STR="课表说明：底色为深色部分表示的是有冲突的课程！";
    private String parseStr;
    private List<String> resultList;
    private List<CourseInfoModel> endList;

    public List<CourseInfoModel> getEndList() {
        return endList;
    }

    /**
    *构造时执行解析，解析后自动填充结果
    *@author KevinWu
    *create at 2016/2/5 16:31
    */
    public CourseInfoParse(String parseStr){
        super();
        this.parseStr = parseStr;
        resultList=new ArrayList<>();
        endList=new ArrayList<>();
        parseData();
    }

    /**
    *解析数据
    *@author KevinWu
    *create at 2016/2/5 16:34
    */
    private void parseData() {
        //先对数据进行划分
        String endStr;
        String firstCut[]= parseStr.split(LEFT_SP_STR);
        if(firstCut.length>1){
            endStr=firstCut[1];
        }
        else{
            endStr=null;
        }
        try {
            HtmlUtil hu=new HtmlUtil(endStr);
            List tempStrList=hu.parseString(ITEM_CSS);
            List tempLinkList=hu.parseRawString(ITEM_CSS);
            for(int i=0;i<=tempStrList.size()-GROUPSIZE;i=i+GROUPSIZE){
                resultList.add(tempStrList.get(i).toString());
                resultList.add(tempStrList.get(i+1).toString());
                resultList.add(tempStrList.get(i+2).toString());
                resultList.add(tempStrList.get(i+3).toString());

                String classMateListLink_LEFT[]=tempLinkList.get(i+4).toString().split("OpenWindow\\('");
                if(classMateListLink_LEFT.length>1){
                    resultList.add(classMateListLink_LEFT[1].split("'\\);")[0]);//同学名单信息
                }
                else{
                    resultList.add("");
                }

                String classForumLink_Left[]=tempLinkList.get(i+5).toString().split("href=\"");
                if(classForumLink_Left.length>1){
                    resultList.add(classForumLink_Left[1].split("\">课程讨论区")[0]);//课程讨论区信息
                }
                else{
                    resultList.add("");
                }
            }
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
    *create at 2016/2/5 17:10
    */
    private void fillEndList() {
        for(int i=0;i<=resultList.size()-GROUPSIZE;i=i+GROUPSIZE){
            endList.add(new CourseInfoModel(
                            resultList.get(i).toString(),
                            resultList.get(i+1).toString(),
                            resultList.get(i+2).toString(),
                            resultList.get(i+3).toString(),
                            resultList.get(i+4).toString(),
                            resultList.get(i+5).toString()
                    )
            );
        }
    }
}
