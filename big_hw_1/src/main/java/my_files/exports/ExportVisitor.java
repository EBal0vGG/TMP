package my_files.exports;

import my_files.model.BA;
import my_files.model.Category;
import my_files.model.Operation;

public interface ExportVisitor {
    void visit(BA ba);
    void visit(Category category);
    void visit(Operation operation);
}