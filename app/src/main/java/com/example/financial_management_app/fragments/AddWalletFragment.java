    package com.example.financial_management_app.fragments;

    import androidx.lifecycle.Observer;
    import androidx.lifecycle.ViewModelProvider;

    import android.content.Context;
    import android.content.SharedPreferences;
    import android.os.Bundle;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;
    import androidx.navigation.NavController;
    import androidx.navigation.Navigation;

    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;

    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.Spinner;
    import android.widget.TextView;

    import com.example.financial_management_app.R;
    import com.example.financial_management_app.viewmodels.WalletViewModel;

    public class AddWalletFragment extends Fragment {

        private WalletViewModel mViewModel;
        private EditText edt_wallet_name;
        private Spinner spn_wallet_category;
        private EditText edt_wallet_balance;
        private Button btn_create_wallet;
        private TextView res;


        public static AddWalletFragment newInstance() {
            return new AddWalletFragment();
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_add_wallet, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            edt_wallet_name = view.findViewById(R.id.edt_wallet_name);
            spn_wallet_category = view.findViewById(R.id.spn_wallet_category);
            edt_wallet_balance = view.findViewById(R.id.edt_wallet_balance);
            btn_create_wallet = (Button) view.findViewById(R.id.btn_create_wallet);

            mViewModel = new ViewModelProvider(this).get(WalletViewModel.class);

            res = view.findViewById(R.id.tv_res);

            btn_create_wallet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String wallet_name = edt_wallet_name.getText().toString();
                    String wallet_category = spn_wallet_category.getSelectedItem().toString();
                    Double wallet_balance = Double.parseDouble(edt_wallet_balance.getText().toString());

                    // Lấy account_id từ SharedPreferences
                    SharedPreferences sharedPref = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    int account_id = sharedPref.getInt("account_id", -1);

                    res.setText(account_id + " - " + wallet_name + " - " + wallet_category + " - " + wallet_balance);

                    mViewModel.createWallet(account_id, wallet_name, wallet_category, wallet_balance);
                }
            });

            // Quan sát LiveData để điều hướng sau khi thêm ví thành công
            mViewModel.getWalletAdded().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean walletAdded) {
                    if (walletAdded) {
                        // Điều hướng trở lại trang hiển thị thông tin ví
                        NavController navController = Navigation.findNavController(view);
                        navController.navigate(R.id.action_AddWalletFragment_to_WalletFragment);

                        // Đặt lại trạng thái của walletAdded để tránh điều hướng không cần thiết
                        mViewModel.resetWalletAdded();
                    }
                }
            });
        }

    }