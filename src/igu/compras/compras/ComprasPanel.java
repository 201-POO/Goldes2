package igu.compras.compras;

import data.ClienteData;
import data.CompraData;
import data.CompraDetData;
import entities.Cliente;
import entities.Compra;
import entities.SaldosCompra;
import igu.util.tables.TableCellNumber;

import java.awt.event.ItemEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import util.Config;
import util.MsgPanel;

/**
 *
 * @author Asullom
 */
public class ComprasPanel extends javax.swing.JPanel {

    ComprasDetTableModel mtdc;

    private List<Cliente> clientes;

    private boolean esActualizacion;
    private Cliente clienteSelected;
    private Compra compraSelected;
    private DefaultComboBoxModel clientesComboxModel;
    SimpleDateFormat iguSDF = new SimpleDateFormat(Config.DEFAULT_DATE_STRING_FORMAT_PE);

    public Compra getCompraSelected() {
        return compraSelected;
    }

    public boolean isEsActualizacion() {
        return esActualizacion;
    }

    public ComprasPanel() {
        initComponents();
        esActualizacion = false;
        tabla.setEnabled(true);
        mtdc = new ComprasDetTableModel();

        tabla.setModel(mtdc);
        tabla.setEnabled(false);

        //tabla.getTableHeader().setDefaultRenderer(new EstiloTablaHeader());
        //tabla.setDefaultRenderer(Object.class, new EstiloTablaFootRenderer());
        clientes = ClienteData.listCmb("");
        clientesComboxModel = new DefaultComboBoxModel(clientes.toArray());

        cmbProveedor.setModel(clientesComboxModel);

        if (clientes.size() > 0) {
            //clienteSelected = clientes.get(0);
            //cmbProveedor.getModel().setSelectedItem(clienteSelected);

        } else {
            // mtdc = new ModeloTablaDetalleCompra();
        }

        paintTable(mtdc);

    }

    public void paintTable(ComprasDetTableModel tableModel) {
        this.mtdc = tableModel;
        tabla.setModel(mtdc);

        tabla.getColumnModel().getColumn(0).setMaxWidth(35);
        //tabla.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() ); 

        tabla.getColumnModel().getColumn(1).setCellEditor(new TableCellGlosaEditor(this));//glosa

        tabla.getColumnModel().getColumn(2).setCellEditor(new TableCelGramosEditor(this));
        tabla.getColumnModel().getColumn(2).setCellRenderer(new TableCellNumber("#,##0.00")); // "#,##0.00"

        tabla.getColumnModel().getColumn(3).setCellEditor(new TableCelOnzaEditor(this));
        tabla.getColumnModel().getColumn(3).setCellRenderer(new TableCellNumber());

        tabla.getColumnModel().getColumn(4).setCellEditor(new TableCelPorcEditor(this));
        tabla.getColumnModel().getColumn(4).setCellRenderer(new TableCellNumber());

        tabla.getColumnModel().getColumn(5).setCellEditor(new TableCelLeyEditor(this));
        tabla.getColumnModel().getColumn(5).setCellRenderer(new TableCellNumber());

        tabla.getColumnModel().getColumn(7).setCellEditor(new TableCelTcEditor(this));
        tabla.getColumnModel().getColumn(7).setCellRenderer(new TableCellNumber());

        tabla.getColumnModel().getColumn(9).setCellEditor(new TableCelTotalSoEditor(this));
        tabla.getColumnModel().getColumn(9).setCellRenderer(new TableCellNumber("#,##0.00"));

        tabla.getColumnModel().getColumn(10).setCellEditor(new TableCelTotalDolaresEditor(this));
        tabla.getColumnModel().getColumn(10).setCellRenderer(new TableCellNumber("#,##0.00"));

        tabla.getColumnModel().getColumn(11).setCellRenderer(new AccionTableCellRenderer(this));
        tabla.getColumnModel().getColumn(11).setCellEditor(new CeldaAccionEditor(this));

        setEventTable();
        if (compraSelected != null) {
            if (compraSelected.getActivo() == 1) {
                estado.setText("EN PROCESO");
            }
            if (compraSelected.getActivo() == 2) {
                estado.setText("COMPLETADO");
            }
        }

    }

