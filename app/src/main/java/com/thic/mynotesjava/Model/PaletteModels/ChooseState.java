package com.thic.mynotesjava.Model.PaletteModels;

import com.thic.mynotesjava.UI.bottomSheetDialog;

public class ChooseState {

    /**
     * @author cemaltuysuz
     * @version 1.0
     * @see bottomSheetDialog
     * This class keeps the changes the user makes in the palette.
     * */

    private int fontTypeCode;
    private int textColorCode;
    private int backColorCode;

    public ChooseState(int fontTypeCode, int textColorCode, int backColorCode) {
        this.fontTypeCode = fontTypeCode;
        this.textColorCode = textColorCode;
        this.backColorCode = backColorCode;
    }

    public int getFontTypeCode() {
        return fontTypeCode;
    }

    public void setFontTypeCode(int fontTypeCode) {
        this.fontTypeCode = fontTypeCode;
    }

    public int getTextColorCode() {
        return textColorCode;
    }

    public void setTextColorCode(int textColorCode) {
        this.textColorCode = textColorCode;
    }

    public int getBackColorCode() {
        return backColorCode;
    }

    public void setBackColorCode(int backColorCode) {
        this.backColorCode = backColorCode;
    }
}
