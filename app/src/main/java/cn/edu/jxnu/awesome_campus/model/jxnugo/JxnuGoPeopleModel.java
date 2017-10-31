package cn.edu.jxnu.awesome_campus.model.jxnugo;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.jxnugo.JxnuGoPeopleDao;
import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * Created by yzr on 16/5/14.
 */
public class JxnuGoPeopleModel implements IModel<JxnuGoPeopleModel> {

    private String aboutMe;
    private String urlLinkUser;
    private String userAvatar;
    private String userId;
    private String userName;

    private JxnuGoPeopleDao dao;


    public JxnuGoPeopleModel(){
        dao=new JxnuGoPeopleDao();
    }
    public JxnuGoPeopleModel(String aboutMe,String urlLinkUser,String userAvatar,String userId,String userName){
        this.aboutMe=aboutMe;
        this.urlLinkUser=urlLinkUser;
        this.userAvatar=userAvatar;
        this.userId=userId;
        this.userName=userName;
    }
    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getUrlLinkUser() {
        return urlLinkUser;
    }

    public void setUrlLinkUser(String urlLinkUser) {
        this.urlLinkUser = urlLinkUser;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean cacheAll(List<JxnuGoPeopleModel> list) {
        return  dao.cacheAll(list);
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
