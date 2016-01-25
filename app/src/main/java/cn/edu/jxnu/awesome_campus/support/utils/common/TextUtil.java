package cn.edu.jxnu.awesome_campus.support.utils.common;

/**
 * Created by MummyDing on 16-1-25.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class TextUtil {
    public static boolean isNull(String str){
        if(str == null || str.equals("")){
            return true;
        }
        return false;
    }
}
