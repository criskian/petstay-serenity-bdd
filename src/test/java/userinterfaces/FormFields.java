package userinterfaces;

import net.serenitybdd.screenplay.targets.Target;

/**
 * Elementos que se repiten con el mismo localizador en varios formularios.
 * Tanto "Registrar mascota" como "Reservar estadía" muestran sus errores de validación
 * en un span.error-campo identificado por data-campo.
 */
public class FormFields {

    private FormFields() {
    }

    // {0} = nombre del campo: nombre, especie, peso, edad, mascota, entrada o salida
    public static final Target ERROR_CAMPO = Target.the("error del campo {0}")
            .locatedBy("//span[contains(@class,'error-campo')][@data-campo='{0}']");
}
