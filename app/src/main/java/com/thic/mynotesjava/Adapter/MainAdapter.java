package com.thic.mynotesjava.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thic.mynotesjava.ImageHelper;
import com.thic.mynotesjava.Model.Notlar;
import com.thic.mynotesjava.Model.PaletteModels.BackColorModel;
import com.thic.mynotesjava.Model.PaletteModels.FontModel;
import com.thic.mynotesjava.Model.PaletteModels.MultiModel;
import com.thic.mynotesjava.Model.PaletteModels.TextColorModel;
import com.thic.mynotesjava.R;
import com.thic.mynotesjava.RecyclerViewOnClick;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    /**
     * This adapter for MainActivity's Note Adapter.
     * @see com.thic.mynotesjava.UI.MainActivity
     * @author cemaltuysuz
     * @version 1.0
     *
     * */

    // Define Variable
    private Context context;
    private List<Notlar> notelist;
    private RecyclerViewOnClick itemOnClick; // This interface for detect itemClick in RecyclerView
    private List<MultiModel> multiModelList;

    public MainAdapter(Context context, RecyclerViewOnClick itemOnClick, List<MultiModel> list) {
        this.context  = context;
        this.itemOnClick = itemOnClick;
        this.multiModelList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_priv_count,parent,false);
        return new MyViewHolder(view,itemOnClick);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Notlar note = notelist.get(position);
        holder.noteDate.setText(note.getNoteDate());
        holder.noteTitle.setText(note.getNoteTitle());
        holder.noteContent.setText(note.getNoteContent());

        // Note Material set (Font type,background color, text color)
        setMaterial(holder.noteTitle,holder.noteContent,holder.cardView2,multiModelList,note);

        // If this note has an image, CardView visibility -> VISIBLE
        if (Integer.parseInt(notelist.get(position).getImgStat())==1)
            ImageHelper.forView(context,holder.noteImage, notelist.get(position).getImgUrl());

        // If this note has not an image, CardView visibility -> GONE
        else holder.cardView.setVisibility(View.GONE);

        holder.noteDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When there is only one note left, the program fails, I solved the problem like this ->
                if (notelist.size()!=1) itemOnClick.deleteClick(Integer.parseInt(notelist.get(position).getNoteId()));
                else itemOnClick.deleteClick(Integer.parseInt(notelist.get(0).getNoteId()));
            }
        });
    }
    // update adapter when searchview is running.
    public void setNotelist(List<Notlar> notifyList){
        this.notelist = notifyList;
        notifyDataSetChanged();
    }
    // update adapter when deleted note
    public void delItem(int itemId){
        for (int i=0;i<notelist.size();i++){
            if (notelist.get(i).getNoteId().equals(String.valueOf(itemId))){
                notelist.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return notelist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView noteTitle,noteContent,noteDate;
        ImageView noteDel,noteImage;
        CardView cardView,cardView2;
        RecyclerViewOnClick recyclerViewOnClick;
        public MyViewHolder(@NonNull View itemView,RecyclerViewOnClick onClick) {
            super(itemView);
            this.recyclerViewOnClick = onClick;
            noteTitle   = itemView.findViewById(R.id.noteTitle);
            noteContent = itemView.findViewById(R.id.priv_noteContent);
            noteDate    = itemView.findViewById(R.id.noteDate);
            noteDel     = itemView.findViewById(R.id.deleteNote);
            noteImage   = itemView.findViewById(R.id.noteImage);
            cardView    = itemView.findViewById(R.id.cardViewForImg);
            cardView2   = itemView.findViewById(R.id.cardViewForNote);
            // detect itemClick with this interface and onClickMethod
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {
            recyclerViewOnClick.itemOnClick(notelist.get(getAdapterPosition()));
        }
    }

    public void setMaterial(TextView title,TextView content,CardView cardView,List<MultiModel> list,Notlar note){

        for (MultiModel m :list){
            for (Object o : m.getItems()){
                if (o instanceof TextColorModel){
                    TextColorModel tc = (TextColorModel) o;
                    if (Integer.parseInt(note.getNoteTextColor()) == tc.getTextColorCode()){
                        title.setTextColor(tc.getColor());
                        content.setTextColor(tc.getColor());
                    }
                }else if(o instanceof FontModel){
                    FontModel fm = (FontModel) o;
                    if (Integer.parseInt(note.getNoteFontType()) == fm.getFontCode()){
                        title.setTypeface(fm.getFontType());
                        content.setTypeface(fm.getFontType());
                    }
                }else {
                    BackColorModel bm = (BackColorModel) o;
                    if (Integer.parseInt(note.getNoteBackColor()) == bm.getBackColorCode()){
                        cardView.setCardBackgroundColor(bm.getColor());
                    }
                }
            }
        }

    }
}
