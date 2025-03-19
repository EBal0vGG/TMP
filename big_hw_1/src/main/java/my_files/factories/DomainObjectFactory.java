package my_files.factories;

import java.time.LocalDateTime;

import my_files.model.BA;
import my_files.model.Category;
import my_files.model.Operation;

public class DomainObjectFactory {
    public static BA createBA(String name, double balance) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя счета не может быть пустым");
        }
        return new BA(balance, name);
    }

    public static Category createCategory(String name, boolean isExpenditure) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя категории не может быть пустыми");
        }
        return new Category(name, isExpenditure);
    }

    public static Operation createOperation(boolean isExpenditure, int BA_id, double sum,
                                              LocalDateTime time, int category_id, String description) {
        if (sum < 0) {
            throw new IllegalArgumentException("Сумма операции не может быть отрицательной!");
        }
        if (sum == 0) {
            throw new IllegalArgumentException("Сумма операции не может быть нулевой!");
        }
        if (time == null) {
            throw new IllegalArgumentException("Время проведения операции не может быть null!");
        }
        // Пример дополнительной проверки: операция не должна быть в будущем
        if (time.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Время операции не может быть в будущем!");
        }
        /* это как раз таки может быть - даже в тз опционально
        if (description == null) {
            throw new IllegalArgumentException("Описание операции не может быть null!");
        }*/
        // Можно также проверить, что идентификаторы BA_id и category_id положительные.
        if (BA_id <= 0) {
            throw new IllegalArgumentException("Идентификатор счета должен быть положительным!");
        }
        if (category_id <= 0) {
            throw new IllegalArgumentException("Идентификатор категории должен быть положительным!");
        }
        return new Operation(null, isExpenditure, BA_id, sum, time, category_id, description);
    }
}
