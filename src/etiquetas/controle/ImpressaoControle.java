package etiquetas.controle;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;

public class ImpressaoControle {

    private String impressora;

    public void setImpressora(String impressora) {
        this.impressora = impressora;
    }

    public void imprimirEtiqueta(String comprovante) throws PrintException {
        PrintService service = this.detectaImpressoras();
        if (service == null) {
            return;
        }
        DocPrintJob docPrintJob = service.createPrintJob();
        InputStream stream = new ByteArrayInputStream(comprovante.getBytes());
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        Doc doc = new SimpleDoc(stream, flavor, new HashDocAttributeSet());
        docPrintJob.print(doc, new HashPrintRequestAttributeSet());
    }    

    private PrintService detectaImpressoras() {
        PrintService service = null;
        try {
            DocFlavor docFlavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
            PrintService[] ps = PrintServiceLookup.lookupPrintServices(docFlavor, new HashPrintServiceAttributeSet());
            for (PrintService printService : ps) {
                if (printService.getName().toUpperCase().contains(impressora.toUpperCase())) {
                    service = printService;
                }
            }
            return service;
        } catch (Exception e) {
            return service;
        }
    }

}
