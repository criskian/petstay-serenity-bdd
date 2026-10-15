package stepdefinitions;

import io.cucumber.java.Before;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

/**
 * Prepara el "escenario" (stage) de Screenplay antes de cada escenario de Cucumber.
 * OnlineCast crea actores con la habilidad BrowseTheWeb, o sea, con su propio navegador.
 * Serenity cierra el navegador y retira a los actores al terminar el escenario.
 */
public class Hooks {

    public static final String ACTOR = "Dueño";

    @Before
    public void prepararEscenario() {
        OnStage.setTheStage(new OnlineCast());
        OnStage.theActorCalled(ACTOR);
    }
}
