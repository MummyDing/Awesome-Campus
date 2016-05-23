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
    protected String goodsBuyTime;
    protected String goodsLocation;
    protected String goodsName;
    protected float goodsPrice;
    protected String goodsQuality;
    protected int goodsTag;
    protected PhotokeyBean[] photos;
    protected int postId;
    protected String postUserAvatar;
    protected String postNickName;
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
                      String goodsLocation,
                      String goodsBuyTime,
                      String goodsName,
                      float goodsPrice,
                      String goodsQuality,
                      int goodsTag,
                      PhotokeyBean[] photos,
                      int postId,
                      String postUserAvatar,
                      String postNickName,
                      String timestamp,
                      String url,
                      int commentsCount){
        this.author_id=author_id;
        this.body=body;
        this.contact=contact;
        this.goodsLocation = goodsLocation;
        this.goodsBuyTime = goodsBuyTime;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsQuality = goodsQuality;
        this.postNickName = postNickName;
        this.goodsTag = goodsTag;
        this.photos=photos;
        this.timestamp=timestamp;
        this.url=url;
        this.commentsCount=commentsCount;
        this.postUserAvatar = postUserAvatar;
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

    public String getGoodsLocation() {
        return goodsLocation;
    }

    public void setGoodsLocation(String goodsLocation) {
        this.goodsLocation = goodsLocation;
    }

    public String getGoodsBuyTime() {
        return goodsBuyTime;
    }

    public void setGoodsBuyTime(String goodsBuyTime) {
        this.goodsBuyTime = goodsBuyTime;
    }

    public float getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(float goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsQuality() {
        return goodsQuality;
    }

    public void setGoodsQuality(String goodsQuality) {
        this.goodsQuality = goodsQuality;
    }

    public int getGoodsTag() {
        return goodsTag;
    }

    public void setGoodsTag(int goodsTag) {
        this.goodsTag = goodsTag;
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
    public String getPostUserAvatar() {
        return postUserAvatar;
    }

    public void setPostUserAvatar(String postUserAvatar) {
        this.postUserAvatar = postUserAvatar;
    }

    public String getPostNickName() {
        return postNickName;
    }

    public void setPostNickName(String postNickName) {
        this.postNickName = postNickName;
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
