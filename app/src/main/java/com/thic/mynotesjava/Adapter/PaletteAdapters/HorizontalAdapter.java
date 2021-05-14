package com.thic.mynotesjava.Adapter.PaletteAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.thic.mynotesjava.Model.PaletteModels.BackColorModel;
import com.thic.mynotesjava.Model.PaletteModels.ChooseState;
import com.thic.mynotesjava.Model.PaletteModels.FontModel;
import com.thic.mynotesjava.Model.PaletteModels.TextColorModel;
import com.thic.mynotesjava.R;
import com.thic.mynotesjava.UI.bottomSheetDialog;

import java.util.List;

public class HorizontalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * @author cemaltuysuz
     * @version 1.0
     * @see bottomSheetDialog
     * This class for Horizontal RecylerView and custom panel rows.
     * */

    private List<Object> items;
    private Context context;
    private ChooseState state;

    public HorizontalAdapter(Context context,List<Object> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType){
            case 0:
                return new FontTypeHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.select_font_type,parent,false));
            case 1:
                return new TextColorHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.color_type_layout,parent,false));
            default:
                return new BackColorHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.color_type_layout,parent,false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        Object o = items.get(position);

        if (o instanceof FontModel) type = 0;
        else if (o instanceof TextColorModel) type = 1;
        else type = 2;

        return type;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){

            case 0:
                FontModel currentFont = (FontModel) items.get(position);
                ((FontTypeHolder) holder).typeText.setText(currentFont.getFontName());
                ((FontTypeHolder) holder).typeText.setTypeface(currentFont.getFontType());

                onClickItem(((FontTypeHolder) holder).itemView,currentFont.getFontName());
                break;

            case 1:
                TextColorModel currentTextColor = (TextColorModel) items.get(position);
                ((TextColorHolder) holder).color.setBackgroundColor(currentTextColor.getColor());
                onClickItem(((TextColorHolder) holder).itemView,currentTextColor.getTextColorName());
                break;


            default:
                BackColorModel currentBackColor = (BackColorModel) items.get(position);
                ((BackColorHolder) holder).color.setBackgroundColor(currentBackColor.getColor());
                onClickItem(((BackColorHolder) holder).itemView,currentBackColor.getBackColorName());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class FontTypeHolder extends RecyclerView.ViewHolder {

        ImageView okeyButton;
        TextView typeText;
        public FontTypeHolder(@NonNull View itemView) {
            super(itemView);
            okeyButton = itemView.findViewById(R.id.done);
            typeText   = itemView.findViewById(R.id.typeText);
        }
    }

    public class BackColorHolder extends RecyclerView.ViewHolder {
        RoundedImageView color;
        public BackColorHolder(@NonNull View itemView) {
            super(itemView);
            color = itemView.findViewById(R.id.color);
        }
    }

    public class TextColorHolder extends RecyclerView.ViewHolder {
        RoundedImageView color;
        public TextColorHolder(@NonNull View itemView) {
            super(itemView);
            color = itemView.findViewById(R.id.color);
        }
    }

    public void onClickItem (View view,String mssg){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v ) {
                Toast.makeText(context,mssg,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
