package cn.edu.jxnu.awesome_campus.model.jxnugo;

/**
 * 用户收藏和发布的帖子
 * collect&post
 * Created by KevinWu on 16-5-20.
 */
public class UserCPListBean {

    public GoodsModel[] getUserPosts() {
        return userPosts;
    }

    public void setUserPosts(GoodsModel[] userPosts) {
        this.userPosts = userPosts;
    }

    private GoodsModel [] userPosts;
    public GoodsModel[] getCollectionPost() {
        return collectionPost;
    }

    public void setCollectionPost(GoodsModel[] collectionPost) {
        this.collectionPost = collectionPost;
    }

    private GoodsModel[] collectionPost;

}
