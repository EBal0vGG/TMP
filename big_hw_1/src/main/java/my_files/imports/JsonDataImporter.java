package my_files.imports;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import my_files.model.BA;
import my_files.model.Category;
import my_files.model.Common_Facade;
import my_files.model.Operation;

public class JsonDataImporter extends DataImporter {

    private final Common_Facade facade;

    public JsonDataImporter(Common_Facade facade) {
        this.facade = facade;
    }

    //Вспомогательный класс для чтения данных из json;
    public static class DataContainer {
        public List <BA> accounts;
        public List <Category> categories;
        public List <Operation> operations;
    }


    @Override
    protected void parseData(String data) {
        System.out.println("Парсинг JSON-данных:\n" + data);

        try {
            facade.clearData();

            ObjectMapper mapper = new ObjectMapper();

            //регистрируем модули для десереализации java.time.LocalDateTime
            mapper.findAndRegisterModules();
            
            // Игнор неизвестных свойств, если появятся:
            //mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            DataContainer container = mapper.readValue(data, DataContainer.class);

            //добавляем данные в фасад:
            if (container.accounts != null) {
                facade.getAccounts().addAll(container.accounts);
            }
            if (container.categories != null) {
                facade.getCategories().addAll(container.categories);
            }
            if (container.operations != null) {
                facade.getOperations().addAll(container.operations);
            }

            System.out.println("Данные из JSON успешно импортированны");
        } catch (Exception e) {
            System.out.println("Ошибка при парсинге JSON-данных");
            e.printStackTrace();
        }
        
        // TODO: реализовать парсинг JSON-данных 
        // с помощью библиотеки Jackson
    }
}
