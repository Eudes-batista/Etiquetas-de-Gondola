package etiquetas.controle;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImpressaoControle {

    public void imprimirEtiqueta(String impressora, String etiqueta) throws FileNotFoundException, IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(impressora)) {
            fileOutputStream.write(etiqueta.getBytes());
            fileOutputStream.flush();
        }
    }

}
