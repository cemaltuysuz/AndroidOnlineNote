package com.thic.mynotesjava.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thic.Repository.NotesRepository;
import com.thic.mynotesjava.Model.Notlar;

import java.util.List;

public class MainViewModel extends AndroidViewModel{
   //Note List
   private NotesRepository repository;
   private  LiveData<List<Notlar>> notes;

   public MainViewModel(@NonNull Application application) {
      super(application);
      repository = new NotesRepository();
      this.notes = repository.getNotes();

   }

   public LiveData<List<Notlar>> getNotes() {
      return notes;
   }

   /*
   public MutableLiveData<List<Notlar>> getNotes() {
      return notes;
   }

   public void setNotes(List<Notlar> notes) {
      this.notes.setValue(notes);
   } */
   /**
    *
    *  Repository sınıfı oluşturup buradaki viewmodeli repositry sınıfı ile bağladım , burada bulunan MutableliveData'yı LiveDataya çevirdim.
    *  set ve get Fonksiyonlarını yorum satırına aldım liveData için yeniden set ve get oluşturuyorum.
    *
    * */


}
