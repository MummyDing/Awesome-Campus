package cn.edu.jxnu.awesome_campus.view.widget.termspinner;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.database.spkey.TermStaticKey;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.TextUtil;

/**
 * Created by MummyDing on 16-2-16.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class TermSpinnerWrapper {

    private OnTermChangedListener listener;

    private int index = 0;

    public TermSpinnerWrapper() {
    }

    public NiceSpinner build(NiceSpinner spinner){
        // init spinner
        spinner.setVisibility(View.VISIBLE);
        
        // 获取学期
        SPUtil sp=new SPUtil(InitApp.AppContext);
        String terms = sp.getStringSP(TermStaticKey.SP_FILE_NAME,TermStaticKey.ALL_TERM_LIST);

        if(TextUtil.isNull(terms)){
            return null;
        }
        // init data
        List<String> termList = Arrays.asList(terms.split("@"));

        spinner.attachDataSource(termList);
        index = termList.size()-1;
        spinner.setSelectedIndex(index);
        listener.onTermChanged(index);
        if(listener != null) {
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    listener.onTermChanged(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        return spinner;
    }

    public void setOnTermChangedListener(OnTermChangedListener listener) {
        this.listener = listener;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
