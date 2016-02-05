package cn.edu.jxnu.awesome_campus.support.utils.html;

/**
 * 当解析的html数据为空时的异常
 * Created by KevinWu on 2016/2/5.
 */
public class NullHtmlStringException extends Exception{
    private String msg;//异常信息
    public NullHtmlStringException(String msg){
        this.msg=msg;
    }
}
