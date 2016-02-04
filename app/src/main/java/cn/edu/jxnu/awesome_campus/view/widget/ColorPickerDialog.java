package cn.edu.jxnu.awesome_campus.view.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.support.utils.common.DisplyUtil;

/**
 * Created by MummyDing on 16-2-3.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class ColorPickerDialog {

    private Context mContext;
    private View view;
    private int [] colors;
    private int rowCount;
    private int colCount;
    private int width;
    private int defaultPadding;
    public ColorPickerDialog(Context context) {
        this.mContext = context;
        defaultPadding = DisplyUtil.dip2px(context,20);
    }

    public ColorPickerDialog(Context context , int [] colors){
        this(context);
        this.colors = colors;
    }

    public ColorPickerDialog build(){
        build(4);
        return this;
    }
    public ColorPickerDialog build(int widthCount){
        colCount = widthCount;
        rowCount = (colors.length-1)/widthCount + 1;
        LinearLayout [] linearLayouts = new LinearLayout[colors.length/widthCount +1];

        for(int i=0 ; i<linearLayouts.length ; i++){
            linearLayouts[i] = new LinearLayout(mContext);
            linearLayouts[i].setOrientation(LinearLayout.HORIZONTAL);
            linearLayouts[i].setPadding(defaultPadding*2,defaultPadding/2,0,defaultPadding/2);
        }
        for(int i=0 ; i<colors.length ; i++){
            ColorButton colorButton = new ColorButton(mContext,colors[i]);
            colorButton.setPadding(defaultPadding,defaultPadding,defaultPadding/2,0);
            linearLayouts[i/widthCount].addView(colorButton);
            width = colorButton.getMeasuredWidth() + defaultPadding;
        }

        LinearLayout parentLinearLayout = new LinearLayout(mContext);
        parentLinearLayout.setOrientation(LinearLayout.VERTICAL);

        for(int i=0 ; i<linearLayouts.length ; i++){
            parentLinearLayout.addView(linearLayouts[i]);
        }
        this.view = parentLinearLayout;
        return this;
    }

    public void show(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.theme);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.setIcon(R.mipmap.ic_theme_black);
        dialog.show();
        //dialog.getWindow().setLayout(width*rowCount,width*colCount);
        dialog.getWindow().setLayout(DisplyUtil.dip2px(mContext,90)*colCount,DisplyUtil.dip2px(mContext,100)+DisplyUtil.dip2px(mContext,80)*rowCount);
        Log.d("size width",width+" "+rowCount+" "+colCount);

    }

    public void setColors(int[] colors) {
        this.colors = colors;
    }
}
