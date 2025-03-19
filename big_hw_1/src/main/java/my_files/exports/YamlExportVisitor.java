package my_files.exports;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import my_files.model.BA;
import my_files.model.Category;
import my_files.model.Operation;



public class YamlExportVisitor implements ExportVisitor{
    private final List<BA> accounts = new ArrayList<>();
    private final List<Category> categories = new ArrayList<>();
    private final List<Operation> operations = new ArrayList<>();

    @Override
    public void visit(BA account) {
        accounts.add(account);
    }

    @Override
    public void visit(Category category) {
        categories.add(category);
    }

    @Override
    public void visit(Operation operation) {
        operations.add(operation);
    }

    public void exportToFile(String filepath) {
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.findAndRegisterModules();

            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            mapper.enable(SerializationFeature.INDENT_OUTPUT);

            DataContainer container = new DataContainer();
            container.accounts = accounts;
            container.categories = categories;
            container.operations = operations;

            mapper.writeValue(new File(filepath), container);
            System.out.println("Данные успешно экспортированы в YAML");
        } catch (Exception e) {
            System.out.println("Ошибка при экспорте данных в YAML:");
            e.printStackTrace();
        }
    }

    public static class DataContainer {
        public List <BA> accounts;
        public List <Category> categories;
        public List <Operation> operations;
    }
}
