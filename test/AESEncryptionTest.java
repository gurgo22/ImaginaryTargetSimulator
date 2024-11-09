import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import its.AESEncrypter;

public class AESEncryptionTest {
    
    public AESEncryptionTest() {}
    
    @Test
    public void testEncryptionDecryption() {
        
        String original = "Data that needs to be encrypted";
        
        String encrypted = AESEncrypter.Encrypt(original);
        String decrypted = AESEncrypter.Decrypt(encrypted);
        
        assertEquals(original, decrypted, "Decrypted value should match the original.");
    }
    
    @Test
    public void testEncryptionIsDifferent() {
        
        String original = "Data that needs to be encrypted";
        String encrypted = AESEncrypter.Encrypt(original);
        
        assertNotEquals(original, encrypted, "Encrypted value should be different from the original.");
    }
}
