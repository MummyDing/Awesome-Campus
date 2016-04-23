package cn.edu.jxnu.awesome_campus.support.htmlparse.libary;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.model.library.HotSearchModel;
import cn.edu.jxnu.awesome_campus.support.utils.html.HtmlUtil;
import cn.edu.jxnu.awesome_campus.support.utils.html.NullHtmlStringException;

/**
 * Created by KevinWu on 16-4-23.
 */
public class HotSearchParse {
    private static final String ITEM_CSS = "a[class=blue]";//数据解析css
    private String parseStr;//待解析字符串

    public List<HotSearchModel> getEndList() {
        return endList;
    }

    private List<HotSearchModel> endList;//最终结果列表

    public HotSearchParse(String parseStr) {
        this.parseStr = parseStr;
        this.endList = new ArrayList<>();
        parseData();
    }


    /**
     * 数据解析
     *
     * @author KevinWu
     * create at 2016/4/23 16:22
     */
    private void parseData() {
        try {
            System.out.println(parseStr);
            HtmlUtil hu = new HtmlUtil(parseStr);
            List tempList = hu.parseString(ITEM_CSS);
            Log.d("标签结果个数为个数为：","--"+tempList.size());
            for (int i = 0; i < tempList.size(); i++) {
                String tempStr= tempList.get(i).toString().split("[([0-9]+)]")[0];
                //tempStr=tempStr.substring(0,tempStr.length()-1);
                endList.add(new HotSearchModel(tempStr));
            }

            Log.d("解析出标签个数为：","--"+endList.size());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NullHtmlStringException e) {
            e.printStackTrace();
        }
    }
}
