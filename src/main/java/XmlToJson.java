import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;

public class XmlToJson {

    public static void main(String[] args) {
        String fileName = "data.xml";
        List<Employee> employeeList = parseXml(fileName);
        String json = CsvToJson.listToJson(employeeList);
        CsvToJson.writeString(json, "data2.json");
    }

    public static List<Employee> parseXml(String fileName) {
        System.out.printf("%-35s", "Parsing data from " + fileName + "...");
        try {
            List<Employee> employeeList = new ArrayList<>();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(fileName);
            NodeList employees = doc.getElementsByTagName("employee");
            for (int i = 0; i < employees.getLength(); i++) {
                Node node = employees.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) node;
                    Employee emp = new Employee();
                    emp.id = Long.parseLong(el.getElementsByTagName("id").item(0).getTextContent());
                    emp.firstName = el.getElementsByTagName("firstName").item(0).getTextContent();
                    emp.lastName = el.getElementsByTagName("lastName").item(0).getTextContent();
                    emp.country = el.getElementsByTagName("country").item(0).getTextContent();
                    emp.age = Integer.parseInt(el.getElementsByTagName("age").item(0).getTextContent());
                    employeeList.add(emp);
                }
            }
            System.out.println("done");
            return employeeList;
        } catch (Exception e) {
            System.out.println("failed");
            return null;
        }
    }
}
