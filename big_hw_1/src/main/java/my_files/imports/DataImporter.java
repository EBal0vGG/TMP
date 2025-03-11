package my_files.imports;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public abstract class DataImporter {
    // ШАБЛОННЫЙ МЕТОД для импорта файла
    public final void importFile(String path) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            String data = String.join("\n", lines);

            parseData(data);
            System.out.println("Импорт файла " + path + " завершен");
            System.out.println("Полученная информация:\n" + data);

        } catch (IOException e) {
            System.out.println("Возникла ошибка при четнии файла " + path);
            e.printStackTrace();;
        }
    }

    protected abstract void parseData(String data);
}

