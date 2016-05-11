package cn.edu.jxnu.awesome_campus.model.jxnugo;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.jxnugo.JxnugoUserDAO;
import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * Created by KevinWu on 16-5-11.
 */
public class JxnugoUserModel implements IModel<JxnugoUserModel> {

    private static final String TAG="JxnugoUserModel";
    private JxnugoUserDAO dao;


    private String about_me;
    private String last_seen;
    private String location;
    private String member_since;
    private String name;
    private int postCount;
    private int collectionPostCount;
    private String sex;
    private String userName;
    private String followed;
    private String followers;

    public JxnugoUserModel(){
        dao=new JxnugoUserDAO();
    }

    public JxnugoUserModel(String about_me,String last_seen,String location,String member_since,
                           String name,int postCount,int collectionPostCount,String sex,String userName,String followed,String followers){
        this.about_me=about_me;
        this.last_seen=last_seen;
        this.location=location;
        this.member_since=member_since;
        this.name=name;
        this.postCount=postCount;
        this.collectionPostCount=collectionPostCount;
        this.sex=sex;
        this.userName=userName;
        this.followed=followed;
        this.followers=followers;
    }
    @Override
    public boolean cacheAll(List<JxnugoUserModel> list) {
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

    public String getMember_since() {
        return member_since;
    }

    public void setMember_since(String member_since) {
        this.member_since = member_since;
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

    public int getCollectionPostCount() {
        return collectionPostCount;
    }

    public void setCollectionPostCount(int collectionPostCount) {
        this.collectionPostCount = collectionPostCount;
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
