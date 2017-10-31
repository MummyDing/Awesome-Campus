package cn.edu.jxnu.awesome_campus.model.jxnugo;

/**
 * Created by KevinWu on 16-5-15.
 */
public class CollectBean {
    private String userId;
    private String postId;

    public CollectBean(String userId,String postId){
        this.userId=userId;
        this.postId=postId;
    }
    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}
