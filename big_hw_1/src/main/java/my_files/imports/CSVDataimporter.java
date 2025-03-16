package my_files.imports;

import java.time.LocalDateTime;

import my_files.model.BA;
import my_files.model.Category;
import my_files.model.Common_Facade;
import my_files.model.Operation;


public class CSVDataimporter extends DataImporter {
    private final Common_Facade facade;
    
    public CSVDataimporter(Common_Facade fc) {
        this.facade = fc;
    }

    @Override
    protected void parseData(String data) {
        facade.clearData();


        String[] lines = data.split("\\r?\\n");
        for (String line : lines) {
            if (line.trim().isEmpty() || line.trim().startsWith("//")) continue;
            String[] tokens = line.split(",");
            String type = tokens[0].trim();
            try {
                switch (type) {
                    case "BA":
                        // Формат: BA, id, name, balance
                        int baId = Integer.parseInt(tokens[1].trim());
                        String baName = tokens[2].trim();
                        double baBalance = Double.parseDouble(tokens[3].trim());
                        // Используем конструктор, аннотированный через @JsonCreator
                        BA account = new BA(baId, baName, baBalance);
                        facade.getAccounts().add(account);
                        break;
                    case "Category":
                        // Формат: Category, id, name, isExpenditure
                        int catId = Integer.parseInt(tokens[1].trim());
                        String catName = tokens[2].trim();
                        boolean isExpenditure = Boolean.parseBoolean(tokens[3].trim());
                        // Используем конструктор из @JsonCreator (Category(Integer id, String name, Boolean isExpenditure))
                        Category cat = new Category(catId, catName, isExpenditure);
                        facade.getCategories().add(cat);
                        break;
                    case "Operation":
                        // Формат: Operation, id, isExpenditure, BA_id, sum, time, category_id, description
                        int opId = Integer.parseInt(tokens[1].trim());
                        boolean opIsExpenditure = Boolean.parseBoolean(tokens[2].trim());
                        int opBA_id = Integer.parseInt(tokens[3].trim());
                        double opSum = Double.parseDouble(tokens[4].trim());
                        // Формат даты должен быть ISO-8601 (например, "2025-03-16T12:00:00")
                        LocalDateTime opTime = LocalDateTime.parse(tokens[5].trim());
                        int opCatId = Integer.parseInt(tokens[6].trim());
                        String opDescr = tokens[7].trim();
                        // Используем конструктор из @JsonCreator: Operation(Integer id, boolean isExpenditure, int BA_id, double sum, LocalDateTime time, int category_id, String description)
                        Operation op = new Operation(opId, opIsExpenditure, opBA_id, opSum, opTime, opCatId, opDescr);
                        facade.getOperations().add(op);
                        break;
                    default:
                        System.out.println("Неизвестный тип объекта: " + type);
                }
            } catch (Exception e) {
                System.out.println("Ошибка при разборе строки: " + line);
                e.printStackTrace();
            }
        }
        System.out.println("Данные из CSV успешно импортированы");
    }
}
