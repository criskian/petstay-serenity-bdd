package tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import userinterfaces.Header;

/** Cierra la sesión con el botón del encabezado. */
public class Logout {

    private Logout() {
    }

    public static Performable ofPetStay() {
        return Task.where("{0} cierra la sesión", Click.on(Header.CERRAR_SESION));
    }
}
