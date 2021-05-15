package com.thic.mynotesjava.UI;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.thic.mynotesjava.Adapter.PaletteAdapters.VerticalAdapter;
import com.thic.mynotesjava.Model.PaletteModels.BackColorModel;
import com.thic.mynotesjava.Model.PaletteModels.ChooseState;
import com.thic.mynotesjava.Model.PaletteModels.FontModel;
import com.thic.mynotesjava.Model.PaletteModels.MultiModel;
import com.thic.mynotesjava.Model.PaletteModels.TextColorModel;
import com.thic.mynotesjava.PaletteClicks;
import com.thic.mynotesjava.R;

import java.util.ArrayList;
import java.util.List;

public class bottomSheetDialog extends BottomSheetDialogFragment implements PaletteClicks {

    /**
     * @author cemaltuysuz
     * @version 1.0
     *
     * To-Do : bu fragmentın consturtor'ında ChooseState parametresini ver. Gelen ChooseState ile shadowing işlemini gerçekleştir.
     *
     * */

    //Define Views and others
    private View root;
    private TextView lorem;
    private Typeface roboto,ptsans,josefinsans,bebasneue;
    private RecyclerView verticalRec;
    private VerticalAdapter adapter;
    private List<MultiModel> multiModels;
    private ChooseState state;
    private CardView cardView;
    private materialListener mListener;

    // Empty Constructor
    public bottomSheetDialog() {
    }
    // Has State parameter Constructor
    public bottomSheetDialog(ChooseState state) {
        this.state = state;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inıtialize Variables
        createTypeFaces();

        //state = new ChooseState(1,2,1);
        multiModels = new ArrayList<>();
        fillTheMainArray(multiModels);
        if (state == null) state = new ChooseState(1,2,1);

        adapter = new VerticalAdapter(getActivity().getApplicationContext(),multiModels,state,this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_bottom_sheet_dialog, container, false);
        Initialize(root);
        return root;
    }
    // TextColor Selected in HorizontalAdapter ->
    @Override
    public void textColorClick(int colorCode, int code) {
    lorem.setTextColor(colorCode);
    mListener.textColor(code,colorCode);
    }
    // BackGroundColor Selected in HorizontalAdapter ->
    @Override
    public void backColorClick(int colorCode, int code) {
    cardView.setCardBackgroundColor(colorCode);
    mListener.backgroundColor(code,colorCode);
    }

    // FontType Selected in HorizontalAdapter
    @Override
    public void fontTypeClick(Typeface typeface, int code) {
    lorem.setTypeface(typeface);
    mListener.fontType(code,typeface);
    }


    // Initialize Variable and others
    private void Initialize(View root) {
        verticalRec = root.findViewById(R.id.VerticalRecycler);
        verticalRec.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        verticalRec.setAdapter(adapter);
        lorem = root.findViewById(R.id.lorem);
        cardView = root.findViewById(R.id.loremCard);
    }

    // Add lists to MultiModelList
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

    // Fill the BackgroundColorList
    private void fillTheBackColorList(List<Object> backColorList) {
        backColorList.add(new BackColorModel(1,"beyaz", Color.parseColor("#FFFFFFFF")));
        backColorList.add(new BackColorModel(2,"siyahB", Color.parseColor("#FF000000")));
        backColorList.add(new BackColorModel(3,"yeşilB", Color.parseColor("#FF018786")));
        backColorList.add(new BackColorModel(4,"maviB", Color.parseColor("#FF3700B3")));
    }

    // Fill the TextColorList
    private void fillTheTextColorList(List<Object> textColorList) {
        textColorList.add(new TextColorModel(1,"beyaz", Color.parseColor("#FFFFFFFF")));
        textColorList.add(new TextColorModel(2,"siyah", Color.parseColor("#FF000000")));
        textColorList.add(new TextColorModel(3,"yeşil",Color.parseColor("#FF018786")));
        textColorList.add(new TextColorModel(4,"mavi",Color.parseColor("#FF3700B3")));
    }

    // Fill the FontTypeArray
    private void fillTheFontArray(List<Object> fontLists) {
        fontLists.add(new FontModel(1,"Roboto",roboto));
        fontLists.add(new FontModel(2,"PtSans",ptsans));
        fontLists.add(new FontModel(3,"JosefinSans",josefinsans));
        fontLists.add(new FontModel(4,"BebasNeue",bebasneue));
    }

    // Create TypeFaces for Font Types
    private void createTypeFaces() {
        roboto = Typeface.createFromAsset(getActivity().getAssets(),"fonts/roboto.ttf");
        ptsans = Typeface.createFromAsset(getActivity().getAssets(),"fonts/ptsans.ttf");
        josefinsans = Typeface.createFromAsset(getActivity().getAssets(),"fonts/josefinsans.ttf");
        bebasneue = Typeface.createFromAsset(getActivity().getAssets(),"fonts/bebasneue.ttf");
    }

    public interface materialListener{
        void fontType(int position,Typeface typeFace);
        void textColor(int position,int color);
        void backgroundColor(int position,int color);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (materialListener)context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement BottomSheet");
        }
    }
}