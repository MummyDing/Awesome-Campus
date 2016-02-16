package cn.edu.jxnu.awesome_campus.view.widget.termspinner;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.database.spkey.TermStaticKey;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.TermUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.TextUtil;

/**
 * Created by MummyDing on 16-2-16.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class TermSpinnerWrapper {

    private OnTermChangedListener listener;

    private int index = 0;

    private NiceSpinner mSpinner;

    private List<String> termList;
    public TermSpinnerWrapper() {
    }

    public NiceSpinner build(NiceSpinner spinner){

        mSpinner = spinner;
        // init spinner
        spinner.setVisibility(View.VISIBLE);

        updateAttachList();
        if(listener != null) {
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(termList != null)
                    listener.onTermChanged(position,termList.get(position));
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

    public int getIndex() {
        return index;
    }

    public void updateAttachList(){
        termList = TermUtil.getTermList();
        if(mSpinner== null && termList == null){
            return;
        }
        // init data

        mSpinner.attachDataSource(termList);
        index = termList.size()-1;
        mSpinner.setSelectedIndex(index);
        listener.onTermChanged(index,termList.get(index));
    }
}
