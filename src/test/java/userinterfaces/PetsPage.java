package userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/** Pantalla "Mis mascotas" (mascotas.html). */
public class PetsPage {

    private PetsPage() {
    }

    public static final Target NOMBRE = Target.the("campo Nombre de la mascota")
            .located(By.cssSelector("#nombreMascota"));

    // {0} = "Perro" o "Gato". El radio se localiza por name + value
    public static final Target ESPECIE = Target.the("opción de especie {0}")
            .locatedBy("//input[@name='especie' and @value='{0}']");

    // Los ids de peso y edad son ilegibles (pw-4k, e-2): se prefieren name y data-testid
    public static final Target PESO = Target.the("campo Peso")
            .located(By.cssSelector("input[name='peso']"));

    public static final Target EDAD = Target.the("campo Edad")
            .located(By.cssSelector("[data-testid='edad']"));

    public static final Target VACUNAS = Target.the("casilla de vacunas al día")
            .located(By.cssSelector("input[name='vacunas']"));

    public static final Target REGISTRAR = Target.the("botón Registrar mascota")
            .locatedBy("//form[@id='form-mascota']//button[@type='submit']");

    public static final Target MENSAJE = Target.the("mensaje de mascotas")
            .located(By.cssSelector("[data-testid='mensaje-mascotas']"));


    public static final Target NOMBRES_REGISTRADOS = Target.the("nombres de las mascotas registradas")
            .located(By.cssSelector("#tabla-mascotas tbody td.nombre"));

    // Columnas de la fila: nombre, especie, peso, edad, tamaño, acciones.
    // Se parte de la celda del nombre y se avanza con following-sibling.
    public static final Target ESPECIE_DE = Target.the("especie de la mascota {0}")
            .locatedBy("//td[@class='nombre'][normalize-space()='{0}']/following-sibling::td[1]");

    public static final Target PESO_DE = Target.the("peso de la mascota {0}")
            .locatedBy("//td[@class='nombre'][normalize-space()='{0}']/following-sibling::td[2]");

    public static final Target TAMANO_DE = Target.the("tamaño de la mascota {0}")
            .locatedBy("//td[@class='nombre'][normalize-space()='{0}']/following-sibling::td[4]");

    public static final Target ENLACE_RESERVAR_DE = Target.the("enlace Reservar de la mascota {0}")
            .locatedBy("//td[@class='nombre'][normalize-space()='{0}']/following-sibling::td/a[normalize-space()='Reservar']");
}
