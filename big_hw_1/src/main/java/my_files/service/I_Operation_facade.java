package my_files.service;

import java.time.LocalDateTime;

import my_files.model.Operation;

// создание, изменение, получение, удаление
public interface I_Operation_facade {
    public void createOperation(boolean is_exp, int ba_id, double Sum, LocalDateTime Time, int cat_id, String descr);
    public void createOperation(boolean is_exp, int ba_id, double Sum, LocalDateTime Time, int cat_id);

    public void deleteOperation(int id);
    public void deleteOperation(int BA_id, LocalDateTime Time);

    public void get_Operation_info(int id);
    public Operation get_Operation(int id); // отдаем ссылку на объект

    public void change_description(int id, String new_description);
    public void change_description(int BA_id, LocalDateTime Time, String new_description);
}
