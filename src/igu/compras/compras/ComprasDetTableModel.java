package igu.compras.compras;

import data.CompraData;
import data.CompraDetData;
import entities.Compra;
import entities.CompraDet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Asullom
 */
public class ComprasDetTableModel extends AbstractTableModel {

    private List<CompraDet> lis = new ArrayList<>();
    private String[] columns = {"MOV", "Glosa", "Gramos", "Onza", "%",
        "Ley", "DOLAR", "TC", "soles", "Total soles",
        "Total DOLARES", "Acciones"};
    private Class[] columnsType = {Integer.class, String.class, Double.class, Double.class, Double.class,
        Double.class, Double.class, Double.class, Double.class, Double.class,
        Double.class, Object.class};

    public ComprasDetTableModel() {
        // this.lis.add(new CompraDet());
    }

    public ComprasDetTableModel(Compra d) {
        this.lis = CompraDetData.listByCompra(d.getId());
        this.lis.add(new CompraDet());
    }

    @Override
    public Object getValueAt(int row, int column) {
        CompraDet d = (CompraDet) lis.get(row);
        switch (column) {
            case 0:
                return d.getMov_tipo();
            case 1:
                return d.getGlosa();
            case 2:
                return d.getCant_gr();
            case 3:
                return d.getOnza();
            case 4:
                return d.getPorc();
            case 5:
                return d.getLey();
            case 6:
                return d.getPrecio_do();
            case 7:
                return d.getTc();
            case 8:
                return d.getPrecio_so();
            case 9:
                return d.getTotal_so();
            case 10:
                return d.getTotal_do();
            case 11:
                return "Agregar/Eliminar";
            default:
                return null;
        }

    }

    @Override
    public void setValueAt(Object valor, int row, int column) {
        CompraDet d = (CompraDet) lis.get(row);

        switch (column) {
            case 0: // no se modifica
                break;
            case 1:
                d.setGlosa("" + valor);
                break;
            case 2:
                System.out.println("setValueAt : " + "" + valor);
                double gr = 0;
                try {
                    gr = Double.parseDouble(valor + "");
                } catch (NumberFormatException nfe) {
                    System.err.println("" + nfe);
                }
                d.setCant_gr(gr);
                System.out.println("getCant_gr : " + "" + d.getCant_gr());
                break;
            case 3:
                double onza = 0;
                try {
                    onza = Double.parseDouble(valor + "");
                } catch (NumberFormatException nfe) {
                    System.err.println("" + nfe);
                }
                d.setOnza(onza);
                break;
            case 4:
                double porc = 0;
                try {
                    porc = Double.parseDouble(valor + "");
                } catch (NumberFormatException nfe) {
                    System.err.println("" + nfe);
                }
                d.setPorc(porc);
                break;
            case 5:
                double ley = 0;
                try {
                    ley = Double.parseDouble(valor + "");
                } catch (NumberFormatException nfe) {
                    System.err.println("" + nfe);
                }
                d.setLey(ley);
                break;

            case 7:
                double tc = 0;
                try {
                    tc = Double.parseDouble(valor + "");
                } catch (NumberFormatException nfe) {
                    System.err.println("" + nfe);
                }
                d.setTc(tc);
                break;
            // para el caso de adelantos
            case 9:
                double val = 0;
                try {
                    val = Double.parseDouble(valor + "");
                } catch (NumberFormatException nfe) {
                    System.err.println("" + nfe);
                }
                d.setTotal_so(val);
                break;
            case 10:
                double vald = 0;
                try {
                    vald = Double.parseDouble(valor + "");
                } catch (NumberFormatException nfe) {
                    System.err.println("" + nfe);
                }
                d.setTotal_do(vald);
                break;
        }
        this.fireTableRowsUpdated(row, column);
        //fireTableCellUpdated(row, column);

    }

    @Override
    public boolean isCellEditable(int row, int column) {
        CompraDet d = (CompraDet) lis.get(row);
        Compra compraSelected = CompraData.getByPId(d.getComp_id());
        if (d.getMov_tipo() > 0 && d.getActivo() ==1 ) {
            if (d.getMov_tipo() == 1) {
                if (column >= 0 && column != 0 && column != 6 && column != 8 && column != 9 && column != 10) {// && column != 9 && column != 10
                    return true;
                }
            } else if (d.getMov_tipo() == 2) {
                
                if (compraSelected.getEsdolares() == 1) {
                    if (column >= 0 && column != 0 && column != 2 && column != 3 && column != 4 && column != 5 && column != 6 && column != 7 && column != 8 && column != 9) {// && column != 9 && column != 10
                        return true;
                    }
                } else {
                    if (column >= 0 && column != 0 && column != 2 && column != 3 && column != 4 && column != 5 && column != 6 && column != 7 && column != 8 && column != 10) {// && column != 9 && column != 10
                        return true;
                    }
                }
                //if (column >= 0 && column != 0 && column != 2 && column != 3 && column != 4 && column != 5 && column != 6 && column != 7 && column != 8) {// && column != 9 && column != 10
                //    return true;
                //}
            } else if (d.getMov_tipo() == 3) {

                if (column >= 0 && column != 0 && column != 2 && column != 3 && column != 4 && column != 5 && column != 6  && column != 8 && column != 9 && column != 10) {// && column != 9 && column != 10
                    return true;
                }
            } else {
                //MsgPanel.success("Debe seleccionar un tipo de movimiento");

            }

        } else if (d.getMov_tipo() == 0) {
            if (column == this.columns.length - 1) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Class getColumnClass(int column) {
        return columnsType[column];
    }

    @Override
    public int getRowCount() {
        return lis.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    public void addRow(CompraDet d) { // con db no se usa
        this.lis.add(d);
        //this.fireTableDataChanged();
        this.fireTableRowsInserted(lis.size(), lis.size());
    }

    public void removeRow(int linha) { // con db no se usa
        this.lis.remove(linha);
        this.fireTableRowsDeleted(linha, linha);
    }

    public Object getRow(int row) { // usado para paintForm
        this.fireTableRowsUpdated(row, row);
        return lis.get(row);
    }

}
