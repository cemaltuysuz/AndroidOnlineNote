package com.thic.mynotesjava.Adapter.PaletteAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thic.mynotesjava.Model.PaletteModels.ChooseState;
import com.thic.mynotesjava.Model.PaletteModels.MultiModel;
import com.thic.mynotesjava.PaletteClicks;
import com.thic.mynotesjava.R;
import com.thic.mynotesjava.UI.bottomSheetDialog;

import java.util.List;

public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.ViewHolder> {

    /**
     * @author cemaltuysuz
     * @version 1.0
     * @see bottomSheetDialog
     * This class for Vertical RecyclerView
     * */

    private Context context;
    private List<MultiModel> multiModelList;
    private PaletteClicks clicks;
    private HorizontalAdapter adapter;
    private ChooseState state;

    public VerticalAdapter(Context context, List<MultiModel> fontModelList,ChooseState state ,PaletteClicks paletteClicks) {
        this.context = context;
        this.multiModelList = fontModelList;
        this.clicks = paletteClicks;
        this.state =state;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.priv_vertical_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // Set Category Title
       holder.title.setText(multiModelList.get(position).getTitle());

       // Create Horizontal Adapter and set Horizontal RecyclerView
        adapter = new HorizontalAdapter(context,multiModelList.get(position).getItems(),clicks);

        /**
         * This method (changeData) , Horizontal Adapter sends the current Material selection.
         * @see ChooseState
         * In this adapter gets this ChooseState from the bottomSheetDialog
         * @see bottomSheetDialog
         * */

        adapter.changeData(state);
        holder.horizontalRec.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        holder.horizontalRec.setHasFixedSize(true);
        holder.horizontalRec.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return multiModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView horizontalRec;
        TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            horizontalRec = itemView.findViewById(R.id.horizontalRecycler);
            title         = itemView.findViewById(R.id.selectTitle);
        }
    }
}
