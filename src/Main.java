import classes.Base64Decoder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.Scanner;

public class Main {
    public static String MAIN_FOLDER_URL = "https://api.github.com/repos/EXBO-Studio/stalcraft-database/contents/ru/items"; // Ссылка на корень базы данных предметов
    public static int CURRENT_LEVEL = 0; // Значение текущего уровня категории
    public static void main(String[] args) {
        PrintJsonArray(GetJsonArrayFromString(MAIN_FOLDER_URL), "name");
    }
    /* Метод для получения JSON-массива, используя ссылку (API) */
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
    public static JsonObject GetJsonObjectFromString (String sUrl) {
        try {
            URLConnection urlConnection = new URL(sUrl).openConnection();
            urlConnection.connect();
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = jsonParser.parse(new InputStreamReader((InputStream) urlConnection.getContent())).getAsJsonObject();
            return jsonObject;
        }
        catch (Exception exp) {
            return null;
        }
    }
    /* Метод для получения введенного значения в консоль. Если введено не число, то рекурсивный вызов этого же метода */
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
    /* Метод для вывода значений JSON-массива в консоль*/
    public static void PrintJsonArray(JsonArray jsonArray, String parameter) {
        int count = 1;
        for (JsonElement jsonElement : jsonArray) {
            System.out.println((count++) + " - " + jsonElement.getAsJsonObject().get(parameter).toString());
        }
        System.out.println("\nИспользуйте -1 для возвращения к списку категорий оружия, 0 для возвращения к предыдущему списку, n для перехода к выбранному значению:\n");
        Scanner scanner = new Scanner(System.in);
        RedicretJsonArray(jsonArray, GetRedicretKey());
    }
    public static void PrintJsonObject(JsonObject jsonObject) {
        System.out.println(jsonObject);
        System.out.println("\nИспользуйте -1 для возвращения к списку категорий оружия, 0 для возвращения к предыдущему списку, n для перехода к выбранному значению:\n");
        Scanner scanner = new Scanner(System.in);
        RedicretJsonArray(null, GetRedicretKey());
    }
    /* Метод для перехода между категориями */
    public static void RedicretJsonArray(JsonArray jsonArray, int redicretKey) {
        switch (redicretKey) {
            case -1 ->  {
                CURRENT_LEVEL = 0;
                PrintJsonArray(GetJsonArrayFromString(MAIN_FOLDER_URL), "name");
            }
            case 0 ->  {
                CURRENT_LEVEL--;
                PrintJsonArray(jsonArray, "name");
            }
            default -> {
                if (CURRENT_LEVEL == 2) {
                    JsonObject targetObject = GetJsonObjectFromString(jsonArray.get(redicretKey - 1).getAsJsonObject().getAsJsonPrimitive("url").getAsString());
                    JsonObject targetObjectContent = Base64Decoder.DecryptContent(targetObject.getAsJsonPrimitive("content").getAsString());
                    PrintJsonObject(targetObjectContent);
                }
                else {
                    CURRENT_LEVEL++;
                    JsonArray targetArray = GetJsonArrayFromString(jsonArray.get(redicretKey - 1).getAsJsonObject().getAsJsonPrimitive("url").getAsString());
                    PrintJsonArray(targetArray, "name");
                }
            }
        }
    }
}