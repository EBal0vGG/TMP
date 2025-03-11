package my_files.exports;

import my_files.model.BA;
import my_files.model.Category;
import my_files.model.Operation;

public class CSVExportVisitor implements ExportVisitor {
    @Override
    public void visit(BA account) {
        // будем писать с помощью Jackson в csv-шку
        System.out.println("Экспорт счета в CSV: " + account.id + " " + account.name);
    }

    @Override
    public void visit(Category category) {
        // будем писать с помощью Jackson в csv-шку
        System.out.println("Экспорт категории в CSV: " + category.id + " " + category.name);
    } 

    @Override
    public void visit(Operation operation) {
        // будем писать с помощью Jackson в csv-шку
        System.out.println("Экспорт операции в CSV: " + operation.id + " " + operation.get_amount());
    }
}