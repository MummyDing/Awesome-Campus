package cn.edu.jxnu.awesome_campus.support.htmlparse.education;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.support.utils.html.HtmlUtil;
import cn.edu.jxnu.awesome_campus.support.utils.html.NullHtmlStringException;

/**
 * 教务在线登录页数据解析，根据解析出来的字符串判断是否登录成功，登录成功即可通过endList的第一个元素获取姓名
 * Created by KevinWu on 2016/2/15.
 */
public class EducationalSysLoginParse {
    public static String LOGIN_FAIL_NO_ID_STR = "学号不存在!";
    public static String LOGIN_FAIL_PASSWORD_INCORRECT_STR = "对不起，您的密码不正确，请注意您是否区分了大小写!";
    private static String ITEM_CSS = "span#lblMsg";
    private String parseStr = null;//待解析字符串
    private List<String> endList = null;//解析后的列表

    public EducationalSysLoginParse(String parseStr) {
        super();
        this.parseStr = parseStr;
        this.endList = new ArrayList<>();
        parseData();
    }

    /**
     * 解析数据
     *
     * @author KevinWu
     * create at 2016/2/15 15:59
     */
    private void parseData() {
        if (parseStr.indexOf(LOGIN_FAIL_NO_ID_STR) >= 0) {
            endList.add(LOGIN_FAIL_NO_ID_STR);
        } else if (parseStr.indexOf(LOGIN_FAIL_PASSWORD_INCORRECT_STR) >= 0) {
            endList.add(LOGIN_FAIL_PASSWORD_INCORRECT_STR);
        } else {
            try {
                HtmlUtil hu = new HtmlUtil(parseStr);
                List tempList = hu.parseString(ITEM_CSS);
                if (tempList.size() >= 1) {
                    String tempStr = tempList.get(0).toString();
                    String first_cut[] = tempStr.split("，");
                    if (first_cut.length >= 1) {
                        String second_cut[] = first_cut[1].split(" ");
                        endList.add(second_cut[0]);
                    }
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (NullHtmlStringException e) {
                e.printStackTrace();
            }
        }

    }


    public List<String> getEndList() {
        return endList;
    }

    public String getParseStr() {
        return parseStr;
    }


}
