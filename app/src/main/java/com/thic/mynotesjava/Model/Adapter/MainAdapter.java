package com.thic.mynotesjava.Model.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.thic.mynotesjava.Model.Models.Notlar;
import com.thic.mynotesjava.R;
import com.thic.mynotesjava.ViewModel.MainViewModel;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    private Context context;
    private List<Notlar> notelist;

    public MainAdapter(Context context, List<Notlar> notelist) {
        this.context  = context;
        this.notelist = notelist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_priv_count,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.noteDate.setText(notelist.get(position).getNoteDate());
        holder.noteTitle.setText(notelist.get(position).getNoteTitle());
        holder.noteContent.setText(notelist.get(position).getNoteContent());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainViewModel.clickInfo.setValue(notelist.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return notelist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView noteTitle,noteContent,noteDate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle   = itemView.findViewById(R.id.noteTitle);
            noteContent = itemView.findViewById(R.id.priv_noteContent);
            noteDate    = itemView.findViewById(R.id.noteDate);
        }
    }
}
