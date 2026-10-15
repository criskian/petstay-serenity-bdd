package questions;

import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.JavascriptExecutor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Nombres de las mascotas que aparecen en la tabla "Mascotas registradas".
 *
 * Se leen con una sola consulta al DOM. Con un Target múltiple, cuando la tabla está vacía
 * (caso negativo) Selenium esperaría todo el timeout implícito antes de responder "no hay filas".
 */
public class RegisteredPets {

    private static final String NOMBRES =
            "return Array.from(document.querySelectorAll('#tabla-mascotas tbody td.nombre'))"
                    + ".map(function (celda) { return celda.textContent.trim(); });";

    private RegisteredPets() {
    }

    @SuppressWarnings("unchecked")
    public static Question<List<String>> names() {
        return Question.about("los nombres de las mascotas registradas").answeredBy(actor -> {
            JavascriptExecutor js = (JavascriptExecutor) BrowseTheWeb.as(actor).getDriver();
            return ((List<Object>) js.executeScript(NOMBRES)).stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
        });
    }
}
