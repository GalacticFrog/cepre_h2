/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.ui.util;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class FiltroNumeros extends PlainDocument {

    private final JTextField textField;
    private final int maximo;

    public FiltroNumeros(JTextField mijtext, int maximo) {
        this.textField = mijtext;
        this.maximo = maximo;
    }

    @Override
    public void insertString(int arg0, String arg1, AttributeSet arg2) throws BadLocationException {
        for (int i = 0; i < arg1.length(); i++) {
            if ((!Character.isDigit(arg1.charAt(i))) || (textField.getText().length() + arg1.length()) > maximo) {
                return;
            }
        }
        super.insertString(arg0, arg1, arg2);
    }
}
