package cn.edu.jxnu.awesome_campus.model.library;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.library.HotSearchDAO;
import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * Created by KevinWu on 16-4-23.
 */
public class HotSearchModel implements IModel<HotSearchModel> {
    private HotSearchDAO dao;
    private String tag;

    public HotSearchModel() {
        dao = new HotSearchDAO();
    }

    public HotSearchModel(String tag) {
        this();
        this.tag = tag;
    }

    @Override
    public boolean cacheAll(List<HotSearchModel> list) {
        return dao.cacheAll(list);
    }

    @Override
    public boolean clearCache() {
        return dao.clearCache();
    }

    @Override
    public void loadFromCache() {
        dao.loadFromCache();
    }

    @Override
    public void loadFromNet() {
        dao.loadFromNet();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
