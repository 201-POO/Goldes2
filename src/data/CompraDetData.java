package data;

import entities.CompraDet;
import entities.SaldosCompra;
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
public class CompraDetData {

    static Connection cn = Conn.connectSQLite();
    static PreparedStatement ps;
    static ErrorLogger log = new ErrorLogger(CompraDetData.class.getName());

    static Date dt = new Date();
    static SimpleDateFormat sdf = new SimpleDateFormat(Config.DEFAULT_DATE_STRING_FORMAT);

    public static int create(CompraDet d) {
        System.out.println("d.getComp_id: " + d.getComp_id());
        int rsId = 0;
        String[] returns = {"id"};
        String sql = "INSERT INTO compra_det(comp_id, mov_tipo, glosa, cant_gr, onza,  "
                + " porc, ley, sistema, precio_do, tc,  "
                + " precio_so, total_do, total_so, esenefec,  user_id" //fecha_pago,
                + " ) " //activo
                + "VALUES(?,?,?,?,?  ,?,?,?,?,? ,?,?,?,?,?)";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql, returns);
            ps.setInt(++i, d.getComp_id());
            ps.setInt(++i, d.getMov_tipo());
            ps.setString(++i, d.getGlosa());
            ps.setDouble(++i, d.getCant_gr());
            ps.setDouble(++i, d.getOnza());
            ps.setDouble(++i, d.getPorc());
            ps.setDouble(++i, d.getLey());
            ps.setDouble(++i, d.getSistema());

            double pre_do = (d.getOnza() / d.getSistema() - (d.getOnza() / d.getSistema()) * d.getPorc() / 100) * d.getLey();
            ps.setDouble(++i, Math.round(pre_do * 100.0) / 100.0);//d.getPrecio_do()
            ps.setDouble(++i, d.getTc());

            double pre_so = pre_do * d.getTc();
            ps.setDouble(++i, Math.round(pre_so * 100.0) / 100.0); //d.getPrecio_so()
            ps.setDouble(++i, Math.round(pre_do * d.getCant_gr() * 100.0) / 100.0); //d.getTotal_do()
            ps.setDouble(++i, Math.round(pre_so * d.getCant_gr() * 100.0) / 100.0); //d.getTotal_so()

            //ps.setString(++i, sdf.format(d.getFecha()));
            ps.setInt(++i, 1);
            ps.setInt(++i, 1);
            rsId = ps.executeUpdate();// 0 no o 1 si commit
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    rsId = rs.getInt(1); // select tk, max(id)  from compra_det
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

    public static int update(CompraDet d) {
        System.out.println("actualizar d.getId(): " + d.getId());
        int comit = 0;
        String sql = "UPDATE compra_det SET "
                + "comp_id=?, "
                + "mov_tipo=?, "
                + "glosa=?, "
                + "cant_gr=?, "
                + "onza=?, "
                + "porc=?, "
                + "ley=?, "
                + "sistema=?, "
                + "precio_do=?, "
                + "tc=?, "
                + "precio_so=?, "
                + "total_do=?, "
                + "total_so=?, "
                //  + "fecha=?, "
                + "esenefec=?, "
                + "user_id=? "
                + "WHERE id=?";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(++i, d.getComp_id());
            ps.setInt(++i, d.getMov_tipo());
            ps.setString(++i, d.getGlosa());
            ps.setDouble(++i, d.getCant_gr());
            ps.setDouble(++i, d.getOnza());
            ps.setDouble(++i, d.getPorc());
            ps.setDouble(++i, d.getLey());
            ps.setDouble(++i, d.getSistema());

            double pre_do = (d.getOnza() / d.getSistema() - (d.getOnza() / d.getSistema()) * d.getPorc() / 100) * d.getLey();
            ps.setDouble(++i, Math.round(pre_do * 100.0) / 100.0);//d.getPrecio_do()
            ps.setDouble(++i, d.getTc());

            double pre_so = pre_do * d.getTc();
            ps.setDouble(++i, Math.round(pre_so * 100.0) / 100.0); //d.getPrecio_so()
            ps.setDouble(++i, Math.round(pre_do * d.getCant_gr() * 100.0) / 100.0); //d.getTotal_do()
            ps.setDouble(++i, Math.round(pre_so * d.getCant_gr() * 100.0) / 100.0); //d.getTotal_so()

//            ps.setString(++i, sdf.format(d.getFecha()));
            ps.setInt(++i, 1);
            ps.setInt(++i, 1);
            ps.setInt(++i, d.getId());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }

