package my_files.imports;


public class JsonDataImporter extends DataImporter {
    @Override
    protected void parseData(String data) {
        System.out.println("Парсинг JSON-данных:\n" + data);
        
        // TODO: реализовать парсинг JSON-данных 
        // с помощью библиотеки Jackson
    }
}
