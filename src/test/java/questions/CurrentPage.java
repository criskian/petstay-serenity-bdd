package questions;

import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.questions.Text;
import userinterfaces.Header;

/** Preguntas sobre en qué pantalla está el actor. */
public class CurrentPage {

    private CurrentPage() {
    }

    /** Título (h1) de la pantalla actual: "Mis mascotas", "Reservar estadía", "Mis reservas"... */
    public static Question<String> title() {
        return Text.of(Header.TITULO_PAGINA).asString();
    }

    public static Question<String> url() {
        return Question.about("la URL actual").answeredBy(actor ->
                BrowseTheWeb.as(actor).getDriver().getCurrentUrl());
    }
}
