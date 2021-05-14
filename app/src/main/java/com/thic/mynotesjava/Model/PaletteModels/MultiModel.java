package com.thic.mynotesjava.Model.PaletteModels;

import java.util.List;

public class MultiModel {

    /**
     * @author cemaltuysuz
     * @version 1.0
     * @see com.thic.mynotesjava.Adapter.PaletteAdapters.VerticalAdapter
     * @see com.thic.mynotesjava.Adapter.PaletteAdapters.HorizontalAdapter
     *
     *   This class for RecyclerView
     * */

    private String title;
    private List<Object> items;

    public MultiModel(String title, List<Object> items) {
        this.title = title;
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Object> getItems() {
        return items;
    }

    public void setItems(List<Object> items) {
        this.items = items;
    }
}
