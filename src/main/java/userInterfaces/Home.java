package userInterfaces;


import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;

@DefaultUrl("https://checatuslineas.osiptel.gob.pe/?fbclid=IwAR27U6aH9H5rcH6zh8oEkMBp582Kp4EelL9kEJYHjEyfFoZbRgTt6wZKPnw")
public class Home extends PageObject {
    public static final Target listaDeOpcionesDocumento = Target.the("lista de opciones").located(By.xpath("//div[@class='nice-select custom-select form-control txtcss']"));
    public static final Target opcionRUC = Target.the("opcion RUC").located(By.xpath("//li[@data-value='02']"));
    public static final Target NumeroDocumento = Target.the("input documento").located(By.id("NumeroDocumento"));
    public static final Target BotonBuscar = Target.the("boton buscar").located(By.id("btnBuscar"));
    public static final Target TotalRegistros = Target.the("total registros").located(By.id("GridConsulta_info"));
    public static final Target Operador_Usuario = Target.the("td operador").located(By.xpath("//table[@id='GridConsulta']//tbody//tr[1]//td[3]"));
}
