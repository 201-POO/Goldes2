/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igu.util.tables;

import java.awt.print.PrinterException;
import java.text.MessageFormat;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author Asullom
 */
public class PrinterTable {
    
    public static int imprime(JTable jTable, String header, String footer, boolean showPrintDialog) {
        boolean fitWidth = true;
        boolean interactive = true;
        // We define the print mode (Definimos el modo de impresión)
        JTable.PrintMode mode = fitWidth ? JTable.PrintMode.FIT_WIDTH : JTable.PrintMode.NORMAL;
        try {
            // Print the table (Imprimo la tabla)             
            boolean complete = jTable.print(mode,
                    new MessageFormat(header),
                    new MessageFormat(footer),
                    showPrintDialog,
                    null,
                    interactive);
            if (complete) {
                // Mostramos el mensaje de impresión existosa
               // JOptionPane.showMessageDialog(jTable,
                //        "Print complete (Impresión completa)",
                //        "Print result (Resultado de la impresión)",
                //        JOptionPane.INFORMATION_MESSAGE);
                return 1;
            } else {
                return 0;
                // Mostramos un mensaje indicando que la impresión fue cancelada                 
                //JOptionPane.showMessageDialog(jTable,
                //        "Print canceled (Impresión cancelada)",
                //        "Print result (Resultado de la impresión)",
                //        JOptionPane.WARNING_MESSAGE);
            }
        } catch (PrinterException pe) {
            JOptionPane.showMessageDialog(jTable,
                    "Print fail (Fallo de impresión): " + pe.getMessage(),
                    "Print result (Resultado de la impresión)",
                    JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }
}
