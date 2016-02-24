package cn.edu.jxnu.awesome_campus.support.htmlparse.education;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.support.spkey.TermStaticKey;
import cn.edu.jxnu.awesome_campus.model.education.CourseScoreModel;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.html.HtmlUtil;
import cn.edu.jxnu.awesome_campus.support.utils.html.NullHtmlStringException;

/**
 * 使用：通过传进来html后，执行getEndList()即可获取模型对象集
 * Created by KevinWu on 2016/2/3.
 */
public class CourseScoreParse {
    private final static int GROUPSIZE = 7;//每组数据大小
    private final static String TERM_CSS = "td[valign=middle]";//学期选择css
    private final static String ITEM_CSS = "font[color=#330099]";//每个item选择css
    private final static String BACKUP_ITEM_CSS = "font[size=9pt]";//备选标签选择css
    private String parseStr;
    private List<String> resultList;
    private List<CourseScoreModel> endList;

    public List<String> getTermList() {
        return termList;
    }

    private List<String> termList;

    public List<String> getResultList() {
        return resultList;
    }
    public List<CourseScoreModel> getEndList(){
        return endList;
    }

    /**
    *构造时即执行解析，填充结果
    *@author KevinWu
    *create at 2016/2/4 18:30
    */
    public CourseScoreParse(String parseStr) {
        super();
        this.parseStr = parseStr;
        resultList = new ArrayList<>();
        endList = new ArrayList<>();
        parseData();
    }

    /**
    *解析数据
    *@author KevinWu
    *create at 2016/2/4 18:29
    */
    private void parseData() {
        try {
            HtmlUtil hu = new HtmlUtil(parseStr);
            termList = hu.parseString(TERM_CSS);
            String all_term="";
            for (int i = 0; i < termList.size(); i++) {
                if(i==0){
                    all_term=termList.get(i).toString();
                }
                else{
                    all_term=all_term+"@"+termList.get(i).toString();
                }
                List aTermList = null;
                if (i < termList.size() - 1) {
                    String nowTerm = termList.get(i).toString();//当前学期
                    String nextTerm = termList.get(i + 1).toString();//下学期
                    String left[] = parseStr.split(nowTerm);//分割下边界
                    String right[] = left[1].split(nextTerm);//分割上边界
                    String aTerm = right[0];//一个学期的数据
                    aTermList = new HtmlUtil(aTerm).parseString(ITEM_CSS);
                } else if (i == termList.size() - 1) {
                    String nowTerm = termList.get(i).toString();//当前学期
                    String left[] = parseStr.split(nowTerm);
                    String aTerm = left[1];
                    aTermList = new HtmlUtil(aTerm).parseString(ITEM_CSS);
                }
                for (int j = 0; j <=aTermList.size() - GROUPSIZE; j = j + GROUPSIZE) {
                    resultList.add(termList.get(i).toString());
                    resultList.add(aTermList.get(j).toString());
                    resultList.add(aTermList.get(j + 1).toString());
                    resultList.add(aTermList.get(j + 2).toString());
                    resultList.add(aTermList.get(j + 3).toString());
                    resultList.add(aTermList.get(j + 4).toString());
                    resultList.add(aTermList.get(j + 5).toString());
//                        resultList.add(aTermList.get(j+6).toString());
                }
                SPUtil sp=new SPUtil(InitApp.AppContext);
                sp.putStringSP(TermStaticKey.SP_FILE_NAME,TermStaticKey.ALL_TERM_LIST,all_term);
//               通过以下方法获取String，再进行分割
//               String temp= sp.getStringSP(TermStaticKey.SP_FILE_NAME,TermStaticKey.ALL_TERM_LIST);

            }
            fillEndList();
        } catch (UnsupportedEncodingException e) {
            System.out.println("解析失败");//JUnit调试用
            e.printStackTrace();
        } catch (NullHtmlStringException e) {
            e.printStackTrace();
        }
    }

    /**
     * 填充最终模型
     *
     * @author KevinWu
     * create at 2016/2/4 18:23
     */
    private void fillEndList() {
        for (int i = 0; i <= resultList.size() - GROUPSIZE; i = i + GROUPSIZE) {
            endList.add(new CourseScoreModel(resultList.get(i).toString()
                    , resultList.get(i + 1).toString(),
                    resultList.get(i + 2).toString()
                    , resultList.get(i + 3).toString()
                    , resultList.get(i + 4).toString()
                    , resultList.get(i + 5).toString()
                    , resultList.get(i + 6).toString()));
        }
    }

}
