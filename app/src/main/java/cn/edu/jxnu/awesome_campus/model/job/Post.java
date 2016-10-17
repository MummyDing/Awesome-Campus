package cn.edu.jxnu.awesome_campus.model.job;

/**
 * Created by yzr on 16/10/17.
 */

import java.util.HashMap;
import java.util.Map;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Post {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("recruitment_time")
    @Expose
    private String recruitmentTime;
    @SerializedName("recruitment_place")
    @Expose
    private String recruitmentPlace;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("publish_person_id")
    @Expose
    private String publishPersonId;
    @SerializedName("link_url")
    @Expose
    private String linkUrl;
    @SerializedName("type_name")
    @Expose
    private String typeName;

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     * The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     * The recruitmentTime
     */
    public String getRecruitmentTime() {
        return recruitmentTime;
    }

    /**
     *
     * @param recruitmentTime
     * The recruitment_time
     */
    public void setRecruitmentTime(String recruitmentTime) {
        this.recruitmentTime = recruitmentTime;
    }

    /**
     *
     * @return
     * The recruitmentPlace
     */
    public String getRecruitmentPlace() {
        return recruitmentPlace;
    }

    /**
     *
     * @param recruitmentPlace
     * The recruitment_place
     */
    public void setRecruitmentPlace(String recruitmentPlace) {
        this.recruitmentPlace = recruitmentPlace;
    }

    /**
     *
     * @return
     * The remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     *
     * @param remark
     * The remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     *
     * @return
     * The publishPersonId
     */
    public String getPublishPersonId() {
        return publishPersonId;
    }

    /**
     *
     * @param publishPersonId
     * The publish_person_id
     */
    public void setPublishPersonId(String publishPersonId) {
        this.publishPersonId = publishPersonId;
    }

    /**
     *
     * @return
     * The linkUrl
     */
    public String getLinkUrl() {
        return linkUrl;
    }

    /**
     *
     * @param linkUrl
     * The link_url
     */
    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    /**
     *
     * @return
     * The typeName
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     *
     * @param typeName
     * The type_name
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

}