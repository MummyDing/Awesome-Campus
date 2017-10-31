package cn.edu.jxnu.awesome_campus.model.jxnugo;

/**
 * Created by KevinWu on 16-6-8.
 * KevinWu.cn
 */
public class DeletePostBean {
    private int userId;
    private int postId;
    private String auth_token;

    public DeletePostBean(int userID, int postID, String auth_token){
        this.userId =userID;
        this.postId =postID;
        this.auth_token=auth_token;
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }


}
