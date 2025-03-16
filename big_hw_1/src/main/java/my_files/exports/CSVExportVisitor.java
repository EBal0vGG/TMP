package my_files.exports;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import my_files.model.BA;
import my_files.model.Category;
import my_files.model.Operation;

public class CSVExportVisitor implements ExportVisitor {

    private final List<String[]> csvLines = new ArrayList<>();

    public CSVExportVisitor() {
        //csvLines.add(new String[] { "---Accounts---" });
    }

    //для всех объектов пишем сначала его тип, а потом поля
    // каждый объект - в отдельной строке

    @Override
    public void visit(BA ba) {
        csvLines.add(new String[] {
            "BA",
            String.valueOf(ba.getId()),
            ba.getName(),
            String.valueOf(ba.getBalance())
        });
    }

    @Override
    public void visit(Category category) {
        /*if (csvLines.stream().noneMatch(line -> line.length > 0 && line[0].equals("---Categories---"))) {
            csvLines.add(new String[] {"---Categories---"});
        }*/

        csvLines.add(new String[] {
            "Category",
            String.valueOf(category.getId()),
            category.getName(),
            String.valueOf(category.isExpenditure)
        });
    }

    @Override
    public void visit(Operation operation) {
        /*if (csvLines.stream().noneMatch(line -> line.length > 0 && line[0].equals("---Operations---"))) {
            csvLines.add(new String[] {"---Operations---"});
        }*/

        csvLines.add(new String[] {
            "Operation",
            String.valueOf(operation.getId()),
            String.valueOf(operation.getIsExpenditure()),
            String.valueOf(operation.getBA_id()),
            String.valueOf(operation.getSum()),
            operation.getTime().toString(),
            String.valueOf(operation.getCategory_id()),
            operation.getDescription()
        });
    }

    public void exportToFile(String filePath) {
        try (PrintWriter writer = new PrintWriter(new File (filePath))) {
            for (String[] line: csvLines) {
                //соединяем поля через ,
                writer.println(String.join(",", line));
            }
            System.out.println("Импорт в CSV завершен");
        } catch (Exception e) {
            System.out.println("Ошибка при экспорте CSV в файл " + filePath + ":");
            e.printStackTrace();
        }
    }
}