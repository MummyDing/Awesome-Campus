package cn.edu.jxnu.awesome_campus.support.utils.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.support.utils.common.TextUtil;

/**
 * Created by KevinWu on 2016/2/3.
 */
public class HtmlUtil {
    private Document doc;

    /**
     * 构造方法
     * 以字符串为参数解析静态html页面
     * 字符编码为后编，可选。
     *
     * @author KevinWu
     * create at 2016/2/3 12:59
     */
    public HtmlUtil(String htmlString, String... charsetName) throws UnsupportedEncodingException, NullHtmlStringException {
        if (TextUtil.isNull(htmlString)!=true) {
            if (charsetName.length == 1) {
                String ehtmlString = URLEncoder.encode(htmlString, charsetName[0]);
                this.doc = Jsoup.parse(ehtmlString);
            } else {
                this.doc = Jsoup.parse(htmlString);
            }
        } else {
            this.doc = null;
            throw new NullHtmlStringException("html解析数据空异常");
        }
    }

    /**
     * 返回解析后的字符串
     * 参数（css选择样式）
     *
     * @author KevinWu
     * create at 2016/2/3 13:46
     */
    public List<String> parseString(String cssQuery) {
        List<String> resultList = new ArrayList<>();
        Elements myElement = doc.select(cssQuery);
        for (int i = 0; i < myElement.size() ;i++) {
                resultList.add(myElement.get(i).text().toString());
        }
        return resultList;
    }

    /**
     * 返回解析后的原生html
     * 参数（css选择样式）
     *
     * @author KevinWu
     * create at 2016/2/3 16:09
     */
    public List<String> parseRawString(String cssQuery) {
        List<String> resultList = new ArrayList<>();
        Elements myElement = doc.select(cssQuery);
        for (int i = 0; i < myElement.size() ;i++) {
            resultList.add(myElement.get(i).toString());
        }
        return resultList;
    }

    /**
     * 指定属性查找
     * @param cssQuery
     * @param cssAttr
     * @return
     */
    public List<String> parseStringByAttr(String cssQuery, String cssAttr) {
        List<String> resultList = new ArrayList<>();
        Elements elements = doc.select(cssQuery);
        if (elements == null) {
            return resultList;
        }
        for (int i = 0 ; i < elements.size() ; i++) {
            resultList.add(elements.get(i).attr(cssAttr));
        }
        return resultList;
    }
}