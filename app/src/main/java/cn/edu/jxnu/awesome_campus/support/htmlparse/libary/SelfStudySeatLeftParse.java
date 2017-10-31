package cn.edu.jxnu.awesome_campus.support.htmlparse.libary;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.model.library.SelfStudySeatLeftModel;
import cn.edu.jxnu.awesome_campus.support.utils.html.HtmlUtil;
import cn.edu.jxnu.awesome_campus.support.utils.html.NullHtmlStringException;

/**
 * Created by KevinWu on 16-4-24.
 */
public class SelfStudySeatLeftParse {
    private static final String ITEM_CSS="li[data-theme=c]";//选择css
    private static final String ITEM_CSS2="li";//选择css2
    private static final String ITEM_CSS3="ul";//选择css3
    private String parseStr = null;//待解析字符串
    private List<String> resultList;//结果列表

    public List<SelfStudySeatLeftModel> getEndList() {
        return endList;
    }

    private List<SelfStudySeatLeftModel> endList;//最终模型列表

    public SelfStudySeatLeftParse(String parseStr){
        this.parseStr=parseStr;
        resultList=new ArrayList<>();
        endList=new ArrayList<>();
        parseData();
    }

    /**
     * 数据解析
     */
    private void parseData() {
        try {
//            System.out.println(parseStr);//使用sysout是为了更方便junit测试
            HtmlUtil hu=new HtmlUtil(parseStr);
            List tempList=hu.parseString(ITEM_CSS);
            List tempRawResultList=hu.parseRawString(ITEM_CSS);

//            System.out.println("获取到的结果列表大小"+resultList.size());
            for(int i=0;i<tempList.size();i++){
                String roomInfo=tempList.get(i).toString().split(" 总座位")[0];
                List tempSubList=new HtmlUtil(tempRawResultList.get(i).toString()).parseString(ITEM_CSS2);
                System.out.println("获取到的子结果列表大小"+tempSubList.size());
//                for(int j=1;j<tempSubList.size()-1;j=j+6){
//                    System.out.println(tempSubList.get(j).toString());
                    endList.add(new SelfStudySeatLeftModel(roomInfo,
                            tempSubList.get(1).toString().split("：")[1],
                            tempSubList.get(2).toString().split("：")[1],
                            tempSubList.get(3).toString().split("：")[1],
                            tempSubList.get(4).toString().split("：")[1]));
//                }
//                System.out.println(roomInfo);

            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NullHtmlStringException e) {
            e.printStackTrace();
        }
    }

}
