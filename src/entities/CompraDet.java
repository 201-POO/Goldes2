package entities;

import java.util.Date;

/**
 *
 * @author Asullom
 */
public class CompraDet {

    private int id;
    private int comp_id;
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

    private double total_do;
    private double total_so;
    
    private double saldo_do; // el monto en dólares que se pagó en soles como adelanto en total_so

    private Date fecha;
    private int esenefec;
    private Date fecha_pago;
    private int user_id;
    private int activo;

    //Datos para visualizar
    //private double egreso_do;//total_do-saldo_porpagar_do
    //private double egreso_so;//total_so-saldo_porpagar_so

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getComp_id() {
        return comp_id;
    }

    public void setComp_id(int comp_id) {
        this.comp_id = comp_id;
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

    public double getTotal_do() {
        return total_do;
    }

    public void setTotal_do(double total_do) {
        this.total_do = total_do;
    }

    public double getTotal_so() {
        return total_so;
    }

    public void setTotal_so(double total_so) {
        this.total_so = total_so;
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

    public double getSaldo_do() {
        return saldo_do;
    }

    public void setSaldo_do(double saldo_do) {
        this.saldo_do = saldo_do;
    }

    
    
}
