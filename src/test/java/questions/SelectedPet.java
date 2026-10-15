package questions;

import net.serenitybdd.screenplay.Question;
import org.openqa.selenium.support.ui.Select;
import userinterfaces.BookingPage;

/** Texto de la opción seleccionada en la lista de mascotas del formulario de reserva. */
public class SelectedPet {

    private SelectedPet() {
    }

    public static Question<String> inBookingForm() {
        return Question.about("la mascota seleccionada en el formulario de reserva").answeredBy(actor -> {
            Select lista = new Select(BookingPage.MASCOTA.resolveFor(actor).getWrappedElement());
            return lista.getFirstSelectedOption().getText().trim();
        });
    }
}
