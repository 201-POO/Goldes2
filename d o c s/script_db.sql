-- (base) D:\adev>pyspark --master spark://192.168.1.104:7077
CREATE TABLE IF NOT EXISTS parametro (
	id			INTEGER PRIMARY KEY,
	onza		REAL	NOT NULL,
    porc		REAL	NOT NULL,
	ley			REAL	NOT NULL,
	sistema		REAL	NOT NULL,
	tc		    REAL	NOT NULL,
	precio_do	REAL	NOT NULL,
	precio_so	REAL	NOT NULL,
    last_updated datetime default current_timestamp
);

CREATE TABLE IF NOT EXISTS user (
	id		INTEGER PRIMARY KEY AUTOINCREMENT,
	rol		INTEGER	NOT NULL,
	username		TEXT	NOT NULL,
    pin		TEXT	NOT NULL
);

CREATE TABLE IF NOT EXISTS caja_aper_cierre (
	id			INTEGER PRIMARY KEY AUTOINCREMENT,
	fecha		datetime	NOT NULL,
    esaper	INTEGER		NOT	NULL,
	saldo_do	REAL			NULL,
	saldo_so	REAL			NULL,
	saldo_bancos_do	REAL		NULL,
	saldo_bancos_so	REAL		NULL,
	gramos		REAL			NULL,
	user_id		INTEGER			NULL,
	date_created datetime default current_timestamp,
    last_updated datetime default current_timestamp
);


CREATE TABLE IF NOT EXISTS cliente (
	id			INTEGER PRIMARY KEY AUTOINCREMENT,
	nombres		TEXT	NOT NULL,
    infoadic	TEXT	NULL
);

CREATE TABLE IF NOT EXISTS compra (
	id			INTEGER PRIMARY KEY AUTOINCREMENT,
    clie_id	    INTEGER		NOT	NULL,
	clie_nom	TEXT			NULL,
	
	esdolares 	INTEGER		default 1,
	activo		INTEGER		default 1,
	date_created datetime default current_timestamp,
    last_updated datetime default current_timestamp,
	FOREIGN KEY (clie_id) REFERENCES cliente (id) 
	ON UPDATE RESTRICT  ON DELETE RESTRICT 
);

CREATE TABLE IF NOT EXISTS compra_det (
	id			INTEGER PRIMARY KEY AUTOINCREMENT,
	comp_id		INTEGER		NOT	NULL, 
	mov_tipo	INTEGER		NOT	NULL, -- 1 compra, 2 Adelanto, 3 Adelanto saldo soles
    glosa   	TEXT			NULL,
	cant_gr		REAL	default 0,
	onza		REAL	default 0,
    porc		REAL	default 0,
	ley			REAL	default 0,
	sistema		REAL	default 0,
	precio_do	REAL	default 0,
	tc			REAL	default 0,
	precio_so	REAL	default 0,
	total_do	REAL	default 0,
	total_so	REAL	default 0,
	saldo_do	REAL	default 0, -- el saldo dólares que se pagó en soles como adelanto en total_so
	fecha		datetime	default current_timestamp,
    esenefec 	INTEGER		default 1,
    fecha_pago	datetime	default current_timestamp, -- no se usa ahora
	user_id		INTEGER			NULL,
	activo		INTEGER		default 1,
	FOREIGN KEY (comp_id) REFERENCES compra (id) 
	ON UPDATE RESTRICT ON DELETE RESTRICT
);



CREATE TABLE IF NOT EXISTS clienteb (
	id			INTEGER PRIMARY KEY AUTOINCREMENT,
	nombres		VARCHAR(100)	NOT NULL,
    infoadic	VARCHAR(260)	NULL
);



















CREATE TABLE IF NOT EXISTS clie_mov (
	id			INTEGER PRIMARY KEY AUTOINCREMENT,
	fecha		datetime	NOT NULL,
    clie_id		INTEGER		NOT	NULL,
    clie_nom	TEXT			NULL,
    glosa		TEXT			NULL,
	esdolares 	INTEGER		NOT	NULL,
	escobro 	INTEGER		NOT	NULL,
	
	devol_do	REAL			NULL, -- o abono o devolucion al banco (egreso)
	devol_so	REAL			NULL, -- 
	cobro_do	REAL			NULL, -- o lo que me debe el bnco (ingreso)
	cobro_so	REAL			NULL, -- o o el adelanto que me da el banco

	user		INTEGER			NULL,
	activo		INTEGER		default 1,
	date_created datetime default current_timestamp,
    last_updated datetime default current_timestamp,
	FOREIGN KEY (clie_id) REFERENCES cliente (id) 
	ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS venta (
	id			INTEGER PRIMARY KEY AUTOINCREMENT,
	fecha		datetime	NOT NULL,
    clie_id		INTEGER		NOT	NULL,
	clie_nom	TEXT			NULL,
	cant_gr		REAL		NOT	NULL,
	esdolares 	INTEGER		default 1,
	
	onza		REAL	NOT NULL,
    porc		REAL	NOT NULL,
	ley			REAL	NOT NULL,
	sistema		REAL	NOT NULL,
	tcambio		REAL	NOT NULL,
	precio_do	REAL	NOT NULL,
	precio_so	REAL	NOT NULL,

	total_do	REAL	NOT NULL,
	total_so	REAL	NOT NULL,
	saldo_porcobrar_do	REAL	NULL, -- el banco me va deber
	saldo_porcobrar_so	REAL	NULL,
	fecha_decobro		datetime	NOT NULL,
	user		INTEGER			NULL,
	activo		INTEGER		default 1,
	date_created datetime default current_timestamp,
    last_updated datetime default current_timestamp,
	FOREIGN KEY (clie_id) REFERENCES cliente (id) 
	ON UPDATE RESTRICT ON DELETE RESTRICT
);
