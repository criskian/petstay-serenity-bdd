package stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import models.PetData;
import net.serenitybdd.screenplay.actors.OnStage;
import questions.RegisteredPets;
import questions.VisibleText;
import net.serenitybdd.screenplay.questions.Text;
import tasks.RegisterPet;
import userinterfaces.FormFields;
import userinterfaces.PetsPage;

import java.util.Map;
import java.util.Objects;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;

public class PetSteps {

    /** Precondición reutilizable: una mascota válida, con vacunas, ya registrada. */
    @Dado("que el dueño tiene registrada la mascota {string} de tipo {string} con peso {string} kg")
    public void queElDuenoTieneRegistradaLaMascota(String nombre, String especie, String peso) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                RegisterPet.withData(PetData.valida(nombre, especie, peso)));
        OnStage.theActorInTheSpotlight().should(
                seeThat(RegisteredPets.names(), hasItem(nombre)));
    }

    /**
     * Una sola tabla cubre el caso feliz y todos los negativos: una celda vacía deja el campo en blanco
     * y la columna "vacunas" admite "sí" o "no".
     */
    @Cuando("registra una mascota con los siguientes datos:")
    public void registraUnaMascotaConLosSiguientesDatos(DataTable tabla) {
        Map<String, String> fila = tabla.asMaps().get(0);
        PetData mascota = new PetData(
                valor(fila, "nombre"),
                valor(fila, "especie"),
                valor(fila, "peso"),
                valor(fila, "edad"),
                valor(fila, "vacunas").equalsIgnoreCase("sí"));
        OnStage.theActorInTheSpotlight().attemptsTo(RegisterPet.withData(mascota));
    }

    @Entonces("el mensaje de mascotas es {string}")
    public void elMensajeDeMascotasEs(String mensaje) {
        OnStage.theActorInTheSpotlight().should(
                seeThat(VisibleText.of(PetsPage.MENSAJE), equalTo(mensaje)));
    }

    @Entonces("la mascota {string} aparece en la lista con tamaño {string}")
    public void laMascotaApareceEnLaListaConTamano(String nombre, String tamano) {
        OnStage.theActorInTheSpotlight().should(
                seeThat(RegisteredPets.names(), hasItem(nombre)),
                seeThat(Text.of(PetsPage.TAMANO_DE.of(nombre)).asString(), equalTo(tamano)));
    }

    @Entonces("la lista de mascotas no incluye a {string}")
    public void laListaDeMascotasNoIncluyeA(String nombre) {
        OnStage.theActorInTheSpotlight().should(
                seeThat(RegisteredPets.names(), not(hasItem(nombre))));
    }

    /** Sirve para los formularios de mascotas y de reservas: ambos usan span.error-campo[data-campo]. */
    @Entonces("el campo {string} muestra el error {string}")
    public void elCampoMuestraElError(String campo, String error) {
        OnStage.theActorInTheSpotlight().should(
                seeThat(Text.of(FormFields.ERROR_CAMPO.of(campo)).asString(), equalTo(error)));
    }

    private static String valor(Map<String, String> fila, String columna) {
        return Objects.toString(fila.get(columna), "").trim();
    }
}
