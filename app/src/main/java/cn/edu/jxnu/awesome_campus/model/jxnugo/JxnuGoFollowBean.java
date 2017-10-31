package cn.edu.jxnu.awesome_campus.model.jxnugo;

/**
 * Created by yzr on 16/5/16.
 */
public class JxnuGoFollowBean {

    private int userId;
    private int followedId;
    public JxnuGoFollowBean(int userId,int followedId){
        this.userId=userId;
        this.followedId=followedId;
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFollowedId() {
        return followedId;
    }

    public void setFollowedId(int followedId) {
        this.followedId = followedId;
    }
}
