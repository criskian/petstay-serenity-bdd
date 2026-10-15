package questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;

/**
 * Texto de un elemento, esperando primero a que sea visible.
 *
 * Los mensajes de PetStay (éxito/error) están ocultos con el atributo [hidden] hasta que la app
 * termina de procesar. Leer el texto sin esperar devolvería "" y haría fallar la verificación
 * por una condición de carrera y no por un defecto real.
 */
public class VisibleText implements Question<String> {

    private final Target elemento;

    private VisibleText(Target elemento) {
        this.elemento = elemento;
    }

    public static VisibleText of(Target elemento) {
        return new VisibleText(elemento);
    }

    @Override
    public String answeredBy(Actor actor) {
        return elemento.resolveFor(actor).waitUntilVisible().getText().trim();
    }

    @Override
    public String toString() {
        return "el texto visible de " + elemento.getName();
    }
}
