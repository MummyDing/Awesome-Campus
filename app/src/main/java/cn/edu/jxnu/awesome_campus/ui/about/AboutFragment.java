package cn.edu.jxnu.awesome_campus.ui.about;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.api.VersoinApi;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.support.utils.common.DisplayUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.SystemUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.TextUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.TimeUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCallback;
import cn.edu.jxnu.awesome_campus.view.widget.dialog.AppUpdateDialog;

/**
 * Created by MummyDing on 16-4-18.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class AboutFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    private Preference mAppIntro;
    private Preference mDemoVideo;
    private Preference mFeedback;
    private Preference mCheckUpdate;
    private Preference mStarProject;
    private Preference mShare;
    private Preference mMummyBlog;
    private Preference mKevinBlog;
    private Preference mzpaulyGithub;
    private Preference mThereisnosponBlog;
    private Preference mCan2Github;
    private Preference mDdragon;
    private Preference license;

    private final String FEEDBACK=InitApp.AppContext.getString(R.string.id_feedback);
    private final String APP_INTRO = InitApp.AppContext.getString(R.string.id_app_intro);
    private final String DEMO_VIDEO = InitApp.AppContext.getString(R.string.id_demo_video);
    private final String CHECK_UPDATE = InitApp.AppContext.getString(R.string.id_check_update);
    private final String STAR_PROJECT = InitApp.AppContext.getString(R.string.id_star_project);
    private final String SHARE = InitApp.AppContext.getString(R.string.id_share);

    private final String MUMMY_BLOG = InitApp.AppContext.getString(R.string.id_mummy_blog);
    private final String KEVIN_BLOG = InitApp.AppContext.getString(R.string.id_kevin_blog);

    private final String zpauly_GITHUB = InitApp.AppContext.getString(R.string.id_zpauly_github);
    private final String Thereisnospon_BLOG = InitApp.AppContext.getString(R.string.id_thereisnospon_blog);
    private final String Can2_GITHUB=InitApp.AppContext.getString(R.string.id_can2studio_github);
    private final String Ddragon_BLOG =InitApp.AppContext.getString(R.string.id_ddragon_blog);
    private final String LICENSE = InitApp.AppContext.getString(R.string.id_license);

    private ProgressBar progressBar;

    private boolean isTouchUpdate = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.about);

        mAppIntro = findPreference(APP_INTRO);
        mDemoVideo = findPreference(DEMO_VIDEO);
        mCheckUpdate = findPreference(CHECK_UPDATE);
        mStarProject = findPreference(STAR_PROJECT);
        mShare = findPreference(SHARE);
        mFeedback=findPreference(FEEDBACK);
        mMummyBlog = findPreference(MUMMY_BLOG);

        mKevinBlog = findPreference(KEVIN_BLOG);
        mzpaulyGithub=findPreference(zpauly_GITHUB);
        mThereisnosponBlog=findPreference(Thereisnospon_BLOG);
        mCan2Github=findPreference(Can2_GITHUB);
        mDdragon=findPreference(Ddragon_BLOG);
        license = findPreference(LICENSE);

        mFeedback.setOnPreferenceClickListener(this);
        mAppIntro.setOnPreferenceClickListener(this);
        mDemoVideo.setOnPreferenceClickListener(this);
        mCheckUpdate.setOnPreferenceClickListener(this);
        mStarProject.setOnPreferenceClickListener(this);
        mShare.setOnPreferenceClickListener(this);

        mMummyBlog.setOnPreferenceClickListener(this);
        mKevinBlog.setOnPreferenceClickListener(this);

        mzpaulyGithub.setOnPreferenceClickListener(this);
        mThereisnosponBlog.setOnPreferenceClickListener(this);
        mCan2Github.setOnPreferenceClickListener(this);
        mDdragon.setOnPreferenceClickListener(this);
        license.setOnPreferenceClickListener(this);

        progressBar = (ProgressBar) getActivity().findViewById(R.id.progressbar);

        EventBus.getDefault().register(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if(mAppIntro == preference){
            Intent toIntro = new Intent(getActivity(),AppIntroActivity.class);
            startActivity(toIntro);
        }else if(mDemoVideo==preference){
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse("http://www.miaopai.com/show/Q-ekXL4V31-TkE8jIR14AQ__.htm");
            intent.setData(content_url);
            startActivity(intent);
        }else if(mFeedback == preference){
            Intent intent = new Intent();
            intent.setClass(InitApp.AppContext,FeedbackActivity.class);
            startActivity(intent);
        }
        else if(mCheckUpdate == preference){
            progressBar.setVisibility(View.VISIBLE);
            isTouchUpdate = true;
            SystemUtil.tryCheckUpdate();
        }else if(mStarProject == preference){
            TextUtil.copyToClipboard(getView(), getString(R.string.project_url));
        }else if(mShare == preference){
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.text_share_info));
            startActivity(Intent.createChooser(sharingIntent, getString(R.string.text_share_awesome_campus)));

        }else if(mMummyBlog == preference){
            TextUtil.copyToClipboard(getView(),getString(R.string.mummyding_blog));
        }
//        }
        else if(mKevinBlog == preference){
            TextUtil.copyToClipboard(getView(),getString(R.string.kevin_blog));
        }

        else if(mThereisnosponBlog == preference){
            TextUtil.copyToClipboard(getView(),getString(R.string.thereisnospon_blog));
        }
        else if(mzpaulyGithub == preference){
            TextUtil.copyToClipboard(getView(),getString(R.string.zpauly_github));
        }
        else if(mDdragon == preference){
            TextUtil.copyToClipboard(getView(),getString(R.string.ddragon_blog));
        }
        else if(mCan2Github == preference){
            TextUtil.copyToClipboard(getView(),getString(R.string.can2studio_github));
        }
        else if (license == preference){
            Intent toLicense = new Intent(getActivity(),LicenseActivity.class);
            startActivity(toLicense);
        }
        return false;
    }

    private Handler handler = new Handler(Looper.getMainLooper());
    private void hideLoading(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Subscribe
    public void onEventMainThread(EventModel eventModel){
        if(eventModel != null){
            switch (eventModel.getEventCode()){
                case EVENT.VERSION_CHECK_ALREADY_LATEST:
                    Snackbar.make(getView(), getString(R.string.notify_current_is_latest), Snackbar.LENGTH_SHORT).show();
                    hideLoading();
                    isTouchUpdate = false;
                    break;
                case EVENT.VERSION_CHECK_NEED_UPDATE:
                    showNewVersionDialog((String) eventModel.getData());
                    hideLoading();
                    isTouchUpdate = false;
                    break;
                case EVENT.VERSION_CHECK_TEST_VERSION:
                    Snackbar.make(getView(), getString(R.string.notify_find_in_test), Snackbar.LENGTH_SHORT).show();
                    hideLoading();
                    isTouchUpdate = false;
                    break;
                case EVENT.VERSION_CHECK_FAILURE:
                    Snackbar.make(getView(), R.string.hint_fail_check_update, Snackbar.LENGTH_SHORT).show();
                    hideLoading();
                    isTouchUpdate = false;
                    break;
            }
        }
    }

    private void showNewVersionDialog(final String newVersion){
        handler.post(new Runnable() {
            @Override
            public void run() {
                AppUpdateDialog appUpdateDialog = new AppUpdateDialog(getActivity(), newVersion);
                appUpdateDialog.show();
            }
        });
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
