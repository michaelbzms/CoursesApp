package Util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;


public class Hashing {

    // Note: For better security we should probably keep a SALT per user but this is too simple an app

    /* WARNING: Do not change hashSalt if the database isn't empty! *
     * Doing so will render user's accounts inaccessible!           */
    private static final String hashSalt = "thisIsASalt";

    public static String getHashSHA256(String text) {
        try {
            // Append pre-defined salt to text then hash it using SHA-256 algorithm
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String saltedText = text + hashSalt;
            byte[] hash = digest.digest(saltedText.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (Exception e) {
            System.err.println("Failed to hash. Returning original text.");
            e.printStackTrace();
            return text;
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte byt : bytes) {
            result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }

}
