package com.thic.mynotesjava.Repository;

import android.app.Application;
import android.graphics.Color;
import android.graphics.Typeface;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.thic.mynotesjava.Model.NoteModel;
import com.thic.mynotesjava.Model.Notlar;
import com.thic.mynotesjava.Model.PaletteModels.BackColorModel;
import com.thic.mynotesjava.Model.PaletteModels.FontModel;
import com.thic.mynotesjava.Model.PaletteModels.MultiModel;
import com.thic.mynotesjava.Model.PaletteModels.TextColorModel;
import com.thic.mynotesjava.Retrofit.API;
import com.thic.mynotesjava.Retrofit.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotesRepository {

    /**
     * This class data layer (Repository)
     * @link https://developer.android.com/jetpack/guide
     * @author cemaltuysuz
     * @version 1.0
     * */

    private API api;

    // MultiModelList Define ( Multimodel for FontTypes , TextColor , BackgroundColors)
    private List<MultiModel>multiModelList = new ArrayList<>();

    private List<Object> fontList = new ArrayList<>();
    private List<Object> textColorList = new ArrayList<>();
    private List<Object> backColorList = new ArrayList<>();

    public NotesRepository(Application application) {
        materials(application);
        api = ApiUtils.api();
    }
    // MultiModel List get Method
    public List<MultiModel> getMultiModelList() {
        return multiModelList;
    }

    // Get Notes with Retrofit2
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

    // Fill the multimodel list
    private void materials(Application application) {

        Typeface roboto      =  Typeface.createFromAsset(application.getApplicationContext().getAssets(),"fonts/roboto.ttf");
        Typeface ptsans      =  Typeface.createFromAsset(application.getApplicationContext().getAssets(),"fonts/ptsans.ttf");
        Typeface josefinsans =  Typeface.createFromAsset(application.getApplicationContext().getAssets(),"fonts/josefinsans.ttf");
        Typeface bebasneue   =  Typeface.createFromAsset(application.getApplicationContext().getAssets(),"fonts/bebasneue.ttf");

        fontList.add(new FontModel(1,"Roboto",roboto));
        fontList.add(new FontModel(2,"PtSans",ptsans));
        fontList.add(new FontModel(3,"JosefinSans",josefinsans));
        fontList.add(new FontModel(4,"BebasNeue",bebasneue));

        textColorList.add(new TextColorModel(1,"White", Color.parseColor("#FFFFFFFF")));
        textColorList.add(new TextColorModel(2,"Black", Color.parseColor("#FF000000")));
        textColorList.add(new TextColorModel(3,"Blue_200",  Color.parseColor("#4168f6")));
        textColorList.add(new TextColorModel(4,"Green", Color.parseColor("#FF018786")));
        textColorList.add(new TextColorModel(5,"Black_200", Color.parseColor("#181c25")));

        backColorList.add(new BackColorModel(1,"Black_200", Color.parseColor("#181c25")));
        backColorList.add(new BackColorModel(2,"Blue_200",  Color.parseColor("#4168f6")));
        backColorList.add(new BackColorModel(3,"Green", Color.parseColor("#FF018786")));
        backColorList.add(new BackColorModel(4,"Black", Color.parseColor("#FF000000")));
        backColorList.add(new BackColorModel(5,"purple", Color.parseColor("#FF3700B3")));

        multiModelList.add(new MultiModel("Fonts",fontList));
        multiModelList.add(new MultiModel("Text Colors",textColorList));
        multiModelList.add(new MultiModel("Background Colors",backColorList));


    }
}
