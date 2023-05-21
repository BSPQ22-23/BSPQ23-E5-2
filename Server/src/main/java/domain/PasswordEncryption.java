package domain;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A simple class to encrypt passwords
 *
 */
public class PasswordEncryption {
	/**
	 * Encryption for passwords in the server side 
	 * @param password password to encrypt
	 * @return the password encrypted
	 */
    public static String encryptPassword(String password) {
        try {
            // Create a SHA-256 hash object
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Convert the password to bytes and hash it
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            // Convert the hash to a hexadecimal string and return it
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle the exception
            e.printStackTrace();
            return null;
        }
    }
}

