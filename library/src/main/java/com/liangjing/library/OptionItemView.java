package com.liangjing.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * function:通过自定义view来实现常用的选项条目布局控件(比如个人信息界面的选项条目--微信个人信息界面)
 * --几乎每个item条目往往都会由一些子控件结合来实现，所以通过该自定义view可以省去布局文件中的大量代码编写。
 * --该自定义view的关键是控制好绘制范围和间距的合理分配以及计算其触发点击事件的范围。
 */
public class OptionItemView extends View {

    /**
     * 控件的宽(由系统属性android:layout_width所设置)
     */
    private int mWidth;
    /**
     * 控件的高(由系统属性android:layout_height所设置)
     */
    private int mHeight;

    private Context mContext;

    /**
     * 左图bitmap
     */
    private Bitmap leftImage;

    /**
     * 右图bitmap
     */
    private Bitmap rightImage;

    private boolean isShowLeftImg = true;
    private boolean isShowLeftText = true;
    private boolean isShowRightImg = true;
    private boolean isShowRightText = true;

    //拆分模式(默认是false，也就是一个整体)--拆分模式下点击事件分为多个,整体模式下点击事件为一个。
    private boolean mSpliteMode = false;
    /**
     * 判断按下开始的位置是否在左
     */
    private boolean leftStartTouchDown = false;
    /**
     * 判断按下开始的位置是否在中间
     */
    private boolean centerStartTouchDown = false;
    /**
     * 判断按下开始的位置是否在右
     */
    private boolean rightStartTouchDown = false;
    /**
     * 标题
     */
    private String title = "";
    /**
     * 标题字体大小
     */
    private float titleTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
            16, getResources().getDisplayMetrics());
    /**
     * 标题颜色
     */
    private int titleTextColor = Color.BLACK;
    /**
     * 左边文字
     */
    private String leftText = "";
    /**
     * 左边文字大小
     */
    private float leftTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
            16, getResources().getDisplayMetrics());
    /**
     * 左字左边距
     */
    private int leftTextMarginLeft = -1;
    /**
     * 左图左边距
     */
    private int leftImageMarginLeft = -1;
    /**
     * 左图右边距
     */
    private int leftImageMarginRight = -1;
    /**
     * 左边文字颜色
     */
    private int leftTextColor = Color.BLACK;
    /**
     * 右边文字
     */
    private String rightText = "";
    /**
     * 右边文字大小(16sp)
     */
    private float rightTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
            16, getResources().getDisplayMetrics());
    /**
     * 右边文字颜色
     */
    private int rightTextColor = Color.BLACK;
    /**
     * 右字右边距
     */
    private int rightTextMarginRight = -1;
    /**
     * 右图左边距
     */
    private int rightImageMarginLeft = -1;
    /**
     * 右图右边距
     */
    private int rightImageMarginRight = -1;

    private Paint mPaint;
    /**
     * 对文本的约束
     */
    private Rect mTextBound;
    /**
     * 控制整体布局
     */
    private Rect rect;


    /**
     * function:初始化属性(用户自己所设置的属性)
     */
    public OptionItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.mContext = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.OptionItemView);

        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.OptionItemView_left_src) {
                leftImage = BitmapFactory.decodeResource(getResources(), typedArray.getResourceId(attr, 0));

            } else if (attr == R.styleable.OptionItemView_right_src) {
                rightImage = BitmapFactory.decodeResource(getResources(), typedArray.getResourceId(attr, 0));

            } else if (attr == R.styleable.OptionItemView_title_size) {
                titleTextSize = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                        16, getResources().getDisplayMetrics()));

            } else if (attr == R.styleable.OptionItemView_title_color) {
                titleTextColor = typedArray.getColor(attr, Color.BLACK);

            } else if (attr == R.styleable.OptionItemView_title) {
                title = typedArray.getString(attr);

            } else if (attr == R.styleable.OptionItemView_left_text) {
                leftText = typedArray.getString(attr);

            } else if (attr == R.styleable.OptionItemView_left_text_size) {
                leftTextSize = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                        16, getResources().getDisplayMetrics()));

            } else if (attr == R.styleable.OptionItemView_left_text_margin_left) {
                leftTextMarginLeft = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        -1, getResources().getDisplayMetrics()));
            } else if (attr == R.styleable.OptionItemView_left_image_margin_left) {
                leftImageMarginLeft = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        -1, getResources().getDisplayMetrics()));
            } else if (attr == R.styleable.OptionItemView_left_image_margin_right) {
                leftImageMarginRight = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        -1, getResources().getDisplayMetrics()));
            } else if (attr == R.styleable.OptionItemView_left_text_color) {
                leftTextColor = typedArray.getColor(attr, Color.BLACK);

            } else if (attr == R.styleable.OptionItemView_right_text) {
                rightText = typedArray.getString(attr);
            } else if (attr == R.styleable.OptionItemView_right_text_size) {
                rightTextSize = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                        16, getResources().getDisplayMetrics()));
            } else if (attr == R.styleable.OptionItemView_right_text_margin_right) {
                rightTextMarginRight = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        -1, getResources().getDisplayMetrics()));
            } else if (attr == R.styleable.OptionItemView_right_image_margin_left) {
                rightImageMarginLeft = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        -1, getResources().getDisplayMetrics()));
            } else if (attr == R.styleable.OptionItemView_right_image_margin_right) {
                rightImageMarginRight = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        -1, getResources().getDisplayMetrics()));
            } else if (attr == R.styleable.OptionItemView_right_text_color) {
                rightTextColor = typedArray.getColor(attr, Color.BLACK);
            } else if (attr == R.styleable.OptionItemView_splite_mode) {
                mSpliteMode = typedArray.getBoolean(attr, false);
            }
        }
        typedArray.recycle();    //回收typeArray

        rect = new Rect();
        mPaint = new Paint();
        mTextBound = new Rect();
        // 计算了描绘字体需要的范围
        mPaint.getTextBounds(title, 0, title.length(), mTextBound);
    }

    /**
     * function:测量控件的宽高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getWidth();
        mHeight = getHeight();

        //抗锯齿处理
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));

        //控制可绘制的范围大小
        rect.left = getPaddingLeft();
        rect.right = mWidth - getPaddingRight();
        rect.top = getPaddingTop();
        rect.bottom = mHeight - getPaddingBottom();

        //抗锯齿
        mPaint.setAntiAlias(true);

        //选择leftTextSize、rightTextSize、titleTextSize中最大的那个TextSize设置给画笔来绘制title.(目的是让title要明显一点)
        mPaint.setTextSize(titleTextSize > leftTextSize ? titleTextSize > rightTextSize ? titleTextSize : rightTextSize : leftTextSize > rightTextSize ? leftTextSize : rightTextSize);
//        mPaint.setTextSize(titleTextSize);
        mPaint.setStyle(Paint.Style.FILL);

        //设置文字水平居中
        mPaint.setTextAlign(Paint.Align.CENTER);

        //计算垂直居中baseline
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        int baseLine = (int) ((rect.bottom + rect.top - fontMetrics.bottom - fontMetrics.top) / 2);

        //如果title不为空，则绘制（将title绘制在中心）
        if (!title.trim().equals("")) {
            // 正常情况，将字体居中
            mPaint.setColor(titleTextColor);
            canvas.drawText(title, rect.centerX(), baseLine, mPaint);
            // 取消使用掉的快
            rect.bottom -= mTextBound.height();
        }


        //如果图片不为空且允许展示，则进行绘制
        if (leftImage != null && isShowLeftImg) {
            // 计算左图范围
            rect.left = leftImageMarginLeft >= 0 ? leftImageMarginLeft : mWidth / 32;
            rect.right = rect.left + mHeight * 1 / 2;
            rect.top = mHeight / 4;
            rect.bottom = mHeight * 3 / 4;
            canvas.drawBitmap(leftImage, null, rect, mPaint);
        }

        //如果图片不为空且允许展示，则进行绘制
        if (rightImage != null && isShowRightImg) {
            // 计算右图范围
            rect.right = mWidth - (rightImageMarginRight >= 0 ? rightImageMarginRight : mWidth / 32);
            rect.left = rect.right - mHeight * 1 / 2;
            rect.top = mHeight / 4;
            rect.bottom = mHeight * 3 / 4;
            canvas.drawBitmap(rightImage, null, rect, mPaint);
        }

        //如果leftText不为空且允许展示，则进行绘制
        if (leftText != null && !leftText.equals("") && isShowLeftText) {
            mPaint.setTextSize(leftTextSize);
            mPaint.setColor(leftTextColor);
            int w = 0;
            if (leftImage != null) {
                w += leftImageMarginLeft >= 0 ? leftImageMarginLeft : (mHeight / 8);//增加左图左间距
                w += mHeight * 1 / 2;//图宽
                w += leftImageMarginRight >= 0 ? leftImageMarginRight : (mWidth / 32);// 增加左图右间距
                w += leftTextMarginLeft > 0 ? leftTextMarginLeft : 0;//增加左字左间距
            } else {
                w += leftTextMarginLeft >= 0 ? leftTextMarginLeft : (mWidth / 32);//增加左字左间距
            }

            mPaint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(leftText, w, baseLine, mPaint);
        }

        //如果rigthText不为空且允许展示，则进行绘制
        if (rightText != null && !rightText.equals("") && isShowRightText) {
            mPaint.setTextSize(rightTextSize);
            mPaint.setColor(rightTextColor);

            int w = mWidth;
            if (rightImage != null) {
                w -= rightImageMarginRight >= 0 ? rightImageMarginRight : (mHeight / 8);//增加右图右间距
                w -= mHeight * 1 / 2;//增加图宽
                w -= rightImageMarginLeft >= 0 ? rightImageMarginLeft : (mWidth / 32);//增加右图左间距
                w -= rightTextMarginRight > 0 ? rightTextMarginRight : 0;//增加右字右间距
            } else {
                w -= rightTextMarginRight >= 0 ? rightTextMarginRight : (mWidth / 32);//增加右字右间距
            }

            // 计算了描绘字体需要的范围
            mPaint.getTextBounds(rightText, 0, rightText.length(), mTextBound);
            canvas.drawText(rightText, w - mTextBound.width(), baseLine, mPaint);
        }
    }

    /**
     * function:点击事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //是一个整体，则不拆分各区域的点击
        if (!mSpliteMode)
            return super.onTouchEvent(event);

        //若不是一个整体的点击事件,则进行拆分
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int _x = (int) event.getX();
                if (_x < mWidth / 8) {
                    leftStartTouchDown = true;
                } else if (_x > mWidth * 7 / 8) {
                    rightStartTouchDown = true;
                } else {
                    centerStartTouchDown = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                int x = (int) event.getX();
                if (leftStartTouchDown && x < mWidth / 8 && listener != null) {
                    listener.leftOnClick();
                } else if (rightStartTouchDown && x > mWidth * 7 / 8 && listener != null) {
                    listener.rightOnClick();
                } else if (centerStartTouchDown && listener != null) {
                    listener.centerOnClick();
                }


                /**
                 *  添加这三个boolean变量的目的是为了判断用户按下的区域是属于哪一个(左、中、右),
                 * (然后又考虑到用户可能是因为不小心而按下去的，这时候用户不想启动该点击事件，那该怎么做？--用户可以在按下之后向其它区域移动手指，
                 * 即脱离了原区域再松开手指即可取消该次点击事件。即：用户在某个区域按下之后必须要在该区域松开手指才可以启动其点击事件)
                 */
                leftStartTouchDown = false;
                centerStartTouchDown = false;
                rightStartTouchDown = false;
                break;
        }
        return true;
    }


    /**
     * function:代码层设置各属性(set方法)
     */
    public void setTitleText(String text) {
        title = text;
        invalidate();
    }

    public void setTitleText(int stringId) {
        title = mContext.getString(stringId);
        invalidate();
    }

    public void setTitleColor(int color) {
        titleTextColor = color;
        invalidate();
    }

    public void setTitleSize(int sp) {
        titleTextSize = sp2px(mContext, sp);
        invalidate();
    }

    public void setLeftText(String text) {
        leftText = text;
        invalidate();
    }

    public void setLeftText(int stringId) {
        leftText = mContext.getString(stringId);
        invalidate();
    }

    public void setLeftTextColor(int color) {
        leftTextColor = color;
        invalidate();
    }

    public void setLeftImageMarginRight(int dp) {
        leftImageMarginRight = dp2px(mContext, dp);
        invalidate();
    }

    public void setLeftImageMarginLeft(int dp) {
        this.leftImageMarginLeft = dp2px(mContext, dp);
        invalidate();
    }

    public void setLeftTextMarginLeft(int dp) {
        this.leftTextMarginLeft = dp2px(mContext, dp);
        invalidate();
    }

    public void setLeftImage(Bitmap bitmap) {
        leftImage = bitmap;
        invalidate();
    }

    public void setRightImage(Bitmap bitmap) {
        rightImage = bitmap;
        invalidate();
    }

    public void setLeftTextSize(int sp) {
        leftTextSize = sp2px(mContext, sp);
        invalidate();
    }

    public void setRightText(String text) {
        rightText = text;
        invalidate();
    }

    public void setRightText(int stringId) {
        rightText = mContext.getString(stringId);
        invalidate();
    }

    public void setRightTextColor(int color) {
        rightTextColor = color;
        invalidate();
    }

    public void setRightTextSize(int sp) {
        leftTextSize = sp2px(mContext, sp);
        invalidate();
    }

    public void setRightImageMarginLeft(int dp) {
        rightImageMarginLeft = dp2px(mContext, dp);
        invalidate();
    }

    public void setRightImageMarginRight(int dp) {
        this.rightImageMarginRight = dp2px(mContext, dp);
        invalidate();
    }

    public void setRightTextMarginRight(int dp) {
        this.rightTextMarginRight = dp2px(mContext, dp);
        invalidate();
    }

    public void showLeftImg(boolean flag) {
        isShowLeftImg = flag;
        invalidate();
    }

    public void showLeftText(boolean flag) {
        isShowLeftText = flag;
        invalidate();
    }

    public void showRightImg(boolean flag) {
        isShowRightImg = flag;
        invalidate();
    }

    public void showRightText(boolean flag) {
        isShowRightText = flag;
        invalidate();
    }

    public void setSpliteMode(boolean spliteMode) {
        mSpliteMode = spliteMode;
    }

    public boolean getSpliteMode() {
        return mSpliteMode;
    }

    private OnOptionItemClickListener listener;

    public interface OnOptionItemClickListener {
        void leftOnClick();

        void centerOnClick();

        void rightOnClick();
    }

    public void setOnOptionItemClickListener(OnOptionItemClickListener listener) {
        this.listener = listener;
    }

    //单位转换--将sp值转换为px值，保证文字大小不变
    private int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    //单位转换--将dp值转换为px值，保证尺寸大小不变
    private int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }
}

