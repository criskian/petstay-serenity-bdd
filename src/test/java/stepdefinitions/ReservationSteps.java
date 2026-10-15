package stepdefinitions;

import interactions.RememberReservationCode;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import questions.ElementState;
import questions.VisibleText;
import tasks.CancelReservation;
import tasks.NavigateTo;
import userinterfaces.ReservationsPage;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class ReservationSteps {

    @Cuando("entra a Mis reservas")
    public void entraAMisReservas() {
        OnStage.theActorInTheSpotlight().attemptsTo(NavigateTo.myReservations());
    }

    @Cuando("cancela la reserva")
    public void cancelaLaReserva() {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.attemptsTo(CancelReservation.opening(RememberReservationCode.of(actor)));
    }

    @Cuando("confirma la cancelación")
    public void confirmaLaCancelacion() {
        OnStage.theActorInTheSpotlight().attemptsTo(CancelReservation.confirming());
    }

    @Cuando("decide no cancelar y vuelve")
    public void decideNoCancelarYVuelve() {
        OnStage.theActorInTheSpotlight().attemptsTo(CancelReservation.dismissing());
    }

    @Entonces("la reserva aparece en Mis reservas para {string} con estado {string} y total {string}")
    public void laReservaApareceEnMisReservas(String mascota, String estado, String total) {
        Actor actor = OnStage.theActorInTheSpotlight();
        String codigo = RememberReservationCode.of(actor);
        actor.should(
                seeThat(VisibleText.of(ReservationsPage.MASCOTA_DE.of(codigo)), equalTo(mascota)),
                seeThat(VisibleText.of(ReservationsPage.ESTADO_DE.of(codigo)), equalTo(estado)),
                seeThat(VisibleText.of(ReservationsPage.TOTAL_DE.of(codigo)), equalTo(total)));
    }

    @Entonces("el diálogo informa un reembolso estimado de {string}")
    public void elDialogoInformaUnReembolsoEstimado(String reembolso) {
        Actor actor = OnStage.theActorInTheSpotlight();
        actor.should(
                seeThat(VisibleText.of(ReservationsPage.MODAL_TEXTO),
                        containsString("Reembolso estimado: " + reembolso)));
    }

    @Entonces("la reserva queda con estado {string} y reembolso {string}")
    public void laReservaQuedaConEstadoYReembolso(String estado, String reembolso) {
        Actor actor = OnStage.theActorInTheSpotlight();
        String codigo = RememberReservationCode.of(actor);
        actor.should(
                seeThat(VisibleText.of(ReservationsPage.ESTADO_DE.of(codigo)), equalTo(estado)),
                seeThat(VisibleText.of(ReservationsPage.REEMBOLSO_DE.of(codigo)), equalTo(reembolso)));
    }

    @Entonces("se informa que la reserva fue cancelada con un reembolso de {string}")
    public void seInformaQueLaReservaFueCancelada(String reembolso) {
        Actor actor = OnStage.theActorInTheSpotlight();
        String codigo = RememberReservationCode.of(actor);
        actor.should(
                seeThat(VisibleText.of(ReservationsPage.MENSAJE),
                        equalTo("La reserva " + codigo + " fue cancelada. Reembolso: " + reembolso)));
    }

    @Entonces("el diálogo de cancelación se cierra")
    public void elDialogoDeCancelacionSeCierra() {
        OnStage.theActorInTheSpotlight().should(
                seeThat(ElementState.isVisible(ReservationsPage.MODAL), equalTo(false)));
    }
}
