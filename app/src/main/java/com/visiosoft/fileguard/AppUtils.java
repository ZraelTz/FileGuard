package com.visiosoft.fileguard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AppUtils {
    public static Map<String, Map<String, String>> encMap;
    public static Map<String, String> innerMap;
    public static ArrayList<String> encryptedField = new ArrayList<String>();
    public static String encryptedText;

    public static String generateEncryptionKey() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 !@#$%^&*()_+{}|\":?><-=[]\\\'";
        int len = characters.length();
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char randomXter = characters.charAt(rnd.nextInt(characters.length()));
            sb.append(randomXter);
            characters = characters.replace(Character.toString(randomXter), "");


        }
        return sb.toString();
    }

    public static String encryptText(String encKey, String field, String text) {
        int text_length = text.length();
        int j = 0;
        int p = 0;
        int length_checker = 0;
        StringBuilder scriptlets = new StringBuilder();
        StringBuilder encrypted_text = new StringBuilder();
        encMap = new HashMap<String, Map<String, String>>();
        innerMap = new HashMap<String, String>();
        encryptedText = text;


        for (int i = 0; i < text_length; i++) {
            j++;
            length_checker++;
            scriptlets.append(text.charAt(i));
            if (j == 4) {
                j = 0;
                innerMap.put(Character.toString(encKey.charAt(p)), scriptlets.toString());
                encrypted_text.append(Character.toString(encKey.charAt(p)));
                scriptlets.setLength(0);
                p++;
            }

            if (length_checker == (text_length - 1) && scriptlets.length() > 0) {
                innerMap.put(Character.toString(encKey.charAt(p)), scriptlets.toString());
                encrypted_text.append(Character.toString(encKey.charAt(p)));
            }
        }
            encryptedField.add(field + ": " + text);

//        encryptedField.add(innerMap.toString());
        encMap.put(field, innerMap);
        return encrypted_text.toString();
    }

    public static String decryptText() {
//        String decryptedString;
//        StringBuilder decryptedChars = new StringBuilder();
//
//        for (int i = 0; i < encryptedField.size(); i++) {
//            decryptedChars.append(encryptedField.get(i));
//        }
//
//        return decryptedChars.toString();
        return encryptedField.toString().replaceAll(",", "\r\n");
    }
}