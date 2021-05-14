package com.thic.mynotesjava.UI;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.thic.mynotesjava.FontClick;
import com.thic.mynotesjava.R;

public class bottomSheetDialog extends BottomSheetDialogFragment implements FontClick {

    private View root;
    private Typeface roboto,ptsans,josefinsans,bebasneue;
    private RecyclerView verticalRec;

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
    }

    @Override
    public void itemClick(int choose) {
       /* for (FontModel m : myFonts){
            if (m.getFontCode() == choose){
                lorem.setTypeface(m.getFontType());
            }
        }
        typeAdapter.notifySelect(choose); */
    }
}