package cn.edu.jxnu.awesome_campus.model.jxnugo;

/**
 * Created by KevinWu on 16-5-20.
 */
public class FollowerJudgeBean {
    private int userId;

    public int getFollowerId() {
        return followerId;
    }

    public void setFollowerId(int followerId) {
        this.followerId = followerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private int followerId;
    public FollowerJudgeBean(int userId,int followerId){
        this.userId=userId;
        this.followerId=followerId;
    }
}
