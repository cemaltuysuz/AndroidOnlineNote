package com.thic.mynotesjava.Adapter.PaletteAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.thic.mynotesjava.Model.PaletteModels.BackColorModel;
import com.thic.mynotesjava.Model.PaletteModels.ChooseState;
import com.thic.mynotesjava.Model.PaletteModels.FontModel;
import com.thic.mynotesjava.Model.PaletteModels.TextColorModel;
import com.thic.mynotesjava.PaletteClicks;
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
    PaletteClicks clicks;

    public HorizontalAdapter(Context context, List<Object> items, PaletteClicks clicks) {
        this.context = context;
        this.items = items;
        this.clicks = clicks;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        /**
         * Multi-ViewType Create. View Types nums defined in getItemViewType method.
         * */

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

        // This classes, What type of classes ?

        int type;
        Object o = items.get(position);

        if (o instanceof FontModel) type = 0;
        else if (o instanceof TextColorModel) type = 1;
        else type = 2;

        return type;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        // ItemViewType -->   0 : FontType ,  1 : TextColor  ,  2 : BackgroundColor
        switch (getItemViewType(position)){

            case 0:
                // Get Current Instance
                FontModel currentFont = (FontModel) items.get(position);
                // TextView set current font name
                ((FontTypeHolder) holder).typeText.setText(currentFont.getFontName());
                // TextView's text , set current font type (TypeFace)
                ((FontTypeHolder) holder).typeText.setTypeface(currentFont.getFontType());
                // If State not null and if the user has pre-selected. Mark that selection when starting.
                // If state null or user not pre-selected , mark is gone.
                if (this.state != null) if (currentFont.getFontCode()==state.getFontTypeCode()) ((FontTypeHolder) holder).okeyButton.setVisibility(View.VISIBLE);
                else ((FontTypeHolder) holder).okeyButton.setVisibility(View.GONE);

                // Font-Type is onClick
                ((FontTypeHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Refresh RecyclerView
                        state.setFontTypeCode(currentFont.getFontCode());
                        notifyDataSetChanged();
                        // Send data bottomSheetDialog with interface
                        clicks.fontTypeClick(currentFont.getFontType(),currentFont.getFontCode());
                    }
                });
                break;

            case 1:
                // Get Current Instance
                TextColorModel currentTextColor = (TextColorModel) items.get(position);
                // Set current color.
                ((TextColorHolder) holder).color.setBackgroundColor(currentTextColor.getColor());
                // I am Setting border color is Transparent because :
                // when user picked the color,  make it border color is white color.
                ((TextColorHolder) holder).color.setBorderColor(context.getResources().getColor(R.color.transparent));

                // If State not null and if the user has pre-selected. Border that visible when starting.
                // If state null or user not pre-selected , Border is Transparent.
                if (this.state != null) if (currentTextColor.getTextColorCode()==state.getTextColorCode())
                    ((TextColorHolder) holder).color.setBorderColor(context.getResources().getColor(R.color.white));
                else ((TextColorHolder) holder).color.setBorderColor(context.getResources().getColor(R.color.transparent));

                // TextColorPicked
                ((TextColorHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Refresh RecyclerView
                        state.setTextColorCode(currentTextColor.getTextColorCode());
                        notifyDataSetChanged();
                        // Send data bottomSheetDialog with interface
                        clicks.textColorClick(currentTextColor.getColor(),currentTextColor.getTextColorCode());
                    }
                });
                break;


            default:

                // Get Current Instance
                BackColorModel currentBackColor = (BackColorModel) items.get(position);
                // Set current color.
                ((BackColorHolder) holder).color.setBackgroundColor(currentBackColor.getColor());

                // If State not null and if the user has pre-selected. Border that visible when starting.
                // If state null or user not pre-selected , Border is Transparent.
                if (this.state != null) if (currentBackColor.getBackColorCode()==state.getBackColorCode())
                    ((BackColorHolder) holder).color.setBorderColor(context.getResources().getColor(R.color.white));
                else ((BackColorHolder) holder).color.setBorderColor(context.getResources().getColor(R.color.transparent));

                // BackgroundColor picked
                ((BackColorHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // Refresh RecyclerView
                        state.setBackColorCode(currentBackColor.getBackColorCode());
                        notifyDataSetChanged();

                        // Send data bottomSheetDialog with interface
                        clicks.backColorClick(currentBackColor.getColor(),currentBackColor.getBackColorCode());
                    }
                });
                break;
        }
    }
    // Shadowing
    public void changeData(ChooseState state){
        this.state = state;
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    // FontType ViewHolder
    public class FontTypeHolder extends RecyclerView.ViewHolder {

        ImageView okeyButton;
        TextView typeText;
        public FontTypeHolder(@NonNull View itemView) {
            super(itemView);
            okeyButton = itemView.findViewById(R.id.done);
            typeText   = itemView.findViewById(R.id.typeText);
        }
    }
    // BackGround ViewHolder
    public class BackColorHolder extends RecyclerView.ViewHolder {
        RoundedImageView color;
        public BackColorHolder(@NonNull View itemView) {
            super(itemView);
            color = itemView.findViewById(R.id.color);
        }
    }
    // TextColor ViewHolder
    public class TextColorHolder extends RecyclerView.ViewHolder {
        RoundedImageView color;
        public TextColorHolder(@NonNull View itemView) {
            super(itemView);
            color = itemView.findViewById(R.id.color);
        }
    }

}
