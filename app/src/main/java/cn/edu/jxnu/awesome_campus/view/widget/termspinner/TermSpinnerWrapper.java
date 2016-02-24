package cn.edu.jxnu.awesome_campus.view.widget.termspinner;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.List;

import cn.edu.jxnu.awesome_campus.support.utils.common.TermUtil;

/**
 * Created by MummyDing on 16-2-16.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class TermSpinnerWrapper {

    private OnTermChangedListener listener;

    private int index = 0;

    private MaterialSpinner mSpinner;

    private List<String> termList;
    public TermSpinnerWrapper() {
    }

    public MaterialSpinner build(MaterialSpinner spinner){

        mSpinner = spinner;
        updateAttachList();
        if(listener != null) {
            spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                    listener.onTermChanged(position,termList.get(position));
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
        if(mSpinner== null || termList == null){
            return;
        }
        // init data

        mSpinner.setItems(termList);
//        mSpinner.setBackgroundColor(ContextCompat.getColor(InitApp.AppContext,R.color.colorAccent));
        index = termList.size()-1;
        mSpinner.setSelectedIndex(index);
        listener.onTermChanged(index,termList.get(index));
    }
}
