package com.coffice.shengtao.cofficemachine.selfview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.coffice.shengtao.cofficemachine.R;


public class FontTextView extends android.support.v7.widget.AppCompatTextView {
    public FontTextView(Context context) {
        super(context);
    }

    public FontTextView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        initParams(context,attrs);
    }



    public FontTextView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams(context,attrs);
    }

    /**
     * 自定义属性 ----attrs xml 对应自定义的控件  添加 属性名称  在空间上使用   在遍历出来  获取使用----
     * @param context
     * @param attrs
     */
    private void initParams(Context context, AttributeSet attrs) {
        String fontpath;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FontTextView);
        if (typedArray != null) {
            fontpath=typedArray.getString(R.styleable.FontTextView_fontPath);
            if(fontpath!=null){//设置字体
                setTypeface(createTypeface(getContext(),fontpath));
            }
            typedArray.recycle();
        }
    }


    private Typeface createTypeface(Context context, String fontPath) {
        return Typeface.createFromAsset(context.getAssets(), fontPath);
    }

    @Override
    public void setTypeface(Typeface tf, int style) {
        super.setTypeface(tf, style);
    }

}
