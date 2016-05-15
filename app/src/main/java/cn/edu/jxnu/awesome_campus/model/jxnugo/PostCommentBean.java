package cn.edu.jxnu.awesome_campus.model.jxnugo;

/**
 * 发表评论时用于json数据构建
 * Created by KevinWu on 16-5-15.
 */
public class PostCommentBean {
    private String userId;
    private String postId;
    private String body;
    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }




    public PostCommentBean(String userId,String postId,String body){
        this.userId=userId;
        this.postId=postId;
        this.body=body;
    }


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }



}
