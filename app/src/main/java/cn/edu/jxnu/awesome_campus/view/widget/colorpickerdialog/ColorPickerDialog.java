package cn.edu.jxnu.awesome_campus.view.widget.colorpickerdialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import java.util.LinkedList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.support.utils.common.DisplayUtil;

/**
 * https://github.com/MummyDing/ColorPickerDialog
 * Created by MummyDing on 16-2-5.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class ColorPickerDialog implements View.OnClickListener{


    private int rowCount;

    private int colCount;

    private int buttonCount;
    /**
    * unit: dip
    */
    private int defaultPadding  = 20;
    /**
     * set of ColorButton's color
     */
    private int [] mColors;

    /**
     * set of ColorButton
     */
    private List<ColorButton> colorButtonList = new LinkedList<>();

    /**
     * current checked ColorButton
     */
    private ColorButton currentButton;

    private AlertDialog dialog;

    /**
     * Dialog Title,default "Theme"
     */
    private String title = "Theme";

    private LinearLayout rootLayout;

    private Context mContext;

    private OnColorChangedListener listener;




    public ColorPickerDialog(Context context ,int[] colors) {
        this(context,colors,colors[0]);
    }

    public ColorPickerDialog(Context context,  int[] colors, int checkedColor) {
        if(context == null || colors == null || colors.length ==0 ){
            throw new IllegalArgumentException("args must not be null");
        }
        this.mContext = context;
        this.mColors = colors;
        buttonCount = colors.length;
        defaultPadding = DisplayUtil.dip2px(mContext,defaultPadding);
        setCheckedColor(checkedColor);
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            ColorButton colorButton = (ColorButton) v;
            if(colorButton.isChecked() == false){
                if(currentButton != null){
                    currentButton.setChecked(false);
                }
                colorButton.setChecked(true);
                listener.onColorChanged(colorButton.getmColor());
                currentButton = colorButton;
                if(dialog != null){
                    dialog.dismiss();
                }
            }
        }
    }


    public ColorPickerDialog build(){
        return build(4);
    }
    public ColorPickerDialog build(int widthCount){
        if(widthCount <=2 && widthCount<=6){
            throw new IllegalArgumentException("widthCount must between 3 and 6 !!!");
        }
        colCount = widthCount;

        rowCount = (buttonCount -1)/widthCount + 1;

        /**
         * create root Layout
         */
        rootLayout = new LinearLayout(mContext);
        rootLayout.setOrientation(LinearLayout.VERTICAL);

        /**
         * create layout of each row & init it
         */
        LinearLayout [] linearLayouts = new LinearLayout[rowCount];

        for(int i= 0 ; i<rowCount ; i++){
            linearLayouts[i] = new LinearLayout(mContext);
            linearLayouts[i].setOrientation(LinearLayout.HORIZONTAL);
            linearLayouts[i].setPadding(defaultPadding,defaultPadding/2,0,defaultPadding/2);
            rootLayout.addView(linearLayouts[i]);
        }

        /**
         * add colorButtons to layout
         */
        for(int i=0 ; i<buttonCount ; i++){
            ColorButton colorButton = new ColorButton(mContext,mColors[i]);
            colorButton.setOnClickListener(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lp.setMargins(0,0,defaultPadding,0);
            linearLayouts[i/widthCount].addView(colorButton,lp);
            colorButtonList.add(colorButton);
        }

        return this;
    }


    public void show(){
        if(rootLayout == null){
            throw new UnsupportedOperationException("you must call build before show");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        dialog = builder.create();
        dialog.setTitle(getTitle());
        dialog.setView(rootLayout);
        dialog.show();
        dialog.getWindow().setLayout((DisplayUtil.dip2px(mContext,30)+defaultPadding)*colCount+defaultPadding*3,DisplayUtil.dip2px(mContext,100+30*rowCount)+defaultPadding*rowCount);
    }

    public ColorPickerDialog setCheckedColor(int color){

        if(currentButton != null && color == currentButton.getmColor()){
            return this;
        }
        for(ColorButton colorButton: colorButtonList){
            if(color == colorButton.getmColor()){
                if(currentButton != null){
                    currentButton.setChecked(false);
                }
                colorButton.setChecked(true);
                listener.onColorChanged(colorButton.getmColor());
                currentButton = colorButton;
            }
        }
        return this;
    }

    public ColorPickerDialog setOnColorChangedListener(OnColorChangedListener listener) {
        this.listener = listener;
        return this;
    }

    public ColorPickerDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener listener){
        dialog.setOnDismissListener(listener);
    }
}
