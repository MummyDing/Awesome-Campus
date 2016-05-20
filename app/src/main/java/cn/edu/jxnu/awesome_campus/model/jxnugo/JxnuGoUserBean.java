package cn.edu.jxnu.awesome_campus.model.jxnugo;

/**
 * Created by KevinWu on 16-5-11.
 */
public class JxnuGoUserBean {
    private String about_me;
    private String last_seen;
    private String location;
    private String menber_since;
    private String name;
    private  String avatar;
    private int postCount;

    public int getPostCollectionCount() {
        return postCollectionCount;
    }

    public void setPostCollectionCount(int postCollectionCount) {
        this.postCollectionCount = postCollectionCount;
    }

    private int postCollectionCount;
    private String sex;
    private int userId;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContactMe() {
        return contactMe;
    }

    public void setContactMe(String contactMe) {
        this.contactMe = contactMe;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private String contactMe;
    private String userName;
    private String followed;
    private String followers;






    public String getAbout_me() {
        return about_me;
    }

    public void setAbout_me(String about_me) {
        this.about_me = about_me;
    }

    public String getLast_seen() {
        return last_seen;
    }

    public void setLast_seen(String last_seen) {
        this.last_seen = last_seen;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMenber_since() {
        return menber_since;
    }

    public void setMenber_since(String menber_since) {
        this.menber_since = menber_since;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public String getFollowed() {
        return followed;
    }

    public void setFollowed(String followed) {
        this.followed = followed;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }


}
