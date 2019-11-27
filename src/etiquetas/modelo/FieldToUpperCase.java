package etiquetas.modelo;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class FieldToUpperCase extends PlainDocument {

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        str = str.toUpperCase();
        super.insertString(offs, str, a);
    }
}
