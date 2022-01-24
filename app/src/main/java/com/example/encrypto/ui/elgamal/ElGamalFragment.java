package com.example.encrypto.ui.elgamal;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.encrypto.databinding.FragmentElgamalBinding;
import com.example.encrypto.encryptocodes.ElGamal;

import java.math.BigInteger;

public class ElGamalFragment extends Fragment {

    private FragmentElgamalBinding binding;
    private ElGamal elGamal = new ElGamal();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentElgamalBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.qTv.setText("q = " + elGamal.secretKey.toString());
        binding.alphaTv.setText("alpha = " + elGamal.alpha.toString());

        binding.generatePublicKeyBtn.setOnClickListener(action -> {
            elGamal.publicKeyGeneration();
            binding.publicKeyTv.setText("Public key = " + elGamal.publicKey.toString());
        });

        binding.btnEncrypt.setOnClickListener(action -> {
            if (TextUtils.isEmpty(binding.plainTextEt.getText())) {
                binding.plainTextLayout.setError("Enter Plain Text");
            } else {
                binding.plainTextLayout.setError(null);
                elGamal.encryption(binding.plainTextEt.getText().toString());

                binding.cipherTextTv.setText("(c1, c2) = " + "( " + elGamal.cipherText1.toString() +
                        ", " +
                        elGamal.cipherText2.toString() + ")");

                binding.btnDecrypt.setEnabled(true);
            }
        });

        binding.btnDecrypt.setOnClickListener(action -> {
            BigInteger plainText = elGamal.decryption();

            binding.plainTextTv.setText("Plain Text = " + plainText.toString());
        });
    }
}
