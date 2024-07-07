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
//        PrintJsonArray(GetJsonArrayFromString(MAIN_FOLDER_URL), "name");
        JsonObject test = Base64Decoder.DecryptContent("ewogICJpZCI6ICIwcjJnMSIsCiAgImNhdGVnb3J5IjogIndlYXBvbi9hc3Nh\\ndWx0X3JpZmxlIiwKICAibmFtZSI6IHsKICAgICJ0eXBlIjogInRyYW5zbGF0\\naW9uIiwKICAgICJrZXkiOiAiaXRlbS53cG4uOWE5MS5uYW1lIiwKICAgICJh\\ncmdzIjoge30sCiAgICAibGluZXMiOiB7CiAgICAgICJydSI6ICI50JAtOTEi\\nLAogICAgICAiZW4iOiAiOdCQLTkxIgogICAgfQogIH0sCiAgImNvbG9yIjog\\nIlJBTktfVkVURVJBTiIsCiAgInN0YXR1cyI6IHsKICAgICJzdGF0ZSI6ICJQ\\nRVJTT05BTF9PTl9VU0UiCiAgfSwKICAiaW5mb0Jsb2NrcyI6IFsKICAgIHsK\\nICAgICAgInR5cGUiOiAibGlzdCIsCiAgICAgICJ0aXRsZSI6IHsKICAgICAg\\nICAidHlwZSI6ICJ0ZXh0IiwKICAgICAgICAidGV4dCI6ICIiCiAgICAgIH0s\\nCiAgICAgICJlbGVtZW50cyI6IFsKICAgICAgICB7CiAgICAgICAgICAidHlw\\nZSI6ICJrZXktdmFsdWUiLAogICAgICAgICAgImtleSI6IHsKICAgICAgICAg\\nICAgInR5cGUiOiAidHJhbnNsYXRpb24iLAogICAgICAgICAgICAia2V5Ijog\\nImNvcmUudG9vbHRpcC5pbmZvLnJhbmsiLAogICAgICAgICAgICAiYXJncyI6\\nIHt9LAogICAgICAgICAgICAibGluZXMiOiB7CiAgICAgICAgICAgICAgInJ1\\nIjogItCg0LDQvdCzIiwKICAgICAgICAgICAgICAiZW4iOiAiUmFuayIKICAg\\nICAgICAgICAgfQogICAgICAgICAgfSwKICAgICAgICAgICJ2YWx1ZSI6IHsK\\nICAgICAgICAgICAgInR5cGUiOiAidHJhbnNsYXRpb24iLAogICAgICAgICAg\\nICAia2V5IjogImNvcmUucmFuay52ZXRlcmFuIiwKICAgICAgICAgICAgImFy\\nZ3MiOiB7fSwKICAgICAgICAgICAgImxpbmVzIjogewogICAgICAgICAgICAg\\nICJydSI6ICLQktC10YLQtdGA0LDQvSIsCiAgICAgICAgICAgICAgImVuIjog\\nIlZldGVyYW4iCiAgICAgICAgICAgIH0KICAgICAgICAgIH0KICAgICAgICB9\\nLAogICAgICAgIHsKICAgICAgICAgICJ0eXBlIjogImtleS12YWx1ZSIsCiAg\\nICAgICAgICAia2V5IjogewogICAgICAgICAgICAidHlwZSI6ICJ0cmFuc2xh\\ndGlvbiIsCiAgICAgICAgICAgICJrZXkiOiAiY29yZS50b29sdGlwLmluZm8u\\nY2F0ZWdvcnkiLAogICAgICAgICAgICAiYXJncyI6IHt9LAogICAgICAgICAg\\nICAibGluZXMiOiB7CiAgICAgICAgICAgICAgInJ1IjogItCa0LvQsNGB0YEi\\nLAogICAgICAgICAgICAgICJlbiI6ICJDbGFzcyIKICAgICAgICAgICAgfQog\\nICAgICAgICAgfSwKICAgICAgICAgICJ2YWx1ZSI6IHsKICAgICAgICAgICAg\\nInR5cGUiOiAidHJhbnNsYXRpb24iLAogICAgICAgICAgICAia2V5IjogImNv\\ncmUuaGFuZGJvb2suY2F0ZWdvcnkuYXNzYXVsdF9yaWZsZSIsCiAgICAgICAg\\nICAgICJhcmdzIjoge30sCiAgICAgICAgICAgICJsaW5lcyI6IHsKICAgICAg\\nICAgICAgICAicnUiOiAi0JDQstGC0L7QvNCw0YLRiyIsCiAgICAgICAgICAg\\nICAgImVuIjogIkFzc2F1bHQgUmlmbGVzIgogICAgICAgICAgICB9CiAgICAg\\nICAgICB9CiAgICAgICAgfSwKICAgICAgICB7CiAgICAgICAgICAidHlwZSI6\\nICJudW1lcmljIiwKICAgICAgICAgICJuYW1lIjogewogICAgICAgICAgICAi\\ndHlwZSI6ICJ0cmFuc2xhdGlvbiIsCiAgICAgICAgICAgICJrZXkiOiAiY29y\\nZS50b29sdGlwLmluZm8ud2VpZ2h0IiwKICAgICAgICAgICAgImFyZ3MiOiB7\\nfSwKICAgICAgICAgICAgImxpbmVzIjogewogICAgICAgICAgICAgICJydSI6\\nICLQktC10YEiLAogICAgICAgICAgICAgICJlbiI6ICJXZWlnaHQiCiAgICAg\\nICAgICAgIH0KICAgICAgICAgIH0sCiAgICAgICAgICAidmFsdWUiOiAyLjI5\\nLAogICAgICAgICAgImZvcm1hdHRlZCI6IHsKICAgICAgICAgICAgInZhbHVl\\nIjogewogICAgICAgICAgICAgICJydSI6ICIyLDI5INC60LMiLAogICAgICAg\\nICAgICAgICJlbiI6ICIyLjI5IGtnIgogICAgICAgICAgICB9LAogICAgICAg\\nICAgICAibmFtZUNvbG9yIjogIjgzODM4MyIsCiAgICAgICAgICAgICJ2YWx1\\nZUNvbG9yIjogIjgzODM4MyIKICAgICAgICAgIH0KICAgICAgICB9LAogICAg\\nICAgIHsKICAgICAgICAgICJ0eXBlIjogIm51bWVyaWMiLAogICAgICAgICAg\\nIm5hbWUiOiB7CiAgICAgICAgICAgICJ0eXBlIjogInRyYW5zbGF0aW9uIiwK\\nICAgICAgICAgICAgImtleSI6ICJjb3JlLnRvb2x0aXAuaW5mby5kdXJhYmls\\naXR5IiwKICAgICAgICAgICAgImFyZ3MiOiB7fSwKICAgICAgICAgICAgImxp\\nbmVzIjogewogICAgICAgICAgICAgICJydSI6ICLQn9GA0L7Rh9C90L7RgdGC\\n0YwiLAogICAgICAgICAgICAgICJlbiI6ICJEdXJhYmlsaXR5IgogICAgICAg\\nICAgICB9CiAgICAgICAgICB9LAogICAgICAgICAgInZhbHVlIjogMTAwLjAs\\nCiAgICAgICAgICAiZm9ybWF0dGVkIjogewogICAgICAgICAgICAidmFsdWUi\\nOiB7CiAgICAgICAgICAgICAgInJ1IjogIjEwMCUiLAogICAgICAgICAgICAg\\nICJlbiI6ICIxMDAlIgogICAgICAgICAgICB9LAogICAgICAgICAgICAibmFt\\nZUNvbG9yIjogIjgzODM4MyIsCiAgICAgICAgICAgICJ2YWx1ZUNvbG9yIjog\\nIjgzODM4MyIKICAgICAgICAgIH0KICAgICAgICB9LAogICAgICAgIHsKICAg\\nICAgICAgICJ0eXBlIjogIm51bWVyaWMiLAogICAgICAgICAgIm5hbWUiOiB7\\nCiAgICAgICAgICAgICJ0eXBlIjogInRyYW5zbGF0aW9uIiwKICAgICAgICAg\\nICAgImtleSI6ICJjb3JlLnRvb2x0aXAuaW5mby5tYXhfZHVyYWJpbGl0eSIs\\nCiAgICAgICAgICAgICJhcmdzIjoge30sCiAgICAgICAgICAgICJsaW5lcyI6\\nIHsKICAgICAgICAgICAgICAicnUiOiAi0JzQsNC60YEuINC/0YDQvtGH0L3Q\\nvtGB0YLRjCIsCiAgICAgICAgICAgICAgImVuIjogIk1heC4gZHVyYWJpbGl0\\neSIKICAgICAgICAgICAgfQogICAgICAgICAgfSwKICAgICAgICAgICJ2YWx1\\nZSI6IDEwMC4wLAogICAgICAgICAgImZvcm1hdHRlZCI6IHsKICAgICAgICAg\\nICAgInZhbHVlIjogewogICAgICAgICAgICAgICJydSI6ICIxMDAlIiwKICAg\\nICAgICAgICAgICAiZW4iOiAiMTAwJSIKICAgICAgICAgICAgfSwKICAgICAg\\nICAgICAgIm5hbWVDb2xvciI6ICI4MzgzODMiLAogICAgICAgICAgICAidmFs\\ndWVDb2xvciI6ICI4MzgzODMiCiAgICAgICAgICB9CiAgICAgICAgfQogICAg\\nICBdCiAgICB9LAogICAgewogICAgICAidHlwZSI6ICJsaXN0IiwKICAgICAg\\nInRpdGxlIjogewogICAgICAgICJ0eXBlIjogInRleHQiLAogICAgICAgICJ0\\nZXh0IjogIiIKICAgICAgfSwKICAgICAgImVsZW1lbnRzIjogW10KICAgIH0s\\nCiAgICB7CiAgICAgICJ0eXBlIjogImxpc3QiLAogICAgICAidGl0bGUiOiB7\\nCiAgICAgICAgInR5cGUiOiAidGV4dCIsCiAgICAgICAgInRleHQiOiAiIgog\\nICAgICB9LAogICAgICAiZWxlbWVudHMiOiBbCiAgICAgICAgewogICAgICAg\\nICAgInR5cGUiOiAia2V5LXZhbHVlIiwKICAgICAgICAgICJrZXkiOiB7CiAg\\nICAgICAgICAgICJ0eXBlIjogInRyYW5zbGF0aW9uIiwKICAgICAgICAgICAg\\nImtleSI6ICJ3ZWFwb24udG9vbHRpcC53ZWFwb24uaW5mby5hbW1vX3R5cGUi\\nLAogICAgICAgICAgICAiYXJncyI6IHt9LAogICAgICAgICAgICAibGluZXMi\\nOiB7CiAgICAgICAgICAgICAgInJ1IjogItCi0LjQvyDQsdC+0LXQv9GA0LjQ\\nv9Cw0YHQvtCyIiwKICAgICAgICAgICAgICAiZW4iOiAiQW1tbyB0eXBlIgog\\nICAgICAgICAgICB9CiAgICAgICAgICB9LAogICAgICAgICAgInZhbHVlIjog\\newogICAgICAgICAgICAidHlwZSI6ICJ0cmFuc2xhdGlvbiIsCiAgICAgICAg\\nICAgICJrZXkiOiAiaXRlbS53cG4uZGlzcGxheV9hbW1vX3R5cGVzLjkzOW1t\\nIiwKICAgICAgICAgICAgImFyZ3MiOiB7fSwKICAgICAgICAgICAgImxpbmVz\\nIjogewogICAgICAgICAgICAgICJydSI6ICI50YUzOSIsCiAgICAgICAgICAg\\nICAgImVuIjogIjl4MzkiCiAgICAgICAgICAgIH0KICAgICAgICAgIH0KICAg\\nICAgICB9LAogICAgICAgIHsKICAgICAgICAgICJ0eXBlIjogIm51bWVyaWMi\\nLAogICAgICAgICAgIm5hbWUiOiB7CiAgICAgICAgICAgICJ0eXBlIjogInRy\\nYW5zbGF0aW9uIiwKICAgICAgICAgICAgImtleSI6ICJjb3JlLnRvb2x0aXAu\\nc3RhdF9uYW1lLmRhbWFnZV90eXBlLmRpcmVjdCIsCiAgICAgICAgICAgICJh\\ncmdzIjoge30sCiAgICAgICAgICAgICJsaW5lcyI6IHsKICAgICAgICAgICAg\\nICAicnUiOiAi0KPRgNC+0L0iLAogICAgICAgICAgICAgICJlbiI6ICJEYW1h\\nZ2UiCiAgICAgICAgICAgIH0KICAgICAgICAgIH0sCiAgICAgICAgICAidmFs\\ndWUiOiA0Ny4xMjUsCiAgICAgICAgICAiZm9ybWF0dGVkIjogewogICAgICAg\\nICAgICAidmFsdWUiOiB7CiAgICAgICAgICAgICAgInJ1IjogIjQ3LDEyINC1\\n0LQiLAogICAgICAgICAgICAgICJlbiI6ICI0Ny4xMiB1bml0KHMpIgogICAg\\nICAgICAgICB9LAogICAgICAgICAgICAibmFtZUNvbG9yIjogIjgzODM4MyIs\\nCiAgICAgICAgICAgICJ2YWx1ZUNvbG9yIjogIkVFRUVFRSIKICAgICAgICAg\\nIH0KICAgICAgICB9LAogICAgICAgIHsKICAgICAgICAgICJ0eXBlIjogIm51\\nbWVyaWMiLAogICAgICAgICAgIm5hbWUiOiB7CiAgICAgICAgICAgICJ0eXBl\\nIjogInRyYW5zbGF0aW9uIiwKICAgICAgICAgICAgImtleSI6ICJ3ZWFwb24u\\ndG9vbHRpcC53ZWFwb24uaW5mby5jbGlwX3NpemUiLAogICAgICAgICAgICAi\\nYXJncyI6IHt9LAogICAgICAgICAgICAibGluZXMiOiB7CiAgICAgICAgICAg\\nICAgInJ1IjogItCe0LHRitC10Lwg0LzQsNCz0LDQt9C40L3QsCIsCiAgICAg\\nICAgICAgICAgImVuIjogIk1hZ2F6aW5lIGNhcGFjaXR5IgogICAgICAgICAg\\nICB9CiAgICAgICAgICB9LAogICAgICAgICAgInZhbHVlIjogMjAuMCwKICAg\\nICAgICAgICJmb3JtYXR0ZWQiOiB7CiAgICAgICAgICAgICJ2YWx1ZSI6IHsK\\nICAgICAgICAgICAgICAicnUiOiAiMC8yMCIsCiAgICAgICAgICAgICAgImVu\\nIjogIjAvMjAiCiAgICAgICAgICAgIH0sCiAgICAgICAgICAgICJuYW1lQ29s\\nb3IiOiAiODM4MzgzIiwKICAgICAgICAgICAgInZhbHVlQ29sb3IiOiAiRUVF\\nRUVFIgogICAgICAgICAgfQogICAgICAgIH0sCiAgICAgICAgewogICAgICAg\\nICAgInR5cGUiOiAibnVtZXJpYyIsCiAgICAgICAgICAibmFtZSI6IHsKICAg\\nICAgICAgICAgInR5cGUiOiAidHJhbnNsYXRpb24iLAogICAgICAgICAgICAi\\na2V5IjogIndlYXBvbi50b29sdGlwLndlYXBvbi5pbmZvLmRpc3RhbmNlIiwK\\nICAgICAgICAgICAgImFyZ3MiOiB7fSwKICAgICAgICAgICAgImxpbmVzIjog\\newogICAgICAgICAgICAgICJydSI6ICLQnNCw0LrRgdC40LzQsNC70YzQvdCw\\n0Y8g0LTQuNGB0YLQsNC90YbQuNGPIiwKICAgICAgICAgICAgICAiZW4iOiAi\\nTWF4aW11bSBkaXN0YW5jZSIKICAgICAgICAgICAgfQogICAgICAgICAgfSwK\\nICAgICAgICAgICJ2YWx1ZSI6IDEzNS4wLAogICAgICAgICAgImZvcm1hdHRl\\nZCI6IHsKICAgICAgICAgICAgInZhbHVlIjogewogICAgICAgICAgICAgICJy\\ndSI6ICIxMzUg0LwiLAogICAgICAgICAgICAgICJlbiI6ICIxMzUgbSIKICAg\\nICAgICAgICAgfSwKICAgICAgICAgICAgIm5hbWVDb2xvciI6ICI4MzgzODMi\\nLAogICAgICAgICAgICAidmFsdWVDb2xvciI6ICJFRUVFRUUiCiAgICAgICAg\\nICB9CiAgICAgICAgfSwKICAgICAgICB7CiAgICAgICAgICAidHlwZSI6ICJu\\ndW1lcmljIiwKICAgICAgICAgICJuYW1lIjogewogICAgICAgICAgICAidHlw\\nZSI6ICJ0cmFuc2xhdGlvbiIsCiAgICAgICAgICAgICJrZXkiOiAid2VhcG9u\\nLnRvb2x0aXAud2VhcG9uLmluZm8ucmF0ZV9vZl9maXJlIiwKICAgICAgICAg\\nICAgImFyZ3MiOiB7fSwKICAgICAgICAgICAgImxpbmVzIjogewogICAgICAg\\nICAgICAgICJydSI6ICLQodC60L7RgNC+0YHRgtGA0LXQu9GM0L3QvtGB0YLR\\njCIsCiAgICAgICAgICAgICAgImVuIjogIlJhdGUgb2YgZmlyZSIKICAgICAg\\nICAgICAgfQogICAgICAgICAgfSwKICAgICAgICAgICJ2YWx1ZSI6IDcwMC4w\\nLAogICAgICAgICAgImZvcm1hdHRlZCI6IHsKICAgICAgICAgICAgInZhbHVl\\nIjogewogICAgICAgICAgICAgICJydSI6ICI3MDAg0LLRi9GB0YLRgC/QvNC4\\n0L0iLAogICAgICAgICAgICAgICJlbiI6ICI3MDAgc2hvdHMvbWluIgogICAg\\nICAgICAgICB9LAogICAgICAgICAgICAibmFtZUNvbG9yIjogIjgzODM4MyIs\\nCiAgICAgICAgICAgICJ2YWx1ZUNvbG9yIjogIkVFRUVFRSIKICAgICAgICAg\\nIH0KICAgICAgICB9LAogICAgICAgIHsKICAgICAgICAgICJ0eXBlIjogIm51\\nbWVyaWMiLAogICAgICAgICAgIm5hbWUiOiB7CiAgICAgICAgICAgICJ0eXBl\\nIjogInRyYW5zbGF0aW9uIiwKICAgICAgICAgICAgImtleSI6ICJ3ZWFwb24u\\ndG9vbHRpcC53ZWFwb24uaW5mby5yZWxvYWRfdGltZSIsCiAgICAgICAgICAg\\nICJhcmdzIjoge30sCiAgICAgICAgICAgICJsaW5lcyI6IHsKICAgICAgICAg\\nICAgICAicnUiOiAi0J/QtdGA0LXQt9Cw0YDRj9C00LrQsCIsCiAgICAgICAg\\nICAgICAgImVuIjogIlJlbG9hZCIKICAgICAgICAgICAgfQogICAgICAgICAg\\nfSwKICAgICAgICAgICJ2YWx1ZSI6IDMuNSwKICAgICAgICAgICJmb3JtYXR0\\nZWQiOiB7CiAgICAgICAgICAgICJ2YWx1ZSI6IHsKICAgICAgICAgICAgICAi\\ncnUiOiAiMyw1INGBIiwKICAgICAgICAgICAgICAiZW4iOiAiMy41IHNlYyIK\\nICAgICAgICAgICAgfSwKICAgICAgICAgICAgIm5hbWVDb2xvciI6ICI4Mzgz\\nODMiLAogICAgICAgICAgICAidmFsdWVDb2xvciI6ICJFRUVFRUUiCiAgICAg\\nICAgICB9CiAgICAgICAgfSwKICAgICAgICB7CiAgICAgICAgICAidHlwZSI6\\nICJudW1lcmljIiwKICAgICAgICAgICJuYW1lIjogewogICAgICAgICAgICAi\\ndHlwZSI6ICJ0cmFuc2xhdGlvbiIsCiAgICAgICAgICAgICJrZXkiOiAid2Vh\\ncG9uLnRvb2x0aXAud2VhcG9uLmluZm8udGFjdGljYWxfcmVsb2FkX3RpbWUi\\nLAogICAgICAgICAgICAiYXJncyI6IHt9LAogICAgICAgICAgICAibGluZXMi\\nOiB7CiAgICAgICAgICAgICAgInJ1IjogItCi0LDQutGC0LjRh9C10YHQutCw\\n0Y8g0L/QtdGA0LXQt9Cw0YDRj9C00LrQsCIsCiAgICAgICAgICAgICAgImVu\\nIjogIlRhY3RpY2FsIHJlbG9hZCIKICAgICAgICAgICAgfQogICAgICAgICAg\\nfSwKICAgICAgICAgICJ2YWx1ZSI6IDIuNiwKICAgICAgICAgICJmb3JtYXR0\\nZWQiOiB7CiAgICAgICAgICAgICJ2YWx1ZSI6IHsKICAgICAgICAgICAgICAi\\ncnUiOiAiMiw2INGBIiwKICAgICAgICAgICAgICAiZW4iOiAiMi42IHNlYyIK\\nICAgICAgICAgICAgfSwKICAgICAgICAgICAgIm5hbWVDb2xvciI6ICI4Mzgz\\nODMiLAogICAgICAgICAgICAidmFsdWVDb2xvciI6ICJFRUVFRUUiCiAgICAg\\nICAgICB9CiAgICAgICAgfSwKICAgICAgICB7CiAgICAgICAgICAidHlwZSI6\\nICJudW1lcmljIiwKICAgICAgICAgICJuYW1lIjogewogICAgICAgICAgICAi\\ndHlwZSI6ICJ0cmFuc2xhdGlvbiIsCiAgICAgICAgICAgICJrZXkiOiAid2Vh\\ncG9uLnRvb2x0aXAud2VhcG9uLmluZm8uc3ByZWFkIiwKICAgICAgICAgICAg\\nImFyZ3MiOiB7fSwKICAgICAgICAgICAgImxpbmVzIjogewogICAgICAgICAg\\nICAgICJydSI6ICLQoNCw0LfQsdGA0L7RgSIsCiAgICAgICAgICAgICAgImVu\\nIjogIlNwcmVhZCIKICAgICAgICAgICAgfQogICAgICAgICAgfSwKICAgICAg\\nICAgICJ2YWx1ZSI6IDAuNDcsCiAgICAgICAgICAiZm9ybWF0dGVkIjogewog\\nICAgICAgICAgICAidmFsdWUiOiB7CiAgICAgICAgICAgICAgInJ1IjogIjAs\\nNDfCsCIsCiAgICAgICAgICAgICAgImVuIjogIjAuNDfCsCIKICAgICAgICAg\\nICAgfSwKICAgICAgICAgICAgIm5hbWVDb2xvciI6ICI4MzgzODMiLAogICAg\\nICAgICAgICAidmFsdWVDb2xvciI6ICJFRUVFRUUiCiAgICAgICAgICB9CiAg\\nICAgICAgfSwKICAgICAgICB7CiAgICAgICAgICAidHlwZSI6ICJudW1lcmlj\\nIiwKICAgICAgICAgICJuYW1lIjogewogICAgICAgICAgICAidHlwZSI6ICJ0\\ncmFuc2xhdGlvbiIsCiAgICAgICAgICAgICJrZXkiOiAid2VhcG9uLnRvb2x0\\naXAud2VhcG9uLmluZm8uaGlwX3NwcmVhZCIsCiAgICAgICAgICAgICJhcmdz\\nIjoge30sCiAgICAgICAgICAgICJsaW5lcyI6IHsKICAgICAgICAgICAgICAi\\ncnUiOiAi0KDQsNC30LHRgNC+0YEg0L7RgiDQsdC10LTRgNCwIiwKICAgICAg\\nICAgICAgICAiZW4iOiAiSGlwLWZpcmUgc3ByZWFkIgogICAgICAgICAgICB9\\nCiAgICAgICAgICB9LAogICAgICAgICAgInZhbHVlIjogMy4wLAogICAgICAg\\nICAgImZvcm1hdHRlZCI6IHsKICAgICAgICAgICAgInZhbHVlIjogewogICAg\\nICAgICAgICAgICJydSI6ICIzwrAiLAogICAgICAgICAgICAgICJlbiI6ICIz\\nwrAiCiAgICAgICAgICAgIH0sCiAgICAgICAgICAgICJuYW1lQ29sb3IiOiAi\\nODM4MzgzIiwKICAgICAgICAgICAgInZhbHVlQ29sb3IiOiAiRUVFRUVFIgog\\nICAgICAgICAgfQogICAgICAgIH0sCiAgICAgICAgewogICAgICAgICAgInR5\\ncGUiOiAibnVtZXJpYyIsCiAgICAgICAgICAibmFtZSI6IHsKICAgICAgICAg\\nICAgInR5cGUiOiAidHJhbnNsYXRpb24iLAogICAgICAgICAgICAia2V5Ijog\\nIndlYXBvbi50b29sdGlwLndlYXBvbi5pbmZvLnJlY29pbCIsCiAgICAgICAg\\nICAgICJhcmdzIjoge30sCiAgICAgICAgICAgICJsaW5lcyI6IHsKICAgICAg\\nICAgICAgICAicnUiOiAi0JLQtdGA0YLQuNC60LDQu9GM0L3QsNGPINC+0YLQ\\ntNCw0YfQsCIsCiAgICAgICAgICAgICAgImVuIjogIlZlcnRpY2FsIHJlY29p\\nbCIKICAgICAgICAgICAgfQogICAgICAgICAgfSwKICAgICAgICAgICJ2YWx1\\nZSI6IDAuNDMsCiAgICAgICAgICAiZm9ybWF0dGVkIjogewogICAgICAgICAg\\nICAidmFsdWUiOiB7CiAgICAgICAgICAgICAgInJ1IjogIjAsNDPCsCIsCiAg\\nICAgICAgICAgICAgImVuIjogIjAuNDPCsCIKICAgICAgICAgICAgfSwKICAg\\nICAgICAgICAgIm5hbWVDb2xvciI6ICI4MzgzODMiLAogICAgICAgICAgICAi\\ndmFsdWVDb2xvciI6ICJFRUVFRUUiCiAgICAgICAgICB9CiAgICAgICAgfSwK\\nICAgICAgICB7CiAgICAgICAgICAidHlwZSI6ICJudW1lcmljIiwKICAgICAg\\nICAgICJuYW1lIjogewogICAgICAgICAgICAidHlwZSI6ICJ0cmFuc2xhdGlv\\nbiIsCiAgICAgICAgICAgICJrZXkiOiAid2VhcG9uLnRvb2x0aXAud2VhcG9u\\nLmluZm8uaG9yaXpvbnRhbF9yZWNvaWwiLAogICAgICAgICAgICAiYXJncyI6\\nIHt9LAogICAgICAgICAgICAibGluZXMiOiB7CiAgICAgICAgICAgICAgInJ1\\nIjogItCT0L7RgNC40LfQvtC90YLQsNC70YzQvdCw0Y8g0L7RgtC00LDRh9Cw\\nIiwKICAgICAgICAgICAgICAiZW4iOiAiSG9yaXpvbnRhbCByZWNvaWwiCiAg\\nICAgICAgICAgIH0KICAgICAgICAgIH0sCiAgICAgICAgICAidmFsdWUiOiAw\\nLjE0LAogICAgICAgICAgImZvcm1hdHRlZCI6IHsKICAgICAgICAgICAgInZh\\nbHVlIjogewogICAgICAgICAgICAgICJydSI6ICIwLDE0wrAiLAogICAgICAg\\nICAgICAgICJlbiI6ICIwLjE0wrAiCiAgICAgICAgICAgIH0sCiAgICAgICAg\\nICAgICJuYW1lQ29sb3IiOiAiODM4MzgzIiwKICAgICAgICAgICAgInZhbHVl\\nQ29sb3IiOiAiRUVFRUVFIgogICAgICAgICAgfQogICAgICAgIH0sCiAgICAg\\nICAgewogICAgICAgICAgInR5cGUiOiAibnVtZXJpYyIsCiAgICAgICAgICAi\\nbmFtZSI6IHsKICAgICAgICAgICAgInR5cGUiOiAidHJhbnNsYXRpb24iLAog\\nICAgICAgICAgICAia2V5IjogIndlYXBvbi50b29sdGlwLndlYXBvbi5pbmZv\\nLmRyYXdfdGltZSIsCiAgICAgICAgICAgICJhcmdzIjoge30sCiAgICAgICAg\\nICAgICJsaW5lcyI6IHsKICAgICAgICAgICAgICAicnUiOiAi0JTQvtGB0YLQ\\nsNCy0LDQvdC40LUiLAogICAgICAgICAgICAgICJlbiI6ICJEcmF3IHRpbWUi\\nCiAgICAgICAgICAgIH0KICAgICAgICAgIH0sCiAgICAgICAgICAidmFsdWUi\\nOiAwLjksCiAgICAgICAgICAiZm9ybWF0dGVkIjogewogICAgICAgICAgICAi\\ndmFsdWUiOiB7CiAgICAgICAgICAgICAgInJ1IjogIjAsOSDRgSIsCiAgICAg\\nICAgICAgICAgImVuIjogIjAuOSBzZWMiCiAgICAgICAgICAgIH0sCiAgICAg\\nICAgICAgICJuYW1lQ29sb3IiOiAiODM4MzgzIiwKICAgICAgICAgICAgInZh\\nbHVlQ29sb3IiOiAiRUVFRUVFIgogICAgICAgICAgfQogICAgICAgIH0sCiAg\\nICAgICAgewogICAgICAgICAgInR5cGUiOiAibnVtZXJpYyIsCiAgICAgICAg\\nICAibmFtZSI6IHsKICAgICAgICAgICAgInR5cGUiOiAidHJhbnNsYXRpb24i\\nLAogICAgICAgICAgICAia2V5IjogIndlYXBvbi50b29sdGlwLndlYXBvbi5p\\nbmZvLmFpbV9zd2l0Y2giLAogICAgICAgICAgICAiYXJncyI6IHt9LAogICAg\\nICAgICAgICAibGluZXMiOiB7CiAgICAgICAgICAgICAgInJ1IjogItCf0YDQ\\nuNGG0LXQu9C40LLQsNC90LjQtSIsCiAgICAgICAgICAgICAgImVuIjogIkFp\\nbWluZyB0aW1lIgogICAgICAgICAgICB9CiAgICAgICAgICB9LAogICAgICAg\\nICAgInZhbHVlIjogMC4xNSwKICAgICAgICAgICJmb3JtYXR0ZWQiOiB7CiAg\\nICAgICAgICAgICJ2YWx1ZSI6IHsKICAgICAgICAgICAgICAicnUiOiAiMCwx\\nNSDRgSIsCiAgICAgICAgICAgICAgImVuIjogIjAuMTUgc2VjIgogICAgICAg\\nICAgICB9LAogICAgICAgICAgICAibmFtZUNvbG9yIjogIjgzODM4MyIsCiAg\\nICAgICAgICAgICJ2YWx1ZUNvbG9yIjogIkVFRUVFRSIKICAgICAgICAgIH0K\\nICAgICAgICB9CiAgICAgIF0KICAgIH0sCiAgICB7CiAgICAgICJ0eXBlIjog\\nImxpc3QiLAogICAgICAidGl0bGUiOiB7CiAgICAgICAgInR5cGUiOiAidHJh\\nbnNsYXRpb24iLAogICAgICAgICJrZXkiOiAic3RhbGtlci50b29sdGlwLmFy\\ndGVmYWN0LmluZm8uYWRkaXRpb25hbF9zdGF0cyIsCiAgICAgICAgImFyZ3Mi\\nOiB7fSwKICAgICAgICAibGluZXMiOiB7CiAgICAgICAgICAicnUiOiAi0JTQ\\nvtC/0L7Qu9C90LjRgtC10LvRjNC90YvQtSDRhdCw0YDQsNC60YLQtdGA0LjR\\ngdGC0LjQutC4IiwKICAgICAgICAgICJlbiI6ICJBZGRpdGlvbmFsIHByb3Bl\\ncnRpZXMiCiAgICAgICAgfQogICAgICB9LAogICAgICAiZWxlbWVudHMiOiBb\\nXQogICAgfSwKICAgIHsKICAgICAgInR5cGUiOiAibGlzdCIsCiAgICAgICJ0\\naXRsZSI6IHsKICAgICAgICAidHlwZSI6ICJ0cmFuc2xhdGlvbiIsCiAgICAg\\nICAgImtleSI6ICJ3ZWFwb24udG9vbHRpcC53ZWFwb24uaW5mby5kYW1hZ2Vf\\nbW9kaWZpZXJzIiwKICAgICAgICAiYXJncyI6IHt9LAogICAgICAgICJsaW5l\\ncyI6IHsKICAgICAgICAgICJydSI6ICLQnNC90L7QttC40YLQtdC70Ywg0YPR\\ngNC+0L3QsCIsCiAgICAgICAgICAiZW4iOiAiRGFtYWdlIG1vZGlmaWVyIgog\\nICAgICAgIH0KICAgICAgfSwKICAgICAgImVsZW1lbnRzIjogWwogICAgICAg\\nIHsKICAgICAgICAgICJ0eXBlIjogInRleHQiLAogICAgICAgICAgInRleHQi\\nOiB7CiAgICAgICAgICAgICJ0eXBlIjogInRyYW5zbGF0aW9uIiwKICAgICAg\\nICAgICAgImtleSI6ICJ3ZWFwb24udG9vbHRpcC53ZWFwb24uaGVhZF9kYW1h\\nZ2VfbW9kaWZpZXIiLAogICAgICAgICAgICAiYXJncyI6IHsKICAgICAgICAg\\nICAgICAibW9kaWZpZXIiOiAiMS4yNSIKICAgICAgICAgICAgfSwKICAgICAg\\nICAgICAgImxpbmVzIjogewogICAgICAgICAgICAgICJydSI6ICLQo9GA0L7Q\\nvSDQsiDQs9C+0LvQvtCy0YM6INGFMSwyNSIsCiAgICAgICAgICAgICAgImVu\\nIjogIkhlYWRzaG90IGRhbWFnZTogeDEuMjUiCiAgICAgICAgICAgIH0KICAg\\nICAgICAgIH0KICAgICAgICB9LAogICAgICAgIHsKICAgICAgICAgICJ0eXBl\\nIjogInRleHQiLAogICAgICAgICAgInRleHQiOiB7CiAgICAgICAgICAgICJ0\\neXBlIjogInRyYW5zbGF0aW9uIiwKICAgICAgICAgICAgImtleSI6ICJ3ZWFw\\nb24udG9vbHRpcC53ZWFwb24ubGltYnNfZGFtYWdlX21vZGlmaWVyIiwKICAg\\nICAgICAgICAgImFyZ3MiOiB7CiAgICAgICAgICAgICAgIm1vZGlmaWVyIjog\\nIjAuOCIKICAgICAgICAgICAgfSwKICAgICAgICAgICAgImxpbmVzIjogewog\\nICAgICAgICAgICAgICJydSI6ICLQo9GA0L7QvSDQv9C+INC60L7QvdC10YfQ\\nvdC+0YHRgtGP0Lw6INGFMCw4IiwKICAgICAgICAgICAgICAiZW4iOiAiTGlt\\nYiBkYW1hZ2U6INGFMC44IgogICAgICAgICAgICB9CiAgICAgICAgICB9CiAg\\nICAgICAgfQogICAgICBdCiAgICB9LAogICAgewogICAgICAidHlwZSI6ICJs\\naXN0IiwKICAgICAgInRpdGxlIjogewogICAgICAgICJ0eXBlIjogInRyYW5z\\nbGF0aW9uIiwKICAgICAgICAia2V5IjogIndlYXBvbi50b29sdGlwLndlYXBv\\nbi5pbmZvLnVwZ3JhZGVfc3RhdHMiLAogICAgICAgICJhcmdzIjoge30sCiAg\\nICAgICAgImxpbmVzIjogewogICAgICAgICAgInJ1IjogItCl0LDRgNCw0LrR\\ngtC10YDQuNGB0YLQuNC60Lgg0LzQvtC00LjRhNC40LrQsNGG0LjQuCIsCiAg\\nICAgICAgICAiZW4iOiAiTW9kaWZpY2F0aW9uIHByb3BlcnRpZXMiCiAgICAg\\nICAgfQogICAgICB9LAogICAgICAiZWxlbWVudHMiOiBbCiAgICAgICAgewog\\nICAgICAgICAgInR5cGUiOiAibnVtZXJpYyIsCiAgICAgICAgICAibmFtZSI6\\nIHsKICAgICAgICAgICAgInR5cGUiOiAidHJhbnNsYXRpb24iLAogICAgICAg\\nICAgICAia2V5IjogIndlYXBvbi5zdGF0X2ZhY3Rvci5kYW1hZ2UiLAogICAg\\nICAgICAgICAiYXJncyI6IHt9LAogICAgICAgICAgICAibGluZXMiOiB7CiAg\\nICAgICAgICAgICAgInJ1IjogItCj0YDQvtC9IiwKICAgICAgICAgICAgICAi\\nZW4iOiAiRGFtYWdlIgogICAgICAgICAgICB9CiAgICAgICAgICB9LAogICAg\\nICAgICAgInZhbHVlIjogMjUuMCwKICAgICAgICAgICJmb3JtYXR0ZWQiOiB7\\nCiAgICAgICAgICAgICJ2YWx1ZSI6IHsKICAgICAgICAgICAgICAicnUiOiAi\\nKzI1JSIsCiAgICAgICAgICAgICAgImVuIjogIisyNSUiCiAgICAgICAgICAg\\nIH0sCiAgICAgICAgICAgICJuYW1lQ29sb3IiOiAiRjFBQTIxIiwKICAgICAg\\nICAgICAgInZhbHVlQ29sb3IiOiAiRjFBQTIxIgogICAgICAgICAgfQogICAg\\nICAgIH0sCiAgICAgICAgewogICAgICAgICAgInR5cGUiOiAibnVtZXJpYyIs\\nCiAgICAgICAgICAibmFtZSI6IHsKICAgICAgICAgICAgInR5cGUiOiAidHJh\\nbnNsYXRpb24iLAogICAgICAgICAgICAia2V5IjogIndlYXBvbi5zdGF0X2Zh\\nY3Rvci5kYW1hZ2VfZGlzdGFudCIsCiAgICAgICAgICAgICJhcmdzIjoge30s\\nCiAgICAgICAgICAgICJsaW5lcyI6IHsKICAgICAgICAgICAgICAicnUiOiAi\\n0KPRgNC+0L0g0LLQtNCw0LvQuCIsCiAgICAgICAgICAgICAgImVuIjogIkRh\\nbWFnZSBhdCByYW5nZSIKICAgICAgICAgICAgfQogICAgICAgICAgfSwKICAg\\nICAgICAgICJ2YWx1ZSI6IDI1LjAsCiAgICAgICAgICAiZm9ybWF0dGVkIjog\\newogICAgICAgICAgICAidmFsdWUiOiB7CiAgICAgICAgICAgICAgInJ1Ijog\\nIisyNSUiLAogICAgICAgICAgICAgICJlbiI6ICIrMjUlIgogICAgICAgICAg\\nICB9LAogICAgICAgICAgICAibmFtZUNvbG9yIjogIkYxQUEyMSIsCiAgICAg\\nICAgICAgICJ2YWx1ZUNvbG9yIjogIkYxQUEyMSIKICAgICAgICAgIH0KICAg\\nICAgICB9CiAgICAgIF0KICAgIH0sCiAgICB7CiAgICAgICJ0eXBlIjogImxp\\nc3QiLAogICAgICAidGl0bGUiOiB7CiAgICAgICAgInR5cGUiOiAidHJhbnNs\\nYXRpb24iLAogICAgICAgICJrZXkiOiAid2VhcG9uLnRvb2x0aXAud2VhcG9u\\nLmluZm8uZmVhdHVyZSIsCiAgICAgICAgImFyZ3MiOiB7fSwKICAgICAgICAi\\nbGluZXMiOiB7CiAgICAgICAgICAicnUiOiAi0J7RgdC+0LHQtdC90L3QvtGB\\n0YLQuCIsCiAgICAgICAgICAiZW4iOiAiRmVhdHVyZXMiCiAgICAgICAgfQog\\nICAgICB9LAogICAgICAiZWxlbWVudHMiOiBbXQogICAgfSwKICAgIHsKICAg\\nICAgInR5cGUiOiAiZGFtYWdlIiwKICAgICAgInN0YXJ0RGFtYWdlIjogNDcu\\nMTI1LAogICAgICAiZGFtYWdlRGVjcmVhc2VTdGFydCI6IDguMCwKICAgICAg\\nImVuZERhbWFnZSI6IDI4LjEyNSwKICAgICAgImRhbWFnZURlY3JlYXNlRW5k\\nIjogNDIuMCwKICAgICAgIm1heERpc3RhbmNlIjogMTM1LjAKICAgIH0sCiAg\\nICB7CiAgICAgICJ0eXBlIjogInRleHQiLAogICAgICAidGl0bGUiOiB7CiAg\\nICAgICAgInR5cGUiOiAidGV4dCIsCiAgICAgICAgInRleHQiOiAiIgogICAg\\nICB9LAogICAgICAidGV4dCI6IHsKICAgICAgICAidHlwZSI6ICJ0cmFuc2xh\\ndGlvbiIsCiAgICAgICAgImtleSI6ICJpdGVtLndwbi45YTkxLmRlc2NyaXB0\\naW9uIiwKICAgICAgICAiYXJncyI6IHt9LAogICAgICAgICJsaW5lcyI6IHsK\\nICAgICAgICAgICJydSI6ICLQnNCw0LvQvtCz0LDQsdCw0YDQuNGC0L3Ri9C5\\nINCw0LLRgtC+0LzQsNGCLCDRgdC60L7QvdGB0YLRgNGD0LjRgNC+0LLQsNC9\\n0L3Ri9C5INCyINCi0YPQu9GM0YHQutC+0Lwg0LrQvtC90YHRgtGA0YPQutGC\\n0L7RgNGB0LrQvtC8INCx0Y7RgNC+INC/0YDQuNCx0L7RgNC+0YHRgtGA0L7Q\\ntdC90LjRjyDQsiAxOTkyINCz0L7QtNGDINCyINC60LDRh9C10YHRgtCy0LUg\\n0LHQvtC70LXQtSDRgtC10YXQvdC+0LvQvtCz0LjRh9C90L7Qs9C+INCw0L3Q\\nsNC70L7Qs9CwICjQuCDQutC+0L3QutGD0YDQtdC90YLQsCkg0LDQstGC0L7Q\\nvNCw0YLQsCDQodCgLTMgwqvQktC40YXRgNGMwrsuIiwKICAgICAgICAgICJl\\nbiI6ICJBIGNvbXBhY3QgYXNzYXVsdCByaWZsZSBtYW51ZmFjdHVyZWQgaW4g\\ndGhlIFR1bHNreSBJbnN0cnVtZW50IERlc2lnbiBCdXJlYXUgaW4gMTk5MiBh\\ncyBhIG1vcmUgdGVjaG5pY2FsIGFsdGVybmF0aXZlIHRvIChhbmQgY29tcGV0\\naXRvciBmb3IpIHRoZSBTUi0zIFZpa2hyIGFzc2F1bHQgcmlmbGUuIgogICAg\\nICAgIH0KICAgICAgfQogICAgfQogIF0KfQ==\\n");
        System.out.println(test);
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
                CURRENT_LEVEL++;
                JsonArray targetArray = GetJsonArrayFromString(jsonArray.get(redicretKey - 1).getAsJsonObject().getAsJsonPrimitive("url").getAsString());
                PrintJsonArray(targetArray, "name");
            }
        }
    }
}