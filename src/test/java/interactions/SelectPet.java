package interactions;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Tasks;
import org.openqa.selenium.support.ui.Select;
import userinterfaces.BookingPage;

/**
 * Elige una mascota en la lista desplegable del formulario de reserva.
 *
 * Cada opción se muestra como "Nombre (Especie, Tamaño)", por ejemplo "Rocky (Perro, Mediano)".
 * Quien escribe el escenario solo conoce el nombre, así que se busca la opción que empieza con él
 * en lugar de exigir el texto completo.
 */
public class SelectPet implements Interaction {

    private final String nombre;

    public SelectPet(String nombre) {
        this.nombre = nombre;
    }

    public static Performable named(String nombre) {
        return Tasks.instrumented(SelectPet.class, nombre);
    }

    @Override
    @Step("{0} selecciona la mascota #nombre en la lista")
    public <T extends Actor> void performAs(T actor) {
        WebElementFacade lista = BookingPage.MASCOTA.resolveFor(actor);
        Select desplegable = new Select(lista.getWrappedElement());

        String textoOpcion = desplegable.getOptions().stream()
                .map(opcion -> opcion.getText().trim())
                .filter(texto -> texto.startsWith(nombre + " ("))
                .findFirst()
                .orElseThrow(() -> new AssertionError(
                        "La mascota '" + nombre + "' no aparece en la lista de reserva"));

        desplegable.selectByVisibleText(textoOpcion);
    }
}
