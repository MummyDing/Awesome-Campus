package cn.edu.jxnu.awesome_campus.support.utils.jxnugo;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.api.JxnuGoApi;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.CollectBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.CollectRTBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.CommentBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.CommentModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.CommentRTBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.PostCommentBean;
import cn.edu.jxnu.awesome_campus.support.spkey.JxnuGoStaticKey;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonCodeEntityCallback;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonEntityCallback;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCodeCallback;

/**
 * Created by KevinWu on 16-5-15.
 */
public class UploadCommentUtil {
    public static final String TAG = "UploadCommentUtil";

    public static void onUploadJson(PostCommentBean pbean) {
        final Handler handler = new Handler(Looper.getMainLooper());
        SPUtil spu = new SPUtil(InitApp.AppContext);
        String userName = spu.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.USERNAME);
        String password = spu.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.PASSWORD);
        NetManageUtil.postAuthJson(JxnuGoApi.CommentUrl)
                .addUserName(userName)
                .addPassword(password)
                .addTag(TAG)
                .addJsonObject(pbean)
                .enqueue(new JsonCodeEntityCallback<CommentRTBean>() {
                    @Override
                    public void onSuccess(CommentRTBean entity, int responseCode, Headers headers) {
                        if (responseCode == 200) {
                            Log.d(TAG, entity.getCommentStatus());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    EventBus.getDefault().post(new EventModel<CommentRTBean>(EVENT.POST_COMMENT_SUCCESS));
                                }
                            });
                        } else {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    EventBus.getDefault().post(new EventModel<CommentRTBean>(EVENT.POST_COMMENT_FAILURE));
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        Log.d(TAG,"错误信息"+error);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new EventModel<CommentRTBean>(EVENT.POST_COMMENT_FAILURE));
                            }
                        });
                    }
                });
//            .enqueue(new StringCodeCallback() {
//                @Override
//                public void onSuccess(String result, int responseCode, Headers headers) {
//                    Log.d(TAG,"-"+responseCode+" "+result);
//                }
//
//                @Override
//                public void onFailure(String error) {
//                    Log.d(TAG,"-"+error);
//                }
//            });
            /*已收藏*/
    }

    //获取网络数据
    public static void onDataRefresh(int postID) {
        if (postID != -1) {
            final Handler handler = new Handler(Looper.getMainLooper());
            NetManageUtil.get(JxnuGoApi.BaseCommentListUrl + postID)
                    .addTag(TAG)
                    .enqueue(new JsonCodeEntityCallback<CommentBean>() {
                        @Override
                        public void onSuccess(CommentBean entity, int responseCode, Headers headers) {
                            if (entity != null) {
                                Log.d("评论条数:", "--" + entity.getComments().length);
                                final List<CommentModel> list = Arrays.asList(entity.getComments());
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        EventBus.getDefault().post(new EventModel<CommentModel>(EVENT.GOODS_COMMENT_REFRESH_SUCCESS, list));
                                    }
                                });
                            } else {
                                EventBus.getDefault().post(new EventModel<CommentModel>(EVENT.GOODS_COMMENT_REFRESH_FAILURE));
                            }
                        }

                        @Override
                        public void onFailure(String error) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    EventBus.getDefault().post(new EventModel<CommentModel>(EVENT.GOODS_COMMENT_REFRESH_FAILURE));
                                }
                            });
                        }

                    });
//            .enqueue(new StringCodeCallback() {
//                @Override
//                public void onSuccess(String result, int responseCode, Headers headers) {
//                    Log.d(TAG,responseCode+"\\\\\\"+result);
//                }
//
//                @Override
//                public void onFailure(String error) {
//                    Log.d(TAG,error);
//                }
//            });
        }
    }
}
