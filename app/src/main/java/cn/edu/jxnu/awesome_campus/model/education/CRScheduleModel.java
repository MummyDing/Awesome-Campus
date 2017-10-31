package cn.edu.jxnu.awesome_campus.model.education;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.education.CRScheduleDAO;
import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * 教室开课安排model
 * Created by KevinWu on 16-5-10.
 */
public class CRScheduleModel implements IModel<CRScheduleModel> {
    private static final String TAG="CRScheduleModel";
    private CRScheduleDAO dao;

    private String classRoom;//教室名称 例：W1301（多媒体）
    private String week;//星期数 例：1
    private String classNum;//课程节数 例：12
//    private String






    @Override
    public boolean cacheAll(List<CRScheduleModel> list) {
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
