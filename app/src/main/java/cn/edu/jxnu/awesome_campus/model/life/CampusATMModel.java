package cn.edu.jxnu.awesome_campus.model.life;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.life.CampusATMDAO;
import cn.edu.jxnu.awesome_campus.database.dao.life.CampusExpressDAO;
import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * Created by MummyDing on 16-4-22.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CampusATMModel implements IModel<CampusATMModel>{

    private CampusATMDAO dao;
    private String bankName;
    private String bankLocation;
    private String imageUrl;

    public CampusATMModel() {
        dao = new CampusATMDAO();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankLocation() {
        return bankLocation;
    }

    public void setBankLocation(String bankLocation) {
        this.bankLocation = bankLocation;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean cacheAll(List<CampusATMModel> list) {
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
}
