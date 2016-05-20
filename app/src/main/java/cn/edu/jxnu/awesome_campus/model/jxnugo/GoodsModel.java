package cn.edu.jxnu.awesome_campus.model.jxnugo;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.jxnugo.GoodsDAO;
import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * Created by KevinWu on 16-5-11.
 */
public class GoodsModel implements IModel<GoodsModel>{

    private GoodsDAO dao;

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    protected int author_id;
    protected String body;
    protected int commentsCount;
    protected String contact;
    protected String goodBuyTime;
    protected String goodLocation;
    protected String goodName;
    protected float goodPrice;
    protected String goodQuality;
    protected int goodTag;
    protected PhotokeyBean[] photos;
    protected int postId;
    protected String postUserAvator;
    protected String postUserName;
    protected String timestamp;
    protected String url;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }




    public GoodsModel(){
        dao=new GoodsDAO();
    }

    public GoodsModel(int author_id,
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
        this.author_id=author_id;
        this.body=body;
        this.contact=contact;
        this.goodLocation=goodLocation;
        this.goodBuyTime=goodBuyTime;
        this.goodName=goodName;
        this.goodPrice=goodPrice;
        this.goodQuality=goodQuality;
        this.postUserName=postUserName;
        this.goodTag=goodTag;
        this.photos=photos;
        this.timestamp=timestamp;
        this.url=url;
        this.commentsCount=commentsCount;
        this.postUserAvator=postUserAvator;
        this.postId=postId;
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
