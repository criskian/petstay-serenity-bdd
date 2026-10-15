package interactions;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.questions.Text;
import userinterfaces.BookingPage;

/**
 * El código de reserva (PS-XXXX) es aleatorio, así que el actor lo "recuerda" al confirmar
 * la reserva para poder buscarlo después en "Mis reservas" o cancelarlo.
 */
public class RememberReservationCode implements Interaction {

    /** Clave bajo la que el actor guarda el código de su última reserva confirmada. */
    public static final String CLAVE = "codigoReserva";

    public static Performable shownOnScreen() {
        return Tasks.instrumented(RememberReservationCode.class);
    }

    public static String of(Actor actor) {
        return actor.recall(CLAVE);
    }

    @Override
    @Step("{0} anota el código de la reserva")
    public <T extends Actor> void performAs(T actor) {
        String codigo = Text.of(BookingPage.CODIGO_RESERVA).answeredBy(actor).trim();
        actor.remember(CLAVE, codigo);
    }
}
