package cn.edu.jxnu.awesome_campus.model.library;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.library.SelfStudySeatLeftDAO;
import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * 图书馆自习室剩余作为model
 * Created by KevinWu on 16-4-24.
 */
public class SelfStudySeatLeftModel implements IModel<SelfStudySeatLeftModel>{
    private String roomInfo;//自习室整体剩余座位信息
    private String totalSeat;//总座位
    private String bePresent;//在座信息
    private String temporaryLeave;//暂离
    private String freeSeat;//空闲座位
    private SelfStudySeatLeftDAO dao;



    public SelfStudySeatLeftModel(){
        dao=new SelfStudySeatLeftDAO();
    }

    public void setCookie(String cookie) {
        dao.setCookie(cookie);
    }

    public SelfStudySeatLeftModel(String roomInfo, String totalSeat, String bePresent, String temporaryLeave, String freeSeat)
    {
        this.roomInfo=roomInfo;
        this.totalSeat=totalSeat;
        this.bePresent=bePresent;
        this.temporaryLeave=temporaryLeave;
        this.freeSeat=freeSeat;
    }



    @Override
    public boolean cacheAll(List<SelfStudySeatLeftModel> list) {
        return dao.cacheAll(list);
    }

    @Override
    public boolean clearCache() {
        return dao.clearCache();
    }

    @Override
    public void loadFromCache() {
        //测试用
        dao.loadFromCache();
    }

    @Override
    public void loadFromNet() {
        dao.loadFromNet();
    }
    public String getBePresent() {
        return bePresent;
    }

    public void setBePresent(String bePresent) {
        this.bePresent = bePresent;
    }

    public String getFreeSeat() {
        return freeSeat;
    }

    public void setFreeSeat(String freeSeat) {
        this.freeSeat = freeSeat;
    }

    public String getTemporaryLeave() {
        return temporaryLeave;
    }

    public void setTemporaryLeave(String temporaryLeave) {
        this.temporaryLeave = temporaryLeave;
    }

    public String getTotalSeat() {
        return totalSeat;
    }

    public void setTotalSeat(String totalSeat) {
        this.totalSeat = totalSeat;
    }

    public String getRoomInfo() {
        return roomInfo;
    }

    public void setRoomInfo(String roomInfo) {
        this.roomInfo = roomInfo;
    }
}
