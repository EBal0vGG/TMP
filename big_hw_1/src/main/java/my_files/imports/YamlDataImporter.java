package my_files.imports;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import my_files.model.BA;
import my_files.model.Category;
import my_files.model.Common_Facade;
import my_files.model.Operation;


public class YamlDataImporter extends DataImporter {

    private final Common_Facade facade;

    public YamlDataImporter(Common_Facade fc) {
        this.facade = fc;
    }

    public static class DataContainer {
        public List<BA> accounts;
        public List <Category> categories;
        public List <Operation> operations;
    }

    @Override
    protected void parseData(String data) {
        System.out.println("Парсинг YAML-данных:\n" + data);
        try {
            facade.clearData();

            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.findAndRegisterModules();

            DataContainer container = mapper.readValue(data, DataContainer.class);

            if (container.accounts != null) {
                facade.getAccounts().addAll(container.accounts);
            }
            if (container.categories != null) {
                facade.getCategories().addAll(container.categories);
            }
            if (container.operations != null) {
                facade.getOperations().addAll(container.operations);
            }
            System.out.println("Данные успешно импортированы из YAML");
        } catch (Exception e) {
            System.out.println("Ошибка при импорте данных из YAML:");
            e.printStackTrace();
        }
    }
}