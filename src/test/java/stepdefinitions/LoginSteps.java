package stepdefinitions;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.waits.WaitUntil;
import questions.CurrentPage;
import questions.ElementState;
import questions.VisibleText;
import tasks.Login;
import tasks.Logout;
import tasks.OpenApp;
import userinterfaces.Header;
import userinterfaces.LoginPage;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class LoginSteps {

    @Dado("que el dueño abre la aplicación PetStay")
    public void queElDuenoAbreLaAplicacion() {
        OnStage.theActorInTheSpotlight().attemptsTo(OpenApp.onLoginPage());
    }

    @Dado("que el dueño inició sesión en PetStay")
    public void queElDuenoInicioSesion() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                OpenApp.onLoginPage(),
                Login.withValidCredentials());
    }

    @Dado("que el dueño intenta abrir la página {string} sin haber iniciado sesión")
    public void queElDuenoIntentaAbrirSinSesion(String pagina) {
        OnStage.theActorInTheSpotlight().attemptsTo(OpenApp.page(pagina));
    }

    @Cuando("inicia sesión con el usuario {string} y la contraseña {string}")
    public void iniciaSesionCon(String usuario, String clave) {
        OnStage.theActorInTheSpotlight().attemptsTo(Login.withCredentials(usuario, clave));
    }

    @Cuando("cierra la sesión")
    public void cierraLaSesion() {
        OnStage.theActorInTheSpotlight().attemptsTo(Logout.ofPetStay());
    }

    @Entonces("ve la pantalla {string}")
    public void veLaPantalla(String titulo) {
        OnStage.theActorInTheSpotlight().should(
                seeThat(CurrentPage.title(), equalTo(titulo)));
    }

    @Entonces("el saludo muestra al usuario {string}")
    public void elSaludoMuestraAlUsuario(String usuario) {
        OnStage.theActorInTheSpotlight().should(
                seeThat(VisibleText.of(Header.SALUDO), equalTo(usuario)));
    }

    @Entonces("ve el error de inicio de sesión {string}")
    public void veElErrorDeInicioDeSesion(String mensaje) {
        OnStage.theActorInTheSpotlight().should(
                seeThat(VisibleText.of(LoginPage.MENSAJE_ERROR), equalTo(mensaje)));
    }

    @Entonces("permanece en la pantalla de ingreso")
    public void permaneceEnLaPantallaDeIngreso() {
        OnStage.theActorInTheSpotlight().should(
                seeThat(ElementState.isVisible(LoginPage.BOTON_INGRESAR), equalTo(true)),
                seeThat(CurrentPage.url(), org.hamcrest.Matchers.not(containsString("mascotas.html"))));
    }

    @Entonces("es redirigido a la pantalla de ingreso")
    public void esRedirigidoALaPantallaDeIngreso() {
        // La redirección ocurre por JavaScript después de cargar la página: se espera a que aparezca el login
        OnStage.theActorInTheSpotlight().attemptsTo(
                WaitUntil.the(LoginPage.BOTON_INGRESAR, isVisible()).forNoMoreThan(10).seconds());
        OnStage.theActorInTheSpotlight().should(
                seeThat(CurrentPage.url(), containsString("index.html")));
    }
}
