package cn.edu.jxnu.awesome_campus.model.jxnugo;

/**
 * Created by KevinWu on 16-5-20.
 */
public class UserCollectionListBean {


    public GoodsModel[] getCollectionPost() {
        return collectionPost;
    }

    public void setCollectionPost(GoodsModel[] collectionPost) {
        this.collectionPost = collectionPost;
    }

    private GoodsModel[] collectionPost;

}
