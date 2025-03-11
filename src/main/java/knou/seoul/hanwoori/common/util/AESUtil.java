package knou.seoul.hanwoori.common.util;

import knou.seoul.hanwoori.common.exception.EncryptionException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESUtil {
    private static final String KEY_ALGORITHM = "AES";
    //AES KEY 는 16바이트,24바이트,32바이트만 지원됨
    private static final byte[] SECRET_KEY = "hanwoori.seoul.k".getBytes();

    public static String encrypt(String plainText) {
        try {
            //null 체크
            plainText = (plainText == null) ? "" : plainText;

            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            SecretKey secretKey = new SecretKeySpec(SECRET_KEY, KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);

        } catch (Exception e) {
            throw new EncryptionException("AES encryption failed", e);
        }

    }

    public static String decrypt(String cipherText) {
        try {
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            SecretKey secretKey = new SecretKeySpec(SECRET_KEY, KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(cipherText));
            return new String(decrypted);

        } catch (Exception e) {
            throw new EncryptionException("AES decryption failed", e);
        }

    }


}
