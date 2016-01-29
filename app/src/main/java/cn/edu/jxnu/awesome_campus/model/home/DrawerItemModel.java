package cn.edu.jxnu.awesome_campus.model.home;

import java.util.List;

import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * Created by MummyDing on 16-1-29.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class DrawerItemModel implements IModel<DrawerItemModel> {

    private String itemName;
    private int itemIconID;

    public DrawerItemModel(String itemName, int itemIconID) {
        this.itemName = itemName;
        this.itemIconID = itemIconID;
    }

    public DrawerItemModel() {
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemIconID() {
        return itemIconID;
    }

    public void setItemIconID(int itemIconID) {
        this.itemIconID = itemIconID;
    }

    @Override
    public List<DrawerItemModel> loadFromNet() {
        return null;
    }

    @Override
    public List<DrawerItemModel> loadFromCache() {
        return null;
    }

    @Override
    public boolean cleanCache() {
        return false;
    }

    @Override
    public boolean cache() {
        return false;
    }
}
