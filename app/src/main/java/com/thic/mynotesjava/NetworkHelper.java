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
    public  API api = ApiUtils.api();

    // All Notes
    public  void getData(){
        api.getData().enqueue(new Callback<NoteModel>() {
            @Override
            public void onResponse(Call<NoteModel> call, Response<NoteModel> response) {
                if (response.body().getSuccess()>0){
                    MainViewModel.notes.setValue(response.body().getNotlar());
                }
            }

            @Override
            public void onFailure(Call<NoteModel> call, Throwable t) {
                Log.d("Fail","Msg : "+ t.getMessage());
            }
        });
    }

    // Insert notes
    public  void insertData(Notlar note){
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

    // Update Notes
    public  void updateData(Notlar note){
        api.noteUpdate(String.valueOf(noteActivity.noteID),note.getNoteTitle(),note.getNoteContent()
                ,note.getNoteFontType(),note.getNoteTextColor(),note.getNoteBackColor())
                .enqueue(new Callback<NoteModel>() {
                    @Override
                    public void onResponse(Call<NoteModel> call, Response<NoteModel> response) {
                        NoteActivityViewModel.isSucces.setValue(response.body().getSuccess());
                    }
                    @Override
                    public void onFailure(Call<NoteModel> call, Throwable t) {
                        NoteActivityViewModel.isSucces.setValue(0);
                        Log.d("fail response : ", t.getMessage());
                    }
                });
    }
    public void deleteData (String noteID){
        api.noteDelete(noteID).enqueue(new Callback<NoteModel>() {
            @Override
            public void onResponse(Call<NoteModel> call, Response<NoteModel> response) {
                if (response.body().getSuccess()>0){
                    MainViewModel.delInfo.setValue(Integer.parseInt(noteID));
                }
                Log.d("success",response.body().getSuccess().toString());
            }

            @Override
            public void onFailure(Call<NoteModel> call, Throwable t) {
            Log.d("failll",t.getMessage());
            }
        });
    }

    public void searchData (String word){
        api.noteDelete(word).enqueue(new Callback<NoteModel>() {
            @Override
            public void onResponse(Call<NoteModel> call, Response<NoteModel> response) {
                if (response.body().getSuccess()>0){
                    if (response.body().getNotlar().size()>0){
                        MainViewModel.notes.setValue(response.body().getNotlar());
                    }

                }
            }

            @Override
            public void onFailure(Call<NoteModel> call, Throwable t) {
                Log.d("failll",t.getMessage());
            }
        });
    }
}
