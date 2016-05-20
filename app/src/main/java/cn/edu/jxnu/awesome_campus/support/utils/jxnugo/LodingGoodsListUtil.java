package cn.edu.jxnu.awesome_campus.support.utils.jxnugo;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.List;

import cn.edu.jxnu.awesome_campus.api.JxnuGoApi;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.GoodsListBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.UserCPListBean;
import cn.edu.jxnu.awesome_campus.support.spkey.JxnuGoStaticKey;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonCodeEntityCallback;

/**
 * Created by KevinWu on 16-5-20.
 */
public class LodingGoodsListUtil {
    public static final String TAG="LodingGoodsListUtil";
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
}
