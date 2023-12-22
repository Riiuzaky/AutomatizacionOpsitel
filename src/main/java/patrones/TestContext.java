package patrones;

import task.ImportarExcel;

public class TestContext {
    private ImportarExcel importador;

    public ImportarExcel getImportador() {
        if (importador == null) {
            importador = ImportarExcel.importar();
        }
        return importador;
    }
}
