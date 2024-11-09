package its;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class AESEncrypter {
    
    private static final String ALGORITHM = "AES";
    private static final String KEY = "Avery99secureSecret10key";

    public static SecretKey GenerateKey () throws Exception {
        
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(128);
        
        return keyGen.generateKey();
    }

    //ENCRYPTS THE GIVEN STRING DATA
    public static String Encrypt (String data) {
        
        String encryptedPassword = "";
        
        try {
            SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
            
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            
            byte[] encryptedData = cipher.doFinal(data.getBytes());
            
            encryptedPassword = Base64.getEncoder().encodeToString(encryptedData);
            
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException exc) {
            System.out.println("Encryption Error: " + exc);
        }
        
        return encryptedPassword;
    }

    //DECRYPTS THE GIVEN STRING DATA
    public static String Decrypt (String encryptedData) {
        
        String decryptedData = "";
        
        try {
            
            SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
            
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            
            byte[] decodedData = Base64.getDecoder().decode(encryptedData);
            byte[] originalData = cipher.doFinal(decodedData);
            
            decryptedData = new String(originalData);
            
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException exc) {
            
            System.out.println("Encryption Error: " + exc);
        }
        
        return decryptedData;
    }
}
