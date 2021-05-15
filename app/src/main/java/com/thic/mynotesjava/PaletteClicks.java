package com.thic.mynotesjava;

import android.graphics.Typeface;

public interface PaletteClicks {

    void textColorClick(int colorCode, int code);
    void backColorClick(int colorCode, int code);
    void fontTypeClick(Typeface typeface, int code);
}
