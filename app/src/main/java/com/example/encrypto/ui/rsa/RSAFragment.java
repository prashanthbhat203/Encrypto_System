package com.example.encrypto.ui.rsa;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.encrypto.databinding.FragmentRsaBinding;
import com.example.encrypto.encryptocodes.RSA;

import java.math.BigInteger;

public class RSAFragment extends Fragment {

    private FragmentRsaBinding binding;
    private final RSA rsa = new RSA();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRsaBinding.inflate(inflater, container, false);
        rsa.generatePrimeNumbers();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.pTv.setText("p = " + rsa.p.toString());
        binding.qTv.setText("q = " + rsa.q.toString());

        binding.btnKeyGeneration.setOnClickListener(action -> {
            if (TextUtils.isEmpty(binding.pubLeyEt.getText())) {
                binding.pubKeyLayout.setError("Enter Public Key");
            } else {
                binding.pubKeyLayout.setError(null);
                BigInteger private_key = rsa.keyGeneration(Integer.parseInt(binding.pubLeyEt.getText().toString()));
                binding.privateKeyTv.setText(private_key.toString());
                Log.d("Private", private_key.toString());
            }
        });

        binding.btnEncrypt.setOnClickListener(action -> {
            if (TextUtils.isEmpty(binding.pubLeyEt.getText())) {
                binding.pubKeyLayout.setError("Enter Public Key");
            } else if (TextUtils.isEmpty(binding.plainTextEt.getText())) {
                binding.pubKeyLayout.setError(null);
                binding.plainTextLayoutLayout.setError("Enter Plain Text");
            } else {
                binding.pubKeyLayout.setError(null);
                binding.plainTextLayoutLayout.setError(null);

                BigInteger cipher_text = rsa.encryption(rsa.b_pubKey,
                        Integer.parseInt(binding.plainTextEt.getText().toString()));
                Log.d("Cipher Text", cipher_text.toString());

                binding.cipherTextTv.setText(String.valueOf(cipher_text));
                binding.btnDecrypt.setEnabled(true);

            }
        });

        binding.btnDecrypt.setOnClickListener(action -> {
            if (TextUtils.isEmpty(binding.pubLeyEt.getText())) {
                binding.pubKeyLayout.setError("Enter Public Key");
            } else if (TextUtils.isEmpty(binding.plainTextEt.getText())) {
                binding.pubKeyLayout.setError(null);
                binding.plainTextLayoutLayout.setError("Enter Plain Text");
            } else if (TextUtils.isEmpty(binding.privateKeyTv.getText())) {
                binding.pubKeyLayout.setError(null);
                binding.plainTextLayoutLayout.setError(null);
                Toast.makeText(view.getContext(), "Private Key is not available", Toast.LENGTH_SHORT).show();
            } else {
                binding.pubKeyLayout.setError(null);
                binding.plainTextLayoutLayout.setError(null);

                int plain_text = rsa.decryption(new BigInteger(binding.cipherTextTv.getText().toString()),
                        new BigInteger(binding.privateKeyTv.getText().toString()));

                binding.plainTextTv.setText(String.valueOf(plain_text));
                Log.d("Plain Text", String.valueOf(plain_text));
            }
        });
    }
}
