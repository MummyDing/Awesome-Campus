package cn.edu.jxnu.awesome_campus.database.dao.library;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.squareup.okhttp.Headers;

import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.model.library.SelfStudySeatLeftModel;
import cn.edu.jxnu.awesome_campus.support.htmlparse.libary.SelfStudySeatLeftParse;
import cn.edu.jxnu.awesome_campus.support.spkey.LibraryStaticKey;
import cn.edu.jxnu.awesome_campus.support.spkey.SelfStudyRoomStaticKey;
import cn.edu.jxnu.awesome_campus.support.urlconfig.Urlconfig;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCallback;

/**
 * Created by KevinWu on 16-4-24.
 */
public class SelfStudySeatLeftDAO implements DAO<SelfStudySeatLeftModel> {
    private static final String TAG="SelfStudySeatLeftDAO";
    @Override
    public boolean cacheAll(List<SelfStudySeatLeftModel> list) {
        return false;
    }

    @Override
    public boolean clearCache() {
        return false;
    }

    @Override
    public void loadFromCache() {
        loadFromNet();
    }

    @Override
    public void loadFromNet() {
        Log.d(TAG,"联网拉去自习室数据");
        final Handler handler = new Handler(Looper.getMainLooper());
        SPUtil spu = new SPUtil(InitApp.AppContext);
        String cookies = spu.getStringSP(SelfStudyRoomStaticKey.SP_FILE_NAME, SelfStudyRoomStaticKey.COOKIE);
        NetManageUtil.get(Urlconfig.SelfStudyRoom_Seat_Left_URL)
                .addHeader("Cookie", cookies)
                .addTag(TAG)
                .enqueue(new StringCallback() {
                    @Override
                    public void onSuccess(String result, Headers headers) {
                        SelfStudySeatLeftParse parse=new SelfStudySeatLeftParse(result);
                    }

                    @Override
                    public void onFailure(String error) {

                    }
                });
    }
}
