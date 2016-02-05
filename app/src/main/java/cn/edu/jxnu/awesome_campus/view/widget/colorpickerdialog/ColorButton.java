package cn.edu.jxnu.awesome_campus.view.widget.colorpickerdialog;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import cn.edu.jxnu.awesome_campus.support.utils.common.DisplayUtil;

/**
 * https://github.com/MummyDing/ColorPickerDialog
 * Created by MummyDing on 16-2-4.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class ColorButton extends View {

    private Context mContext;
    /**
     * color of the ColorButton,default RED.
     */
    private int mColor = Color.RED;


    /**
     * radius of the ColorButton
     */
    private int mRadius;


    private boolean isChecked = false;


    private Paint mPaint;
    /**
     * paddingHorizontal is relative to the "circle" inside ColorButton (Horizontal)
     */
    private int paddingHorizontal;

    /**
     *  paddingVertical is relative to the "circle" inside ColorButton (Vertical)
     */
    private int paddingVertical;

    /**
     * real width of ColorButton (including paddingHorizontal)
     * width = 2*(mRadius + paddingHorizontal)
     */
    private int width;

    /**
     * real height of ColorButton (including paddingVertical)
     * height = 2*(mRadius + paddingVertical)
     */
    private int height;


    /**
     * set size to defaultWidth if user has not specified
     * unit: dip
     */
    private int defaultWidth = 30;


    public ColorButton(Context context, int color) {
        super(context);
        mContext = context;
        mColor = color;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(5);
        defaultWidth = DisplayUtil.dip2px(mContext,defaultWidth);
    }

    public ColorButton(Context context) {
        this(context,Color.RED);
    }

    public ColorButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(5);
        defaultWidth = DisplayUtil.dip2px(mContext,defaultWidth);
    }

    public ColorButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(5);
        defaultWidth = DisplayUtil.dip2px(mContext,defaultWidth);
    }

    /**
     * ColorButton is not support self-define padding
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);


        if(widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(defaultWidth,defaultWidth);
        }else if(widthMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(defaultWidth,heightSize);
        }else if(heightMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSize,defaultWidth);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
        mRadius = Math.min(width,height)/2;
        paddingHorizontal = (width - mRadius*2)/2;
        paddingVertical = (height - mRadius*2)/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);
        if(isChecked()){
            drawCheck(canvas);
        }
    }

    private void drawCircle(Canvas canvas){
        mPaint.setColor(mColor);
        canvas.drawCircle(paddingHorizontal + width/2,paddingVertical + height/2,mRadius,mPaint);
    }
    private void drawCheck(Canvas canvas){
        if(mColor == Color.WHITE){
            mPaint.setColor(Color.BLACK);
        }else {
            mPaint.setColor(Color.WHITE);
        }

        Point p1 = new Point(paddingHorizontal + (int)(0.3 * (double) mRadius),paddingVertical + mRadius);
        Point p2 = new Point(paddingHorizontal + (int)(0.8 * (double) mRadius),paddingVertical + (int)(1.4 * (double) mRadius));
        Point p3 = new Point(paddingHorizontal + (int)(1.4 * (double)mRadius),paddingVertical +(int)(0.4 * (double) mRadius));

        canvas.drawLine(p1.x,p1.y,p2.x,p2.y,mPaint);
        canvas.drawLine(p2.x,p2.y,p3.x,p3.y,mPaint);
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
        invalidate();
    }


    public int getmColor() {
        return mColor;
    }
}
