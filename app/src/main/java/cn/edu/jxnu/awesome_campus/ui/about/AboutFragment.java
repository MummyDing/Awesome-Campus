package cn.edu.jxnu.awesome_campus.ui.about;

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
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.api.VersoinApi;
import cn.edu.jxnu.awesome_campus.support.utils.common.SystemUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.TextUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.TimeUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCallback;

/**
 * Created by MummyDing on 16-4-18.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class AboutFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {


    private Preference mAppIntro;
    private Preference mDemoVideo;
    private Preference mCheckUpdate;
    private Preference mStarProject;
    private Preference mShare;
    private Preference mMummyBlog;
    private Preference mMummyGitHub;
    private Preference mMummyEmail;
    private Preference mMummyApp;
    private Preference mKevinBlog;
    private Preference mKevinGitHub;
    private Preference mKevinEmail;
    private Preference mKevinApp;

    private final String APP_INTRO = InitApp.AppContext.getString(R.string.id_app_intro);
    private final String DEMO_VIDEO = InitApp.AppContext.getString(R.string.id_demo_video);
    private final String CHECK_UPDATE = InitApp.AppContext.getString(R.string.id_check_update);
    private final String STAR_PROJECT = InitApp.AppContext.getString(R.string.id_star_project);
    private final String SHARE = InitApp.AppContext.getString(R.string.id_share);
    private final String MUMMY_BLOG = InitApp.AppContext.getString(R.string.id_mummy_blog);
    private final String MUMMY_GITHUB = InitApp.AppContext.getString(R.string.id_github_mummy);
    private final String MUMMY_EMAIL = InitApp.AppContext.getString(R.string.id_mummy_email);
    private final String MUMMY_APP = InitApp.AppContext.getString(R.string.id_mummy_app);

    private final String KEVIN_BLOG = InitApp.AppContext.getString(R.string.id_kevin_blog);
    private final String KEVIN_GITHUB = InitApp.AppContext.getString(R.string.id_kevin_github);
    private final String KEVIN_EMAIL = InitApp.AppContext.getString(R.string.id_kevin_email);
    private final String KEVIN_APP = InitApp.AppContext.getString(R.string.id_kevin_app);

    private ProgressBar progressBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.about);

        mAppIntro = findPreference(APP_INTRO);
        mDemoVideo = findPreference(DEMO_VIDEO);
        mCheckUpdate = findPreference(CHECK_UPDATE);
        mStarProject = findPreference(STAR_PROJECT);
        mShare = findPreference(SHARE);

        mMummyBlog = findPreference(MUMMY_BLOG);
        mMummyGitHub = findPreference(MUMMY_GITHUB);
        mMummyEmail = findPreference(MUMMY_EMAIL);
        mMummyApp = findPreference(MUMMY_APP);

        mKevinBlog = findPreference(KEVIN_BLOG);
        mKevinGitHub = findPreference(KEVIN_GITHUB);
        mKevinEmail = findPreference(KEVIN_EMAIL);
        mKevinApp = findPreference(KEVIN_APP);

        mAppIntro.setOnPreferenceClickListener(this);
        mDemoVideo.setOnPreferenceClickListener(this);
        mCheckUpdate.setOnPreferenceClickListener(this);
        mStarProject.setOnPreferenceClickListener(this);
        mShare.setOnPreferenceClickListener(this);

        mMummyBlog.setOnPreferenceClickListener(this);
        mMummyGitHub.setOnPreferenceClickListener(this);
        mMummyEmail.setOnPreferenceClickListener(this);
        mMummyApp.setOnPreferenceClickListener(this);

        mKevinBlog.setOnPreferenceClickListener(this);
        mKevinGitHub.setOnPreferenceClickListener(this);
        mKevinEmail.setOnPreferenceClickListener(this);
        mKevinApp.setOnPreferenceClickListener(this);

        progressBar = (ProgressBar) getActivity().findViewById(R.id.progressbar);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if(mAppIntro == preference){
            Intent toIntro = new Intent(getActivity(),AppIntroActivity.class);
            startActivity(toIntro);
        }else if(mCheckUpdate == preference){
            progressBar.setVisibility(View.VISIBLE);

            NetManageUtil.get(VersoinApi.versionUrl)
                    .enqueue(new StringCallback() {
                        @Override
                        public void onSuccess(String result, Headers headers) {
                            if (SystemUtil.getVersionName().equals(result)) {
                                Snackbar.make(getView(), getString(R.string.notify_current_is_latest), Snackbar.LENGTH_SHORT).show();
                            } else {
                                Snackbar.make(getView(), getString(R.string.notify_find_new_version) + result, Snackbar.LENGTH_SHORT).show();
                            }
                            hideLoading();
                        }

                        @Override
                        public void onFailure(String error) {
                            Snackbar.make(getView(), R.string.hint_fail_check_update, Snackbar.LENGTH_SHORT).show();
                            hideLoading();
                        }
                    });

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
        }else if(mMummyGitHub == preference){
            TextUtil.copyToClipboard(getView(),getString(R.string.mummyding_github));
        }else if(mMummyEmail == preference){
            TextUtil.copyToClipboard(getView(),getString(R.string.mummy_email));
        }else if (mMummyApp == preference){
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse("http://coolapk.com/apk/com.mummyding.app.leisure");
            intent.setData(content_url);
            startActivity(intent);
        }else if(mKevinBlog == preference){
            TextUtil.copyToClipboard(getView(),getString(R.string.kevin_blog));
        }else if(mKevinGitHub == preference){
            TextUtil.copyToClipboard(getView(),getString(R.string.kevin_github));
        }else if(mKevinEmail == preference){
            TextUtil.copyToClipboard(getView(),getString(R.string.kevin_email));
        }else if (mKevinApp == preference){
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse("http://coolapk.com/apk/fsyt.ytweather");
            intent.setData(content_url);
            startActivity(intent);
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
}
