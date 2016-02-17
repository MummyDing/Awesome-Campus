package cn.edu.jxnu.awesome_campus.support.htmlparse.leisure;

/**
 * 简书内容详情解析页
 * Created by KevinWu on 2016/2/17.
 */
public class JianshuContentParse {
    private static final String STR_CUT_LEFT = "<div class=\"container\">";//分割左边界
    private static final String STR_CUT_RIGHT = " <div class=\"visitor_edit\">";//分割右边界
    private static final String OTHER_STR = "<div class=\"btn btn-small btn-success follow\">\n" +
            "    <a data-signin-link=\"true\" data-toggle=\"modal\" href=\"/sign_in\"><i class=\"fa fa-plus\"></i>  <span>添加关注</span></a>\n" +
            "  </div>";//去除字符串
    private String parseStr=null;//待解析字符串

    public String getEndStr() {
        return endStr;
    }

    private String endStr=null;//解析后字符串


    public JianshuContentParse(String parseStr){
        super();
        this.parseStr=parseStr;
        parseData();
    }

    /**
    *解析字符串
    *@author KevinWu
    *create at 2016/2/17 16:58
    */
    private void parseData() {
        String first_cut[]=parseStr.split(STR_CUT_LEFT);
        if(first_cut.length>1){
            String second_cut[]=first_cut[1].split(STR_CUT_RIGHT);
            endStr=(" <link rel=\"stylesheet\" media=\"all\" href=\"http://cdn0.jianshu.io/assets/base-e3bc3201b4c1ecbe0b50fbb515013655.css\" />\n" +
                    "\n" +
                    "    <link rel=\"stylesheet\" media=\"all\" href=\"http://cdn0.jianshu.io/assets/reading-note-537cd03df12ad2bbd35bd01d34cdc095.css\" />\n" +
                    "  <link rel=\"stylesheet\" media=\"all\" href=\"http://cdn0.jianshu.io/assets/base-read-mode-bbb4b1a780c5883d30bdc237f80aba01.css\" />\n" +
                    "  <script src=\"http://cdn0.jianshu.io/assets/modernizr-b24c000b09feb5c7177d4db960f050bd.js\"></script>"+STR_CUT_LEFT+second_cut[0]).replaceAll(OTHER_STR,"<span></span>");
        }else{
            endStr=null;
        }
    }
}
