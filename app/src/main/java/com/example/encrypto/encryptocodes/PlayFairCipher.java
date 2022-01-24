package com.example.encrypto.encryptocodes;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class PlayFairCipher {

    String key;
    String plainText;
    char[][] matrix = new char[5][5];

    public PlayFairCipher(String key, String plainText) {
        // convert all the characters to lowercase
        this.key = key.toLowerCase();
        this.plainText = plainText.toLowerCase();
    }

    public void cleanPlayFairKey() {
        LinkedHashSet<Character> set
                = new LinkedHashSet<>();

        String newKey = "";

        for (int i = 0; i < key.length(); i++) {
            set.add(key.charAt(i));
        }

        Iterator<Character> it = set.iterator();

        while (it.hasNext()) {
            newKey += it.next();
        }

        key = newKey;
    }

    public void generateCipherKeyMatrix() {
        Set<Character> set = new HashSet();

        for (int i = 0; i < key.length(); i++) {
            if (key.charAt(i) == 'j') {
                continue;
            }
            set.add(key.charAt(i));
        }

        // remove repeated characters from the cipher key
        String tempKey = key;

        for (int i = 0; i < 26; i++) {
            char ch = (char) (i + 97);
            if (ch == 'j') {
                continue;
            }

            if (!set.contains(ch)) {
                tempKey += ch;
            }
        }

        // create cipher key table
        for (int i = 0, idx = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = tempKey.charAt(idx++);
            }
        }

//        System.out.println("Playfair Cipher Key Matrix:");
//
//        for (int i = 0; i < 5; i++) {
//            System.out.println(Arrays.toString(matrix[i]));
//        }
    }

    public String formatPlainText() {
        String message = "";
        int len = plainText.length();

        for (int i = 0; i < len; i++) {
            // if plaintext contains the character 'j',
            // replace it with 'i'
            if (plainText.charAt(i) == 'j') {
                message += 'i';
            } else {
                message += plainText.charAt(i);
            }
        }

        // if two consecutive characters are same, then
        // insert character 'x' in between them
        for (int i = 0; i < message.length(); i += 2) {
            if (i == message.length() - 1) {
                break;
            } else {

                if (message.charAt(i) == message.charAt(i + 1)) {
                    message = message.substring(0, i + 1) + 'x'
                            + message.substring(i + 1);
                }
            }
        }

        // make the plaintext of even length
        if (len % 2 == 1) {
            message += 'x'; // dummy character
        }
        return message;
    }

    public String[] formPairs(String message) {
        int len = message.length();
        String[] pairs = new String[len / 2];

        for (int i = 0, cnt = 0; i < len / 2; i++) {
            pairs[i] = message.substring(cnt, cnt += 2);
        }

        return pairs;
    }

    public int[] getCharPos(char ch) {
        int[] keyPos = new int[2];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                if (matrix[i][j] == ch) {
                    keyPos[0] = i;
                    keyPos[1] = j;
                    break;
                }
            }
        }
        return keyPos;
    }

    public String encryptMessage() {
        String message = formatPlainText();
        String[] msgPairs = formPairs(message);
        String encText = "";

        for (String msgPair : msgPairs) {
            char ch1 = msgPair.charAt(0);
            char ch2 = msgPair.charAt(1);
            int[] ch1Pos = getCharPos(ch1);
            int[] ch2Pos = getCharPos(ch2);
            // if both the characters are in the same row
            if (ch1Pos[0] == ch2Pos[0]) {
                ch1Pos[1] = (ch1Pos[1] + 1) % 5;
                ch2Pos[1] = (ch2Pos[1] + 1) % 5;
            } // if both the characters are in the same column
            else if (ch1Pos[1] == ch2Pos[1]) {
                ch1Pos[0] = (ch1Pos[0] + 1) % 5;
                ch2Pos[0] = (ch2Pos[0] + 1) % 5;
            } // if both the characters are in different rows
            // and columns
            else {
                int temp = ch1Pos[1];
                ch1Pos[1] = ch2Pos[1];
                ch2Pos[1] = temp;
            }
            // get the corresponding cipher characters from
            // the key matrix
            encText = encText + matrix[ch1Pos[0]][ch1Pos[1]]
                    + matrix[ch2Pos[0]][ch2Pos[1]];
        }

        return encText;
    }

    public String decryptMessage(String cipherText) {

        String message = formatPlainText();
        String[] msgPairs = formPairs(message);
        String plTxt = "";

        for (int i = 0; i < msgPairs.length; i++) {
            char ch1 = msgPairs[i].charAt(0);
            char ch2 = msgPairs[i].charAt(1);
            int[] ch1Pos = getCharPos(ch1);
            int[] ch2Pos = getCharPos(ch2);

            // if both the characters are in the same row
            if (ch1Pos[0] == ch2Pos[0]) {
                ch1Pos[1] = (ch1Pos[1] + 4) % 5;
                ch2Pos[1] = (ch2Pos[1] + 4) % 5;
            } // if both the characters are in the same column
            else if (ch1Pos[1] == ch2Pos[1]) {
                ch1Pos[0] = (ch1Pos[0] + 4) % 5;
                ch2Pos[0] = (ch2Pos[0] + 4) % 5;
            } // if both the characters are in different rows
            // and columns
            else {
                int temp = ch1Pos[1];
                ch1Pos[1] = ch2Pos[1];
                ch2Pos[1] = temp;
            }

            // get the corresponding cipher characters from
            // the key matrix
            plTxt = plTxt + matrix[ch1Pos[0]][ch1Pos[1]]
                    + matrix[ch2Pos[0]][ch2Pos[1]];
        }

        return plTxt;
    }
}
