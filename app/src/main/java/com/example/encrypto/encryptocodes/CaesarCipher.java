package com.example.encrypto.encryptocodes;

public class CaesarCipher {

    public StringBuffer encrypt(String plainText, int shift) {
        StringBuffer result = new StringBuffer();

        for (int i = 0; i < plainText.length(); i++) {
            if (Character.isUpperCase(plainText.charAt(i))) {
                char ch = (char) (((int) plainText.charAt(i) + shift - 65) % 26 + 65);
                result.append(ch);
            } else {
                char ch = (char) (((int) plainText.charAt(i) + shift - 97) % 26 + 97);
                result.append(ch);
            }
        }
        return result;
    }

    public StringBuffer decrypt(String ciperText, int shift) {
        StringBuffer result = new StringBuffer();

        for (int i = 0; i < ciperText.length(); i++) {
            if (Character.isUpperCase(ciperText.charAt(i))) {
                char ch = (char) (((int) ciperText.charAt(i) - shift - 65) % 26 + 65);
                result.append(ch);
            } else {
                char ch = (char) (((int) ciperText.charAt(i) - shift - 97) % 26 + 97);
                result.append(ch);
            }
        }
        return result;
    }
}
