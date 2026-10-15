package utils;

import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;

/**
 * Lee la URL de la aplicación desde serenity.conf (propiedad petstay.base.url),
 * que se puede sobrescribir con -Dpetstay.base.url=... al ejecutar Gradle.
 */
public final class Config {

    private static final EnvironmentVariables VARIABLES = SystemEnvironmentVariables.createEnvironmentVariables();

    private Config() {
    }

    public static String urlDe(String pagina) {
        String base = VARIABLES.getProperty("petstay.base.url", "https://petstay-aphn.onrender.com");
        return base.endsWith("/") ? base + pagina : base + "/" + pagina;
    }
}
