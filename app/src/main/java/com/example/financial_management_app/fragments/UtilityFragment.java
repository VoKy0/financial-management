package com.example.financial_management_app.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.financial_management_app.R;
import com.example.financial_management_app.viewmodels.UtilityViewModel;

public class UtilityFragment extends Fragment {

    private UtilityViewModel mViewModel;

    public static UtilityFragment newInstance() {
        return new UtilityFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_utility, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(UtilityViewModel.class);
        // TODO: Use the ViewModel
    }

}