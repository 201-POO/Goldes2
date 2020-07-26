package data;

import entities.Parametro;
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
import util.ErrorLogger;

/**
 *
 * @author Asullom
 */
public class ParametroData {

    static Connection cn = Conn.connectSQLite();
    static PreparedStatement ps;
    static ErrorLogger log = new ErrorLogger(ParametroData.class.getName());

    public static int create(Parametro d) {
        int rsId = 0;
        String[] returns = {"id"};
        String sql = "INSERT INTO parametro(onza, porc, ley, sistema, precio_do, "
                + "  tc, precdtio_so) "
                + "VALUES(?,?,?,?,?  ,?,?)";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql, returns);
            ps.setDouble(++i, d.getOnza());
            ps.setDouble(++i, d.getPorc());
            ps.setDouble(++i, d.getLey());
            ps.setDouble(++i, d.getSistema());

            double pre_do = (d.getOnza() / d.getSistema() - (d.getOnza() / d.getSistema()) * d.getPorc() / 100) * d.getLey();
            ps.setDouble(++i, Math.round(pre_do * 100.0) / 100.0);//d.getPrecio_do()
            ps.setDouble(++i, d.getTc());
            double pre_so = pre_do * d.getTc();
            ps.setDouble(++i, Math.round(pre_so * 100.0) / 100.0); //d.getPrecio_so()

            rsId = ps.executeUpdate();// 0 no o 1 si commit
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    rsId = rs.getInt(1); // select tk, max(id)  from parametro
                    //System.out.println("rs.getInt(rsId): " + rsId);
                }
                rs.close();
            }
        } catch (SQLException ex) {
            //System.err.println("create:" + ex.toString());
            log.log(Level.SEVERE, "create", ex);
        }
        return rsId;
    }

    public static int update(Parametro d) {
        System.out.println("actualizar d.getId(): " + d.getId());
        int comit = 0;
        String sql = "UPDATE parametro SET "
                + "onza=?, "
                + "porc=?, "
                + "ley=?, "
                + "sistema=?, "
                + "precio_do=?, "
                + "tc=?, "
                + "precio_so=? "
                + "WHERE id=?";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setDouble(++i, d.getOnza());
            ps.setDouble(++i, d.getPorc());
            ps.setDouble(++i, d.getLey());
            ps.setDouble(++i, d.getSistema());

            double pre_do = (d.getOnza() / d.getSistema() - (d.getOnza() / d.getSistema()) * d.getPorc() / 100) * d.getLey();
            ps.setDouble(++i, Math.round(pre_do * 100.0) / 100.0);//d.getPrecio_do()
            ps.setDouble(++i, d.getTc());
            double pre_so = pre_do * d.getTc();
            ps.setDouble(++i, Math.round(pre_so * 100.0) / 100.0); //d.getPrecio_so()
            
             ps.setInt(++i, d.getId());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }

    public static int delete(int id) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM parametro WHERE id = ?";
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(1, id);
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "delete", ex);
            // System.err.println("NO del " + ex.toString());
            throw new Exception("Detalle:" + ex.getMessage());
        }
        return comit;
    }

    public static List<Parametro> list(String filter) {
        String filtert = null;
        if (filter == null) {
            filtert = "";
        } else {
            filtert = filter;
        }
        System.out.println("list.filtert:" + filtert);

        List<Parametro> ls = new ArrayList();
        String sql = "";
        if (filtert.equals("")) {
            sql = "SELECT * FROM parametro ORDER BY id";
        } else {
            sql = "SELECT * FROM parametro WHERE (id LIKE'" + filter + "%' OR "
                    + "onza LIKE'" + filter + "%' OR porc LIKE'" + filter + "%' OR "
                    + "id LIKE'" + filter + "%') "
                    + "ORDER BY id";
        }
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Parametro d = new Parametro();
                d.setId(rs.getInt("id"));
                d.setOnza(rs.getDouble("onza"));
                d.setPorc(rs.getDouble("porc"));
                d.setLey(rs.getDouble("ley"));
                d.setSistema(rs.getDouble("sistema"));
                d.setTc(rs.getDouble("tc"));
                d.setPrecio_do(rs.getDouble("precio_do"));
                d.setPrecio_so(rs.getDouble("precio_so"));
                ls.add(d);
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "list", ex);
        }
        return ls;
    }


    public static Parametro getById(int id) {
        Parametro d = new Parametro();

        String sql = "SELECT * FROM parametro WHERE id = ? ";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(++i, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                d.setId(rs.getInt("id"));
                d.setOnza(rs.getDouble("onza"));
                d.setPorc(rs.getDouble("porc"));
                d.setLey(rs.getDouble("ley"));
                d.setSistema(rs.getDouble("sistema"));
                d.setTc(rs.getDouble("tc"));
                d.setPrecio_do(rs.getDouble("precio_do"));
                d.setPrecio_so(rs.getDouble("precio_so"));

            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "getByPId", ex);
        }
        return d;
    }
}
