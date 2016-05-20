package cn.edu.jxnu.awesome_campus.model.jxnugo;

import java.util.List;

/**
 * Created by zpauly on 16-5-14.
 */
public class PublishGoodsBean {



    /**
     * body : 二手笔记本，成色非常好，见鲁大师，见鲁大师二手笔记本，成色非常好，见鲁大师，见鲁大师二手笔记本，成色非常好，见鲁大师，见鲁大师二手笔记本，
     * goodName : 戴尔灵越5537
     * goodNum : 1
     * goodPrice : 2000
     * goodLocation : 一栋N204
     * goodQuality : 7成新
     * goodBuyTime : 2014年6月
     * goodTag : 1
     * contact : 13361640744
     * photos : [{"key":"84BE7838-E41C-4E60-A1B8-CA95DBEE326B"},{"key":"84BE7838-E41C-4E60-A1B8-CA95DBEE326B"},{"key":"84BE7838-E41C-4E60-A1B8-CA95DBEE326B"}]
     */
    private String userId;
    private String body;
    private String goodName;
    private int goodNum;
    private float goodPrice;
    private String goodLocation;
    private String goodQuality;
    private String goodBuyTime;
    private int goodTag;
    private String contact;
    /**
     * key : 84BE7838-E41C-4E60-A1B8-CA95DBEE326B
     */

    private List<PhotokeyBean> photos;

    public PublishGoodsBean(String userId,String body, String goodName, int goodNum, float goodPrice
            , String goodLocation, String goodQuality, String goodBuyTime
            , int goodTag, String contact, List<PhotokeyBean> photos) {
        this.userId=userId;
        this.body = body;
        this.goodName = goodName;
        this.goodNum = goodNum;
        this.goodPrice = goodPrice;
        this.goodLocation = goodLocation;
        this.goodQuality = goodQuality;
        this.goodBuyTime = goodBuyTime;
        this.goodTag = goodTag;
        this.contact = contact;
        this.photos = photos;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public int getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(int goodNum) {
        this.goodNum = goodNum;
    }

    public float getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(float goodPrice) {
        this.goodPrice = goodPrice;
    }

    public String getGoodLocation() {
        return goodLocation;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setGoodLocation(String goodLocation) {
        this.goodLocation = goodLocation;
    }

    public String getGoodQuality() {
        return goodQuality;
    }

    public void setGoodQuality(String goodQuality) {
        this.goodQuality = goodQuality;
    }

    public String getGoodBuyTime() {
        return goodBuyTime;
    }

    public void setGoodBuyTime(String goodBuyTime) {
        this.goodBuyTime = goodBuyTime;
    }

    public int getGoodTag() {
        return goodTag;
    }

    public void setGoodTag(int goodTag) {
        this.goodTag = goodTag;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public List<PhotokeyBean> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotokeyBean> photos) {
        this.photos = photos;
    }
}
