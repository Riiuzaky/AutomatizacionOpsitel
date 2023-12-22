package task;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import org.apache.poi.ss.usermodel.*;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class ImportarExcel implements Task {

    private List<String> numerosRUC;
    private boolean importacionRealizada = false;

    public ImportarExcel() {
        this.numerosRUC = new ArrayList<>();
    }

    public void importar2() {
        if (!importacionRealizada) {
        try {
            // Utiliza JFileChooser para que el usuario seleccione el archivo Excel.
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Seleccione el archivo Excel");
            int result = fileChooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                try (Workbook workbook = WorkbookFactory.create(selectedFile)) {
                    Sheet sheet = workbook.getSheetAt(0);


                    // Utiliza DataFormatter para obtener el valor formateado de la celda.
                    DataFormatter dataFormatter = new DataFormatter();

                    // Itera sobre las filas y columnas del archivo Excel para obtener los datos.
                    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                        Row row = sheet.getRow(i);

                        // Asumiendo que la primera columna es RUC.
                        Cell cell = row.getCell(0);
                        if (cell != null) {
                            String valorCelda = dataFormatter.formatCellValue(cell);

                            // Extraer solo el número de la cadena.
                            String soloNumero = valorCelda.replaceAll("\\D+", "");

                            // Agregar el número a la lista.
                            this.numerosRUC.add(soloNumero);
                        }
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Manejo básico de excepciones, ajusta según tus necesidades.
        }
            importacionRealizada = true;
        }
    }

    public List<String> obtenerRegistros() {
        return numerosRUC;
    }


    @Override
    public <T extends Actor> void performAs(T actor) {
        importar2();
    }


    public static ImportarExcel importar (){
        return instrumented(ImportarExcel.class);
    }
}
