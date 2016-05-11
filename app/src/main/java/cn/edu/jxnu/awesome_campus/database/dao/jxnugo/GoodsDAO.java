package cn.edu.jxnu.awesome_campus.database.dao.jxnugo;

import android.os.Handler;
import android.os.Looper;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.model.jxnugo.GoodsModel;

/**
 * Created by KevinWu on 16-5-11.
 */
public class GoodsDAO implements DAO<GoodsModel> {
    private static final String TAG="GoodsDAO";

    @Override
    public boolean cacheAll(List<GoodsModel> list) {
        return false;
    }

    @Override
    public boolean clearCache() {
        return false;
    }

    @Override
    public void loadFromCache() {

    }

    @Override
    public void loadFromNet() {
        final Handler handler = new Handler(Looper.getMainLooper());

    }
}
