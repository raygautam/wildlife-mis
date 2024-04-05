package in.gov.forest.wildlifemis.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class AESEncryptionUsingSalt {
    private static final int AES_KEY_SIZE = 128;
    private static final String SECRET_KEY = "Wildlife-mis2024";

    public static String encrypt(String plaintext) throws Exception {
        byte[] salt = generateSalt();
        SecretKeySpec key = new SecretKeySpec(generateAESKey(salt), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedData = cipher.doFinal(plaintext.getBytes());
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String encodedData = Base64.getEncoder().encodeToString(encryptedData);
        return encodedSalt + ":" + encodedData;
    }

    public static String decrypt(String encryptedText) throws Exception {
        String[] parts = encryptedText.split(":");
        byte[] salt = Base64.getDecoder().decode(parts[0]);
        byte[] encryptedData = Base64.getDecoder().decode(parts[1]);
        SecretKeySpec key = new SecretKeySpec(generateAESKey(salt), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedData = cipher.doFinal(encryptedData);
        return new String(decryptedData);
    }

    private static byte[] generateAESKey(byte[] salt) {
        byte[] key = new byte[AES_KEY_SIZE / 8];
        SecureRandom secureRandom = new SecureRandom(SECRET_KEY.getBytes());
        secureRandom.nextBytes(key);
        for (int i = 0; i < key.length; i++) {
            key[i] ^= salt[i % salt.length];
        }
        return key;
    }

    private static byte[] generateSalt() {
        byte[] salt = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        return salt;
    }
}
