package com.thic.mynotesjava.UI;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
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
import com.thic.mynotesjava.ViewModel.BottomSheetViewModel;
import com.thic.mynotesjava.ViewModel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class bottomSheetDialog extends BottomSheetDialogFragment implements PaletteClicks {

    //Define Views and others
    private View root;
    private TextView lorem;
    private RecyclerView verticalRec;
    private VerticalAdapter adapter;
    //private List<MultiModel> multiModels;
    private ChooseState state;
    private CardView cardView;
    private materialListener mListener;
    private BottomSheetViewModel viewModel;

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
        viewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(BottomSheetViewModel.class);

        if (state == null) state = new ChooseState(1,2,1);

        adapter = new VerticalAdapter(getActivity().getApplicationContext(),viewModel.getMultiModelList(),state,this);
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

    // This interface for recyclerView clicks
    public interface materialListener{
        void fontType(int position,Typeface typeFace);
        void textColor(int position,int color);
        void backgroundColor(int position,int color);
    }

    // for Interface
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