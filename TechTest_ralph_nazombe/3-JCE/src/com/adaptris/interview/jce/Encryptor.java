/*
 *
 */

package com.adaptris.interview.jce;

import java.security.*;
import java.io.*;

/**
 */
public interface Encryptor {

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

  String encrypt(String plaintext) throws Exception;

  /** Convert the given byte array into a base64 encoded string.
   */
  String convertToBase64(byte[] bytes);

  /** Encrypt the given plain text.
   * @param plain the plain text to encrypt.
   * @param  charset the text is in.
   * @param password the password for encryption.
   */
  public byte[] encrypt(String plain, String charset, char[] password) throws GeneralSecurityException, UnsupportedEncodingException;

}