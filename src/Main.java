import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class Main {
    public static String MAIN_FOLDER_URL = "https://api.github.com/repos/EXBO-Studio/stalcraft-database/contents/ru/items/weapon";
    public static void main(String[] args) {
        JsonArray mainFolder = GetJsonArrayFromString(MAIN_FOLDER_URL);
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
    public static int GetRedicretKey() {
        try {
            Scanner scanner = new Scanner(System.in);
            int redicretKey = scanner.nextInt();
            return redicretKey;
        }
        catch (Exception exp) {
            System.out.println("\nОшибка значения. Повтрите ввод:\n");
            return GetRedicretKey();
        }
    }
    public static void PrintJsonArray(JsonArray jsonArray, String parameter) {
        int count = 1;
        for (JsonElement jsonElement : jsonArray) {
            System.out.println((count++) + " - " + jsonElement.getAsJsonObject().get(parameter).toString());
        }
        System.out.println("\nИспользуйте -1 для возвращения к списку категорий оружия, 0 для возвращения к предыдущему списку, n для перехода к выбранному значению:\n");
        Scanner scanner = new Scanner(System.in);
        RedicretJsonArray(jsonArray, GetRedicretKey());
    }
    public static void RedicretJsonArray(JsonArray jsonArray, int redicretKey) {
        switch (redicretKey) {
            case -1 -> PrintJsonArray(GetJsonArrayFromString(MAIN_FOLDER_URL), "name");
            case 0 -> PrintJsonArray(jsonArray, "name");
            default -> {
                JsonArray targetArray = GetJsonArrayFromString(jsonArray.get(redicretKey - 1).getAsJsonObject().getAsJsonPrimitive("url").getAsString());
                PrintJsonArray(targetArray, "name");
            }
        }
    }
}