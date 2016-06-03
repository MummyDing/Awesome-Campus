package cn.edu.jxnu.awesome_campus.ui.jxnugo;

import android.os.Bundle;

import com.tendcloud.tenddata.TCAgent;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.api.JxnuGoApi;
import cn.edu.jxnu.awesome_campus.ui.base.BaseWebViewActivity;

/**
 * Created by KevinWu on 16-6-3.
 * KevinWu.cn
 */
public class NoticeActivity extends BaseWebViewActivity {
    public static final String TAG="NoticeActivity";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TCAgent.onPageStart(InitApp.AppContext, TAG);

    }
    @Override
    protected String getLink() {
        return null;
    }

    @Override
    protected String getData() {
        return null;
    }

    @Override
    protected String getLinkData() {
        super.title="JxnuGo用户协议";
        return JxnuGoApi.NoticeUrl;
    }

    @Override
    protected String getLinkParseData() {
        return null;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        TCAgent.onPageEnd(InitApp.AppContext, TAG);
    }
}
