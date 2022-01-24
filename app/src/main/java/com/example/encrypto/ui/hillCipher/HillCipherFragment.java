package com.example.encrypto.ui.hillCipher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.encrypto.databinding.FragmentHillCipherBinding;

public class HillCipherFragment extends Fragment {

    private FragmentHillCipherBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentHillCipherBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
