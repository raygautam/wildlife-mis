package in.gov.forest.wildlifemis.util;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class CustomEncryption {
    private static final int AES_KEY_SIZE = 128;
    private static final String SECRET_KEY = "Wildlife-mis2024"; // 16 bytes secret key for AES

    public static String encrypt(String plaintext) throws Exception {
        SecretKeySpec key = new SecretKeySpec(generateAESKey(SECRET_KEY), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedData = cipher.doFinal(plaintext.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public static String decrypt(String encryptedText, String secretKey) throws Exception {
        SecretKeySpec key = new SecretKeySpec(generateAESKey(secretKey), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedData);
    }

    private static byte[] generateAESKey(String secretKey) {
        byte[] key = new byte[AES_KEY_SIZE / 8];
        SecureRandom secureRandom = new SecureRandom(secretKey.getBytes());
        secureRandom.nextBytes(key);
        return key;
    }






    // Encryption method
//    public static String encrypt(String plaintext, int shift) {
//        StringBuilder ciphertext = new StringBuilder();
//
//        for (int i = 0; i < plaintext.length(); i++) {
//            char ch = plaintext.charAt(i);
//            if (Character.isLetter(ch)) {
//                // Shift the character by the specified number of positions
//                char shiftedChar = (char) (ch + shift);
//                // Handle wrap-around for lowercase letters
//                if (Character.isLowerCase(ch) && shiftedChar > 'z') {
//                    shiftedChar = (char) ('a' + (shiftedChar - 'z' - 1));
//                }
//                // Handle wrap-around for uppercase letters
//                else if (Character.isUpperCase(ch) && shiftedChar > 'Z') {
//                    shiftedChar = (char) ('A' + (shiftedChar - 'Z' - 1));
//                }
//                ciphertext.append(shiftedChar);
//            } else {
//                ciphertext.append(ch); // Leave non-letter characters unchanged
//            }
//        }
//
//        return ciphertext.toString();
//    }
//
//    // Decryption method
//    public static String decrypt(String ciphertext, int shift) {
//        StringBuilder plaintext = new StringBuilder();
//
//        for (int i = 0; i < ciphertext.length(); i++) {
//            char ch = ciphertext.charAt(i);
//            if (Character.isLetter(ch)) {
//                // Shift the character back by the specified number of positions
//                char shiftedChar = (char) (ch - shift);
//                // Handle wrap-around for lowercase letters
//                if (Character.isLowerCase(ch) && shiftedChar < 'a') {
//                    shiftedChar = (char) ('z' - ('a' - shiftedChar - 1));
//                }
//                // Handle wrap-around for uppercase letters
//                else if (Character.isUpperCase(ch) && shiftedChar < 'A') {
//                    shiftedChar = (char) ('Z' - ('A' - shiftedChar - 1));
//                }
//                plaintext.append(shiftedChar);
//            } else {
//                plaintext.append(ch); // Leave non-letter characters unchanged
//            }
//        }
//
//        return plaintext.toString();
//    }
//
//    public static void main(String[] args) {
//        String plaintext = "Hello, World!";
//        int shift = 3;
//
//        // Encryption
//        String encryptedText = encrypt(plaintext, shift);
//        System.out.println("Encrypted Text: " + encryptedText);
//
//        // Decryption
//        String decryptedText = decrypt(encryptedText, shift);
//        System.out.println("Decrypted Text: " + decryptedText);
//    }
}

