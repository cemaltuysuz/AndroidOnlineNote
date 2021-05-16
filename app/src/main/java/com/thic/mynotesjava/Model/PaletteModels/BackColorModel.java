package com.thic.mynotesjava.Model.PaletteModels;

public class BackColorModel {

    /**
     * @author cemaltuysuz
     * @version 1.0
     * -- See NotesRepository --
     * This class for Custom Note Background Color Instance and Mysql database
     * */

    private int BackColorCode;
    private String BackColorName;
    private int color;

    public BackColorModel(int backColorCode, String backColorName, int color) {
        BackColorCode = backColorCode;
        BackColorName = backColorName;
        this.color = color;
    }

    public int getBackColorCode() {
        return BackColorCode;
    }

    public void setBackColorCode(int backColorCode) {
        BackColorCode = backColorCode;
    }

    public String getBackColorName() {
        return BackColorName;
    }

    public void setBackColorName(String backColorName) {
        BackColorName = backColorName;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
