package com.common.util;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.IntDef;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * by y on 22/11/2017.
 */

public class AESUtils {
    private final static String SHA1_PRNG = "SHA1PRNG";
    private static final int KEY_SIZE = 32;

    /**
     * Aes加密/解密
     *
     * @param content  字符串
     * @param password 密钥
     * @param type     加密：{@link Cipher#ENCRYPT_MODE}，解密：{@link Cipher#DECRYPT_MODE}
     * @return 加密/解密结果字符串
     */
    @SuppressLint({"DeletedProvider", "GetInstance"})
    public static String des(String content, String password, @AESType int type) {
        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(password)) {
            return null;
        }
        try {
            SecretKeySpec secretKeySpec;
            if (Build.VERSION.SDK_INT >= 28) {
                secretKeySpec = deriveKeyInsecurely(password);
            } else {
                secretKeySpec = fixSmallVersion(password);
            }
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(type, secretKeySpec);
            if (type == Cipher.ENCRYPT_MODE) {
                byte[] byteContent = content.getBytes("utf-8");
                return parseByte2HexStr(cipher.doFinal(byteContent));
            } else {
                byte[] byteContent = parseHexStr2Byte(content);
                return new String(cipher.doFinal(byteContent));
            }
        } catch (NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException |
                UnsupportedEncodingException | InvalidKeyException | NoSuchPaddingException |
                NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressLint("DeletedProvider")
    private static SecretKeySpec fixSmallVersion(String password) throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            secureRandom = SecureRandom.getInstance(SHA1_PRNG, new CryptoProvider());
        } else {
            secureRandom = SecureRandom.getInstance(SHA1_PRNG, "Crypto");
        }
        secureRandom.setSeed(password.getBytes());
        generator.init(128, secureRandom);
        byte[] enCodeFormat = generator.generateKey().getEncoded();
        return new SecretKeySpec(enCodeFormat, "AES");
    }

    private static SecretKeySpec deriveKeyInsecurely(String password) {
        byte[] passwordBytes = password.getBytes(StandardCharsets.US_ASCII);
        return new SecretKeySpec(InsecureSHA1PRNGKeyDerivator.deriveInsecureKey(passwordBytes, AESUtils.KEY_SIZE), "AES");
    }

    private static String parseByte2HexStr(byte buf[]) {
        StringBuilder sb = new StringBuilder();
        for (byte b : buf) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    @IntDef({Cipher.ENCRYPT_MODE, Cipher.DECRYPT_MODE})
    @interface AESType {
    }

    private static final class CryptoProvider extends Provider {
        CryptoProvider() {
            super("Crypto", 1.0, "HARMONY (SHA1 digest; SecureRandom; SHA1withDSA signature)");
            put("SecureRandom.SHA1PRNG", "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl");
            put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
        }
    }
}
