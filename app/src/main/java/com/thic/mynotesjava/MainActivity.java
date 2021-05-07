package com.thic.mynotesjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thic.mynotesjava.Model.Adapter.MainAdapter;
import com.thic.mynotesjava.Model.Models.Notlar;
import com.thic.mynotesjava.ViewModel.MainViewModel;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    // Define Variable
    private RecyclerView mainRecyclerView;
    private FloatingActionButton fab;
    private MainAdapter adapter;
    Intent fabIntent;
    Intent clickIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetworkHelper.getData();

        // Initialize Variable
        mainRecyclerView = findViewById(R.id.mainRecyclerView);
        fab = findViewById(R.id.mainFabIcon);

        //RecyclerView Settings
        mainRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mainRecyclerView.setHasFixedSize(true);

        // Data coming
        MainViewModel.notes.observe(this, new Observer<List<Notlar>>() {
            @Override
            public void onChanged(List<Notlar> notlars) {
                if (notlars.size()>0){
                    Collections.reverse(notlars);
                    adapter = new MainAdapter(getApplicationContext(),notlars);
                    mainRecyclerView.setAdapter(adapter);
                }
            }
        });

        // Note Activity
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabIntent = new Intent(getApplicationContext(),noteActivity.class);
                fabIntent.putExtra("isEmpty",true);
                startActivity(fabIntent);
            }
        });

        MainViewModel.clickInfo.observe(this, new Observer<Notlar>() {
            @Override
            public void onChanged(Notlar notlar) {
            Bundle bundle = new Bundle();
            clickIntent = new Intent(getApplicationContext(),noteActivity.class);
            sendData(notlar,bundle);
            clickIntent.putExtra("data",bundle);
            clickIntent.putExtra("isEmpty",false);
            startActivity(clickIntent);
            }
        });
    }

    public void sendData(Notlar notlar,Bundle bundle){
        bundle.putString("noteTitle",notlar.getNoteTitle());
        bundle.putString("noteContent",notlar.getNoteContent());
        bundle.putString("noteDate",notlar.getNoteDate());
        bundle.putString("noteID",notlar.getNoteId());
        bundle.putString("noteTextColor",notlar.getNoteTextColor());
        bundle.putString("noteBackColor",notlar.getNoteBackColor());
        bundle.putString("noteFontType",notlar.getNoteFontType());
    }
}