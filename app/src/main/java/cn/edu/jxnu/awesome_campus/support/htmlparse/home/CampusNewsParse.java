package cn.edu.jxnu.awesome_campus.support.htmlparse.home;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.model.home.CampusNewsModel;
import cn.edu.jxnu.awesome_campus.support.utils.common.TimeUtil;
import cn.edu.jxnu.awesome_campus.support.utils.html.HtmlUtil;
import cn.edu.jxnu.awesome_campus.support.utils.html.NullHtmlStringException;

/**
 * 校内要闻解析
 * 使用：通过传进来html后，执行getEndList()即可获取模型对象集
 * Created by KevinWu on 2016/2/3.
 */
public class CampusNewsParse {
    private final static int GROUPSIZE = 5;//信息分组大小
    private final static String ITEM_CSS = "table[class=columnStyle]";//选择CSS
    private final static String REFERENCE_STR = "2015-12-24";//参考时间字符串
    private String parseStr;//待解析字符串

    public List<CampusNewsModel> getEndList() {
        return endList;
    }

    private List<String> resultList;//结果列表
    private List<CampusNewsModel> endList;//最终返回列表

    /**
     * 构造时即执行解析，解析后自动填充结果
     *
     * @author KevinWu
     * create at 2016/2/6 19:03
     */
    public CampusNewsParse(String parseStr) {
        super();
        this.parseStr = parseStr;
        resultList = new ArrayList<>();
        endList = new ArrayList<>();
        parseData();
    }

    /**
     * 解析数据
     *
     * @author KevinWu
     * create at 2016/2/6 19:06
     */
    private void parseData() {
        try {
            HtmlUtil hu = new HtmlUtil(parseStr);
            List tempStrList = hu.parseString(ITEM_CSS);
            List tempRawStrList = hu.parseRawString(ITEM_CSS);
            for (int i = 0; i < tempStrList.size(); i++) {
                String tempStr = tempStrList.get(i).toString();
                String newsTitle = tempStr.substring(0, tempStr.length() - REFERENCE_STR.length()).trim();
                String newsTime = tempStr.substring(tempStr.length() - REFERENCE_STR.length(), tempStr.length());
                String tempRawStr = tempRawStrList.get(i).toString();
                String newsUrl;
                String newsPicUrl = "";//图片地址留空（因为都没有）
                String updateTime = TimeUtil.getYearMonthDay();
                String cutUrlLeft[] = tempRawStr.split("href=\"");
                if (cutUrlLeft.length > 1) {
                    String cutUrlRight[] = cutUrlLeft[1].split("\" target=\"_blank\"");
                    if (cutUrlRight.length > 1) {
                        newsUrl = cutUrlRight[0];
                    } else {
                        continue;
                    }
                } else {
                    continue;
                }
                resultList.add(newsTitle);
                resultList.add(newsTime);
                resultList.add(newsUrl);
                resultList.add(newsPicUrl);
                resultList.add(updateTime);
            }
            fillEndList();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NullHtmlStringException e) {
            e.printStackTrace();
        }

    }

    /**
     * 填充最终模型
     *
     * @author KevinWu
     * create at 2016/2/6 21:25
     */
    private void fillEndList() {
        for (int i = 0; i <= resultList.size() - GROUPSIZE; i = i + GROUPSIZE) {
            endList.add(new CampusNewsModel(
                    resultList.get(i).toString(),
                    resultList.get(i + 1).toString(),
                    resultList.get(i + 2).toString(),
                    resultList.get(i + 3).toString(),
                    resultList.get(i + 4).toString(),""
            ));
        }
    }
}
