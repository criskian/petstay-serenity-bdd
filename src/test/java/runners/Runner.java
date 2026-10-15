package runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * Punto de entrada de la suite: Cucumber lee los .feature y Serenity registra
 * cada paso para construir el reporte con capturas de pantalla.
 *
 * Para ejecutar solo una parte de la suite se filtra por tags:
 *   ./gradlew test -Dcucumber.filter.tags="@HU-03"      (un requisito)
 *   ./gradlew test -Dcucumber.filter.tags="@issue:12"   (un caso de prueba / issue)
 *   ./gradlew test -Dcucumber.filter.tags="@negativo"   (solo escenarios negativos)
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin = "pretty",
        features = "src/test/resources/features",
        glue = "stepdefinitions",
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class Runner {

}
