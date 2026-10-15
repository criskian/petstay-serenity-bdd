package tasks;

import interactions.WaitForLoading;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import userinterfaces.ReservationsPage;

/** Flujo de cancelación en dos tiempos: primero se abre el diálogo y luego se confirma o se desiste. */
public class CancelReservation {

    private CancelReservation() {
    }

    public static Performable opening(String codigo) {
        return Task.where("{0} pulsa Cancelar en la reserva " + codigo,
                Click.on(ReservationsPage.CANCELAR.of(codigo)));
    }

    public static Performable confirming() {
        return Task.where("{0} confirma la cancelación",
                Click.on(ReservationsPage.CONFIRMAR_CANCELACION),
                WaitForLoading.toFinish());
    }

    public static Performable dismissing() {
        return Task.where("{0} decide no cancelar y vuelve",
                Click.on(ReservationsPage.VOLVER));
    }
}
