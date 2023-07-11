package com.adaptris.interview.jce;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.*;
import java.io.*;
import java.security.spec.KeySpec;
import java.util.Base64;

public class EncryptorImp implements Encryptor {

    /** The salt to use for encryption parameters
     */
    byte[] SALT =
            {
                    (byte) 0x4c, (byte) 0x45, (byte) 0x57, (byte) 0x49, (byte) 0x4e,
                    (byte) 0x43, (byte) 0x48, (byte) 0x41
            };

    /** The interation count for initialising the encryption parameters.
     */
    int ITERATIONS = 20;
    /** The algorithm to use when encrypting or decrypting.
     */
    String ALGORITHM = "PBEWithMD5AndDES";

    @Override
    public String encrypt(String plaintext) throws Exception {
        byte[] encryptedBytes = encrypt(plaintext, "UTF-8", "password".toCharArray());
        return convertToBase64(encryptedBytes);
    }

    @Override
    public String convertToBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    @Override
    public byte[] encrypt(String plain, String charset, char[] password) throws GeneralSecurityException, UnsupportedEncodingException {
        KeySpec keySpec = new PBEKeySpec(password, SALT, ITERATIONS);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey key = factory.generateSecret(keySpec);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] plainBytes = plain.getBytes(charset);
        byte[] encryptedBytes = cipher.doFinal(plainBytes);
        return encryptedBytes;
    }

}