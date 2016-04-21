package cn.edu.jxnu.awesome_campus.support.htmlparse.libary;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.model.library.BookBorrowedModel;
import cn.edu.jxnu.awesome_campus.support.utils.html.HtmlUtil;
import cn.edu.jxnu.awesome_campus.support.utils.html.NullHtmlStringException;

/**
 * 借书解析
 * Created by KevinWu on 2016/2/19.
 */
public class BookBorrowedParse {
    private static final String TAG="BookBorrowedParse";
    private static final int GROUP_SIZE=8;
    private static final String ITEM_CSS="td[class=whitetext]";//选择css
    private String parseStr;//待解析字符串
    private List<String> resultList;//结果列表

    public List<BookBorrowedModel> getEndList() {
        return endList;
    }

    private List<BookBorrowedModel> endList;//最终模型列表

    public BookBorrowedParse(String parseStr){
        super();
        this.parseStr=parseStr;
        this.resultList=new ArrayList<>();
        this.endList=new ArrayList<>();
        parseData();
    }

    
    /**
    *数据解析
    *@author KevinWu
    *create at 2016/2/19 1:29
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
    *填充结果列表
    *@author KevinWu
    *create at 2016/2/19 1:31
    */
    private void fillEndList() {
        Log.d(TAG,"获取到的结果列表的大小为："+resultList.size()/GROUP_SIZE);
        for(int i=0;i<=resultList.size()-GROUP_SIZE;i=i+GROUP_SIZE){
            String first_cut[]=resultList.get(i+1).toString().split(" / ");
            String bookTitle=first_cut[0];
            String bookAuthor="";
            if (first_cut.length>1){
                bookAuthor=first_cut[1];
            }
            endList.add(
                    new BookBorrowedModel(
                            resultList.get(i).toString(),
                            bookTitle,
                            bookAuthor,
                            resultList.get(i+2).toString(),
                            resultList.get(i+3).toString(),
                            resultList.get(i+4).toString(),
                            resultList.get(i+5).toString()
                    )
            );
            Log.d(TAG,"应该归还时间为："+resultList.get(i+3).toString());
        }
        Log.d(TAG,"获取到最终的结果列表的大小为："+endList.size());
    }
}
