package com.thic.mynotesjava.Model.PaletteModels;

public class TextColorModel {

    private int TextColorCode;
    private String TextColorName;
    private int color;

    public TextColorModel(int colorCode, String colorName, int color) {
        this.TextColorCode = colorCode;
        this.TextColorName = colorName;
        this.color = color;
    }

    public int getTextColorCode() {
        return TextColorCode;
    }

    public void setTextColorCode(int textColorCode) {
        this.TextColorCode = textColorCode;
    }

    public String getTextColorName() {
        return TextColorName;
    }

    public void setTextColorName(String textColorName) {
        this.TextColorName = textColorName;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
