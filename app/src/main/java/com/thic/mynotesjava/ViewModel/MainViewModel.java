package com.thic.mynotesjava.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.thic.mynotesjava.Repository.NotesRepository;
import com.thic.mynotesjava.Model.Notlar;
import com.thic.mynotesjava.Model.PaletteModels.MultiModel;

import java.util.List;

public class MainViewModel extends AndroidViewModel{
   //Note List
   private NotesRepository repository;
   private List<MultiModel> multiModelList;
   private  LiveData<List<Notlar>> notes;

   public MainViewModel(@NonNull Application application) {
      super(application);
      repository = new NotesRepository(application);
      this.notes = repository.getNotes();
      this.multiModelList = repository.getMultiModelList();

   }

   public LiveData<List<Notlar>> getNotes() {
      return notes;
   }
   public List<MultiModel> getMultiModelList() {
      return multiModelList;
   }
}
