package se.snorbuse.coronaimporter.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashHelper {

    private static MessageDigest sh256;

    public static String sha256(String input) {
        if (sh256 == null) {
            sh256 = createInstance();
        }

        byte[] hash = sh256.digest(input.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hash);
    }

    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private static MessageDigest createInstance() {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Could not create SHA-256 instance", e);
        }
    }
}
