package cn.edu.jxnu.awesome_campus.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by MummyDing on 16-2-3.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class ColorButton extends View {


    private int mRadius = 40;
    private int mColor = Color.RED;
    private boolean isChecked = false;


    private int paddingLeft;
    private int paddingRight;
    private int paddingTop;
    private int paddingBottom;
    private int width;
    private int height;
    private int defaultWidth = 80;

    private int count = 0;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public ColorButton(Context context,int color) {
        super(context);
        this.mColor = color;
    }

    public ColorButton(Context context) {
        super(context);
    }

    public ColorButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


       // Log.d("size: Times","this is "+ count++ +"times");
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSpec = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpec = MeasureSpec.getSize(heightMeasureSpec);

        if(widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(defaultWidth,defaultWidth);
        }else if(widthSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(defaultWidth,heightSpec);
        }else if(heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSpec,defaultWidth);
        }


    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        paddingLeft = getPaddingLeft();
        paddingRight = getPaddingRight();
        paddingTop = getPaddingTop();
        paddingBottom = getPaddingBottom();
        width = getWidth() - paddingLeft - paddingRight;
        height = getHeight() - paddingTop - paddingBottom;
        int halfWidth = Math.min(width,height)/2;
        mRadius = halfWidth <= 0 ? mRadius : halfWidth;
        paddingTop = (height - 2*mRadius)/2;
        paddingLeft = (width - 2*mRadius)/2;
        mPaint.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawCircle(canvas);
        if(isChecked) {
            drawCheck(canvas);
        }
    }


    private void drawCircle(Canvas canvas){
        mPaint.setColor(mColor);
        canvas.drawCircle(width/2,height/2,mRadius,mPaint);
    }

    private void drawCheck(Canvas canvas){
        if(mColor == Color.WHITE){
            mPaint.setColor(Color.BLACK);
        }else {
            mPaint.setColor(Color.WHITE);
        }

        Point p1 = new Point(paddingLeft + (int)(0.3 * (double) mRadius),paddingTop + mRadius);
        Point p2 = new Point(paddingLeft + (int)(0.8 * (double) mRadius),paddingTop + (int)(1.4 * (double) mRadius));
        Point p3 = new Point(paddingLeft + (int)(1.4 * (double)mRadius),paddingTop +(int)(0.4 * (double) mRadius));

        canvas.drawLine(p1.x,p1.y,p2.x,p2.y,mPaint);
        canvas.drawLine(p2.x,p2.y,p3.x,p3.y,mPaint);
    }


    public int getmRadius() {
        return mRadius;
    }

    public void setmRadius(int mRadius) {
        this.mRadius = mRadius;
        mPaint.setStrokeWidth(5);
        invalidate();
    }

    public int getmColor() {
        return mColor;
    }

    public void setmColor(int mColor) {
        this.mColor = mColor;
        invalidate();
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
        invalidate();
    }
}
