package cn.edu.jxnu.awesome_campus.support.htmlparse.education;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.model.home.CourseTableModel;
import cn.edu.jxnu.awesome_campus.support.utils.html.HtmlUtil;
import cn.edu.jxnu.awesome_campus.support.utils.html.NullHtmlStringException;

/**
 * 使用：通过传进来html后，执行getEndList()即可获取模型对象集
 * Created by KevinWu on 2016/2/3.
 */
public class CourseTableParse {
    private final static int GROUPSIZE=7;//数据每7个为一组，对应每个时间段课在一周的课表情况
    private final static String ITEM_CSS = "DIV[align=center]";//每个item选择css
    //html左边界分割字符串
    private final static String LEFT_SP_STR=">上午</TD>";
    //html右边界分割字符串
    private final static String RIGHT_SP_STR="课表说明：底色为深色部分表示的是有冲突的课程！";
        private String parseStr;
    private List<String> resultList;

    public List<CourseTableModel> getEndList() {
        return endList;
    }

    public List<String> getResultList() {
        return resultList;
    }

    private List<CourseTableModel> endList;

    /**
    *构造时即执行解析，解析后自动填充结果
    *@author KevinWu
    *create at 2016/2/5 12:05
    */
    public CourseTableParse(String parseStr){
        super();
        this.parseStr=parseStr;
        Log.d("待解析字符串为","--"+parseStr);
        resultList=new ArrayList<>();
        endList=new ArrayList<>();
        parseData();
    }

    /**
    *解析数据
    *@author KevinWu
    *create at 2016/2/5 12:05
    */
    private void parseData() {
        //先对待解析数据进行划分，避免非有效数据干扰
        String endStr;
        String firstCut[]=parseStr.split(LEFT_SP_STR);
        if(firstCut.length>1){
            String secondCut[]=firstCut[1].split(RIGHT_SP_STR);
            endStr=secondCut[0];
        }
        else{
            endStr=null;
        }
        try {
            HtmlUtil hu=new HtmlUtil(endStr);
            List tempList=hu.parseString(ITEM_CSS);
            Log.d("临时列表大小", tempList.size() + "");

            //细分工作，虽然耗时，但是灵活
            for(int i=0;i<tempList.size();i++){
                String tempStr=tempList.get(i).toString();
                System.out.println("结果：" + tempStr);
                if(tempStr.equals("1 2")
                        ||tempStr.equals("3")
                        ||tempStr.equals("4")
                        ||tempStr.equals("5")
                        ||tempStr.equals("6 7")
                        ||tempStr.equals("8 9")
                        ||tempStr.equals("中 午")
                        ||tempStr.equals("下午")
                        ||tempStr.equals("晚上")
                        ||tempStr.equals("晚 上"))continue;
                if(tempStr.equals(" ")){
                    resultList.add("");
                }else{
                    String cutPosLeft[]=tempStr.split("\\(");
                    if(cutPosLeft.length>1){
                        String cutPosRight[]=cutPosLeft[1].split("\\)");
                        resultList.add(cutPosLeft[0]+"@"+cutPosRight[0]);
                    }
                }
            }
            Log.d("resultlist大小为",resultList.size()+"--");
            for(int i=0;i<resultList.size();i++){
                System.out.println("结果："+resultList.get(i).toString());
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
    *create at 2016/2/5 12:41
    */
    private void fillEndList() {
        int week=1;//初始数据为周一
        String term="";//学期数据暂时留空
        for(int i=0;i<7;i++){
            int nowWeek=i+1;
            if(nowWeek==0)nowWeek=7;
            endList.add(new CourseTableModel(
                    nowWeek,
                    term,
                    resultList.get(i).toString(),
                    resultList.get(i+7).toString(),
                    resultList.get(i+14).toString(),
                    resultList.get(i+21).toString(),
                    resultList.get(i+28).toString(),
                    resultList.get(i+35).toString(),
                    resultList.get(i+42).toString()
            ));
        }
    }
}
