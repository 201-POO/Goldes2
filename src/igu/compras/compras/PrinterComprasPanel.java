/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igu.compras.compras;

import data.CompraDetData;
import entities.Compra;
import entities.CompraDet;
import entities.SaldosCompra;
import igu.util.tables.EstiloTablaRendererXX;
import igu.util.tables.ExportarExcel;
import igu.util.tables.PrinterTable;
import java.awt.Component;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import util.Config;

/**
 *
 * @author Asullom
 */
public class PrinterComprasPanel extends javax.swing.JPanel {

    int d; // opcion seleccionado
    JOptionPane op;

    ExportarExcel obj;
    SimpleDateFormat iguSDF = new SimpleDateFormat(Config.DEFAULT_DATE_STRING_FORMAT_PE);
    private Compra compraSelected;

    /**
     * Creates new form PrinterTablePanel
     */
    public PrinterComprasPanel(Compra compraSelectedx) {
        initComponents();
        compraSelected = compraSelectedx;
        if (compraSelected != null) {
            paintTable(compraSelected.getId() + "");
            cliente.setText(compraSelected.getClie_nom() + " ac:"+compraSelected.getActivo());
        }

    }

    public int getImprimio() {
        return d;
    }

    private void getOptionPane() {
        if (op != null) {
            return;
        }
        Component pdr = this.getParent();
        while (pdr.getParent() != null) {
            if (pdr instanceof JOptionPane) {
                op = (JOptionPane) pdr;
                break;
            }
            pdr = pdr.getParent();
        }
    }

    private void paintTable(String comp_id) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();

        List<CompraDet> lis = null;
        try {
            lis = CompraDetData.listByCompra(Integer.parseInt(comp_id));
        } catch (NumberFormatException e1) {
            //lis = CompraDetData.list(0);
        }

        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
        String datos[] = new String[20];
        double stotal_do = 0;
        double stotal_so = 0;
        for (CompraDet d : lis) {
            stotal_do = stotal_do + d.getTotal_do();
            stotal_so = stotal_so + d.getTotal_so();

            //datos[0] = d.getGlosa() + "";
            datos[0] = d.getCant_gr() == 0 ? "" : new DecimalFormat(Config.DEFAULT_DECIMAL_FORMAT).format(d.getCant_gr()) + "";
            datos[1] = d.getOnza() == 0 ? "" : new DecimalFormat(Config.DEFAULT_DECIMAL_FORMAT).format(d.getOnza()) + "";
            datos[2] = d.getPorc() == 0 ? "" : new DecimalFormat(Config.DEFAULT_DECIMAL_FORMAT).format(d.getPorc()) + "";
            datos[3] = d.getLey() == 0 ? "" : new DecimalFormat(Config.DEFAULT_DECIMAL_FORMAT).format(d.getLey()) + "";
            datos[4] = d.getPrecio_do() == 0 ? "" : new DecimalFormat(Config.DEFAULT_DECIMAL_STRING_FORMAT).format(d.getPrecio_do()) + "";
            datos[5] = d.getTc() == 0 ? "" : new DecimalFormat(Config.DEFAULT_DECIMAL_FORMAT).format(d.getTc()) + "";

            datos[6] = d.getPrecio_so() == 0 ? "" : new DecimalFormat(Config.DEFAULT_DECIMAL_STRING_FORMAT).format(d.getPrecio_so()) + "";
            if (d.getMov_tipo() == 2) {
                datos[6] = d.getGlosa() + "";
            }
            if (d.getMov_tipo() == 3) {
                datos[6] = d.getGlosa() + "";
            }

            datos[7] = d.getTotal_so() == 0 ? "" : new DecimalFormat(Config.DEFAULT_DECIMAL_STRING_FORMAT).format(d.getTotal_so()) + "";
            datos[8] = d.getTotal_do() == 0 ? "" : new DecimalFormat(Config.DEFAULT_DECIMAL_STRING_FORMAT).format(d.getTotal_do()) + "";

            modelo.addRow(datos);
        }
        SaldosCompra sal = CompraDetData.getSaldosByCompId(compraSelected.getId());
        datos[0] = "";
        datos[1] = "";
        datos[2] = "";
        datos[3] = "";
        datos[4] = "";
        datos[5] = "";

        datos[6] = "TOTAL COMPRAS";
        datos[7] = new DecimalFormat(Config.DEFAULT_DECIMAL_STRING_FORMAT).format(sal.getSum_com_so());
        datos[8] = new DecimalFormat(Config.DEFAULT_DECIMAL_STRING_FORMAT).format(sal.getSum_com_do());
        modelo.addRow(datos);

        datos[0] = "";
        datos[1] = "";
        datos[2] = "";
        datos[3] = "";
        datos[4] = "";
        datos[5] = "";

        datos[6] = "TOTAL ADELANTOS";
        datos[7] = new DecimalFormat(Config.DEFAULT_DECIMAL_STRING_FORMAT).format(sal.getSum_ade_so());
        datos[8] = new DecimalFormat(Config.DEFAULT_DECIMAL_STRING_FORMAT).format(sal.getSum_ade_do());
        modelo.addRow(datos);

        datos[0] = "";
        datos[1] = "";
        datos[2] = "";
        datos[3] = "";
        datos[4] = "";
        datos[5] = "";

        datos[6] = "SALDO";
        datos[7] = new DecimalFormat(Config.DEFAULT_DECIMAL_STRING_FORMAT).format(sal.getSaldo_so());
        datos[8] = new DecimalFormat(Config.DEFAULT_DECIMAL_STRING_FORMAT).format(sal.getSaldo_do());
        if (compraSelected.getEsdolares() == 1) {
            datos[7] = "0";
        }
        if (compraSelected.getEsdolares() == 2) {
            datos[8] = "0";
        }
        modelo.addRow(datos);

        DefaultTableCellRenderer rightRenderer = new EstiloTablaRendererXX("numerico");
        tabla.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
        tabla.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(15);
        tabla.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(15);
        tabla.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
         tabla.getColumnModel().getColumn(3).setPreferredWidth(15);

        tabla.getColumnModel().getColumn(4).setPreferredWidth(15);
        tabla.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(15);
        tabla.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
        // tabla.getColumnModel().getColumn(6).setPreferredWidth(40);
        tabla.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
        // tabla.getColumnModel().getColumn(7).setPreferredWidth(40);
        tabla.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
        //tabla.getColumnModel().getColumn(8).setPreferredWidth(40);
        tabla.getColumnModel().getColumn(8).setCellRenderer(rightRenderer);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        theButton1 = new igu.util.buttons.TheButton();
        cliente = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        theButton1.setText("Imprimir y bloquear movimientos");
        theButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                theButton1ActionPerformed(evt);
            }
        });

        cliente.setText("Cliente:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(theButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(cliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(theButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "GRAMOS", "ONZA", "%", "LEY", "DÓLAR", "TC", "SOLES", "TOTAL SOLES", "TOTLA DÓLARES"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tabla);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 990, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void theButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_theButton1ActionPerformed
        // TODO add your handling code here:
        getOptionPane();

        if (compraSelected != null) {
            d=PrinterTable.imprime(tabla, "Cliente \n" + compraSelected.getClie_nom(), "", true);
            //d = 1;
        }

        op.setValue(1);

    }//GEN-LAST:event_theButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel cliente;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla;
    private igu.util.buttons.TheButton theButton1;
    // End of variables declaration//GEN-END:variables
}
