package de.waishon.kstlogin.api;

/**
 * Created by Sören on 27.05.2017.
 */

public class ApiMethodBuilder {
    // REST Pfad in der API
    private String restPath;

    // Daten zur Authentifizierung
    private AuthCredentials authCredentials;

    /**
     * Konstruiert den API Builder
     * @param restPath Den Pfad der Methode
     */
    public ApiMethodBuilder(String restPath) {
        this.restPath = restPath;
    }

    /**
     * Fügt Auth Credentails zu
     * @param authCredentials
     * @return
     */
    public ApiMethodBuilder addAuthCredentials(AuthCredentials authCredentials) {
        this.authCredentials = authCredentials;
        return this;
    }

    /**
     * Gibt den REST Pfad zurück
     * @return
     */
    public String getRestPath() {
        return restPath;
    }

    /**
     * Gibt die Authentifizierungsdaten zurück
     * @return
     */
    public AuthCredentials getAuthCredentials() {
        return authCredentials;
    }
}
