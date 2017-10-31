package cn.edu.jxnu.awesome_campus.model.life;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.life.CampusExpressDAO;
import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * Created by MummyDing on 16-2-20.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CampusExpressModel implements IModel<CampusExpressModel>{

    private CampusExpressDAO dao;
    private String expTextName;
    private String expSpellName;
    private String expTel;
    private String expLoc;
    private String workTime;
    private String itemImage;
    private String topImage;
    private String body;


    public CampusExpressModel() {
        dao = new CampusExpressDAO();
    }

    public CampusExpressModel(String expTextName, String expSpellName, String expTel, String expLoc, String workTime, String itemImage, String topImage, String body) {
        this();
        this.expTextName = expTextName;
        this.expSpellName = expSpellName;
        this.expTel = expTel;
        this.expLoc = expLoc;
        this.workTime = workTime;
        this.itemImage = itemImage;
        this.topImage = topImage;
        this.body = body;
    }

    @Override
    public boolean cacheAll(List<CampusExpressModel> list) {
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

    public String getExpTextName() {
        return expTextName;
    }

    public void setExpTextName(String expTextName) {
        this.expTextName = expTextName;
    }

    public String getExpSpellName() {
        return expSpellName;
    }

    public void setExpSpellName(String expSpellName) {
        this.expSpellName = expSpellName;
    }

    public String getExpTel() {
        return expTel;
    }

    public void setExpTel(String expTel) {
        this.expTel = expTel;
    }

    public String getExpLoc() {
        return expLoc;
    }

    public void setExpLoc(String expLoc) {
        this.expLoc = expLoc;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getTopImage() {
        return topImage;
    }

    public void setTopImage(String topImage) {
        this.topImage = topImage;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
