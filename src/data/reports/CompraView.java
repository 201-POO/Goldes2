package data.reports;

import java.util.Date;

/**
 *
 * @author Asullom
 */
public class CompraView {

    private int comp_id;
    private int clie_id;
    private String clie_nom;
    private int esdolares;
    private int comp_activo;

    private int id;
    private int mov_tipo;
    private String glosa;
    private double cant_gr;

    private double onza;
    private double porc;
    private double ley;
    private double sistema;
    private double precio_do;

    private double tc;
    private double precio_so;

    private double compra_so;
    private double compra_do;

    private double adelanto_so;
    private double adelanto_do;

    private double saldo_do; // el monto en dólares que se pagó en soles como adelanto en total_so

    private Date fecha;
    private int esenefec;
    private Date fecha_pago;
    private int user_id;
    private int activo;

    //Datos para visualizar
    //private double egreso_do;//total_do-saldo_porpagar_do
    //private double egreso_so;//total_so-saldo_porpagar_so

    public int getComp_id() {
        return comp_id;
    }

    public void setComp_id(int comp_id) {
        this.comp_id = comp_id;
    }

    public int getClie_id() {
        return clie_id;
    }

    public void setClie_id(int clie_id) {
        this.clie_id = clie_id;
    }

    public String getClie_nom() {
        return clie_nom;
    }

    public void setClie_nom(String clie_nom) {
        this.clie_nom = clie_nom;
    }

    public int getEsdolares() {
        return esdolares;
    }

    public void setEsdolares(int esdolares) {
        this.esdolares = esdolares;
    }

    public int getComp_activo() {
        return comp_activo;
    }

    public void setComp_activo(int comp_activo) {
        this.comp_activo = comp_activo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMov_tipo() {
        return mov_tipo;
    }

    public void setMov_tipo(int mov_tipo) {
        this.mov_tipo = mov_tipo;
    }

    public String getGlosa() {
        return glosa;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }

    public double getCant_gr() {
        return cant_gr;
    }

    public void setCant_gr(double cant_gr) {
        this.cant_gr = cant_gr;
    }

    public double getOnza() {
        return onza;
    }

    public void setOnza(double onza) {
        this.onza = onza;
    }

    public double getPorc() {
        return porc;
    }

    public void setPorc(double porc) {
        this.porc = porc;
    }

    public double getLey() {
        return ley;
    }

    public void setLey(double ley) {
        this.ley = ley;
    }

    public double getSistema() {
        return sistema;
    }

    public void setSistema(double sistema) {
        this.sistema = sistema;
    }

    public double getPrecio_do() {
        return precio_do;
    }

    public void setPrecio_do(double precio_do) {
        this.precio_do = precio_do;
    }

    public double getTc() {
        return tc;
    }

    public void setTc(double tc) {
        this.tc = tc;
    }

    public double getPrecio_so() {
        return precio_so;
    }

    public void setPrecio_so(double precio_so) {
        this.precio_so = precio_so;
    }

    public double getCompra_so() {
        return compra_so;
    }

    public void setCompra_so(double compra_so) {
        this.compra_so = compra_so;
    }

    public double getCompra_do() {
        return compra_do;
    }

    public void setCompra_do(double compra_do) {
        this.compra_do = compra_do;
    }

    public double getAdelanto_so() {
        return adelanto_so;
    }

    public void setAdelanto_so(double adelanto_so) {
        this.adelanto_so = adelanto_so;
    }

    public double getAdelanto_do() {
        return adelanto_do;
    }

    public void setAdelanto_do(double adelanto_do) {
        this.adelanto_do = adelanto_do;
    }

    public double getSaldo_do() {
        return saldo_do;
    }

    public void setSaldo_do(double saldo_do) {
        this.saldo_do = saldo_do;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getEsenefec() {
        return esenefec;
    }

    public void setEsenefec(int esenefec) {
        this.esenefec = esenefec;
    }

    public Date getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(Date fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }
   

}
