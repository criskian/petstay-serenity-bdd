package userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/** Pantalla "Mis reservas" (reservas.html). */
public class ReservationsPage {

    private ReservationsPage() {
    }

    public static final Target MENSAJE = Target.the("mensaje de reservas")
            .located(By.cssSelector("#mensaje-reservas"));

    public static final Target CODIGOS = Target.the("códigos de las reservas")
            .located(By.cssSelector("[data-testid='tabla-reservas'] tbody td.codigo"));

    // {0} = código de la reserva (ej: PS-1234). Se parte de la celda del código para llegar al resto de su fila
    public static final Target MASCOTA_DE = Target.the("mascota de la reserva {0}")
            .locatedBy("//td[@class='codigo'][normalize-space()='{0}']/following-sibling::td[1]");

    public static final Target TOTAL_DE = Target.the("total de la reserva {0}")
            .locatedBy("//td[@class='codigo'][normalize-space()='{0}']/following-sibling::td[@class='total']");

    public static final Target ESTADO_DE = Target.the("estado de la reserva {0}")
            .locatedBy("//td[@class='codigo'][normalize-space()='{0}']/following-sibling::td/span[contains(@class,'estado')]");

    public static final Target REEMBOLSO_DE = Target.the("reembolso de la reserva {0}")
            .locatedBy("//td[@class='codigo'][normalize-space()='{0}']/following-sibling::td[@class='reembolso']");

    // El botón no tiene un texto único: lleva un aria-label con el código
    public static final Target CANCELAR = Target.the("botón Cancelar de la reserva {0}")
            .locatedBy("//button[@aria-label='Cancelar reserva {0}']");

    // ----- Diálogo de cancelación -----
    public static final Target MODAL = Target.the("diálogo de cancelación")
            .located(By.cssSelector(".modal"));

    public static final Target MODAL_TEXTO = Target.the("texto del diálogo de cancelación")
            .located(By.cssSelector(".modal .texto-modal"));

    public static final Target CONFIRMAR_CANCELACION = Target.the("botón 'Sí, cancelar'")
            .located(By.cssSelector("#confirmar-cancelacion"));

    public static final Target VOLVER = Target.the("botón 'No, volver'")
            .located(By.cssSelector(".modal [data-accion='volver']"));
}
