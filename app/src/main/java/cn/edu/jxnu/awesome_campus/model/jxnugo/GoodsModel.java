package cn.edu.jxnu.awesome_campus.model.jxnugo;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.jxnugo.GoodsDAO;
import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * Created by KevinWu on 16-5-11.
 */
public class GoodsModel implements IModel<GoodsModel>{

    private GoodsDAO dao;

    private String author;
    private String body;
    private int commentsCount;
    private String contact;
    private String goodLocation;
    private String goodBuyTime;
    private String goodName;
    private float goodPrice;
    private String goodQuality;
    private int goodTag;
    private String[] photo;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    private int postId;
    private String postUserAvator;
    private String postUserName;
    private String timestamp;
    private String url;


    public GoodsModel(){
        dao=new GoodsDAO();
    }

    public GoodsModel(String author,String body,String contact,String goodLocation,String goodBuyTime,
                      String goodName,float goodPrice,String goodQuality,int goodTag,String[] photo
    ,int postId,String postUserAvator,String postUserName,String timestamp,String url,int commentsCount){
        this.author=author;
        this.body=body;
        this.contact=contact;
        this.goodLocation=goodLocation;
        this.goodBuyTime=goodBuyTime;
        this.goodName=goodName;
        this.goodPrice=goodPrice;
        this.goodQuality=goodQuality;
        this.postUserName=postUserName;
        this.goodTag=goodTag;
        this.photo=photo;
        this.timestamp=timestamp;
        this.url=url;
        this.commentsCount=commentsCount;
        this.postUserAvator=postUserAvator;
        this.postId=postId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getGoodLocation() {
        return goodLocation;
    }

    public void setGoodLocation(String goodLocation) {
        this.goodLocation = goodLocation;
    }

    public String getGoodBuyTime() {
        return goodBuyTime;
    }

    public void setGoodBuyTime(String goodBuyTime) {
        this.goodBuyTime = goodBuyTime;
    }

    public float getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(float goodPrice) {
        this.goodPrice = goodPrice;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodQuality() {
        return goodQuality;
    }

    public void setGoodQuality(String goodQuality) {
        this.goodQuality = goodQuality;
    }

    public int getGoodTag() {
        return goodTag;
    }

    public void setGoodTag(int goodTag) {
        this.goodTag = goodTag;
    }

    public String[] getPhoto() {
        return photo;
    }

    public void setPhoto(String[] photo) {
        this.photo = photo;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int comments_count) {
        this.commentsCount = comments_count;
    }
    public String getPostUserAvator() {
        return postUserAvator;
    }

    public void setPostUserAvator(String postUserAvator) {
        this.postUserAvator = postUserAvator;
    }

    public String getPostUserName() {
        return postUserName;
    }

    public void setPostUserName(String postUserName) {
        this.postUserName = postUserName;
    }


    @Override
    public boolean cacheAll(List<GoodsModel> list) {
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
