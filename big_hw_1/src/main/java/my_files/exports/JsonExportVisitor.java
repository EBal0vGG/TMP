package my_files.exports;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import my_files.model.BA;
import my_files.model.Category;
import my_files.model.Operation;


public class JsonExportVisitor implements ExportVisitor {
    private final List<BA> accounts = new ArrayList<>();
    private final List<Category> categories = new ArrayList<>();
    private final List<Operation> operations = new ArrayList<>();

    @Override
    public void visit(BA ba) {
        accounts.add(ba);
    }

    @Override
    public void visit(Category category) {
        categories.add(category);
    }

    @Override
    public void visit(Operation operation) {
        operations.add(operation);
    }


    public void exportToFile(String filePath) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            //регистрируем модули (например, для поддержки LocalDateTime)
            mapper.findAndRegisterModules();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);

            //Вспомогательная структура для экспорта
            DataContainer container = new DataContainer();
            container.accounts = accounts;
            container.categories = categories;
            container.operations = operations;

            //пишем в файл
            mapper.writeValue(new File(filePath), container);
            System.out.println("Данные успешно экспортированы в JSON");
        } catch (Exception e) {
            System.out.println("Ошибка при экспорте данных в JSON:");
            e.printStackTrace();
        }
    }
    
    public static class DataContainer {
        public List <BA> accounts;
        public List <Category> categories;
        public List <Operation> operations;
    }

}
