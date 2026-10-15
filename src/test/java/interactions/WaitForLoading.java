package interactions;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.waits.WaitUntil;
import userinterfaces.Header;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotVisible;

/**
 * PetStay simula procesos lentos (login, registrar, reservar, cancelar) mostrando una capa
 * de "cargando" que tapa la pantalla entre 0,3 y 1,5 segundos con un tiempo aleatorio.
 *
 * Si no se espera a que desaparezca, el siguiente clic falla con ElementClickInterceptedException.
 * Por eso toda acción que dispara esa capa termina con esta espera EXPLÍCITA (no con un sleep fijo).
 */
public class WaitForLoading {

    private WaitForLoading() {
    }

    public static Performable toFinish() {
        return Task.where("{0} espera a que termine la carga",
                WaitUntil.the(Header.CAPA_CARGANDO, isNotVisible()).forNoMoreThan(20).seconds());
    }
}
