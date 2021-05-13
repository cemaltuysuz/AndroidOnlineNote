package com.thic.mynotesjava.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thic.mynotesjava.Adapter.MainAdapter;
import com.thic.mynotesjava.Model.NoteModel;
import com.thic.mynotesjava.Model.Notlar;
import com.thic.mynotesjava.R;
import com.thic.mynotesjava.RecyclerViewOnClick;
import com.thic.mynotesjava.Retrofit.API;
import com.thic.mynotesjava.Retrofit.ApiUtils;
import com.thic.mynotesjava.UI.noteActivity;
import com.thic.mynotesjava.ViewModel.MainViewModel;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecyclerViewOnClick {

    // Define Variable
    private RecyclerView mainRecyclerView;
    private FloatingActionButton fab;
    private MainAdapter adapter;
    private Intent intent;
    private MainViewModel viewModel;
    private API api = ApiUtils.api();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Initialize();
        // Data coming
        viewModel.getNotes().observe(this, new Observer<List<Notlar>>() {
            @Override
            public void onChanged(List<Notlar> notlars) {
                if (notlars !=null){
                    Collections.reverse(notlars);
                    adapter.setNotelist(notlars);
                    mainRecyclerView.setAdapter(adapter);
                }
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
    }

    private void Initialize() {
        // Initialize Variable
        viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MainViewModel.class);
        mainRecyclerView = findViewById(R.id.mainRecyclerView);
        fab = findViewById(R.id.mainFabIcon);
        //RecyclerView Settings
        mainRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mainRecyclerView.setHasFixedSize(true);
        adapter = new MainAdapter(getApplicationContext(),this);
        intent = new Intent(getApplicationContext(), noteActivity.class);
    }

    // RecyclerView Item Click (Interface)
    @Override
    public void itemOnClick(Notlar note) {
        Bundle bundle = new Bundle();
        intent = new Intent(getApplicationContext(),noteActivity.class);
        sendData(note,bundle);
        intent.putExtra("data",bundle);
        intent.putExtra("isEmpty",false);
        startActivity(intent);
    }

    // RecyclerView Delete Click (Interface)
    @Override
    public void deleteClick(int position) {
            deleteData(String.valueOf(position));
    }

    // Data put Bundle
    private void sendData(Notlar notlar,Bundle bundle){
        bundle.putString("noteTitle",notlar.getNoteTitle());
        bundle.putString("noteContent",notlar.getNoteContent());
        bundle.putString("noteDate",notlar.getNoteDate());
        bundle.putString("noteID",notlar.getNoteId());
        bundle.putString("noteTextColor",notlar.getNoteTextColor());
        bundle.putString("noteBackColor",notlar.getNoteBackColor());
        bundle.putString("noteFontType",notlar.getNoteFontType());
        bundle.putString("noteImageStat",notlar.getImgStat());
        bundle.putString("noteImageUrl",notlar.getImgUrl());
    }

    // Delete Note
    private void deleteData (String noteID){
            api.noteDelete(noteID).enqueue(new Callback<NoteModel>() {
                @Override
                public void onResponse(Call<NoteModel> call, Response<NoteModel> response) {
                    if (response.body().getSuccess()==1) adapter.delItem(Integer.parseInt(noteID));
                    else erorMessage("No data found.");
                }
                @Override
                public void onFailure(Call<NoteModel> call, Throwable t) {
                    erorMessage("Eror :"+t.getMessage());
                }
            });
    }

    // Eror Message
    private void erorMessage(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
}