package com.thic.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.thic.mynotesjava.Model.NoteModel;
import com.thic.mynotesjava.Model.Notlar;
import com.thic.mynotesjava.Retrofit.API;
import com.thic.mynotesjava.Retrofit.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotesRepository {

    private API api;

    public NotesRepository() {
        api = ApiUtils.api();
    }
    public LiveData<List<Notlar>> getNotes(){

        MutableLiveData<List<Notlar>> data = new MutableLiveData<>();

        api.getData().enqueue(new Callback<NoteModel>() {
            @Override
            public void onResponse(Call<NoteModel> call, Response<NoteModel> response) {
                if (response.body() != null){
                    data.setValue(response.body().getNotlar());
                }
            }

            @Override
            public void onFailure(Call<NoteModel> call, Throwable t) {
                    data.setValue(null);
            }
        });
        return data;
    }
}
