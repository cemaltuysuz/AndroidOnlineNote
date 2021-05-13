package com.thic.mynotesjava;

import com.thic.mynotesjava.Model.Notlar;

public interface RecyclerViewOnClick {

    // RecyclerView Clicks
    void itemOnClick(Notlar note);
    void deleteClick (int position);
}
