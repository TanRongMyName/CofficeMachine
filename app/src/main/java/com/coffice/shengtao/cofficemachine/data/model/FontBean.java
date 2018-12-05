package com.coffice.shengtao.cofficemachine.data.model;

import android.graphics.Typeface;

/**
 * 字体对象
 */
public class FontBean {
    String fontname;
    Typeface typeface;

    public FontBean(String fontname, Typeface typeface) {
        this.fontname = fontname;
        this.typeface = typeface;
    }

    public String getFontname() {
        return fontname;
    }

    public void setFontname(String fontname) {
        this.fontname = fontname;
    }

    public Typeface getTypeface() {
        return typeface;
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
    }
}
