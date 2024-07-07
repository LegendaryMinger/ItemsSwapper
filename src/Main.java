import common.Base64Decoder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static String MAIN_FOLDER_URL = "https://api.github.com/repos/EXBO-Studio/stalcraft-database/contents/ru/items"; // Ссылка на корень базы данных предметов
    public static JsonObject CURRENT_WEAPON = new JsonObject(); // Хранение Json-объекта текущего оружия
    public static JsonObject CURRENT_ARMOR = null; // Хранение Json-объекта текущей брони
    public static int CURRENT_LEVEL = 0; // Значение текущего уровня категории
    public static void main(String[] args) {
        RedicretController();
    }
    /* Контроллер для управления переходами между разделами или категориями */
    public static void RedicretController() {
        JsonArray jsonArray = new JsonArray();
        for (JsonElement jsonElement : GetJsonArrayFromString(MAIN_FOLDER_URL)) {
            if (jsonElement.getAsJsonObject().getAsJsonPrimitive("name").getAsString().matches("armor||weapon")) {
                jsonArray.add(jsonElement);
            }
        }
        PrintJsonArray(jsonArray, "name");
        if (CURRENT_WEAPON != null) {
            System.out.println("Введите gun для перехода к текущему оружию");
        }
        if (CURRENT_ARMOR != null) {
            System.out.println("Введите gun для перехода к текущему оружию");
        }

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
        RedicretBetweenJsons(jsonArray, GetRedicretKey());
    }
    public static void PrintJsonObject(JsonObject jsonObject) {
        System.out.println("Выбранное оружие изменено\nПолученный Json-объект: " + jsonObject);
        System.out.println("\nИспользуйте -1 для возвращения к списку категорий оружия, 0 для возвращения к предыдущему списку, n для перехода к выбранному значению:\n");
        Scanner scanner = new Scanner(System.in);
        RedicretBetweenJsons(null, GetRedicretKey());
    }
    /* Метод для перехода между категориями */
    public static void RedicretBetweenJsons(JsonArray jsonArray, int redicretKey) {
        switch (redicretKey) {
            case -1 ->  {
                CURRENT_LEVEL = 0;
                RedicretController();
            }
            case 0 ->  {
                CURRENT_LEVEL--;
                RedicretController();
            }
            default -> {
                if (CURRENT_LEVEL == 2) {
                    JsonObject targetObject = GetJsonObjectFromString(jsonArray.get(redicretKey - 1).getAsJsonObject().getAsJsonPrimitive("url").getAsString());
                    JsonObject targetObjectFromContent = Base64Decoder.DecryptContent(targetObject.getAsJsonPrimitive("content").getAsString());
                    CURRENT_WEAPON = targetObjectFromContent;
                    PrintJsonObject(targetObjectFromContent);
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