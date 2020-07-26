package data.reports;

import data.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import org.sqlite.SQLiteConfig;
import util.Config;
import util.ErrorLogger;

/**
 *
 * @author Asullom
 */
public class CompraViewReport {

    static Connection cn = Conn.connectSQLite();
    static PreparedStatement ps;
    static ErrorLogger log = new ErrorLogger(CompraViewReport.class.getName());

    static Date dt = new Date();
    static SimpleDateFormat sdf = new SimpleDateFormat(Config.DEFAULT_DATE_STRING_FORMAT);

    public static List<CompraView> list(String filter) {
        String filtert = null;
        if (filter == null) {
            filtert = "";
        } else {
            filtert = filter;
        }
        System.out.println("list.filtert:" + filtert);

        List<CompraView> ls = new ArrayList();
        String sql = "";
        sql = " SELECT \n"
                + "	d.comp_id,\n"
                + "	c.clie_id,\n"
                + "	c.clie_nom,\n"
                + "	c.esdolares,\n"
                + "	c.activo as comp_activo,\n"
                + "	d.id, fecha, mov_tipo,\n"
                + "	glosa,\n"
                + "	cant_gr,\n"
                + "	onza,\n"
                + "	porc,\n"
                + "	ley,\n"
                + "	sistema,\n"
                + "	precio_do,\n"
                + "	tc,\n"
                + "	precio_so,\n"
                + "	0 as compra_so, \n"
                + "	0 as compra_do, \n"
                + "	total_so as adelanto_so, \n"
                + "	total_do as adelanto_do, \n"
                + "	saldo_do\n"
                + "	FROM compra_det d \n"
                + "		INNER JOIN compra c ON c.id = d.comp_id\n"
                + "		\n"
                + "	WHERE mov_tipo in (2,3) \n"
                + "	UNION\n"
                + "	SELECT \n"
                + "	d.comp_id,\n"
                + "	c.clie_id,\n"
                + "	c.clie_nom,\n"
                + "	c.esdolares,\n"
                + "	c.activo as comp_activo,\n"
                + "	d.id, fecha, mov_tipo,\n"
                + "	glosa,\n"
                + "	cant_gr,\n"
                + "	onza,\n"
                + "	porc,\n"
                + "	ley,\n"
                + "	sistema,\n"
                + "	precio_do,\n"
                + "	tc,\n"
                + "	precio_so,\n"
                + "	CASE\n"
                + "	  WHEN c.esdolares ==2   THEN total_so\n"
                + "	  ELSE 0\n"
                + "	  END compra_so,\n"
                + "	CASE\n"
                + "	  WHEN c.esdolares ==1   THEN total_do\n"
                + "	  ELSE 0\n"
                + "	  END compra_do,\n"
                + "	  \n"
                + "	0 as adelanto_so, \n"
                + "	0 as adelanto_do, \n"
                + "	0 as saldo_do\n"
                + "\n"
                + "	FROM compra_det d \n"
                + "		INNER JOIN compra c ON c.id = d.comp_id\n"
                + "	WHERE mov_tipo in (1)\n"
                + "\n"
                + "	ORDER BY d.comp_id, d.id ";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                CompraView d = new CompraView();

                String fechax = rs.getString("fecha");
                System.err.println("fechax:"+fechax);
                try {
                    Date datex = sdf.parse(fechax);
                    d.setFecha(datex);
                } catch (Exception e) {
                    System.err.println("e:"+e);
                }
                d.setComp_id(rs.getInt("comp_id"));
                d.setClie_nom(rs.getString("clie_nom"));
                d.setId(rs.getInt("id"));
                d.setMov_tipo(rs.getInt("mov_tipo"));

                d.setGlosa(rs.getString("glosa"));
                d.setCant_gr(rs.getDouble("cant_gr"));

                d.setOnza(rs.getDouble("onza"));
                d.setPorc(rs.getDouble("porc"));
                d.setLey(rs.getDouble("ley"));
                d.setSistema(rs.getDouble("sistema"));
                d.setTc(rs.getDouble("tc"));
                d.setPrecio_do(rs.getDouble("precio_do"));
                d.setPrecio_so(rs.getDouble("precio_so"));

                d.setCompra_so(rs.getDouble("compra_so"));
                d.setCompra_do(rs.getDouble("compra_do"));
                d.setAdelanto_so(rs.getDouble("adelanto_so"));
                d.setAdelanto_do(rs.getDouble("adelanto_do"));
                d.setSaldo_do(rs.getDouble("saldo_do"));
                ls.add(d);
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "list", ex);
        }
        return ls;
    }

}
