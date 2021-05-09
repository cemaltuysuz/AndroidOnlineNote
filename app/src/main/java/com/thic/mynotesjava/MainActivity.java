package com.thic.mynotesjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
    private SearchView searchView;
    private FloatingActionButton fab;
    private MainAdapter adapter;
    private Intent intent;
    private NetworkHelper networkHelper = new NetworkHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        networkHelper.getData();
        intent = new Intent(getApplicationContext(),noteActivity.class);

        // Initialize Variable
        mainRecyclerView = findViewById(R.id.mainRecyclerView);
        searchView       = findViewById(R.id.searchView);
        fab = findViewById(R.id.mainFabIcon);

        //RecyclerView Settings
        mainRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mainRecyclerView.setHasFixedSize(true);

        // Data coming
        MainViewModel.notes.observe(this, new Observer<List<Notlar>>() {
            @Override
            public void onChanged(List<Notlar> notlars) {
                    Collections.reverse(notlars);
                    adapter = new MainAdapter(getApplicationContext(),notlars);
                    mainRecyclerView.setAdapter(adapter);
            }
        });
        // SearchView Action
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                networkHelper.searchData(newText);
                return true;
            }
        });

        // Note Activity
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("isEmpty",true);
                startActivity(intent);
            }
        });

         // Note Click Info
        MainViewModel.clickInfo.observe(this, new Observer<Notlar>() {
            @Override
            public void onChanged(Notlar notlar) {
            Bundle bundle = new Bundle();
            intent = new Intent(getApplicationContext(),noteActivity.class);
            sendData(notlar,bundle);
            intent.putExtra("data",bundle);
            intent.putExtra("isEmpty",false);
            startActivity(intent);
            }
        });

        MainViewModel.delInfo.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                adapter.delItem(integer);
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