package cn.edu.jxnu.awesome_campus.model.jxnugo;

/**
 * Created by KevinWu on 16-6-8.
 * KevinWu.cn
 */
public class DeletePostBean {
    private int userID;
    private int postID;
    private String auth_token;

    public DeletePostBean(int userID, int postID, String auth_token){
        this.userID=userID;
        this.postID=postID;
        this.auth_token=auth_token;
    }
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }


}
