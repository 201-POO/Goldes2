/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class TableCellGlosaEditor extends AbstractCellEditor implements TableCellEditor {

    JTextField valor;
    private String valorInicial = "";
    private String valorActual = "";
    private int fila;
    private int col;

    // private ComprasDetTableModel mtdc;
    ComprasPanel compra;
    private JTable tabla;

    public ComprasPanel getCompra() {
        return compra;
    }

    public TableCellGlosaEditor(ComprasPanel compra) {
        this.compra = compra;
        valor = new JTextField();
        //glosa.setHorizontalAlignment(JTextField.RIGHT);
        valor.setFont(new Font("Tahoma", 1, 14));
        valor.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        valor.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
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
                    //System.out.println("getGlosa : " + d.getGlosa());
                    //System.out.println("getOnza : " + d.getOnza());

                    if (d.getMov_tipo() == 1) {
                        d.setGlosa(valorActual);
                        d.setComp_id(d.getComp_id());
                        d.setMov_tipo(d.getMov_tipo());

                        d.setCant_gr(d.getCant_gr());
                        d.setOnza(d.getOnza());
                        d.setPorc(d.getPorc());
                        d.setLey(d.getLey());
                        d.setSistema(d.getSistema());
                        // d.setPrecio_do(d.getPrecio_do());
                        d.setTc(d.getTc());
                        //d.setPrecio_so(d.getPrecio_so());

                        int opcion = CompraDetData.update(d);
                        if (opcion != 0) {
                            MsgPanel.success("Se han modificado el detalle de la compra");
                            // paintParamsSoloPrecios(1);
                        }
                    } else if (d.getMov_tipo() == 2) {
                        d.setGlosa(valorActual);
                      
                        d.setTotal_do(d.getTotal_do());
                        d.setTotal_so(d.getTotal_so());

                        int opcion = CompraDetData.updateAdelanto(d);
                        if (opcion != 0) {
                            MsgPanel.success("Se han modificado el adelanto");
                            // paintParamsSoloPrecios(1);
                        }
                    } else {
                        MsgPanel.success("Debe seleccionar un tipo de movimietno");
                        fireEditingStopped();
                    }
                    

                } else {
                  //  mt.setValueAt(valorInicial, fila, col);//aquiiii
                    tmp.setBorder(new LineBorder(new java.awt.Color(255, 0, 0), 3));
                    MsgPanel.error("glosa es requerido", true);
                   // tmp.setText(valorInicial);
               //    tmp.requestFocus();
                }

            }
        });
/*
        glosa.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                System.out.println("Tienee el focoooo");
            }

            @Override
            public void focusLost(FocusEvent e) {
                System.out.println("Lost el focoooo");

                //JTextField tmp = (JTextField) e.getSource();
               // valorActual = tmp.getText();
                System.out.println("focusLost valorActual : " + valorActual);
                System.out.println("focusLost valorInicial : " + valorInicial);

                ComprasDetTableModel mt = ((ComprasDetTableModel) tabla.getModel());
                if (valorActual.trim().isEmpty()) { //reset

                 //   tmp.setText(valorInicial);
                   // glosa.setText(valorInicial);
                    mt.setValueAt(valorInicial, fila, col);//aquiiii
                   // tmp.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1));
                    MsgPanel.success("glosa es requerido, valor recupuerado");
                }
                fireEditingStopped();
            }
        });*/

    }

    @Override
    public Object getCellEditorValue() {
        if (valorActual.trim().isEmpty()) { 
            return valorInicial;
        }else {
            return valorActual;
        }
        
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        //System.out.println("getTableCellEditorComponent  glosa fila: " + row);
        tabla = table;
        fila = row;
        col = column;
        
        //if (valorInicial == null) {
        //valorInicial = ((ComprasDetTableModel) table.getModel()).getValueAt(row, column);
        //}
        //if (valorInicial.trim().isEmpty()) {
        valorInicial = ((ComprasDetTableModel) table.getModel()).getValueAt(row, column) + "";
        //}
        valorActual = value == null ? "" : "" + value;
        valor.setText(valorActual);
       // if (valor.getText().trim().isEmpty()) { //reset
            //  valorActual=valorInicial;
            //    glosa.setText(valorInicial);
       // }
       // System.out.println("Component valorActual : " + valorActual);
        //System.out.println("Component valorInicial : " + valorInicial);

        return valor;
    }

}
