package com.thic.mynotesjava;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.thic.mynotesjava.Model.Models.NoteModel;
import com.thic.mynotesjava.Model.Models.Notlar;
import com.thic.mynotesjava.Model.Network.API;
import com.thic.mynotesjava.Model.Network.ApiUtils;
import com.thic.mynotesjava.ViewModel.MainViewModel;
import com.thic.mynotesjava.ViewModel.NoteActivityViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkHelper {
    public static API api = ApiUtils.api();

    public static void getData(){
        api.getData().enqueue(new Callback<NoteModel>() {
            @Override
            public void onResponse(Call<NoteModel> call, Response<NoteModel> response) {
                MainViewModel.notes.setValue(response.body().getNotlar());
                Log.d("Succesfullyyy","Boyut :" +response.body().getNotlar().size() );
            }

            @Override
            public void onFailure(Call<NoteModel> call, Throwable t) {
                Log.d("Fail","Msg : "+ t.getMessage());
            }
        });
    }

    public static void insertData(Notlar note){
        api.noteInsert(note.getNoteTitle(),note.getNoteContent(),note.getNoteDate()
                ,note.getNoteFontType(),note.getNoteTextColor(),note.getNoteBackColor())
                .enqueue(new Callback<NoteModel>() {
            @Override
            public void onResponse(Call<NoteModel> call, Response<NoteModel> response) {
                NoteActivityViewModel.isSucces.setValue(response.body().getSuccess());
                noteActivity.noteID = Integer.parseInt(response.body().getNotlar().get(0).getNoteId());
            }
            @Override
            public void onFailure(Call<NoteModel> call, Throwable t) {
                NoteActivityViewModel.isSucces.setValue(0);
                Log.d("fail response : ", t.getMessage());
            }
        });
    }
}
