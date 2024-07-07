package classes;

import java.util.Base64;

public class Base64Decoder {
    public static String DecryptContent(String encryptedValue) {
        try {
            String test = encryptedValue.replace("\\n", "");
            byte[] decodeBytes = Base64.getDecoder().decode(test);
            String encryptedTest = new String(decodeBytes);
            return encryptedTest;
        }
        catch (Exception exp) {
            return null;
        }
    }
}
