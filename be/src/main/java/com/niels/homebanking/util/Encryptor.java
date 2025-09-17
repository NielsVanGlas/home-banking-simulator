package com.niels.homebanking.util;

import jakarta.persistence.AttributeConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Component
public class Encryptor implements AttributeConverter<String, String> {

    private static SecretKey key;
    private static Cipher cipher;

    public Encryptor(@Value("${app.symmetric.secret}") String secretParam, @Value("${app.symmetric.salt}") String saltParam) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException {
        key = getKeyFromPassword(secretParam, saltParam);
        cipher = Cipher.getInstance("AES/CTR/NoPadding");
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return attribute != null ? encrypt(attribute) : null;
    }

    @Override
    public String convertToEntityAttribute(String data) {
        return data != null ? decrypt(data) : null;
    }

    public String encrypt(String input) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(new byte[cipher.getBlockSize()]));
            byte[] cipherText = cipher.doFinal(input.getBytes());
            return Base64.getEncoder().encodeToString(cipherText);
        } catch (Exception ex) {
            return null;
        }
    }

    public String decrypt(String input) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[cipher.getBlockSize()]));
            byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(input));
            return new String(plainText);
        } catch (Exception ex) {
            return null;
        }
    }

    private SecretKey getKeyFromPassword(String password, String salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 40968, 256);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }

}
