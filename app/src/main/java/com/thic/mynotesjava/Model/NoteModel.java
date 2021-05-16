
package com.thic.mynotesjava.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoteModel {

    /**
     * This class for Retrofit2 callback
     * @author cemaltuysuz
     * @version 1.0
     * @see com.thic.mynotesjava.Retrofit.API
     * */

    @SerializedName("notlar")
    @Expose
    private List<Notlar> notlar = null;
    @SerializedName("success")
    @Expose
    private Integer success;

    public List<Notlar> getNotlar() {
        return notlar;
    }

    public void setNotlar(List<Notlar> notlar) {
        this.notlar = notlar;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

}
