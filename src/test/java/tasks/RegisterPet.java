package tasks;

import interactions.WaitForLoading;
import models.PetData;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import userinterfaces.PetsPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Diligencia y envía el formulario "Registrar mascota".
 *
 * Los pasos se arman según los datos recibidos: si la especie viene vacía no se marca ningún
 * radio, y si no hay vacunas no se marca la casilla. Así el mismo Task sirve para el caso feliz
 * y para los casos negativos (formulario incompleto, sin vacunas, etc.).
 */
public class RegisterPet {

    private RegisterPet() {
    }

    public static Performable withData(PetData mascota) {
        List<Performable> pasos = new ArrayList<>();

        pasos.add(Enter.theValue(mascota.nombre()).into(PetsPage.NOMBRE));
        if (!mascota.especie().isBlank()) {
            pasos.add(Click.on(PetsPage.ESPECIE.of(mascota.especie())));
        }
        pasos.add(Enter.theValue(mascota.peso()).into(PetsPage.PESO));
        pasos.add(Enter.theValue(mascota.edad()).into(PetsPage.EDAD));
        if (mascota.vacunasAlDia()) {
            pasos.add(Click.on(PetsPage.VACUNAS));
        }
        pasos.add(Click.on(PetsPage.REGISTRAR));
        pasos.add(WaitForLoading.toFinish());

        return Task.where("{0} registra la mascota '" + mascota.nombre() + "'",
                pasos.toArray(new Performable[0]));
    }
}
