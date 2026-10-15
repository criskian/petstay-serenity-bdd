package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Convierte expresiones de fecha escritas en lenguaje natural dentro de los .feature
 * (por ejemplo "dentro de 5 días") en el formato DD/MM/AAAA que exige la aplicación.
 *
 * Así los escenarios no dependen de fechas fijas que mañana ya estarían en el pasado.
 */
public final class Fechas {

    private static final DateTimeFormatter FORMATO_APP = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final Pattern DENTRO_DE = Pattern.compile("^dentro de (\\d+) d[ií]as?$", Pattern.CASE_INSENSITIVE);
    private static final Pattern HACE = Pattern.compile("^hace (\\d+) d[ií]as?$", Pattern.CASE_INSENSITIVE);

    private Fechas() {
    }

    /**
     * Acepta: "hoy", "ayer", "mañana", "dentro de N días", "hace N días".
     * Cualquier otro texto se devuelve tal cual (permite probar formatos inválidos como "31/02/2026" o "abc").
     */
    public static String interpretar(String expresion) {
        String texto = expresion.trim();
        LocalDate hoy = LocalDate.now();

        if (texto.equalsIgnoreCase("hoy")) return hoy.format(FORMATO_APP);
        if (texto.equalsIgnoreCase("ayer")) return hoy.minusDays(1).format(FORMATO_APP);
        if (texto.equalsIgnoreCase("mañana")) return hoy.plusDays(1).format(FORMATO_APP);

        Matcher dentro = DENTRO_DE.matcher(texto);
        if (dentro.matches()) return hoy.plusDays(Long.parseLong(dentro.group(1))).format(FORMATO_APP);

        Matcher hace = HACE.matcher(texto);
        if (hace.matches()) return hoy.minusDays(Long.parseLong(hace.group(1))).format(FORMATO_APP);

        return texto;
    }

    /** Fecha de salida = fecha de entrada + noches (ambas en formato DD/MM/AAAA). */
    public static String sumarNoches(String fechaEntrada, int noches) {
        return LocalDate.parse(fechaEntrada, FORMATO_APP).plusDays(noches).format(FORMATO_APP);
    }
}
