package tasks;

import interactions.WaitForLoading;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import userinterfaces.LoginPage;

/** Diligencia el formulario de ingreso y lo envía. */
public class Login {

    // Credenciales de práctica publicadas en la propia pantalla de ingreso
    public static final String USUARIO_VALIDO = "dueno";
    public static final String CLAVE_VALIDA = "huellitas123";

    private Login() {
    }

    public static Performable withValidCredentials() {
        return withCredentials(USUARIO_VALIDO, CLAVE_VALIDA);
    }

    public static Performable withCredentials(String usuario, String clave) {
        // La contraseña no se incluye en la descripción para que no aparezca en el reporte
        return Task.where("{0} inicia sesión con el usuario '" + usuario + "'",
                Enter.theValue(usuario).into(LoginPage.USUARIO),
                Enter.theValue(clave).into(LoginPage.CLAVE),
                Click.on(LoginPage.BOTON_INGRESAR),
                WaitForLoading.toFinish());
    }
}
