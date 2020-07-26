package data;

import entities.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import util.ErrorLogger;

/**
 *
 * @author Asullom
 */
public class UserData {

    static Connection cn = Conn.connectSQLite();
    static PreparedStatement ps;
    static ErrorLogger log = new ErrorLogger(UserData.class.getName());

    public static int create(User d) {
        int rsId = 0;
        String[] returns = {"id"};
        String sql = "INSERT INTO user(rol, pin, username) "
                + "VALUES(?,?,?)";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql, returns);
            ps.setDouble(++i, d.getRol());
            ps.setString(++i, d.getPin());
            ps.setString(++i, d.getUsername());

            rsId = ps.executeUpdate();// 0 no o 1 si commit
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    rsId = rs.getInt(1); // select tk, max(id)  from user
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

    public static int update(User d) {
        System.out.println("actualizar d.getId(): " + d.getId());
        int comit = 0;
        String sql = "UPDATE user SET "
                + "rol=?, "
                + "pin=?, "
                + "username=? "
                + "WHERE id=?";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setDouble(++i, d.getRol());
            ps.setString(++i, d.getPin());
            ps.setString(++i, d.getUsername());
            ps.setInt(++i, d.getId());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }

    public static int delete(int id) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM user WHERE id = ?";
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

    public static List<User> list(String filter) {
        String filtert = null;
        if (filter == null) {
            filtert = "";
        } else {
            filtert = filter;
        }
        System.out.println("list.filtert:" + filtert);

        List<User> ls = new ArrayList();
        String sql = "";
        if (filtert.equals("")) {
            sql = "SELECT * FROM user ORDER BY id";
        } else {
            sql = "SELECT * FROM user WHERE (id LIKE'" + filter + "%' OR "
                    + "rol LIKE'" + filter + "%' OR pin LIKE'" + filter + "%' OR "
                    + "id LIKE'" + filter + "%') "
                    + "ORDER BY id";
        }
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                User d = new User();
                d.setId(rs.getInt("id"));
                d.setRol(rs.getInt("rol"));
                d.setPin(rs.getString("pin"));
                d.setUsername(rs.getString("username"));
                ls.add(d);
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "list", ex);
        }
        return ls;
    }

    public static User getByPId(int id) {
        User d = new User();

        String sql = "SELECT * FROM user WHERE id = ? ";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(++i, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                d.setId(rs.getInt("id"));

                d.setRol(rs.getInt("rol"));
                d.setPin(rs.getString("pin"));
                d.setUsername(rs.getString("username"));

            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "getByPId", ex);
        }
        return d;
    }

    public static User getByPinAndRol(String pin, String rol) {
        User d = new User();

        String sql = "SELECT * FROM user WHERE pin = ? and  rol = ? ";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(++i, pin);
            ps.setString(++i, rol);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                d.setId(rs.getInt("id"));

                d.setRol(rs.getInt("rol"));
                d.setPin(rs.getString("pin"));
                d.setUsername(rs.getString("username"));

            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "getByPin", ex);
        }
        return d;
    }
}
