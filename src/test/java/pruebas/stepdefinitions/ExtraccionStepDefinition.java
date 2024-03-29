package pruebas.stepdefinitions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import org.openqa.selenium.WebDriver;
import patrones.TestContext;
import task.ExtraerOperador;
import task.ImportarExcel;
import userInterfaces.Home;
import java.util.List;


public class ExtraccionStepDefinition {

    private WebDriver navegador;
    private Actor actor = Actor.named("Jonathan");
    private Home home = new Home();

    private TestContext testContext = new TestContext();

    @Given("el asesor navego a la url de OSIPTEL")
    public void elAsesorNavegoALaUrlDeLaPagina() {
        ImportarExcel importador = testContext.getImportador();
        importador.performAs(actor);



    }
    @And("Cargo el archivo de excel")
    public void cargoElArchivoDeExcel() {
        actor.can(BrowseTheWeb.with(navegador));
        actor.wasAbleTo(Open.browserOn(home));
    }
    @When("extraigo documento del usuario")
    public void extraigoDocumentoDelUsuario() {
        ImportarExcel importador = testContext.getImportador();
        List<String> documentos = importador.obtenerRegistros(); // Obtener la lista desde la misma instancia

        for (String documento : documentos) {
           actor.wasAbleTo(
                   ExtraerOperador.extraer(documento)
          );
       }
    }

    @Then("agrego los operadores al documento")
    public void agregoElOperadorAlDocumento() {

    }



}
