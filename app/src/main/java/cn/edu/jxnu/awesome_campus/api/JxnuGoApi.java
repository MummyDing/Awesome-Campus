package cn.edu.jxnu.awesome_campus.api;

/**
 * Created by KevinWu on 16-5-11.
 */
public class JxnuGoApi {
    public static final String LoginUrl="http://www.jxnugo.com/api/get_token";
    public static final String RegisterUrl="http://www.jxnugo.com/apiv1.1/register";
    public static final String QiniuToken="http://www.jxnugo.com/api/get_mobile_token";//七牛图片token
    public static final String AllPostUrl="http://www.jxnugo.com/api/posts";//所有帖子
    public static final String PublishNewPostUrl="http://www.jxnugo.com/apiv1.1/new_post";//发表新的帖子
    public static final String BaseOnePostUrl="http://www.jxnugo.com/api/posts/";//获取一篇基础url
    public static final String BaseUserPostUrl="http://www.jxnugo.com/api/user_posts/";//获取一个用户基础url
    public static final String BaseUserCollectionPostUrl="http://www.jxnugo.com/api/user_collectionpost/";//获取一个用户基础url
    public static final String BaseCommentListUrl="http://www.jxnugo.com/api/comments/";//基础评论url
    public static final String UserFollowers="http://www.jxnugo.com/api/user_followers/";//用户粉丝url
    public static final String UserFollowerd="http://www.jxnugo.com/api/user_followed/";//用户关注url
    public static final String BaseCollectUrl="http://www.jxnugo.com/api/collect";//收藏帖子的URL
    public static final String BaseUnCollectUrl="http://www.jxnugo.com/api/uncollect";//取消收藏帖子的URL
    public static final String CommentUrl="http://www.jxnugo.com/apiv1.1/new_comment";
    public static final String BaseUserUrl="http://www.jxnugo.com/api/user/";
    public static final String FollowUrl="http://www.jxnugo.com/api/follow";
    public static final String UnfollowUrl="http://www.jxnugo.com/api/unfollow";
    public static final String UpdateUserInfo="http://www.jxnugo.com/apiv1.1/update_userInfo";//更新个人信息
    public static final String BaseTradeURL="http://www.jxnugo.com/trade_detail/";//分享帖子时的基础url
    public static final String JudgeCollect="http://www.jxnugo.com/api/judge_collect";//判断用户是否已收藏
    public static final String JudgeFollow="http://www.jxnugo.com/api/judge_follow";//判断是否关注某用户的url
    public static final String BasePicUrl="http://7xrkww.com1.z0.glb.clouddn.com/";//图片基础url
    public static final String BaseTagGoodsUrl="http://www.jxnugo.com/api/post_category/";//根据物品标签查询相关的帖子
    public static final String BaseSearchUrl="http://www.jxnugo.com/api/query_post";
    public static final String NoticeUrl="http://www.jxnugo.com/api/get_notice";//免责声明
    public static final String DeletePostUrl="http://www.jxnugo.com/apiv1.1/post_delete";//删除帖子url
}
