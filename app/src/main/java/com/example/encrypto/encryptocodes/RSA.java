package com.example.encrypto.encryptocodes;

import android.util.Log;

import java.math.BigInteger;
import java.util.Random;

public class RSA {

    public BigInteger n, p, q;
    public BigInteger b_pubKey;

    public void generatePrimeNumbers() {
        Random r1 = new Random(System.currentTimeMillis());
        Random r2 = new Random(System.currentTimeMillis() * 10);

        p = BigInteger.probablePrime(32, r1);
        q = BigInteger.probablePrime(32, r2);
    }

    public BigInteger keyGeneration(int publicKey) {

        n = new BigInteger(String.valueOf(p.multiply(q)));
        Log.d("n value", n.toString());

        BigInteger p_1 = p.subtract(new BigInteger("1"));
        BigInteger q_1 = q.subtract(new BigInteger("1"));
        BigInteger p_1q_1 = p_1.multiply(q_1);

        while (true) {
            BigInteger GCD = p_1q_1.gcd(new BigInteger("" + publicKey));

            if (GCD.equals(BigInteger.ONE))
                break;
            publicKey++;
        }

        //Key Generation
        b_pubKey = new BigInteger("" + publicKey);
        Log.d("Public key", b_pubKey.toString());
        BigInteger b_privKey = b_pubKey.modInverse(p_1q_1);

        return b_privKey;
    }

    public BigInteger encryption(BigInteger b_pubKey, int plainText) {

        int asciival = Integer.parseInt(String.valueOf(plainText));  //user input
        BigInteger b_val = new BigInteger("" + asciival);
        BigInteger cipher_val = b_val.modPow(b_pubKey, n);

        return cipher_val;
    }

    public int decryption(BigInteger cipher_val, BigInteger b_privKey) {
        //Decryption
        BigInteger b_plainVal = cipher_val.modPow(b_privKey, n);
        int plainVal = b_plainVal.intValue();

        return plainVal;
    }
}
