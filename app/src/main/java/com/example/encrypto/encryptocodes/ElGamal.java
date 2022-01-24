package com.example.encrypto.encryptocodes;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class ElGamal {

    public BigInteger privateKey, publicKey, cipherText2, cipherText1;
    public Random sc = new SecureRandom();
    public BigInteger secretKey = new BigInteger("12345678901234567890");
    public BigInteger alpha = new BigInteger("3");

    public void publicKeyGeneration() {
        privateKey = BigInteger.probablePrime(64, sc);
        publicKey = alpha.modPow(secretKey, privateKey);
    }

    public void encryption(String plaintText) {
        BigInteger X_plainText = new BigInteger(plaintText);
        BigInteger random_k = new BigInteger(64, sc);
        cipherText2 = X_plainText.multiply(publicKey.modPow(random_k, privateKey)).mod(privateKey);
        cipherText1 = alpha.modPow(random_k, privateKey);
    }

    public BigInteger decryption() {
        BigInteger K = cipherText1.modPow(secretKey, privateKey);
        BigInteger K_inverse = K.modInverse(privateKey);
        BigInteger plainText = K_inverse.multiply(cipherText2).mod(privateKey);
        return plainText;
    }


}
