package com.thic.mynotesjava.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.thic.mynotesjava.Repository.NotesRepository;
import com.thic.mynotesjava.Model.PaletteModels.MultiModel;

import java.util.List;

public class BottomSheetViewModel extends AndroidViewModel {

    private NotesRepository repository;
    private List<MultiModel> multiModelList;

    public BottomSheetViewModel(@NonNull Application application) {
        super(application);
        repository = new NotesRepository(application);
        this.multiModelList = repository.getMultiModelList();
    }

    public List<MultiModel> getMultiModelList() {
        return multiModelList;
    }
}
