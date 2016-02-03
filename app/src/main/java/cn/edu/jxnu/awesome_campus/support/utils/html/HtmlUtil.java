package cn.edu.jxnu.awesome_campus.support.utils.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.dataentity.BaseEntity;

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
    public HtmlUtil(String htmlString, String... charsetName) throws UnsupportedEncodingException {
        if (htmlString != null && !htmlString.equals("")) {
            if (charsetName.length == 1) {
                String ehtmlString = URLEncoder.encode(htmlString, charsetName[0]);
                this.doc = Jsoup.parse(ehtmlString);
            } else {
                this.doc = Jsoup.parse(htmlString);
            }
        } else {
            this.doc = null;
        }
    }

    /**
     * 以实体列表形式返回解析后的字符串
     * 参数（css选择样式，对应实体）
     *
     * @author KevinWu
     * create at 2016/2/3 13:46
     */
    public List<List> parseString(String cssQuery, BaseEntity baseEntity) {
        List<List> resultList = new ArrayList<>();
        List tempList = new ArrayList<String>();
        Elements myElement = doc.select(cssQuery);
        for (int i = 0; i < myElement.size() - baseEntity.elementNum; i = i + baseEntity.elementNum) {
            tempList.clear();
            for (int j = 0; j < baseEntity.elementNum; j++) {
                tempList.add(myElement.get(i + j).text().toString());
            }
            resultList.add(tempList);
        }
        return resultList;
    }

    /**
     * 以实体列表形式返回解析后的原生html
     * 参数（css选择样式，对应实体）
     *
     * @author KevinWu
     * create at 2016/2/3 16:09
     */
    public List<List> parseRawString(String cssQuery, BaseEntity baseEntity) {
        List<List> resultList = new ArrayList<>();
        List tempList = new ArrayList<String>();
        Elements myElement = doc.select(cssQuery);
        for (int i = 0; i < myElement.size() - baseEntity.elementNum; i = i + baseEntity.elementNum) {
            tempList.clear();
            for (int j = 0; j < baseEntity.elementNum; j++) {
                tempList.add(myElement.get(i + j).text());
            }
            resultList.add(tempList);
        }
        return resultList;

    }
}