package cn.edu.jxnu.awesome_campus.model.jxnugo;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.jxnugo.UserCollectionDAO;
import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * Created by KevinWu on 16-5-20.
 */
public class UserCollectionModel extends GoodsModel{
    private UserCollectionDAO dao;

    public UserCollectionModel(){
        dao=new UserCollectionDAO();
    }

    public UserCollectionModel(String author,
                      String body,
                      String contact,
                      String goodLocation,
                      String goodBuyTime,
                      String goodName,
                      float goodPrice,
                      String goodQuality,
                      int goodTag,
                      PhotokeyBean[] photos,
                      int postId,
                      String postUserAvator,
                      String postUserName,
                      String timestamp,
                      String url,
                      int commentsCount){
        super.author=author;
        super.body=body;
        super.contact=contact;
        super.goodLocation=goodLocation;
        super.goodBuyTime=goodBuyTime;
        super.goodName=goodName;
        super.goodPrice=goodPrice;
        super.goodQuality=goodQuality;
        super.postUserName=postUserName;
        super.goodTag=goodTag;
        super.photos=photos;
        super.timestamp=timestamp;
        super.url=url;
        super.commentsCount=commentsCount;
        super.postUserAvator=postUserAvator;
        super.postId=postId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        super.author = author;
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

    public PhotokeyBean[] getPhotos() {
        return photos;
    }

    public void setPhotos(PhotokeyBean[] photos) {
        this.photos = photos;
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
