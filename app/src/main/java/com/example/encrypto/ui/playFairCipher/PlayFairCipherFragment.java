package com.example.encrypto.ui.playFairCipher;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.encrypto.databinding.FragmentPlayFairCipherBinding;
import com.example.encrypto.encryptocodes.PlayFairCipher;

public class PlayFairCipherFragment extends Fragment {

    private FragmentPlayFairCipherBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPlayFairCipherBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnEncrypt.setOnClickListener(action -> {
            if (TextUtils.isEmpty(binding.keyEt.getText())) {
                binding.keyLayout.setError("Enter Key");
            } else if (TextUtils.isEmpty(binding.plainTextEt.getText())) {
                binding.keyLayout.setError(null);
                binding.plainTextLayout.setError("Enter Plain text");
            } else {
                binding.keyLayout.setError(null);
                binding.plainTextLayout.setError(null);
                String key = binding.keyEt.getText().toString();
                String plainText = binding.plainTextEt.getText().toString();
                PlayFairCipher playFairCipher;
                playFairCipher = new PlayFairCipher(key, plainText);

                playFairCipher.cleanPlayFairKey();
                playFairCipher.generateCipherKeyMatrix();

                String cipherText = playFairCipher.encryptMessage();
                binding.cipherTextTv.setText(cipherText.toUpperCase());

                binding.btnDecrypt.setEnabled(true);
            }
        });

        binding.btnDecrypt.setOnClickListener(action -> {
            if (TextUtils.isEmpty(binding.keyEt.getText())) {
                binding.keyLayout.setError("Enter Key");
            } else if (TextUtils.isEmpty(binding.plainTextEt.getText())) {
                binding.keyLayout.setError(null);
                binding.plainTextLayout.setError("Enter Plain text");
            } else if (TextUtils.isEmpty(binding.cipherTextTv.getText())) {
                Toast.makeText(view.getContext(), "Cipher Text is not obtained", Toast.LENGTH_SHORT).show();
            } else {
                binding.keyLayout.setError(null);
                binding.plainTextLayout.setError(null);

                String key = binding.keyEt.getText().toString();
                String decryptText = binding.cipherTextTv.getText().toString();
                PlayFairCipher playFairCipher2;
                playFairCipher2 = new PlayFairCipher(key, decryptText);

                playFairCipher2.cleanPlayFairKey();
                playFairCipher2.generateCipherKeyMatrix();
                playFairCipher2.decryptMessage(decryptText);

                binding.plainTextTv.setText(playFairCipher2.decryptMessage(decryptText).toUpperCase());
            }
        });
    }
}
