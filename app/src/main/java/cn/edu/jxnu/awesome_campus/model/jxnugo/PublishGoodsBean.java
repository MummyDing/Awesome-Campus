package cn.edu.jxnu.awesome_campus.model.jxnugo;

import java.util.List;

/**
 * Created by zpauly on 16-5-14.
 */
public class PublishGoodsBean {



    /**
     * body : 二手笔记本，成色非常好，见鲁大师，见鲁大师二手笔记本，成色非常好，见鲁大师，见鲁大师二手笔记本，成色非常好，见鲁大师，见鲁大师二手笔记本，
     * goodsName : 戴尔灵越5537
     * goodsNum : 1
     * goodsPrice : 2000
     * goodsLocation : 一栋N204
     * goodsQuality : 7成新
     * goodsBuyTime : 2014年6月
     * goodsTag : 1
     * contact : 13361640744
     * photos : [{"key":"84BE7838-E41C-4E60-A1B8-CA95DBEE326B"},{"key":"84BE7838-E41C-4E60-A1B8-CA95DBEE326B"},{"key":"84BE7838-E41C-4E60-A1B8-CA95DBEE326B"}]
     */
    private String userId;
    private String body;
    private String goodsName;
    private int goodsNum;
    private float goodsPrice;
    private String goodsLocation;
    private String goodsQuality;
    private String goodsBuyTime;
    private int goodsTag;
    private String contact;
    private String auth_token;
    /**
     * key : 84BE7838-E41C-4E60-A1B8-CA95DBEE326B
     */

    private List<PhotokeyBean> photos;

    public PublishGoodsBean(String userId, String body, String goodsName, int goodsNum, float goodsPrice
            , String goodsLocation, String goodsQuality, String goodsBuyTime
            , int goodsTag, String contact, List<PhotokeyBean> photos,String auth_token) {
        this.userId=userId;
        this.body = body;
        this.goodsName = goodsName;
        this.goodsNum = goodsNum;
        this.goodsPrice = goodsPrice;
        this.goodsLocation = goodsLocation;
        this.goodsQuality = goodsQuality;
        this.goodsBuyTime = goodsBuyTime;
        this.goodsTag = goodsTag;
        this.contact = contact;
        this.photos = photos;
        this.auth_token=auth_token;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }
    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public float getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(float goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsLocation() {
        return goodsLocation;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setGoodsLocation(String goodsLocation) {
        this.goodsLocation = goodsLocation;
    }

    public String getGoodsQuality() {
        return goodsQuality;
    }

    public void setGoodsQuality(String goodsQuality) {
        this.goodsQuality = goodsQuality;
    }

    public String getGoodsBuyTime() {
        return goodsBuyTime;
    }

    public void setGoodsBuyTime(String goodsBuyTime) {
        this.goodsBuyTime = goodsBuyTime;
    }

    public int getGoodsTag() {
        return goodsTag;
    }

    public void setGoodsTag(int goodsTag) {
        this.goodsTag = goodsTag;
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
