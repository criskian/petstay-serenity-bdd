package questions;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;

/**
 * Estado actual (sin esperas) de un elemento: ¿se ve? ¿está habilitado?
 *
 * Se usa para verificaciones "negativas" (el descuento NO aparece, el paseo NO se puede marcar).
 * Si se esperara a que el elemento se hiciera visible, esas pruebas tardarían todo el timeout implícito.
 */
public class ElementState {

    private ElementState() {
    }

    public static Question<Boolean> isVisible(Target elemento) {
        return Question.about("si " + elemento.getName() + " es visible").answeredBy(actor ->
                elemento.resolveAllFor(actor).stream().anyMatch(WebElementFacade::isCurrentlyVisible));
    }

    public static Question<Boolean> isEnabled(Target elemento) {
        return Question.about("si " + elemento.getName() + " está habilitado").answeredBy(actor ->
                elemento.resolveFor(actor).isCurrentlyEnabled());
    }
}
