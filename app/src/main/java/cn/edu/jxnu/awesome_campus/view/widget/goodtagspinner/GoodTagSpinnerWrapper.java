package cn.edu.jxnu.awesome_campus.view.widget.goodtagspinner;

import android.content.Context;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;

/**
 * Created by KevinWu on 16-5-14.
 */
public class GoodTagSpinnerWrapper {
    private Context mContext = InitApp.AppContext;
    private OnGoodTagChangedLister listener;
    private int index;

    public MaterialSpinner build(MaterialSpinner spinner) {
        List<String> tagList = new ArrayList<>();
        tagList = Arrays.asList(GoodTagList.tag);
        spinner.setItems(tagList);
//        lister.onTagChanged();
        spinner.setSelectedIndex(4);//
        if (listener != null) {
            spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                @Override
                public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                    listener.onTagChanged(position);
                }
            });
        }
        return spinner;
    }

    public void setOnTagChangeedListener(OnGoodTagChangedLister listener) {
        this.listener = listener;

    }

    public void setIndex(int index) {
        this.index = index;
    }
}
