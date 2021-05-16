package com.thic.mynotesjava.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thic.mynotesjava.Adapter.MainAdapter;
import com.thic.mynotesjava.Model.NoteModel;
import com.thic.mynotesjava.Model.Notlar;
import com.thic.mynotesjava.R;
import com.thic.mynotesjava.RecyclerViewOnClick;
import com.thic.mynotesjava.Retrofit.API;
import com.thic.mynotesjava.Retrofit.ApiUtils;
import com.thic.mynotesjava.ViewModel.MainViewModel;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecyclerViewOnClick, SearchView.OnQueryTextListener {

    // Define Views and Others
    private RecyclerView mainRecyclerView;
    private Toolbar toolbar;
    private TextView Nothing;
    private FloatingActionButton fab;
    private MainAdapter adapter;
    private Intent intent;
    private MainViewModel viewModel;
    private API api = ApiUtils.api();
    private List<Notlar> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize variable and others
        Initialize();
        // Set Support actionbar
        setSupportActionBar(toolbar);

        // Data Handler
        viewModel.getNotes().observe(this, new Observer<List<Notlar>>() {
            @Override
            public void onChanged(List<Notlar> notlars) {
                if (notlars !=null){
                    Collections.reverse(notlars);
                    adapter.setNotelist(notlars);
                    mainRecyclerView.setAdapter(adapter);
                    noteList = notlars;
                }else Nothing.setVisibility(View.VISIBLE);
            }
        });

        // to  Note Activity
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("isEmpty",true);
                startActivity(intent);
            }
        });
    }

    private void Initialize() {
        toolbar = findViewById(R.id.toolbarMain);
        Nothing = findViewById(R.id.nothing);
        viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MainViewModel.class);
        mainRecyclerView = findViewById(R.id.mainRecyclerView);
        fab = findViewById(R.id.mainFabIcon);
        //RecyclerView Settings
        mainRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mainRecyclerView.setHasFixedSize(true);
        adapter = new MainAdapter(getApplicationContext(),this,viewModel.getMultiModelList());
        intent = new Intent(getApplicationContext(), noteActivity.class);
    }

    // RecyclerView Item Click
    @Override
    public void itemOnClick(Notlar note) {
        Bundle bundle = new Bundle();
        intent = new Intent(getApplicationContext(),noteActivity.class);
        sendData(note,bundle);
        intent.putExtra("data",bundle);
        intent.putExtra("isEmpty",false);
        startActivity(intent);
    }

    // RecyclerView Delete Click
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

    // Search Note with Retrofit2
    private void searchData(String word){
        api.noteSearch(word).enqueue(new Callback<NoteModel>() {

            @Override
            public void onResponse(Call<NoteModel> call, Response<NoteModel> response) {
                if (response.body() != null && response.body().getNotlar() != null){
                    adapter.setNotelist(response.body().getNotlar());
                }
            }
            @Override
            public void onFailure(Call<NoteModel> call, Throwable t) {
            }
        });
    }

    // Eror Message
    private void erorMessage(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu
        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) searchItem.getActionView();

        // add TextChaneListener for searchData
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        // If searchview is empty, set default notes
       if (newText.length()>0) searchData(newText);
        else   adapter.setNotelist(noteList);
        return true;
    }
}