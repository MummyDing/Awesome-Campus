package cn.edu.jxnu.awesome_campus.model.leisure;

import java.util.List;

import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * 简书拉取的数据的model
 * Created by KevinWu on 2016/2/16.
 */
public class FilmModel implements IModel<FilmModel> {
    private String url;//内容url
    private String title;//标题
    private String reading_count;//阅读量
    private String detail;//内容
    private String TopPic;//标题图片url

    @Override
    public boolean cacheAll(List<FilmModel> list) {
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
