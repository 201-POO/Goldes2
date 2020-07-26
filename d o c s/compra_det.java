package modelo.entities;

/**
 * @author Asullom
 * @version 1.0
 * @created 25-Jun-2020 09:34:11 a.m.
 */
public class compra_det {

	private int mov_tipo = 1 compra, 2 Adelanto, 3 Pago, 4 cobro;
	private String glosa;
	private double cant_gr;
	private double onza;
	private double porc;
	private double ley;
	private double sistema;
	private double precio_do;
	private double tc;
	private int esdolares = 1 si, 0 soles;
	private double precio_so;
	private double total_do;
	private double total_so;
	private int esenefec = 1 si, 0 transf banc;
	/**
	 * fecha de la transaccion para arqueo
	 */
	private Date fecha;
	/**
	 * En caso de compra y le quede debiendo, para cuando le pago
	 */
	private Date fecha_pago;
	private int user_id;
	public compra m_compra;
	public user m_user;

	public compra_det(){

	}

	public void finalize() throws Throwable {

	}

}