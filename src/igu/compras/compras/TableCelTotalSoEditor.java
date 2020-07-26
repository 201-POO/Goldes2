package igu.compras.compras;

import data.CompraDetData;
import entities.CompraDet;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellEditor;
import util.MsgPanel;

/**
 *
 * @author Asullom
 */
public class TableCelTotalSoEditor extends AbstractCellEditor implements TableCellEditor {

    JTextField valor;
    private String valorInicial = "";
    private String valorActual = "";
    private int fila;
    private int col;

    ComprasPanel compra;
    private JTable tabla;

    public ComprasPanel getCompra() {
        return compra;
    }

    public TableCelTotalSoEditor(ComprasPanel compra) {
        this.compra = compra;
        valor = new JTextField();
        valor.setHorizontalAlignment(JTextField.RIGHT);
        valor.setFont(new Font("Tahoma", 1, 14));
        valor.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        valor.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                String filterStr = "0123456789.";
                char c = (char) e.getKeyChar();
                if (filterStr.indexOf(c) < 0) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void keyReleased(KeyEvent e) {
                JTextField tmp = (JTextField) e.getSource();
                valorActual = tmp.getText();
                System.out.println("keyReleased valorActual: " + valorActual);
                System.out.println("keyReleased valorInicial: " + valorInicial);

                ComprasDetTableModel mt = ((ComprasDetTableModel) tabla.getModel());

                if (!valorActual.trim().isEmpty()) {

                    mt.setValueAt(valorActual, fila, col);
                    tmp.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1));
                    MsgPanel.error("");

                    CompraDet d = ((CompraDet) mt.getRow(fila));
                    System.out.println("getTotal_so : " + d.getTotal_so());

                    if (d.getMov_tipo() == 1) {

                    } else if (d.getMov_tipo() == 2) {
                        d.setGlosa(d.getGlosa());
                        d.setTotal_so(d.getTc());
                        d.setTotal_so(Double.parseDouble(valorActual + ""));
                        d.setTotal_do(d.getTotal_do());

                        int opcion = CompraDetData.updateAdelanto(d);
                        if (opcion != 0) {
                            MsgPanel.success("Se han modificado el adelanto soles");
                            // paintParamsSoloPrecios(1);
                        }

                    } else {
                        MsgPanel.success("Debe seleccionar un tipo de movimiento");
                        fireEditingStopped();
                    }
                } else {
                    tmp.setBorder(new LineBorder(new java.awt.Color(255, 0, 0), 3));
                    MsgPanel.error("Total_so es requerido", true);
                }
            }
        });
    }

    @Override
    public Object getCellEditorValue() {
        if (valorActual.trim().isEmpty()) {
            return valorInicial;
        } else {
            return valorActual;
        }
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        System.out.println("getTableCellEditorComponent Total_so fila: " + row);
        tabla = table;
        fila = row;
        col = column;
        valorInicial = ((ComprasDetTableModel) table.getModel()).getValueAt(row, column) + "";
        valorActual = value == null ? "" : "" + value;
        valor.setText(valorActual);
        //System.out.println("Component valorActual : " + valorActual);
        //System.out.println("Component valorInicial : " + valorInicial);
        return valor;
    }

}
