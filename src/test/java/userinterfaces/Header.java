package userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/** Encabezado común a las pantallas internas (se dibuja con JavaScript en comun.js). */
public class Header {

    private Header() {
    }

    public static final Target SALUDO = Target.the("saludo al usuario")
            .located(By.cssSelector("header.barra .saludo strong"));

    public static final Target CERRAR_SESION = Target.the("botón Cerrar sesión")
            .located(By.cssSelector("button[name='cerrar-sesion']"));

    // {0} = texto del enlace del menú: "Mis mascotas", "Reservar" o "Mis reservas"
    public static final Target ENLACE_MENU = Target.the("enlace '{0}' del menú")
            .locatedBy("//nav[@aria-label='Menú principal']//a[normalize-space()='{0}']");

    // Capa que tapa la pantalla mientras la app "procesa" (dura entre 0,3 y 1,5 s)
    public static final Target CAPA_CARGANDO = Target.the("indicador de carga")
            .located(By.cssSelector("#capa-cargando"));

    public static final Target TITULO_PAGINA = Target.the("título de la página")
            .located(By.cssSelector("main h1"));
}
