package com.thic.mynotesjava.UI;

import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.thic.mynotesjava.R;

public class bottomSheetDialog extends BottomSheetDialogFragment {

  View root;
  RadioGroup textStyle,textColor,backColor;
  TextView lorem;
    Typeface roboto,ptsans,josefinsans,bebasneue;

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
        textStyle = root.findViewById(R.id.TextStyleGroup);
        textColor = root.findViewById(R.id.TextColorGroup);
        backColor = root.findViewById(R.id.BackColorGroup);
        lorem     = root.findViewById(R.id.lorem);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textStyle.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId){
                case R.id.roboto:
                    lorem.setTypeface(roboto);
                    break;
                case R.id.ptsans:
                    lorem.setTypeface(ptsans);
                    break;
                case R.id.josefinsans:
                    lorem.setTypeface(josefinsans);
                    break;
                case R.id.bebasneue:
                    lorem.setTypeface(bebasneue);
                    break;
            }
        });

        textColor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });

        backColor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });
    }
}