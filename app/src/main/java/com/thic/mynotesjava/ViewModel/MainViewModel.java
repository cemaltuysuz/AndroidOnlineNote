package com.thic.mynotesjava.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thic.mynotesjava.Model.Models.Notlar;

import java.util.List;

public class MainViewModel extends ViewModel {
   public static MutableLiveData<List<Notlar>> notes  = new MutableLiveData<>();
   public static MutableLiveData<Notlar> clickInfo    = new MutableLiveData<>();
}
