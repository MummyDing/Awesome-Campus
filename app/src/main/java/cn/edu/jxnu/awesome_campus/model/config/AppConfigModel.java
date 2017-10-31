package cn.edu.jxnu.awesome_campus.model.config;

import java.util.List;

import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * Created by wqd19 on 2017/10/31.
 */

public class AppConfigModel implements IModel {
    private String EDU_LOGIN__VIEWSTATE;
    private String EDU_LOGIN__EVENTVALIDATION;

    public String getEDU_LOGIN__VIEWSTATE() {
        return EDU_LOGIN__VIEWSTATE;
    }

    public String getEDU_LOGIN__EVENTVALIDATION() {
        return EDU_LOGIN__EVENTVALIDATION;
    }

    @Override
    public boolean cacheAll(List list) {
        return false;
    }

    @Override
    public boolean clearCache() {
        return false;
    }

    @Override
    public void loadFromCache() {

    }

    @Override
    public void loadFromNet() {

    }
}
