package com.witfactory.witfactory.model;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.witfactory.witfactory.R;

public class MiBottomSheetDialogFragment extends BottomSheetDialogFragment {

    public static MiBottomSheetDialogFragment newInstance() {
        return new MiBottomSheetDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.persistent_bottom_sheet, container, false);
        return v;
    }
}