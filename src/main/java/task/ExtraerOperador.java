package task;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.*;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Keys;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static userInterfaces.Home.*;

public class ExtraerOperador implements Task {

    private String documento;
    private String operador;
    private String totalRegistros;

    public ExtraerOperador(String documento) {
        this.documento = documento;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Scroll.to(NumeroDocumento),
                //Click.on(listaDeOpcionesDocumento),
                Hit.the(Keys.ENTER).into(listaDeOpcionesDocumento),
                Click.on(opcionRUC),
                Enter.theValue(this.documento).into(NumeroDocumento),
                Click.on(BotonBuscar),
                WaitUntil.the(TotalRegistros, isVisible()).forNoMoreThan(15).seconds(),
                Scroll.to(TotalRegistros)

        );
        this.totalRegistros = TotalRegistros.resolveFor(actor).getText();

        this.totalRegistros = recortarCadena(this.totalRegistros);

        if (!Objects.equals(this.totalRegistros, "0")){
            actor.attemptsTo(
                    WaitUntil.the(Operador_Usuario, isVisible()).forNoMoreThan(15).seconds(),
                    Scroll.to(Operador_Usuario)
            );
            this.operador = Operador_Usuario.resolveFor(actor).getText();
        }else {
            this.operador = "Sin Operador";
        }

        try {
            crearDocumento(this.documento, this.operador, this.totalRegistros);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void crearDocumento(String documento, String operador, String registros) throws IOException {

        //String filePath = "C:/Users/Riiuzaky/Documents/Automatizaciones/Automatizaciones Web/Opsitel/src/test/resources/archivos/archivo.xlsx";

        // Crea el archivo si no existe
        
        //File file = new File(filePath);
        String filePath = "src/test/resources/archivos/archivo.xlsx";
        File file = new File(filePath);
        if (!file.exists()) {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Hoja 1");
            Row headerRow = sheet.createRow(0);
            Cell headerCell1 = headerRow.createCell(0);
            headerCell1.setCellValue("Documento");
            Cell headerCell2 = headerRow.createCell(1);
            headerCell2.setCellValue("Operador");
            Cell headerCell3 = headerRow.createCell(2);
            headerCell3.setCellValue("Registros");
            FileOutputStream outputStream = new FileOutputStream(filePath);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        }

        // Abre el archivo y agrega los datos
        FileInputStream inputStream = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        int rowCount = sheet.getLastRowNum();
        Row row = sheet.createRow(++rowCount);
        Cell cell1 = row.createCell(0);
        cell1.setCellValue(documento);
        Cell cell2 = row.createCell(1);
        cell2.setCellValue(operador);
        Cell cell3 = row.createCell(2);
        cell3.setCellValue(registros);
        inputStream.close();
        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

    }


    public String recortarCadena(String cadena){
        String regex = "de (\\d+) totales";
        Pattern pattern = Pattern.compile(regex);

        // Crear el objeto Matcher y hacer la búsqueda en la cadena
        Matcher matcher = pattern.matcher(cadena);

        // Verificar si se encuentra la coincidencia
        if (matcher.find()) {
            // Obtener el valor del grupo capturado (número)
            String subcadena = matcher.group(1);

            // Imprimir el resultado
            return subcadena;
        } else {
            return ("No se encontró la subcadena");
        }
    }

    public static ExtraerOperador extraer(String documento) {
        return instrumented(ExtraerOperador.class, documento);
    }
}
