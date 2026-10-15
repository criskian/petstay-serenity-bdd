package tasks;

import interactions.RememberReservationCode;
import interactions.SelectPet;
import interactions.WaitForLoading;
import models.StayData;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;
import userinterfaces.BookingPage;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/** Pasos del formulario "Reservar estadía". Cada paso es pequeño para poder combinarlo en los escenarios. */
public class BookStay {

    private BookStay() {
    }

    /** Llena mascota y fechas, sin enviar nada todavía. */
    public static Performable fillingForm(StayData estadia) {
        return Task.where("{0} diligencia la reserva de '" + estadia.mascota() + "' del "
                        + estadia.entrada() + " al " + estadia.salida(),
                SelectPet.named(estadia.mascota()),
                Enter.theValue(estadia.entrada()).into(BookingPage.FECHA_ENTRADA),
                Enter.theValue(estadia.salida()).into(BookingPage.FECHA_SALIDA));
    }

    public static Performable addingBathService() {
        return Task.where("{0} agrega el servicio de baño y peluquería",
                Click.on(BookingPage.SERVICIO_BANO));
    }

    public static Performable addingWalkService() {
        return Task.where("{0} agrega el servicio de paseo diario",
                Click.on(BookingPage.SERVICIO_PASEO));
    }

    public static Performable calculatingTotal() {
        return Task.where("{0} calcula el total de la estadía",
                Click.on(BookingPage.CALCULAR));
    }

    /**
     * Confirma la reserva, espera a que la app termine de procesarla y anota el código generado
     * (PS-XXXX) para poder buscarlo después en "Mis reservas" o cancelarlo.
     */
    public static Performable confirming() {
        return Task.where("{0} confirma la reserva",
                Click.on(BookingPage.CONFIRMAR),
                WaitForLoading.toFinish(),
                WaitUntil.the(BookingPage.CODIGO_RESERVA, isVisible()).forNoMoreThan(15).seconds(),
                RememberReservationCode.shownOnScreen());
    }

    /** Flujo completo de reserva sin servicios adicionales; útil como precondición (Dado) de otros escenarios. */
    public static Performable completeBooking(StayData estadia) {
        return Task.where("{0} hace y confirma una reserva para '" + estadia.mascota() + "'",
                NavigateTo.booking(),
                fillingForm(estadia),
                calculatingTotal(),
                confirming());
    }
}