    public static int createAdelanto(CompraDet d) {
        System.out.println("d.getComp_id: " + d.getComp_id());
        int rsId = 0;
        String[] returns = {"id"};
        String sql = "INSERT INTO compra_det(comp_id, mov_tipo, glosa, cant_gr, onza,  "
                + " porc, ley, sistema, precio_do, tc,  "
                + " precio_so, total_do, total_so, saldo_do, esenefec,   " //fecha_pago,
                + " user_id) " //activo
                + "VALUES(?,?,?,?,?  ,?,?,?,?,? ,?,?,?,?,? ,?)";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql, returns);
            ps.setInt(++i, d.getComp_id());
            ps.setInt(++i, d.getMov_tipo());
            ps.setString(++i, d.getGlosa());
            ps.setDouble(++i, d.getCant_gr());
            ps.setDouble(++i, d.getOnza());
            ps.setDouble(++i, d.getPorc());
            ps.setDouble(++i, d.getLey());
            ps.setDouble(++i, d.getSistema());

            ps.setDouble(++i, d.getPrecio_do());//d.getPrecio_do()
            ps.setDouble(++i, d.getTc());

            ps.setDouble(++i, d.getPrecio_so()); //d.getPrecio_so()
            ps.setDouble(++i, Math.round(d.getTotal_do() * 100.0) / 100.0); //d.getTotal_do()
            ps.setDouble(++i, Math.round(d.getTotal_so() * 100.0) / 100.0); //d.getTotal_so()
            ps.setDouble(++i, Math.round(d.getSaldo_do() * 100.0) / 100.0);
            //ps.setString(++i, sdf.format(d.getFecha()));
            ps.setInt(++i, 1);
            ps.setInt(++i, 1);

            rsId = ps.executeUpdate();// 0 no o 1 si commit
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    rsId = rs.getInt(1); // select tk, max(id)  from compra_det
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

    public static int updateAdelanto(CompraDet d) {
        System.out.println("actualizar d.getId(): " + d.getId());
        int comit = 0;
        String sql = "UPDATE compra_det SET "
                + "glosa=?, "
                + "tc=?, "
                + "total_do=?, "
                + "total_so=?, "
                + "saldo_do=?, "
                + "esenefec=?, "
                + "user_id=? "
                + "WHERE id=?";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);

            ps.setString(++i, d.getGlosa());
            ps.setDouble(++i, d.getTc());
            ps.setDouble(++i, Math.round(d.getTotal_do() * 100.0) / 100.0);
            ps.setDouble(++i, Math.round(d.getTotal_so() * 100.0) / 100.0);
            ps.setDouble(++i, Math.round(d.getSaldo_do() * 100.0) / 100.0);

            ps.setInt(++i, 1);
            ps.setInt(++i, 1);

