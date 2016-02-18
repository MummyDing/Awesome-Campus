package cn.edu.jxnu.awesome_campus.support.htmlparse.libary;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.model.library.BookSearchResultModel;
import cn.edu.jxnu.awesome_campus.support.utils.html.HtmlUtil;
import cn.edu.jxnu.awesome_campus.support.utils.html.NullHtmlStringException;

/**
 * 解析查找的书
 * Created by KevinWu on 2016/2/19.
 */
public class BookSearchResultParse {
    private static final int GROUP_SIZE=8;
    private static final String ITEM_CSS="div[class=list_books]";//数据解析css
    private static final String H3_ITEM_CSS="h3";//h3解析标签
    private static final String A_ITEM_CSS="a";//a解析标签
    private static final String TYPE_ITEM_CSS="span[class=doc_type_class]";//图书类型css
    private String parseStr;//待解析字符串

    public List<String> getResultList() {
        return resultList;
    }

    public List<BookSearchResultModel> getEndList() {
        return endList;
    }

    private List<String> resultList;//结果列表
    private List<BookSearchResultModel> endList;//最终数据模型列表
    public BookSearchResultParse(String parseStr){
        super();
        this.parseStr=parseStr;
        this.resultList=new ArrayList<>();
        this.endList=new ArrayList<>();
        parseData();
    }


    /**
    *解析数据
    *@author KevinWu
    *create at 2016/2/19 2:42
    */
    private void parseData() {
        try {
            HtmlUtil hu=new HtmlUtil(parseStr);
            List rawList=hu.parseRawString(ITEM_CSS);
            for(int i=0;i<rawList.size();i++){
                String type_firsr_cut[]=rawList.get(i).toString().split("\"doc_type_class\">");
                String type_secont_cut[]=type_firsr_cut[1].split("</span><");
                String type=type_secont_cut[0];
                HtmlUtil hu2=new HtmlUtil(rawList.get(i).toString());
                String title=hu2.parseString(A_ITEM_CSS).get(0).toString();
                String no_first_cut[]=rawList.get(i).toString().split("</a>");
                String no_second_cut[]=no_first_cut[1].split("</h3>");
                String no=no_second_cut[0];
//                String bookcount_first_cut[]=no_second_cut[1].split("馆藏复本：</strong>");
//                String bookcount_second_cut[]=bookcount_first_cut[1].split("<br />\n" +
//                        "            <strong>");
//                String bookcount=bookcount_second_cut[0];
//                String left_first_cut[]=bookcount_second_cut[1].split("</strong>");
//                String left_second_cut[]=left_first_cut[1].split("</span>");
//                String left=left_second_cut[0];
//                String author=left_second_cut[1].split("<br />")[0];
//                String publisher=left_second_cut[1].split("<br />")[1].split("</p>")[0];
                endList.add(new BookSearchResultModel(
                        title,
                        no,
                        type,
                        "",
                        "",
                        "",
                        ""
                ));
            }
            fillEndList();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NullHtmlStringException e) {
            e.printStackTrace();
        }
    }

    /**
    *填充最终列表
    *@author KevinWu
    *create at 2016/2/19 2:42
    */
    private void fillEndList() {

    }

}
