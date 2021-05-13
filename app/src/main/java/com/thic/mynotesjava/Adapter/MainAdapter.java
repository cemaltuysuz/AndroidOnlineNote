package com.thic.mynotesjava.Adapter;

import android.content.Context;
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
import com.thic.mynotesjava.R;
import com.thic.mynotesjava.RecyclerViewOnClick;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    private Context context;
    private List<Notlar> notelist;
    private RecyclerViewOnClick itemOnClick;

    public MainAdapter(Context context,RecyclerViewOnClick itemOnClick) {
        this.context  = context;
        this.itemOnClick = itemOnClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_priv_count,parent,false);
        return new MyViewHolder(view,itemOnClick);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.noteDate.setText(notelist.get(position).getNoteDate());
        holder.noteTitle.setText(notelist.get(position).getNoteTitle());
        holder.noteContent.setText(notelist.get(position).getNoteContent());

        if (Integer.parseInt(notelist.get(position).getImgStat())==1){
            ImageHelper.forView(context,holder.noteImage, notelist.get(position).getImgUrl());
        }else {
            holder.cardView.setVisibility(View.GONE);
        }

        holder.noteDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notelist.size()!=1) itemOnClick.deleteClick(Integer.parseInt(notelist.get(position).getNoteId()));
                else itemOnClick.deleteClick(Integer.parseInt(notelist.get(0).getNoteId()));
            }
        });
    }
    public void setNotelist(List<Notlar> notifyList){
        this.notelist = notifyList;
        notifyDataSetChanged();
    }

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
        CardView cardView;
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
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {
            recyclerViewOnClick.itemOnClick(notelist.get(getAdapterPosition()));
        }
    }
}
