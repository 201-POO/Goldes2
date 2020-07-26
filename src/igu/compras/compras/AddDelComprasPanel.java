package igu.compras.compras;

import data.CompraData;
import data.CompraDetData;
import data.ParametroData;
import entities.Compra;
import entities.CompraDet;
import entities.Parametro;
import entities.SaldosCompra;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 *
 * @author Asullom
 */
public class AddDelComprasPanel extends javax.swing.JPanel {

    /**
     * Creates new form AccionPanel
     */
    private JPanel ifr;
    private JTable tabla;
    private ComprasDetTableModel mtdc;
    private int indexFila = -1;
    private CeldaAccionEditor cae;
    //private Compra compraSelected;

    public AddDelComprasPanel(JPanel ifr) {
        initComponents();
        this.ifr = ifr;

    }

    public void setCeldaEditor(CeldaAccionEditor cae) {
        this.cae = cae;
    }

    public void setTabla(JTable tabla) {
        this.tabla = tabla;
        mtdc = (ComprasDetTableModel) this.tabla.getModel();
    }

    public void setIndexFila(int fila) {
        this.indexFila = fila;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addButton = new javax.swing.JButton();
        delButton = new javax.swing.JButton();

        setLayout(new java.awt.GridLayout(1, 0));

        addButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/igu/imgs/icons/agregar.png"))); // NOI18N
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        add(addButton);

        delButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/igu/imgs/icons/eliminar.png"))); // NOI18N
        delButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delButtonActionPerformed(evt);
            }
        });
        add(delButton);
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // TODO add your handling code here:

        if (this.tabla.getSelectedRow() != -1) {
            this.indexFila = this.tabla.getSelectedRow();
            System.err.println("add " + this.indexFila);
        }
        if (((ComprasPanel) this.ifr).isEsActualizacion()) {
            System.out.println("isEsActualizacion  true ");
        }

        ComprasPanel cp = ((ComprasPanel) this.ifr);
        Compra compraSelected = cp.getCompraSelected();
        if (compraSelected != null) {
            MovTipoChoicePanel mov = new MovTipoChoicePanel(compraSelected);
            // mov.setCompraSelected(compraSelected);
            //mov.getCompraSelected().getEsdolares()
            JOptionPane.showOptionDialog(null, mov,
                    "Elija una opción ",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{}, null);

            if (mov.getMovTipo() > 0) {
                CompraDet d = ((CompraDet) mtdc.getRow(this.indexFila));
                Parametro p = ParametroData.getById(1);

                if (mov.getMovTipo() == 1) {
                    d.setComp_id(compraSelected.getId());
                    d.setMov_tipo(mov.getMovTipo());
                    d.setGlosa("Compra");
                    d.setCant_gr(0);
                    d.setOnza(p.getOnza());
                    d.setPorc(p.getPorc());
                    d.setLey(p.getLey());
                    d.setSistema(p.getSistema());
                    d.setPrecio_do(p.getPrecio_do());
                    d.setTc(p.getTc());
                    d.setPrecio_so(p.getPrecio_so());
                    if (d.getId() > 0) {
                        CompraDetData.update(d);
                    } else {
                        int idx = CompraDetData.create(d);
                        d.setId(idx);
                        d.setActivo(1);
                    }
                }

                if (mov.getMovTipo() == 2) {
                    d.setComp_id(compraSelected.getId());
                    d.setMov_tipo(mov.getMovTipo());
                    d.setGlosa("ADELANTO");
                    d.setCant_gr(0);
                    d.setOnza(0);
                    d.setPorc(0);
                    d.setLey(0);
                    d.setSistema(0);
                    d.setPrecio_do(0);
                    d.setTc(0);
                    d.setPrecio_so(0);
                    d.setTotal_so(0);
                    d.setTotal_do(0);
                    d.setSaldo_do(0);
                    if (d.getId() > 0) {
                        CompraDetData.updateAdelanto(d);
                    } else {
                        int idx = CompraDetData.create(d);
                        d.setId(idx);
                        d.setActivo(1);
                    }
                }

                if (mov.getMovTipo() == 3) { // ADELANTO EN SOLES cuando la compra es en dólares
                    SaldosCompra sal = CompraDetData.getSaldosByCompId(compraSelected.getId());

                    d.setComp_id(compraSelected.getId());
                    d.setMov_tipo(mov.getMovTipo());
                    d.setGlosa("ADELANTO saldo$" + sal.getSaldo_do());
                    
                    d.setCant_gr(0);
                    d.setOnza(0);
                    d.setPorc(0);
                    d.setLey(0);
                    d.setSistema(0);
                    d.setPrecio_do(0);
                    d.setPrecio_so(0);
                    d.setTotal_do(0);
                    
                    
                    d.setSaldo_do(sal.getSaldo_do());
                    d.setTc(p.getTc());
                    
                    d.setTotal_so(sal.getSaldo_do() * p.getTc());
                    d.setTotal_do(0);

                    if (d.getId() > 0) {
                        CompraDetData.updateAdelanto(d);
                    } else {
                        int idx = CompraDetData.createAdelanto(d);
                        d.setId(idx);
                        d.setActivo(1);
                    }

                }

                this.tabla.changeSelection(this.indexFila, 1, true, false);
                // mtdc.contarItems();
                cae.lanzarDetencionEdicion();
            }

            // CompraDet d = new CompraDet();
            // mtdc.addRow(d);
            if (this.indexFila == this.tabla.getRowCount() - 1 && mov.getMovTipo() > 0) {
                // if (d.getCant_gr() > 0) {
                // this.lis.add(new CompraDet());
                //this.lis = CompraDetData.listByCompra(d.getId());
                //ComprasPanel cp = ((ComprasPanel) this.ifr);   
                //cp.paintTable(new ComprasDetTableModel(compraSelected));// eso te refresca toda la tabla

                mtdc.addRow(new CompraDet());
                // } else {
                //     JOptionPane.showMessageDialog(ifr, "La cantidad debe ser mayor que cero.",
                //             "Error: cantidad cero", JOptionPane.ERROR_MESSAGE);
                // }
            }

        }


    }//GEN-LAST:event_addButtonActionPerformed

    private void delButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delButtonActionPerformed
        // TODO add your handling code here:
        if (this.tabla.getSelectedRow() != -1) {
            this.indexFila = this.tabla.getSelectedRow();
        }
        CompraDet d = ((CompraDet) mtdc.getRow(this.indexFila));

        //if(d.getId() >0 )
        //{
        if (this.tabla.getRowCount() > 1) {
            if (this.indexFila < this.tabla.getRowCount() - 1) {
                int opc = JOptionPane.showConfirmDialog(ifr, "¿Realmente desea eliminar?", "Quitar", JOptionPane.YES_NO_OPTION);
                if (opc == JOptionPane.OK_OPTION) {

                    if (d != null) {
                        try {
                            int opcion = CompraDetData.delete(d.getId());
                            if (opcion != 0) {
                                mtdc.removeRow(this.indexFila);
                                System.out.println("removeRow fila " + this.indexFila);
                            }
                        } catch (Exception ex) {

                        }

                    } else {
                        System.err.println("no se encontró el detalle en la db fila " + this.indexFila);
                    }

                    // mtdc.contarItems();
                    cae.lanzarDetencionEdicion();
                    return;
                }
            }
        }
        if ((this.indexFila == 0 && this.tabla.getRowCount() == 1) || (this.indexFila == this.tabla.getRowCount() - 1 && this.tabla.getRowCount() > 1)) {
            int opc = JOptionPane.showConfirmDialog(ifr, "¿Realmente desea eliminar, no conteste o es igual?", "Quitar", JOptionPane.YES_NO_OPTION);
            if (opc == JOptionPane.OK_OPTION) {
                //d.setIdProducto(new Producto());
                //mtdc.remplazarProducto(new Producto(), indexFila);    
                //mtdc.contarItems();
                System.out.println("remplazarProducto en fila " + this.indexFila);
                cae.lanzarDetencionEdicion();
                return;
            }
        }
        // }


    }//GEN-LAST:event_delButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton delButton;
    // End of variables declaration//GEN-END:variables
}


/*
        //String[] options = {"Compra", "Adelanto"};
        // int x = JOptionPane.showOptionDialog(null, "Qué tipo de operación desea agregar?",
        //        "Elija una opción",
        //         JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        // System.out.println(x);
 */
