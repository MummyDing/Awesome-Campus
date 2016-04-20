package cn.edu.jxnu.awesome_campus.ui.about;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.widget.ProgressBar;

import cn.edu.jxnu.awesome_campus.R;

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

    private final String APP_INTRO = getString(R.string.id_app_intro);
    private final String DEMO_VIDEO = getString(R.string.id_demo_video);
    private final String CHECK_UPDATE = getString(R.string.id_check_update);
    private final String STAR_PROJECT = getString(R.string.id_star_project);
    private final String SHARE = getString(R.string.id_share);
    private final String MUMMY_BLOG = getString(R.string.id_mummy_blog);
    private final String MUMMY_GITHUB = getString(R.string.id_github_mummy);
    private final String MUMMY_EMAIL = getString(R.string.id_mummy_email);

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

        mAppIntro.setOnPreferenceClickListener(this);
        mDemoVideo.setOnPreferenceClickListener(this);
        mCheckUpdate.setOnPreferenceClickListener(this);
        mStarProject.setOnPreferenceClickListener(this);
        mShare.setOnPreferenceClickListener(this);

        mMummyBlog.setOnPreferenceClickListener(this);
        mMummyGitHub.setOnPreferenceClickListener(this);
        mMummyEmail.setOnPreferenceClickListener(this);

        progressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        return false;
    }
}
