package cn.edu.jxnu.awesome_campus.ui.settings;

import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.presenter.home.HomePresenterImpl;
import cn.edu.jxnu.awesome_campus.support.Settings;
import cn.edu.jxnu.awesome_campus.support.utils.common.SettingsUtil;

/**
 * Created by MummyDing on 16-4-17.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {

    private Settings mSettings;

    private Preference mLanguage;
    private Preference mAvatar;
    private CheckBoxPreference mAutoRefresh;
    private Preference mSwipeBack;
    private CheckBoxPreference mExitConfirm;
    private Preference mClearCache;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        mSettings = Settings.getsInstance();

        mLanguage = findPreference(Settings.LANGUAGE);
        mAvatar = findPreference(Settings.AVATAR);

        mAutoRefresh = (CheckBoxPreference) findPreference(Settings.AUTO_REFRESH);
        mSwipeBack = findPreference(Settings.SWIPE_BACK);

        mExitConfirm = (CheckBoxPreference) findPreference(Settings.EXIT_CONFIRM);
        mClearCache = findPreference(Settings.CLEAR_CACHE);

        mLanguage.setSummary(this.getResources().getStringArray(R.array.langs)[SettingsUtil.getCurrentLanguage()]);

        mAvatar.setSummary(this.getResources().getStringArray(R.array.avatars)[Settings.avatorID]);

        mSwipeBack.setSummary(this.getResources().getStringArray(R.array.swipe_back)[Settings.swipeID]);





        mAutoRefresh.setChecked(Settings.autoRefresh);
        mExitConfirm.setChecked(Settings.isExitConfirm);
        mAutoRefresh.setOnPreferenceChangeListener(this);
        mExitConfirm.setOnPreferenceChangeListener(this);





        mLanguage.setOnPreferenceClickListener(this);
        mAvatar.setOnPreferenceClickListener(this);
        mSwipeBack.setOnPreferenceClickListener(this);
        mClearCache.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if(preference == mExitConfirm){
            Settings.isExitConfirm = Boolean.valueOf(newValue.toString());
            mSettings.putBoolean(Settings.EXIT_CONFIRM, Settings.isExitConfirm);
            return true;
        }else if (preference == mAutoRefresh){
            Settings.autoRefresh = Boolean.valueOf(newValue.toString());
            mSettings.putBoolean(Settings.AUTO_REFRESH,Settings.autoRefresh);
            return true;
        }
        return false;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if(preference == mLanguage){
            showLangDialog();


        }else if (preference == mAvatar){
            showAvatarSettingsDialog();
        }else if(preference == mClearCache){



            SettingsUtil.clearCache();
            Settings.needRecreate = true;
            Snackbar.make(getView(), R.string.text_clear_cache_successful,Snackbar.LENGTH_SHORT).show();
        }else if(preference == mSwipeBack){
            showSwipeSettingsDialog();
        }
        return false;
    }


    private void showLangDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.text_language))
                .setSingleChoiceItems(
                        getResources().getStringArray(R.array.langs), SettingsUtil.getCurrentLanguage(),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("lang",which+"");
                                if (which != SettingsUtil.getCurrentLanguage()) {
                                    Log.d("lang",which+"");
                                    mSettings.putInt(Settings.LANGUAGE, which);
                                    Settings.needRecreate = true;
                                }
                                dialog.dismiss();
                                if (Settings.needRecreate) {
                                    getActivity().recreate();
                                }
                            }
                        }
                ).show();

    }

    private void showSwipeSettingsDialog(){
        new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.text_swipe_to_return))
                .setSingleChoiceItems(
                        getResources().getStringArray(R.array.swipe_back), Settings.swipeID,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if(which != Settings.swipeID){
                                    Settings.swipeID = which;
                                    mSettings.putInt(Settings.SWIPE_BACK,which);
                                    mSwipeBack.setSummary(getResources().getStringArray(R.array.swipe_back)[Settings.swipeID]);
                                    getActivity().recreate();
                                }
                            }
                        }

                ).show();
    }
    private void showAvatarSettingsDialog(){
        new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.avatar))
                .setSingleChoiceItems(
                        getResources().getStringArray(R.array.avatars), Settings.avatorID,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if(which != Settings.avatorID){
                                    Settings.avatorID = which;
                                    mSettings.putInt(Settings.AVATAR,which);
                                    mAvatar.setSummary(getResources().getStringArray(R.array.avatars)[Settings.avatorID]);
                                    Settings.needUpdateAvatar = true;
                                }
                            }
                        }

                ).show();
    }
}


