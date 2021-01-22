package Recursos;

import java.io.File;
import java.net.URL;

public class Url {

    /**
     * Devuelve la URL donde se encuentra el fxml
     * @param nombre
     * @return
     */
    public static URL getURL(String nombre) {
        return Thread.currentThread().getContextClassLoader().getResource("interfaz" + File.separator + nombre);
    }

}
