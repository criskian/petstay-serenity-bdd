package userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/** Pantalla de ingreso (index.html). */
public class LoginPage {

    private LoginPage() {
    }

    // El campo usuario no tiene id: se localiza por su atributo name
    public static final Target USUARIO = Target.the("campo Usuario")
            .located(By.cssSelector("input[name='usuario']"));

    // La contraseña sí tiene un id estable
    public static final Target CLAVE = Target.the("campo Contraseña")
            .located(By.cssSelector("#pass-01"));

    // El botón expone un atributo pensado para pruebas
    public static final Target BOTON_INGRESAR = Target.the("botón Ingresar")
            .located(By.cssSelector("[data-testid='btn-ingresar']"));

    public static final Target MENSAJE_ERROR = Target.the("mensaje de error del login")
            .located(By.cssSelector("#error-login"));
}