            ps.setInt(++i, d.getId());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "updateAdelanto", ex);
        }
        return comit;
    }

    public static int delete(int id) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM compra_det WHERE id = ?";
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

    public static List<CompraDet> list(String filter) {
        String filtert = null;
        if (filter == null) {
            filtert = "";
        } else {
            filtert = filter;
        }
        System.out.println("list.filtert:" + filtert);

        List<CompraDet> ls = new ArrayList();
        String sql = "";
        if (filtert.equals("")) {
            sql = "SELECT * FROM compra_det ORDER BY comp_id, id ";
        } else {
            sql = "SELECT * FROM compra_det WHERE (id LIKE'" + filter + "%' OR "
                    + "mov_tipo LIKE'" + filter + "%' OR glosa LIKE'" + filter + "%' OR "
                    + "id LIKE'" + filter + "%') "
                    + "ORDER BY comp_id, id";
        }
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                CompraDet d = new CompraDet();
                d.setId(rs.getInt("id"));
                String fechax = rs.getString("fecha");
                try {
                    Date datex = sdf.parse(fechax);
                    d.setFecha(datex);
                } catch (Exception e) {
                }
                d.setComp_id(rs.getInt("comp_id"));
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

                d.setTotal_do(rs.getDouble("total_do"));
                d.setTotal_so(rs.getDouble("total_so"));
                d.setUser_id(rs.getInt("user_id"));
                d.setActivo(rs.getInt("activo"));
                ls.add(d);
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "list", ex);
        }
        return ls;
    }

    public static List<CompraDet> listByCompra(int comp_id) {
        String sql = "";
        List<CompraDet> ls = new ArrayList<CompraDet>();

        sql = " SELECT * FROM compra_det "
                + " WHERE comp_id = " + comp_id + " "
                + " ORDER BY id ";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                CompraDet d = new CompraDet();
                d.setId(rs.getInt("id"));
                String fechax = rs.getString("fecha");
                try {
                    Date datex = sdf.parse(fechax);
                    d.setFecha(datex);
                } catch (Exception e) {
                }
                d.setComp_id(rs.getInt("comp_id"));
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

                d.setTotal_do(rs.getDouble("total_do"));
                d.setTotal_so(rs.getDouble("total_so"));
                d.setUser_id(rs.getInt("user_id"));
                d.setActivo(rs.getInt("activo"));
                ls.add(d);
            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "listByCompra", ex);
        }
        return ls;
    }

    public static CompraDet getByPId(int id) {
        CompraDet d = new CompraDet();

        String sql = "SELECT * FROM compra_det WHERE id = ? ";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            ps.setInt(++i, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String fechax = rs.getString("fecha");
                try {
                    Date datex = sdf.parse(fechax);
                    d.setFecha(datex);
                } catch (Exception e) {
                }
                d.setComp_id(rs.getInt("comp_id"));
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

                d.setTotal_do(rs.getDouble("total_do"));
                d.setTotal_so(rs.getDouble("total_so"));
                d.setUser_id(rs.getInt("user_id"));
                d.setActivo(rs.getInt("activo"));

            }
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "getByPId", ex);
        }
        return d;
    }

    public static SaldosCompra getSaldosByCompId(int comp_id) {
        SaldosCompra d = new SaldosCompra();

        try {
            String sql = "SELECT "
                    + "   'compas' as mov "
                    + "    ,sum(DISTINCT total_so) as sum_so "
                    + "    ,sum(DISTINCT total_do) as sum_do "
                    + "  FROM compra_det WHERE  mov_tipo=1 and comp_id = ? ";

            ps = cn.prepareStatement(sql);
            ps.setInt(1, comp_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                d.setSum_com_so(Math.round(rs.getDouble("sum_so") * 100.0) / 100.0);
                d.setSum_com_do(Math.round(rs.getDouble("sum_do") * 100.0) / 100.0);

            }
            sql = "SELECT "
                    + "   'adelantos' as mov "
                    + "    ,sum(DISTINCT saldo_do) as sum_saldo_do "
                    + "    ,sum(DISTINCT total_so) as sum_so "
                    + "    ,sum(DISTINCT total_do) as sum_do "
                    + "  FROM compra_det WHERE  mov_tipo in (2,3) and comp_id = ? ";

            ps = cn.prepareStatement(sql);
            ps.setInt(1, comp_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                d.setSum_ade_so(Math.round(rs.getDouble("sum_so") * 100.0) / 100.0);
                d.setSum_ade_do(Math.round(rs.getDouble("sum_do") * 100.0) / 100.0);
                d.setSum_sal_do(Math.round(rs.getDouble("sum_saldo_do") * 100.0) / 100.0);
            }

            d.setSaldo_do(Math.round((d.getSum_com_do() - (d.getSum_ade_do()+ d.getSum_sal_do())) * 100.0) / 100.0);
            d.setSaldo_so(Math.round((d.getSum_com_so() - d.getSum_ade_so() ) * 100.0) / 100.0);

        } catch (SQLException ex) {
            log.log(Level.SEVERE, "getSaldosByCompId", ex);
        }
        return d;
    }

    public static int updateCampoxx(String key, CompraDet d) {
        System.out.println("(" + key + "," + d.getId() + ")");
        int comit = 0;
        String sql = "UPDATE compra_det SET "
                + key + "=? "
                + "WHERE id=?";
        int i = 0;
        try {
            ps = cn.prepareStatement(sql);
            if (key.equals("glosa")) {
                ps.setString(++i, d.getGlosa());
            }

            ps.setInt(++i, d.getComp_id());
            ps.setInt(++i, d.getMov_tipo());
            ps.setString(++i, d.getGlosa());
            ps.setDouble(++i, d.getCant_gr());
            ps.setDouble(++i, d.getOnza());
            ps.setDouble(++i, d.getPorc());
            ps.setDouble(++i, d.getLey());
            ps.setDouble(++i, d.getSistema());

            double pre_do = (d.getOnza() / d.getSistema() - (d.getOnza() / d.getSistema()) * d.getPorc() / 100) * d.getLey();
            ps.setDouble(++i, Math.round(pre_do * 100.0) / 100.0);//d.getPrecio_do()
            ps.setDouble(++i, d.getTc());

            double pre_so = pre_do * d.getTc();
            ps.setDouble(++i, Math.round(pre_so * 100.0) / 100.0); //d.getPrecio_so()
            ps.setDouble(++i, Math.round(pre_do * d.getCant_gr() * 100.0) / 100.0); //d.getTotal_do()
            ps.setDouble(++i, Math.round(pre_so * d.getCant_gr() * 100.0) / 100.0); //d.getTotal_so()

            ps.setInt(++i, 1);
            ps.setInt(++i, 1);
            ps.setInt(++i, d.getId());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }
}
