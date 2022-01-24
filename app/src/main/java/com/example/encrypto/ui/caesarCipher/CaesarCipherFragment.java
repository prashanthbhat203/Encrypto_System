package com.example.encrypto.ui.caesarCipher;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.encrypto.databinding.FragmentCaesarCipherBinding;
import com.example.encrypto.encryptocodes.CaesarCipher;

public class CaesarCipherFragment extends Fragment {

    private FragmentCaesarCipherBinding binding;
    private final CaesarCipher caesarCipher = new CaesarCipher();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCaesarCipherBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnEncrypt.setOnClickListener(action -> {
            if (TextUtils.isEmpty(binding.plainTextEt.getText())) {
                binding.plainTextLayout.setError("Cannot be empty");
            } else if (TextUtils.isEmpty(binding.shiftEt.getText())) {
                binding.plainTextLayout.setError(null);
                binding.shiftLayout.setError("Enter number");
            } else {
                binding.shiftLayout.setError(null);
                String plainText = binding.plainTextEt.getText().toString();
                int shift = Integer.parseInt(binding.shiftEt.getText().toString());
                StringBuffer cipherText = caesarCipher.encrypt(plainText, shift);
                binding.cipherTextTv.setText(cipherText.toString());
                binding.btnDecrypt.setEnabled(true);
            }
        });

        binding.btnDecrypt.setOnClickListener(action -> {
            if (TextUtils.isEmpty(binding.plainTextEt.getText())) {
                binding.plainTextLayout.setError("Cannot be empty");
            } else if (TextUtils.isEmpty(binding.shiftEt.getText())) {
                binding.shiftLayout.setError("Enter number");
            } else if (TextUtils.isEmpty(binding.cipherTextTv.getText())) {
                Toast.makeText(view.getContext(), "Cipher Text is not available", Toast.LENGTH_SHORT).show();
            } else {
                StringBuffer decryptText = caesarCipher.decrypt(binding.cipherTextTv.getText().toString(),
                        Integer.parseInt(binding.shiftEt.getText().toString()));
                binding.plainTextTv.setText(decryptText.toString());
            }
        });
    }
}

