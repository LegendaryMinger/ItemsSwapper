import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Main {
    public static void main(String[] args) {
        JsonArray mainFolder = GetJsonArrayFromString("https://api.github.com/repos/EXBO-Studio/stalcraft-database/contents/ru/items/weapon");
        PrintJsonArray(mainFolder, "name");
    }
    public static JsonArray GetJsonArrayFromString(String sUrl) {
        try {
            URLConnection urlConnection = new URL(sUrl).openConnection();
            urlConnection.connect();
            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = jsonParser.parse(new InputStreamReader((InputStream) urlConnection.getContent())).getAsJsonArray();
            return jsonArray;
        }
        catch (Exception exp) {
            return null;
        }
    }
    public static void PrintJsonArray(JsonArray jsonArray, String parameter) {
        for (JsonElement jsonElement : jsonArray) {
            System.out.println(jsonElement.getAsJsonObject().get(parameter).toString());
        }
    }
}