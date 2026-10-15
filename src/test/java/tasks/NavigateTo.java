package tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import userinterfaces.Header;

/** Navega usando el menú principal, como lo haría una persona. */
public class NavigateTo {

    private NavigateTo() {
    }

    public static Performable myPets() {
        return menu("Mis mascotas");
    }

    public static Performable booking() {
        return menu("Reservar");
    }

    public static Performable myReservations() {
        return menu("Mis reservas");
    }

    private static Performable menu(String opcion) {
        return Task.where("{0} entra a '" + opcion + "' desde el menú",
                Click.on(Header.ENLACE_MENU.of(opcion)));
    }
}
