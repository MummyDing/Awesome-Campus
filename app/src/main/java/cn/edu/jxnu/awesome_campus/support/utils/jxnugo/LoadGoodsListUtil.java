package cn.edu.jxnu.awesome_campus.support.utils.jxnugo;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.List;

import cn.edu.jxnu.awesome_campus.api.JxnuGoApi;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.DeletePostBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.GoodsListBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.SearchKeyBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.UserCPListBean;
import cn.edu.jxnu.awesome_campus.support.Settings;
import cn.edu.jxnu.awesome_campus.support.spkey.JxnuGoStaticKey;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonCodeEntityCallback;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCodeCallback;

/**
 * Created by KevinWu on 16-5-20.
 */
public class LoadGoodsListUtil {
    public static final String TAG="LoadGoodsListUtil";
    private static String getUserName(Context context){
        SPUtil sp=new SPUtil(context);
        return sp.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.USERNAME);
    }
    private static String getPassword(Context context){
        SPUtil sp=new SPUtil(context);
        return sp.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.PASSWORD);
    }

    /**
     * 获取收藏商品列表
     * @param context
     * @param userId
     */
    public static void getCGoodsList(Context context, int userId){
        final Handler handler = new Handler(Looper.getMainLooper());
        try {
            NetManageUtil.getAuth(JxnuGoApi.BaseUserCollectionPostUrl+userId)
                    .addUserName(getUserName(context))
                    .addPassword(getPassword(context))
                    .addTag(TAG)
                    .enqueue(new JsonCodeEntityCallback<UserCPListBean>() {
                        @Override
                        public void onSuccess(UserCPListBean entity, int responseCode, Headers headers) {
                            if (entity != null) {
                                final List<UserCPListBean> list = Arrays.asList(entity);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        EventBus.getDefault().post(new EventModel<UserCPListBean>(EVENT.USERCOLLECT_REFRESH_SUCCESS, list));

                                    }
                                });
                            } else {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        EventBus.getDefault().post(new EventModel<UserCPListBean>(EVENT.USERCOLLECT_REFRESH_FAILURE));
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(String error) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    EventBus.getDefault().post(new EventModel<GoodsListBean>(EVENT.USERCOLLECT_REFRESH_FAILURE));
                                }
                            });
                        }
                    });
        } catch (IllegalStateException e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(new EventModel<GoodsListBean>(EVENT.USERCOLLECT_REFRESH_FAILURE));
                }
            });
        }
    }
    /**
     * 获取发布商品列表
     * @param context
     * @param userId
     */
    public static void getPGoodsList(Context context, int userId){
        final Handler handler = new Handler(Looper.getMainLooper());
        try {
            NetManageUtil.getAuth(JxnuGoApi.BaseUserPostUrl+userId)
                    .addUserName(getUserName(context))
                    .addPassword(getPassword(context))
                    .addTag(TAG)
                    .enqueue(new JsonCodeEntityCallback<UserCPListBean>() {
                        @Override
                        public void onSuccess(UserCPListBean entity, int responseCode, Headers headers) {
                            if (entity != null) {
                                final List<UserCPListBean> list = Arrays.asList(entity);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        EventBus.getDefault().post(new EventModel<UserCPListBean>(EVENT.USERPOST_REFRESH_SUCCESS, list));

                                    }
                                });
                            } else {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        EventBus.getDefault().post(new EventModel<UserCPListBean>(EVENT.USERPOST_REFRESH_FAILURE));
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(String error) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    EventBus.getDefault().post(new EventModel<GoodsListBean>(EVENT.USERPOST_REFRESH_FAILURE));
                                }
                            });
                        }
                    });
        } catch (IllegalStateException e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(new EventModel<GoodsListBean>(EVENT.USERPOST_REFRESH_FAILURE));
                }
            });
        }
    }

    /**
     * 根据标签分裂返回商品信息
     * @param context
     * @param tagID
     */
    public static void getTagGoodsList(Context context,int tagID){
        final Handler handler = new Handler(Looper.getMainLooper());
        try {
            NetManageUtil.getAuth(JxnuGoApi.BaseTagGoodsUrl+tagID)
                    .addUserName(getUserName(context))
                    .addPassword(getPassword(context))
                    .addTag(TAG)
                    .enqueue(new JsonCodeEntityCallback<GoodsListBean>() {
                        @Override
                        public void onSuccess(GoodsListBean entity, int responseCode, Headers headers) {
                            if (entity != null) {
                                final List<GoodsListBean> list = Arrays.asList(entity);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        EventBus.getDefault().post(new EventModel<GoodsListBean>(EVENT.JXNUGO_TAG_GOODS_LIST_REFRESH_SUCCESS, list));

                                    }
                                });
                            } else {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        EventBus.getDefault().post(new EventModel<GoodsListBean>(EVENT.JXNUGO_TAG_GOODS_LIST_REFRESH_FAILURE));
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(String error) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    EventBus.getDefault().post(new EventModel<GoodsListBean>(EVENT.JXNUGO_TAG_GOODS_LIST_REFRESH_FAILURE));
                                }
                            });
                        }
                    });
        } catch (IllegalStateException e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(new EventModel<GoodsListBean>(EVENT.JXNUGO_TAG_GOODS_LIST_REFRESH_FAILURE));
                }
            });
        }
    }


    /**
     * 根据可以返回搜索结果
     * @param context
     * @param key
     */
     public static void searchByKey(Context context,String key){
         final Handler handler = new Handler(Looper.getMainLooper());
         SearchKeyBean bean=new SearchKeyBean(key);
         NetManageUtil.postAuthJson(JxnuGoApi.BaseSearchUrl)
                 .addUserName(getUserName(context))
                 .addPassword(getPassword(context))
                 .addJsonObject(bean)
                 .addTag(TAG)
                 .enqueue(new JsonCodeEntityCallback<GoodsListBean>() {
                     @Override
                     public void onSuccess(GoodsListBean entity, int responseCode, Headers headers) {
                         if(responseCode==200&&entity!=null){
                             final List<GoodsListBean> list = Arrays.asList(entity);
                             handler.post(new Runnable() {
                                 @Override
                                 public void run() {
                                     EventBus.getDefault().post(new EventModel<GoodsListBean>(EVENT.JXNUGO_SEARCH_GOODS_SUCCESS,list));
                                 }
                             });
                         }else{
                             handler.post(new Runnable() {
                                 @Override
                                 public void run() {
                                     EventBus.getDefault().post(new EventModel<Void>(EVENT.JXNUGO_SEARCH_GOODS_FAILURE));
                                 }
                             });
                         }
                     }

                     @Override
                     public void onFailure(String error) {
                         handler.post(new Runnable() {
                             @Override
                             public void run() {
                                 EventBus.getDefault().post(new EventModel<Void>(EVENT.JXNUGO_SEARCH_GOODS_FAILURE));
                             }
                         });
                     }
                 });
//         .enqueue(new StringCodeCallback() {
//             @Override
//             public void onSuccess(String result, int responseCode, Headers headers) {
//                 Log.d(TAG,"状态码为："+responseCode);
//                 Log.d(TAG,"内容为："+result);
//             }
//
//             @Override
//             public void onFailure(String error) {
//
//             }
//         });
     }

    public static void deletePost(Context context,int userID,int postID){
        final Handler handler = new Handler(Looper.getMainLooper());
        DeletePostBean bean=new DeletePostBean(userID,postID, Settings.getJxnugoAuthToken());
        NetManageUtil.postAuthJson(JxnuGoApi.DeletePostUrl)
                .addUserName(getUserName(context))
                .addPassword(getPassword(context))
                .addJsonObject(bean)
                .addTag(TAG)
         .enqueue(new StringCodeCallback() {
             @Override
             public void onSuccess(String result, int responseCode, Headers headers) {
                 Log.d(TAG,"状态码为："+responseCode);
                 Log.d(TAG,"内容为："+result);
                 if(responseCode==200){
                     /**
                      * 成功删除
                      */
                 handler.post(new Runnable() {
                     @Override
                     public void run() {
                         EventBus.getDefault().post(new EventModel<Void>(EVENT.JXNUGO_DELETE_POST_SUCCESS));
                     }
                 });
                 }else{
                     handler.post(new Runnable() {
                         @Override
                         public void run() {
                             EventBus.getDefault().post(new EventModel<Void>(EVENT.JXNUGO_DELETE_POST_FAILURE));

                         }
                     });
                 }
             }

             @Override
             public void onFailure(String error) {
                 handler.post(new Runnable() {
                     @Override
                     public void run() {
                         EventBus.getDefault().post(new EventModel<Void>(EVENT.JXNUGO_DELETE_POST_FAILURE));
                     }
                 });
             }
         });
    }
}
