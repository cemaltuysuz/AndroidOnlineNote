package com.thic.mynotesjava.UI;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.thic.mynotesjava.Adapter.PaletteAdapters.VerticalAdapter;
import com.thic.mynotesjava.Model.PaletteModels.BackColorModel;
import com.thic.mynotesjava.Model.PaletteModels.FontModel;
import com.thic.mynotesjava.Model.PaletteModels.MultiModel;
import com.thic.mynotesjava.Model.PaletteModels.TextColorModel;
import com.thic.mynotesjava.R;

import java.util.ArrayList;
import java.util.List;

public class bottomSheetDialog extends BottomSheetDialogFragment  {

    private View root;
    private Typeface roboto,ptsans,josefinsans,bebasneue;
    private RecyclerView verticalRec;
    private VerticalAdapter adapter;
    private List<MultiModel> multiModels;

    public bottomSheetDialog() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        roboto = Typeface.createFromAsset(getActivity().getAssets(),"fonts/roboto.ttf");
        ptsans = Typeface.createFromAsset(getActivity().getAssets(),"fonts/ptsans.ttf");
        josefinsans = Typeface.createFromAsset(getActivity().getAssets(),"fonts/josefinsans.ttf");
        bebasneue = Typeface.createFromAsset(getActivity().getAssets(),"fonts/bebasneue.ttf");
        multiModels = new ArrayList<>();
        fillTheMainArray(multiModels);
        adapter = new VerticalAdapter(getActivity().getApplicationContext(),multiModels);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_bottom_sheet_dialog, container, false);
        Initialize(root);
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private void Initialize(View root) {
    verticalRec = root.findViewById(R.id.VerticalRecycler);
    verticalRec.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false));
    verticalRec.setAdapter(adapter);
    }

    private void fillTheMainArray(List<MultiModel> multiModels) {

        List<Object> fontList  = new ArrayList<>();
        fillTheFontArray(fontList);

        List<Object> textColorList = new ArrayList<>();
        fillTheTextColorList(textColorList);

        List<Object> backColorList = new ArrayList<>();
        fillTheBackColorList(backColorList);

        multiModels.add(new MultiModel("Text Style", fontList));
        multiModels.add(new MultiModel("Text Color", textColorList));
        multiModels.add(new MultiModel("Background Color", backColorList));

    }

    private void fillTheBackColorList(List<Object> backColorList) {
        backColorList.add(new BackColorModel(1,"siyahB", Color.parseColor("#FF000000")));
        backColorList.add(new BackColorModel(2,"yeşilB", Color.parseColor("#FF018786")));
        backColorList.add(new BackColorModel(3,"maviB", Color.parseColor("#FF3700B3")));
    }

    private void fillTheTextColorList(List<Object> textColorList) {
        textColorList.add(new TextColorModel(1,"siyah", Color.parseColor("#FF000000")));
        textColorList.add(new TextColorModel(2,"yeşil",Color.parseColor("#FF018786")));
        textColorList.add(new TextColorModel(3,"mavi",Color.parseColor("#FF3700B3")));
    }

    private void fillTheFontArray(List<Object> fontLists) {
        fontLists.add(new FontModel(1,"Roboto",roboto));
        fontLists.add(new FontModel(2,"PtSans",ptsans));
        fontLists.add(new FontModel(3,"JosefinSans",josefinsans));
        fontLists.add(new FontModel(4,"BebasNeue",bebasneue));
    }

    /* @Override
    public void itemClick(int choose) {
       for (FontModel m : myFonts){
            if (m.getFontCode() == choose){
                lorem.setTypeface(m.getFontType());
            }
        }
        typeAdapter.notifySelect(choose);
    } */
}