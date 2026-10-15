package userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/** Pantalla "Reservar estadía" (reservar.html). */
public class BookingPage {

    private BookingPage() {
    }

    // El <select> no tiene id: se localiza por name
    public static final Target MASCOTA = Target.the("lista de mascotas")
            .located(By.cssSelector("select[name='mascota']"));

    public static final Target FECHA_ENTRADA = Target.the("campo Fecha de entrada")
            .located(By.cssSelector("#fecha-entrada"));

    public static final Target FECHA_SALIDA = Target.the("campo Fecha de salida")
            .located(By.cssSelector("[data-testid='fecha-salida']"));

    public static final Target SERVICIO_BANO = Target.the("servicio Baño y peluquería")
            .located(By.cssSelector("#srv-bano"));

    public static final Target SERVICIO_PASEO = Target.the("servicio Paseo diario")
            .located(By.cssSelector("input[name='paseo']"));

    public static final Target NOTA_PASEO = Target.the("aviso 'paseo solo para perros'")
            .located(By.cssSelector(".nota-paseo"));

    public static final Target CALCULAR = Target.the("botón Calcular total")
            .located(By.cssSelector("#btn-calcular"));

    public static final Target CONFIRMAR = Target.the("botón Confirmar reserva")
            .located(By.cssSelector(".panel-resumen .btn-primario"));

    public static final Target MENSAJE = Target.the("mensaje de reserva")
            .located(By.cssSelector("[data-testid='mensaje-reserva']"));

    public static final Target CODIGO_RESERVA = Target.the("código de la reserva confirmada")
            .located(By.cssSelector("[data-testid='codigo-reserva']"));


    // ----- Panel de resumen -----
    public static final Target RESUMEN = Target.the("panel de resumen")
            .located(By.cssSelector(".panel-resumen"));

    public static final Target RESUMEN_NOCHES = Target.the("noches del resumen")
            .located(By.cssSelector("[data-testid='res-noches']"));

    public static final Target RESUMEN_DESCUENTO = Target.the("descuento del resumen")
            .located(By.cssSelector("[data-testid='res-descuento']"));

    public static final Target FILA_DESCUENTO = Target.the("fila de descuento")
            .located(By.cssSelector(".fila-descuento"));

    public static final Target RESUMEN_TOTAL = Target.the("total del resumen")
            .located(By.cssSelector("[data-testid='res-total']"));
}
