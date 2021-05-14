package com.thic.mynotesjava.Adapter.PaletteAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thic.mynotesjava.FontClick;
import com.thic.mynotesjava.Model.PaletteModels.FontModel;
import com.thic.mynotesjava.R;

import java.util.List;

public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.ViewHolder> {

    private Context context;
    private List<FontModel> fontModelList;
    private int choose;
    private FontClick fontClick;

    public VerticalAdapter(Context context, List<FontModel> fontModelList, FontClick click) {
        this.context = context;
        this.fontModelList = fontModelList;
        this.fontClick = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.font_type_container,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        FontModel current = fontModelList.get(position);

        if (choose == current.getFontCode()) holder.done.setVisibility(View.VISIBLE);
        else holder.done.setVisibility(View.GONE);

        holder.font.setText(current.getFontName());
        holder.font.setTypeface(current.getFontType());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fontClick.itemClick(current.getFontCode());
            }
        });
    }

    public void notifySelect (int choose){
        this.choose = choose;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return fontModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView done;
        TextView font;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            done = itemView.findViewById(R.id.font_done);
            font = itemView.findViewById(R.id.fontType);
        }
    }
}
