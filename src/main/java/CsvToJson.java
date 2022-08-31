import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvToJson {

    public static void main(String[] args) {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";
        List<Employee> list = parseCSV(columnMapping, fileName);
        String json = listToJson(list);
        writeString(json, "data.json");
    }

    public static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        System.out.printf("%-35s", "Parsing data from " + fileName + "...");
        try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) {
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);
            CsvToBean<Employee> csvToBean = new CsvToBeanBuilder<Employee>(csvReader)
                    .withMappingStrategy(strategy)
                    .build();
            System.out.println("done");
            return csvToBean.parse();
        } catch (IOException e) {
            System.out.println("failed");
            return null;
        }
    }

    public static String listToJson(List<Employee> list) {
        return new Gson().toJson(list, new TypeToken<List<Employee>>() {}.getType());
    }

    public static void writeString(String str, String fileName) {
        System.out.printf("%-35s", "Writing data to " + fileName + "...");
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(str);
            System.out.println("done");
        } catch (IOException e) {
            System.out.println("failed");
        }
    }
}
