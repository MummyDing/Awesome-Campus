package cn.edu.jxnu.awesome_campus.model.jxnugo;

/**
 * Created by zpauly on 16-5-14.
 */
public class PublishGoodsBean {

    /**
     * body :
     * timestamp :
     * goodName :
     * goodPrice :
     * goodNum :
     * goodLocation :
     * goodQuality :
     * goodBuyTime :
     * goodTag : 1
     * contact :
     * photos :
     */

    private String body;
    private String goodName;
    private float goodPrice;
    private int goodNum;
    private String goodLocation;
    private String goodQuality;
    private String goodBuyTime;
    private int goodTag;
    private String contact;
    private String photos;

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

    public float getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(float goodPrice) {
        this.goodPrice = goodPrice;
    }

    public int getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(int goodNum) {
        this.goodNum = goodNum;
    }

    public String getGoodLocation() {
        return goodLocation;
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

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }
}
