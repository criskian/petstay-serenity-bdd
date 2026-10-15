package tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;
import utils.Config;

/** Abre una página de PetStay. La URL base sale de serenity.conf (petstay.base.url). */
public class OpenApp {

    private OpenApp() {
    }

    public static Performable onLoginPage() {
        return page("index.html");
    }

    public static Performable page(String pagina) {
        return Task.where("{0} abre la página " + pagina,
                Open.url(Config.urlDe(pagina)));
    }
}
