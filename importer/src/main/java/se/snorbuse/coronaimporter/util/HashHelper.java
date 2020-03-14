package se.snorbuse.coronaimporter.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashHelper {
    public static String sha256(String input) {
        MessageDigest sh256 = createInstance();
        byte[] hash = sh256.digest(input.getBytes());
        return new String(hash);
    }

    private static MessageDigest createInstance() {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Could not create SHA-256 instance", e);
        }
    }
}
