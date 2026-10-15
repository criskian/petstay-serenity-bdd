package stepdefinitions;

import interactions.RememberReservationCode;
import interactions.SelectPet;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import models.StayData;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actors.OnStage;
import questions.CurrentPage;
import questions.ElementState;
import questions.SelectedPet;
import questions.VisibleText;
import tasks.BookStay;
import tasks.NavigateTo;
import userinterfaces.BookingPage;
import userinterfaces.PetsPage;
import utils.Fechas;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.startsWith;

public class BookingSteps {

    // ---------- Precondiciones ----------

    @Dado("que el dueño tiene una reserva confirmada para {string} con entrada {string} por {int} noches")
    public void queElDuenoTieneUnaReservaConfirmada(String mascota, String entrada, int noches) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                BookStay.completeBooking(estadia(mascota, entrada, noches)));
    }

    // ---------- Acciones ----------

    @Cuando("entra a la sección Reservar")
    public void entraALaSeccionReservar() {
        OnStage.theActorInTheSpotlight().attemptsTo(NavigateTo.booking());
    }

    @Cuando("completa la reserva de {string} con entrada {string} y salida {string}")
    public void completaLaReservaConEntradaYSalida(String mascota, String entrada, String salida) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                BookStay.fillingForm(new StayData(mascota, Fechas.interpretar(entrada), Fechas.interpretar(salida))));
    }

    @Cuando("completa la reserva de {string} con entrada {string} por {int} noches")
    public void completaLaReservaConEntradaPorNoches(String mascota, String entrada, int noches) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                BookStay.fillingForm(estadia(mascota, entrada, noches)));
    }

    @Cuando("selecciona a la mascota {string} en el formulario de reserva")
    public void seleccionaLaMascotaEnElFormulario(String mascota) {
        OnStage.theActorInTheSpotlight().attemptsTo(SelectPet.named(mascota));
    }

    @Cuando("agrega el servicio de baño y peluquería")
    public void agregaElServicioDeBano() {
        OnStage.theActorInTheSpotlight().attemptsTo(BookStay.addingBathService());
    }

    @Cuando("agrega el servicio de paseo diario")
    public void agregaElServicioDePaseo() {
        OnStage.theActorInTheSpotlight().attemptsTo(BookStay.addingWalkService());
    }

    @Cuando("calcula el total")
    public void calculaElTotal() {
        OnStage.theActorInTheSpotlight().attemptsTo(BookStay.calculatingTotal());
    }

    @Cuando("confirma la reserva")
    public void confirmaLaReserva() {
        OnStage.theActorInTheSpotlight().attemptsTo(BookStay.confirming());
    }

    @Cuando("pulsa Reservar en la fila de la mascota {string}")
    public void pulsaReservarEnLaFilaDeLaMascota(String mascota) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Click.on(PetsPage.ENLACE_RESERVAR_DE.of(mascota)));
    }

    // ---------- Verificaciones ----------

    @Entonces("el resumen muestra {string} noches")
    public void elResumenMuestraNoches(String noches) {
        OnStage.theActorInTheSpotlight().should(
                seeThat(VisibleText.of(BookingPage.RESUMEN_NOCHES), equalTo(noches)));
    }

    @Entonces("el total de la reserva es {string}")
    public void elTotalDeLaReservaEs(String total) {
        OnStage.theActorInTheSpotlight().should(
                seeThat(VisibleText.of(BookingPage.RESUMEN_TOTAL), equalTo(total)));
    }

    @Entonces("el resumen muestra un descuento por estadía larga de {string}")
    public void elResumenMuestraUnDescuento(String descuento) {
        OnStage.theActorInTheSpotlight().should(
                seeThat(VisibleText.of(BookingPage.RESUMEN_DESCUENTO), equalTo(descuento)));
    }

    @Entonces("el resumen no muestra descuento por estadía larga")
    public void elResumenNoMuestraDescuento() {
        OnStage.theActorInTheSpotlight().should(
                seeThat(ElementState.isVisible(BookingPage.RESUMEN), equalTo(true)),
                seeThat(ElementState.isVisible(BookingPage.FILA_DESCUENTO), equalTo(false)));
    }

    @Entonces("no se muestra el resumen de la reserva")
    public void noSeMuestraElResumen() {
        OnStage.theActorInTheSpotlight().should(
                seeThat(ElementState.isVisible(BookingPage.RESUMEN), equalTo(false)));
    }

    @Entonces("el mensaje de reserva indica {string}")
    public void elMensajeDeReservaIndica(String texto) {
        OnStage.theActorInTheSpotlight().should(
                seeThat(VisibleText.of(BookingPage.MENSAJE), containsString(texto)));
    }

    @Entonces("el código de la reserva tiene el formato PS-0000")
    public void elCodigoTieneElFormato() {
        String codigo = RememberReservationCode.of(OnStage.theActorInTheSpotlight());
        OnStage.theActorInTheSpotlight().should(
                seeThat("el código de reserva generado", actor -> codigo, matchesPattern("PS-\\d{4}")));
    }

    @Entonces("el servicio de paseo diario está deshabilitado")
    public void elPaseoEstaDeshabilitado() {
        OnStage.theActorInTheSpotlight().should(
                seeThat(ElementState.isEnabled(BookingPage.SERVICIO_PASEO), equalTo(false)));
    }

    @Entonces("se muestra el aviso {string}")
    public void seMuestraElAviso(String aviso) {
        OnStage.theActorInTheSpotlight().should(
                seeThat(VisibleText.of(BookingPage.NOTA_PASEO), equalTo(aviso)));
    }

    @Entonces("el formulario de reserva tiene seleccionada a la mascota {string}")
    public void elFormularioTieneSeleccionadaLaMascota(String mascota) {
        OnStage.theActorInTheSpotlight().should(
                seeThat(CurrentPage.title(), equalTo("Reservar estadía")),
                seeThat(SelectedPet.inBookingForm(), startsWith(mascota + " (")));
    }

    // ---------- Utilidad ----------

    private static StayData estadia(String mascota, String entrada, int noches) {
        String fechaEntrada = Fechas.interpretar(entrada);
        return new StayData(mascota, fechaEntrada, Fechas.sumarNoches(fechaEntrada, noches));
    }
}
