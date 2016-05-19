package cn.edu.jxnu.awesome_campus.model.jxnugo;

/**
 * Created by KevinWu on 16-5-19.
 */
public class UpdateInfoBean {
    private String userId;
    private String name;
    private String location;
    private String sex;
    private String contact;
    private String about_me;
    private String avatar;

    public UpdateInfoBean(String userId,String name,String location,String sex,String contact,String about_me,String avatar){
        this.userId=userId;
        this.name=name;
        this.location=location;
        this.sex=sex;
        this.contact=contact;
        this.about_me=about_me;
        this.avatar=avatar;
    }
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAbout_me() {
        return about_me;
    }

    public void setAbout_me(String about_me) {
        this.about_me = about_me;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}
