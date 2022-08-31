import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class JsonParser {

    public static void main(String[] args) {
        String fileName = "data.json";
        String json = readJson(fileName);
        jsonToList(json).forEach(System.out::println);
    }

    public static String readJson(String fileName) {
        System.out.printf("%-35s", "Reading data from " + fileName + "...");
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String nextStr;
            StringBuilder result = new StringBuilder();
            while ((nextStr = reader.readLine()) != null) {
                result.append(nextStr.trim());
            }
            System.out.println("done");
            return result.toString();
        } catch (IOException e) {
            System.out.println("failed");
            return null;
        }
    }

    public static List<Employee> jsonToList(String json) {
        return new Gson().fromJson(json, new TypeToken<List<Employee>>() {}.getType());
    }
}
