package common;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Base64;

/* Класс для расшифровки base64 и преобразования расшифрованного значения в JsonObject */
public class Base64Decoder {
    /* Метод для расшифровки base64 и преобразования расшифрованного значения в JsonObject */
    public static JsonObject DecryptContent(String encryptedValue) {
        try {
            byte[] decryptBytesFromValue = Base64.getDecoder().decode(encryptedValue.replace("\n", ""));
            String decryptedValue = new String(decryptBytesFromValue);
            JsonObject jsonObject = JsonParser.parseString(decryptedValue).getAsJsonObject();
            return jsonObject;
        }
        catch (Exception exp) {
            return null;
        }
    }
}
