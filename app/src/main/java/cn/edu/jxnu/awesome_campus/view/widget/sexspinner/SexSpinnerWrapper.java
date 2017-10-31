package cn.edu.jxnu.awesome_campus.view.widget.sexspinner;

import android.content.Context;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;

/**
 * Created by KevinWu on 16-5-18.
 */
public class SexSpinnerWrapper {
    public static final String TAG="SexSpinnerWrapper";
    private Context mContext = InitApp.AppContext;
    private OnSexChangedListener listener;
    private String sex;
    public MaterialSpinner build(MaterialSpinner spinner) {
        List<String> sexList =Arrays.asList(SexList.sex);
        spinner.setItems(sexList);
        spinner.setSelectedIndex(0);//
        if (listener != null) {
            final List<String> finalSexList = sexList;
            spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                @Override
                public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                    listener.onSexChanged(finalSexList.get(position));
                }
            });
        }
        return spinner;
    }
    public void setOnSexChangedListener(OnSexChangedListener listener){
        this.listener=listener;
    }
}
