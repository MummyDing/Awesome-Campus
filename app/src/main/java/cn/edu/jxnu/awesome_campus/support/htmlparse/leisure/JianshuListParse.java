package cn.edu.jxnu.awesome_campus.support.htmlparse.leisure;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.model.leisure.FilmModel;
import cn.edu.jxnu.awesome_campus.support.config.Urlconfig;
import cn.edu.jxnu.awesome_campus.support.utils.html.HtmlUtil;
import cn.edu.jxnu.awesome_campus.support.utils.html.NullHtmlStringException;

/**
 * 解析简书的返回list
 * Created by KevinWu on 2016/2/16.
 */
public class JianshuListParse {
    private static final String CUT_LEFT_STR = "hide loader-tiny";//左边切割字符串
    private static final String CUT_RIGHT_STR = "reject-collection-submission";//右边切割字符串
    private static final String ALL_ITEM_CSS = "ul[class=note-list]";//所有列表选择css
    private static final String ITEM_CSS = "li";
    private static final String ITEM_TITLE_CSS="a[class=title]";
    private String parseStr;

    public List getEndList() {
        return endList;
    }

    private List<FilmModel> endList;

    public JianshuListParse(String parseStr) {
        super();
        this.parseStr = parseStr;
        endList = new ArrayList();
        parseData();
    }

    /**
     * 解析数据
     *
     * @author KevinWu
     * create at 2016/2/16 13:28
     */
    private void parseData() {
        try {
            List<String> picUrl = new ArrayList<>();//图片地址列表
            List<String> contentUrl = new ArrayList<>();//内容地址列表
            List<String> readingCount=new ArrayList<>();//阅读量统计
            HtmlUtil hu = new HtmlUtil(parseStr);

            List all_list = hu.parseRawString(ALL_ITEM_CSS);
            if (all_list == null || all_list.isEmpty()) {
                return;
            }
            hu = new HtmlUtil(all_list.get(0).toString());
            List<String> titleString=hu.parseString(ITEM_TITLE_CSS);
            List pUrlList = hu.parseRawString(ITEM_CSS);//这个列表用来解析图片

            for (int i = 0; i < pUrlList.size(); i++) {

                //有图片解析图片
                if (pUrlList.get(i).toString().indexOf("have-img") > 0) {
                    String firstCut[] = pUrlList.get(i).toString().split("<img src=\"");
                    if (firstCut.length > 1) {
                        String secondCut[] = firstCut[1].split("\" alt=");
                        if (secondCut.length > 1) {
                            picUrl.add(secondCut[0]);
                        } else {
                            picUrl.add("");
                        }
                    }
                }
                //没图片
                else {
                    picUrl.add("");
                }

                String urlFirstCut[] = pUrlList.get(i).toString().split("a class=\"title\" target=\"_blank\" href=\"");
                if (urlFirstCut.length > 1) {
                    String urlSecondCut[] = urlFirstCut[1].split("\">");
                    if (urlSecondCut.length > 1) {
                        contentUrl.add(urlSecondCut[0]);
                    } else {
                        contentUrl.add("");
                    }
                } else {
                    contentUrl.add("");
                }

                //解析阅读量
                String readingFirstCut[]=pUrlList.get(i).toString().split("ic-list-read\"></i>");
                if(readingFirstCut.length>1){
                    String readingSecondCut[]=readingFirstCut[1].split("</a>");
                    if(readingSecondCut.length>1){
                        readingCount.add(readingSecondCut[0].trim());
                    }
                    else{
                        readingCount.add("0");
                    }
                }
                else{
                    readingCount.add("0");
                }
            }


            for(int i=0;i<titleString.size();i++){
                endList.add(
                        new FilmModel(
                                Urlconfig.JianShu_Base_URL+contentUrl.get(i).toString(),
                                titleString.get(i).toString(),
                                readingCount.get(i).toString(),
                                "",
                                "http:" + picUrl.get(i).toString()
                        )
                );
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NullHtmlStringException e) {
            e.printStackTrace();
        }
    }

}
