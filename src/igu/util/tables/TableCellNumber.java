package igu.util.tables;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Asullom
 */
public class TableCellNumber extends DefaultTableCellRenderer {

    JTextField valor;
    JFormattedTextField ftfcampo;

    public TableCellNumber() {

        valor = new JTextField();
        valor.setHorizontalAlignment(JTextField.RIGHT);
        valor.setFont(new Font("Tahoma", 1, 14));
        valor.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
    }

    public TableCellNumber(String formato) {
        ftfcampo = new JFormattedTextField();
        ftfcampo.setHorizontalAlignment(JTextField.RIGHT);
        ftfcampo.setFont(new Font("Tahoma", 1, 14));
        ftfcampo.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        if (formato == null || formato.isEmpty()) {
            formato = "#0";
        }
        ftfcampo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(formato))));

    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        if (ftfcampo != null) {
            ftfcampo.setValue(value);
            if (isSelected) {
                ftfcampo.setBackground(table.getSelectionBackground());
            } else {
                ftfcampo.setBackground(null);
            }

            return ftfcampo;
        } else {
            valor.setText(value + "");
            if (isSelected) {
                valor.setBackground(table.getSelectionBackground());
            } else {
                valor.setBackground(null);
            }
            return valor;
        }

    }
}
