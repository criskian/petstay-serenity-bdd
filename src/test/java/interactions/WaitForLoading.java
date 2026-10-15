package interactions;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.Tasks;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

/**
 * PetStay simula procesos lentos (login, registrar, reservar, cancelar) mostrando una capa
 * de "cargando" (#capa-cargando) que tapa la pantalla entre 0,3 y 1,5 segundos, con un tiempo aleatorio.
 *
 * Si no se espera a que desaparezca, el siguiente clic falla con ElementClickInterceptedException.
 * Por eso toda acción que dispara esa capa termina con esta espera EXPLÍCITA (no con un sleep fijo).
 *
 * Se consulta el DOM con JavaScript y no con WaitUntil(isNotVisible) porque, tras cambiar de página,
 * la capa ni siquiera existe: Selenium esperaría todo el timeout implícito (15 s) buscando un
 * elemento que nunca va a aparecer, y cada escenario tardaría medio minuto de más.
 */
public class WaitForLoading implements Interaction {

    private static final String CAPA_OCULTA_O_AUSENTE =
            "var capa = document.getElementById('capa-cargando'); return !capa || capa.hidden;";

    public static Performable toFinish() {
        return Tasks.instrumented(WaitForLoading.class);
    }

    @Override
    @Step("{0} espera a que termine la carga")
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofMillis(100))
                // Durante un cambio de página el script puede fallar un instante: se reintenta
                .ignoring(RuntimeException.class)
                .withMessage("La capa de carga de PetStay no desapareció en 20 segundos")
                .until(d -> (Boolean) ((JavascriptExecutor) d).executeScript(CAPA_OCULTA_O_AUSENTE));
    }
}
