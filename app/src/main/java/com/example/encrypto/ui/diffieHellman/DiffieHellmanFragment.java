package com.example.encrypto.ui.diffieHellman;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.encrypto.databinding.FragmentDiffieHellmanBinding;
import com.example.encrypto.encryptocodes.diffiehellman.DiffieHellman;

import java.math.BigInteger;

public class DiffieHellmanFragment extends Fragment {

    private FragmentDiffieHellmanBinding binding;
    private final DiffieHellman diffieHellman = new DiffieHellman();

    BigInteger alicePrivateKey;
    BigInteger bobPrivateKey;

    BigInteger alicePublicKey;
    BigInteger bobPublicKey;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentDiffieHellmanBinding.inflate(inflater, container, false);
        diffieHellman.genPrimeAndPrimitiveRoot();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BigInteger p = diffieHellman.getP();
        BigInteger alpha = diffieHellman.getG();

        binding.random1Tv.setText("q = " + p.toString());
        binding.random2Tv.setText("alpha = " + alpha.toString());

        binding.generatePublicKeyBtn.setOnClickListener(action -> {
            if (TextUtils.isEmpty(binding.alicePrivateKeyEt.getText())) {
                binding.alicePrivateKeyLayout.setError("Enter Private Key");
            } else if (TextUtils.isEmpty(binding.bobPrivateKeyEt.getText())) {
                binding.alicePrivateKeyLayout.setError(null);
                binding.bobPrivateKeyLayout.setError("Enter Private Key");
            } else {
                binding.alicePrivateKeyLayout.setError(null);
                binding.bobPrivateKeyLayout.setError(null);
                if (Integer.parseInt(p.toString()) > Integer.parseInt(binding.alicePrivateKeyEt.getText().toString()) && Integer.parseInt(alpha.toString()) > Integer.parseInt(binding.bobPrivateKeyEt.getText().toString())) {
                    binding.alicePrivateKeyLayout.setError("Public key should be greater than q");
                    binding.bobPrivateKeyLayout.setError("Public key should be greater than q");
                } else {
                    alicePrivateKey = BigInteger.valueOf(Long.parseLong(binding.alicePrivateKeyEt.getText().toString()));
                    bobPrivateKey = BigInteger.valueOf(Long.parseLong(binding.bobPrivateKeyEt.getText().toString()));

                    alicePublicKey = diffieHellman.getAliceMessage(alicePrivateKey);
                    bobPublicKey = diffieHellman.getBobMessage(bobPrivateKey);

                    binding.alicePublicKeyTv.setText("Alice Public key = " + alicePublicKey.toString());
                    binding.bobPublicKeyTv.setText("Bob Public key = " + bobPublicKey.toString());

                    binding.generateSharedKeyBtn.setEnabled(true);
                }

            }
        });

        binding.generateSharedKeyBtn.setOnClickListener(action -> {

            BigInteger aliceCalculatedKey = diffieHellman.aliceCalculationOfKey(bobPublicKey, alicePrivateKey);
            BigInteger bobCalculatedKey = diffieHellman.bobCalculationOfKey(alicePublicKey, bobPrivateKey);

            binding.aliceSharedKeyTv.setText("Alice Shared key = " + aliceCalculatedKey.toString());
            binding.bobSharedKeyTv.setText("Bob Shared key = " + bobCalculatedKey.toString());
        });


    }
}
