package com.arkapps.onlinetestapp.auth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arkapps.onlinetestapp.R;

import com.arkapps.onlinetestapp.databinding.FragmentLoginBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class LoginFragment extends BottomSheetDialogFragment {

  private FragmentLoginBinding binding;
    public LoginFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding = FragmentLoginBinding.inflate(inflater,container,false);

       binding.login.setOnClickListener(v -> {

           NavHostFragment.findNavController(this).navigate(R.id.phoneLoginFragment);

       });
       return binding.getRoot();
    }
}