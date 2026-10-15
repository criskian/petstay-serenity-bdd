package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * Prepara y limpia el "escenario" (stage) de Screenplay alrededor de cada escenario de Cucumber.
 */
public class Hooks {

    public static final String ACTOR = "Dueño";

    /**
     * OnlineCast crea actores con la habilidad BrowseTheWeb, o sea, con su propio navegador.
     */
    @Before
    public void prepararEscenario() {
        OnStage.setTheStage(new OnlineCast());
        OnStage.theActorCalled(ACTOR);
    }

    /**
     * PetStay guarda la sesión, las mascotas y las reservas en localStorage. Serenity puede reutilizar
     * el mismo navegador entre escenarios, y entonces el siguiente escenario heredaría la sesión
     * iniciada (index.html redirigiría a "Mis mascotas") y las mascotas del anterior.
     * Para que cada escenario sea independiente se borra todo al terminar, pase o falle.
     */
    @After
    public void limpiarDatosDelNavegador() {
        try {
            WebDriver navegador = BrowseTheWeb.as(OnStage.theActorInTheSpotlight()).getDriver();
            ((JavascriptExecutor) navegador).executeScript(
                    "window.localStorage.clear(); window.sessionStorage.clear();");
            navegador.manage().deleteAllCookies();
        } catch (RuntimeException sinNavegador) {
            // Si el navegador ya se cerró o nunca se abrió no hay nada que limpiar
        }
    }
}
