package com.thic.mynotesjava.Model.PaletteModels;

import android.graphics.Typeface;

public class FontModel {

    private int fontCode;
    private String fontName;
    private Typeface fontType;

    public FontModel(int fontCode, String fontName, Typeface fontType) {
        this.fontCode = fontCode;
        this.fontName = fontName;
        this.fontType = fontType;
    }

    public Typeface getFontType() {
        return fontType;
    }

    public void setFontType(Typeface fontType) {
        this.fontType = fontType;
    }

    public int getFontCode() {
        return fontCode;
    }

    public void setFontCode(int fontCode) {
        this.fontCode = fontCode;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }
}


