package models;

/**
 * Datos del formulario "Registrar mascota". Se usa un record inmutable para que
 * los Tasks reciban todos los datos juntos en lugar de seis parámetros sueltos.
 */
public record PetData(String nombre, String especie, String peso, String edad, boolean vacunasAlDia) {

    /** Mascota válida y con vacunas: solo importan nombre, especie y peso. */
    public static PetData valida(String nombre, String especie, String peso) {
        return new PetData(nombre, especie, peso, "3", true);
    }

    public PetData conEdad(String nuevaEdad) {
        return new PetData(nombre, especie, peso, nuevaEdad, vacunasAlDia);
    }

    public PetData sinVacunas() {
        return new PetData(nombre, especie, peso, edad, false);
    }

    public PetData conPeso(String nuevoPeso) {
        return new PetData(nombre, especie, nuevoPeso, edad, vacunasAlDia);
    }
}
