package com.example.financial_management_app.fragments;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.financial_management_app.R;
import com.example.financial_management_app.adapters.TransactionLedgerAdapter;
import com.example.financial_management_app.databinding.FragmentTransactionLedgerBinding;
import com.example.financial_management_app.viewmodels.TransactionLedgerViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class TransactionLedgerFragment extends Fragment {
    private FragmentTransactionLedgerBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TransactionLedgerViewModel transactionLedgerViewModel =
                new ViewModelProvider(this).get(TransactionLedgerViewModel.class);

        binding = FragmentTransactionLedgerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setupViewPager(binding.viewPager);
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        return root;
    }

    private void setupViewPager(ViewPager viewPager) {
        TransactionLedgerAdapter adapter = new TransactionLedgerAdapter(getChildFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new OverviewFragment(), "Tổng quan");
        adapter.addFragment(new TransactionFragment(), "Giao dịch");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}