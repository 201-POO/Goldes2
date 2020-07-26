
package igu.compras.compras;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Asullom
 */
public class AccionTableCellRenderer implements TableCellRenderer {
    private AddDelComprasPanel comp;

     public AccionTableCellRenderer(final JPanel ifr) {
       comp = new AddDelComprasPanel(ifr);
    }

    

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        comp.setTabla(table);        
        return comp;
    }
    
    
}