    private void setEventTable() {
        TableModelListener tml = new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                System.out.printf("tableChanged \n");
                tableHandlerEvent();
            }
        };
        this.tabla.getModel().addTableModelListener(tml);

    }

    private void tableHandlerEvent() {
        if (compraSelected != null) {
            System.out.printf("compraSelected \n");
            SaldosCompra sal = CompraDetData.getSaldosByCompId(compraSelected.getId());
            sum_com_so.setText(new DecimalFormat(Config.DEFAULT_DECIMAL_STRING_FORMAT).format(sal.getSum_com_so()));
            sum_com_do.setText(new DecimalFormat(Config.DEFAULT_DECIMAL_STRING_FORMAT).format(sal.getSum_com_do()));

            sum_ade_so.setText(new DecimalFormat(Config.DEFAULT_DECIMAL_STRING_FORMAT).format(sal.getSum_ade_so()));
            sum_ade_do.setText(new DecimalFormat(Config.DEFAULT_DECIMAL_STRING_FORMAT).format(sal.getSum_ade_do()));

            saldo_so.setText(new DecimalFormat(Config.DEFAULT_DECIMAL_STRING_FORMAT).format(sal.getSaldo_so()));
            saldo_do.setText(new DecimalFormat(Config.DEFAULT_DECIMAL_STRING_FORMAT).format(sal.getSaldo_do()));
            if (compraSelected.getEsdolares() == 1) {
                saldo_so.setText("0");
            }
            if (compraSelected.getEsdolares() == 2) {
                saldo_do.setText("0");
            }
        } else {
            System.out.printf("compra no Selected \n");
            sum_com_so.setText("0");
            sum_com_do.setText("0");
            sum_ade_so.setText("0");
            sum_ade_do.setText("0");
            saldo_do.setText("0");
            saldo_do.setText("0");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cmbProveedor = new javax.swing.JComboBox();
        theButton1 = new igu.util.buttons.TheButton();
        iniciarCompra = new igu.util.buttons.TheButton();
        jLabel8 = new javax.swing.JLabel();
        moneda_soles = new javax.swing.JRadioButton();
        moneda_dolares = new javax.swing.JRadioButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        sum_com_so = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        sum_com_do = new javax.swing.JFormattedTextField();
        msgPanel1 = new util.MsgPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        sum_ade_so = new javax.swing.JFormattedTextField();
        sum_ade_do = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        saldo_so = new javax.swing.JFormattedTextField();
        saldo_do = new javax.swing.JFormattedTextField();
        theButton2 = new igu.util.buttons.TheButton();
        jLabel9 = new javax.swing.JLabel();
        estado = new javax.swing.JLabel();
        imprimir = new igu.util.buttons.TheButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setText("COMPRAS");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1))
        );

        jPanel4.setBackground(new java.awt.Color(204, 255, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Cliente:");

        cmbProveedor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbProveedorItemStateChanged(evt);
            }
        });

        theButton1.setText("BUSCAR");
        theButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                theButton1ActionPerformed(evt);
            }
        });

        iniciarCompra.setText(" INICIAR O CONTINUAR");
        iniciarCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iniciarCompraActionPerformed(evt);
            }
        });

        jLabel8.setText("MONEDA: ");

        buttonGroup1.add(moneda_soles);
        moneda_soles.setText("SOLES");
        moneda_soles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moneda_solesActionPerformed(evt);
            }
        });

        buttonGroup1.add(moneda_dolares);
        moneda_dolares.setText("DÓLARES");
        moneda_dolares.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moneda_dolaresActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(16, 16, 16)
                .addComponent(cmbProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(theButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(moneda_soles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(moneda_dolares)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(iniciarCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(theButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(iniciarCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(moneda_soles)
                        .addComponent(moneda_dolares)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setText("SOLES");

        sum_com_so.setEditable(false);
        sum_com_so.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        sum_com_so.setText("0");
        sum_com_so.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel4.setText("DÓLARES");

        sum_com_do.setEditable(false);
        sum_com_do.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        sum_com_do.setText("0");
        sum_com_do.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel5.setText("TOTAL COMPRA");

        jLabel6.setText("TOTAL ADELANTOS");

        sum_ade_so.setEditable(false);
        sum_ade_so.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        sum_ade_so.setText("0");
        sum_ade_so.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        sum_ade_do.setEditable(false);
        sum_ade_do.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        sum_ade_do.setText("0");
        sum_ade_do.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel7.setText("SALDO FINAL");

        saldo_so.setEditable(false);
        saldo_so.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        saldo_so.setText("0");
        saldo_so.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        saldo_do.setEditable(false);
        saldo_do.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        saldo_do.setText("0");
        saldo_do.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        theButton2.setText("CERRAR COMPRA DEL CLIENTE PARA INICIAR NUEVA COMPRA");
        theButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                theButton2ActionPerformed(evt);
            }
        });

        jLabel9.setText("ESTADO DE LA COMPRA:");

        estado.setText("ESTADO");

        imprimir.setText("IMPRIMIR");
        imprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(msgPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addComponent(jLabel9)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(estado, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel5))
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(theButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(274, 274, 274))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sum_com_so, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sum_ade_so, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saldo_so, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sum_com_do, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sum_ade_do, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(saldo_do, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(72, 72, 72))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(msgPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(estado)))
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(sum_com_so, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(sum_ade_so, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(saldo_so, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(sum_com_do)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(sum_ade_do)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(saldo_do))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(theButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imprimir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabla.setRowHeight(30);
        jScrollPane1.setViewportView(tabla);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(204, 255, 204));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 23, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void cmbProveedorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbProveedorItemStateChanged
        // TODO add your handling code here:
        compraSelected = null;
        estado.setText("");
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            clienteSelected = (Cliente) cmbProveedor.getSelectedItem();
            int id = clienteSelected.getId();
            if (id > 0) {
                // MsgPanel.success(" cliente seleccionado: " + clienteSelected.getId());
                List<Compra> comprasDelCliente = CompraData.listActivesByCliente(clienteSelected.getId());
                if (comprasDelCliente.size() > 0) {
                    int compraId = comprasDelCliente.get(0).getId();// coge el primero
                    compraSelected = CompraData.getByPId(compraId);
                    MsgPanel.success(" compraid: " + compraSelected.getId() + " de " + compraSelected.getClie_nom() + " continuar con la compra");
                } else {
                    compraSelected = null;
                    MsgPanel.success(" cliente: " + clienteSelected.getNombres() + " sin compras pendientes, se va crear nueva compra");
                }

                tabla.setEnabled(true);
                esActualizacion = true;
                cmbProveedor.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1));
                // MsgPanel.error("");
                paintTable(new ComprasDetTableModel());
            } else {
                MsgPanel.success("seleccione cliente");
                clienteSelected = null;
                compraSelected = null;
                tabla.setEnabled(false);
                paintTable(new ComprasDetTableModel());

            }

            //    this.setProveedor(prv);
        }
        if (compraSelected != null) {
            if (compraSelected.getActivo() == 1) {
                estado.setText("EN PROCESO");
            }
            if (compraSelected.getActivo() == 2) {
                estado.setText("COMPLETADO");
            }
        }
        tableHandlerEvent();


    }//GEN-LAST:event_cmbProveedorItemStateChanged

    private void theButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_theButton1ActionPerformed
        // TODO add your handling code here:

        BuscarClientePanel pvc = new BuscarClientePanel();
        //  JLabel aviso = pvc.getAviso();
        //   String msj = "Lista de Proveedores";
        //   JOptionPane.showInternalOptionDialog(this, pvc, msj,JOptionPane.OK_CANCEL_OPTION,
        //                                       JOptionPane.QUESTION_MESSAGE, null, new Object[]{aviso},null);
        JOptionPane.showOptionDialog(null, pvc,
                "Lista de clienteSelecteds",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{}, null);
        //  System.out.println(x);

        if (pvc.getCliente() != null) {
            clienteSelected = (Cliente) pvc.getCliente();

            // System.out.printf(" getId:%s nom:%s \n", prv.getId(),prv.getNombres());
            cmbProveedor.getModel().setSelectedItem(clienteSelected);

        }
    }//GEN-LAST:event_theButton1ActionPerformed

    private void iniciarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iniciarCompraActionPerformed
        // TODO add your handling code here:
        moneda_dolares.setSelected(false);
        moneda_soles.setSelected(false);
        estado.setText("");
        if (clienteSelected != null) { //.getId() >0

            if (compraSelected != null) {
                if (compraSelected.getEsdolares() == 1) {
                    moneda_dolares.setSelected(true);
                }
                if (compraSelected.getEsdolares() == 2) {
                    moneda_soles.setSelected(true);
                }

                paintTable(new ComprasDetTableModel(compraSelected));
                MsgPanel.success("compra id:" + compraSelected.getId() + "  existente de " + compraSelected.getClie_nom() + " idcli:" + clienteSelected.getId());

            } else {
                compraSelected = null;
                Compra d = new Compra();
                d.setClie_id(clienteSelected.getId());
                d.setClie_nom(clienteSelected.getNombres());
                int compraId = CompraData.create(d);

                if (compraId > 0) {
                    compraSelected = CompraData.getByPId(compraId);
                    MsgPanel.success("compra id:" + compraSelected.getId() + " creado para " + compraSelected.getClie_nom() + " idcli:" + clienteSelected.getId());
                    if (compraSelected.getEsdolares() == 1) {
                        moneda_dolares.setSelected(true);
                    }
                    if (compraSelected.getEsdolares() == 2) {
                        moneda_soles.setSelected(true);
                    }
                    paintTable(new ComprasDetTableModel(compraSelected));

                } else {

                    compraSelected = null;
                    MsgPanel.error("No se pudo crear la compra para idcli:" + clienteSelected.getId(), true);
                }
            }
            tableHandlerEvent();

        } else {
            compraSelected = null;
            clienteSelected = null;
            cmbProveedor.setBorder(new LineBorder(new java.awt.Color(255, 0, 0), 3));
            MsgPanel.error("Elija un proveedor", true);
        }


    }//GEN-LAST:event_iniciarCompraActionPerformed

    private void moneda_solesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moneda_solesActionPerformed
        // TODO add your handling code here:
        if (compraSelected != null) {
            compraSelected.setEsdolares(2);
            int op = CompraData.update(compraSelected);

            if (op > 0) {
                MsgPanel.success("compra id:" + compraSelected.getId() + " moneda:" + compraSelected.getEsdolares());
            } else {
                MsgPanel.error("No se pudo cambiar la moneda ", true);
            }
        }

    }//GEN-LAST:event_moneda_solesActionPerformed

    private void moneda_dolaresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moneda_dolaresActionPerformed
        // TODO add your handling code here:
        if (compraSelected != null) {
            compraSelected.setEsdolares(1);
            int op = CompraData.update(compraSelected);

            if (op > 0) {
                MsgPanel.success("compra id:" + compraSelected.getId() + "  moneda:" + compraSelected.getEsdolares());
            } else {
                MsgPanel.error("No se pudo cambiar  la moneda ", true);
            }
        }
    }//GEN-LAST:event_moneda_dolaresActionPerformed

    private void theButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_theButton2ActionPerformed
        // TODO add your handling code here:
        if (compraSelected != null) {
            int opc = JOptionPane.showConfirmDialog(null, "¿Realmente desea terminar la compra? Una vez cerrada ya no podrá modificar esta compra", "TERMINAR COMPRA", JOptionPane.YES_NO_OPTION);
            if (opc == JOptionPane.OK_OPTION) {
                compraSelected.setActivo(2);
                int op = CompraData.update(compraSelected);

                if (op > 0) {
                    MsgPanel.success("compra id:" + compraSelected.getId() + " estado :" + compraSelected.getActivo());
                } else {
                    MsgPanel.error("No se pudo cambiar el estado ", true);
                }
            }
            if (compraSelected.getActivo() == 1) {
                estado.setText("EN PROCESO");
            }
            if (compraSelected.getActivo() == 2) {
                estado.setText("COMPLETADO");
            }
        }
    }//GEN-LAST:event_theButton2ActionPerformed

    private void imprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imprimirActionPerformed
        // TODO add your handling code here:
        if (compraSelected != null) {
            PrinterComprasPanel mov = new PrinterComprasPanel(compraSelected);

            JOptionPane.showOptionDialog(null, mov,
                    "Imprimir  ",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{}, null);
            System.out.println("mov.getImprimio():" + mov.getImprimio());
            if (mov.getImprimio() > 0) {
                int op = CompraData.updateActivo(compraSelected.getId(), 0);

                if (op > 0) {
                    MsgPanel.success("se han cerrado los items de la compra ya no podrá modificarlos");
                    paintTable(new ComprasDetTableModel(compraSelected));
                } else {
                    MsgPanel.error("No se pudo cambiar  de estado ", true);
                }
            }
        }


    }//GEN-LAST:event_imprimirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cmbProveedor;
    private javax.swing.JLabel estado;
    private igu.util.buttons.TheButton imprimir;
    private igu.util.buttons.TheButton iniciarCompra;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton moneda_dolares;
    private javax.swing.JRadioButton moneda_soles;
    private util.MsgPanel msgPanel1;
    private javax.swing.JFormattedTextField saldo_do;
    private javax.swing.JFormattedTextField saldo_so;
    private javax.swing.JFormattedTextField sum_ade_do;
    private javax.swing.JFormattedTextField sum_ade_so;
    private javax.swing.JFormattedTextField sum_com_do;
    private javax.swing.JFormattedTextField sum_com_so;
    private javax.swing.JTable tabla;
    private igu.util.buttons.TheButton theButton1;
    private igu.util.buttons.TheButton theButton2;
    // End of variables declaration//GEN-END:variables
}
