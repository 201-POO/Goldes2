package data;

import entities.Compra;
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
public class CompraData {

    static Connection cn = Conn.connectSQLite();
    static PreparedStatement ps;
    static ErrorLogger log = new ErrorLogger(CompraData.class.getName());

    static Date dt = new Date();
    static SimpleDateFormat sdf = new SimpleDateFormat(Config.DEFAULT_DATE_STRING_FORMAT);

    public static int create(Compra d) {
        int rsId = 0;
        String[] returns = {"id"};
        String sql = "INSERT INTO compra(clie_id, clie_nom) " //activo
                + "VALUES(?,?)";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql, returns);
            ps.setInt(++i, d.getClie_id());
            ps.setString(++i, d.getClie_nom());
            rsId = ps.executeUpdate();// 0 no o 1 si commit
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    rsId = rs.getInt(1); // select tk, max(id)  from compra
                    //System.out.println("rs.getInt(rsId): " + rsId);
                }
                rs.close();
            }
            System.out.println("create.rsId:" + rsId);
        } catch (SQLException ex) {
            //System.err.println("create:" + ex.toString());
            log.log(Level.SEVERE, "create", ex);
        }
        return rsId;
    }

    public static int update(Compra d) {
        System.out.println("actualizar d.getId(): " + d.getId());
        int comit = 0;
        String sql = "UPDATE compra SET "
                + "clie_id=?, "
                + "clie_nom=?, "
                + "esdolares=?, "
                + "last_updated=?, "
                + "activo=? "
                + "WHERE id=?";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(++i, d.getClie_id());
            ps.setString(++i, d.getClie_nom());
            ps.setInt(++i, d.getEsdolares());
            ps.setString(++i, sdf.format(dt));
            ps.setInt(++i, d.getActivo());
            ps.setInt(++i, d.getId());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }

    public static int delete(int id) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM compra WHERE id = ?";
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

    public static List<Compra> list(String filter) {
        String filtert = null;
        if (filter == null) {
            filtert = "";
        } else {
            filtert = filter;
        }
        System.out.println("list.filtert:" + filtert);

        List<Compra> ls = new ArrayList();
        String sql = "";
        if (filtert.equals("")) {
            sql = "SELECT * FROM compra ORDER BY id";
        } else {
            sql = "SELECT * FROM compra WHERE (id LIKE'" + filter + "%' OR "
                    + "nombres LIKE'" + filter + "%' OR cod LIKE'" + filter + "%' OR "
                    + "id LIKE'" + filter + "%') "
                    + "ORDER BY nombres";
        }
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Compra d = new Compra();
                d.setId(rs.getInt("id"));

                try {
                    d.setDate_created(sdf.parse(rs.getString("date_created")));
                    d.setLast_updated(sdf.parse(rs.getString("last_updated")));
                } catch (Exception e) {
                }

                d.setClie_id(rs.getInt("clie_id"));
                d.setClie_nom(rs.getString("clie_nom"));
                d.setEsdolares(rs.getInt("esdolares"));

                d.setActivo(rs.getInt("activo"));
                ls.add(d);
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "list", ex);
        }
        return ls;
    }

    public static List<Compra> listActivesByCliente(int clie_id) {
        System.out.println("listByCliente.clie_id:" + clie_id);
        String sql = "";
        List<Compra> ls = new ArrayList<Compra>();

        sql = " SELECT * FROM compra "
                + " WHERE clie_id = " + clie_id + " and activo=1 "
                + " ORDER BY id ";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Compra d = new Compra();
                d.setId(rs.getInt("id"));
                try {
                    d.setDate_created(sdf.parse(rs.getString("date_created")));
                    d.setLast_updated(sdf.parse(rs.getString("last_updated")));
                } catch (Exception e) {
                }

                d.setClie_id(rs.getInt("clie_id"));
                d.setClie_nom(rs.getString("clie_nom"));
                d.setEsdolares(rs.getInt("esdolares"));

                d.setActivo(rs.getInt("activo"));
                ls.add(d);
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "listByCliente", ex);
        }
        return ls;
    }

    public static Compra getByPId(int id) {
        Compra d = new Compra();

        String sql = "SELECT * FROM compra WHERE id = ? ";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(++i, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                d.setId(rs.getInt("id"));
                try {
                    d.setDate_created(sdf.parse(rs.getString("date_created")));
                    d.setLast_updated(sdf.parse(rs.getString("last_updated")));
                } catch (Exception e) {
                }

                d.setClie_id(rs.getInt("clie_id"));
                d.setClie_nom(rs.getString("clie_nom"));
                d.setEsdolares(rs.getInt("esdolares"));

                d.setActivo(rs.getInt("activo"));

            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "getByPId", ex);
        }
        return d;
    }
    
    
    public static int updateActivo(int id, int estado) { // cambia de estado a todos los items de la compra
        System.out.println("actualizar id: " + id);
        int comit = 0;
        String sql = "UPDATE compra_det SET "
                + "activo=? "
               
                + "WHERE comp_id=?";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);

            ps.setInt(++i, estado);
            ps.setInt(++i, id);
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "updateActivo", ex);
        }
        return comit;
    }
}
