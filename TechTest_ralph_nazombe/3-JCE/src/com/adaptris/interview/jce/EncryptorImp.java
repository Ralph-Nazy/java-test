package com.adaptris.interview.jce;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.util.Base64;

public class EncryptorImp implements Encryptor {

    @Override
    public String encrypt(String plaintext) throws Exception {
        if (plaintext == null) {
            throw new IllegalArgumentException("Plaintext cannot be null");
        }

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec("password".toCharArray()));

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(SALT, ITERATIONS));

        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes("UTF-8"));
        return convertToBase64(encryptedBytes);
    }

    @Override
    public String convertToBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    @Override
    public byte[] encrypt(String plain, String charset, char[] password) throws GeneralSecurityException, UnsupportedEncodingException {
        if (plain == null || charset == null || password == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(password));

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(SALT, ITERATIONS));

        return cipher.doFinal(plain.getBytes(charset));
    }
}
