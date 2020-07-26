SET FOREIGN_KEY_CHECKS=0;



CREATE TABLE bancocliente
(
	nombres String,
	infoadic String,
	bancoclienteID Integer NOT NULL,
	PRIMARY KEY (bancoclienteID)
) 
;


CREATE TABLE caja_aper_cierre
(
	fecha Date,
	esaper int,
	efectivo_do double,
	efectivo_so double,
	saldo_do_bancos double,
	saldo_so_bancos double,
	gramos double,
	user_id int,
	caja_aper_cierreID Integer NOT NULL,
	userID Integer NOT NULL,
	PRIMARY KEY (caja_aper_cierreID),
	KEY (userID)
) 
;


CREATE TABLE cliente
(
	nombres String,
	infoadic String,
	clienteID Integer NOT NULL,
	PRIMARY KEY (clienteID)
) 
;


CREATE TABLE compra
(
	clie_id int,
	clie_nom String,
	activo int DEFAULT 1 abi, 0 cerr,
	date_created Date,
	last_updated Date,
	compraID Integer NOT NULL,
	clienteID Integer NOT NULL,
	PRIMARY KEY (compraID),
	KEY (clienteID)
) 
;


CREATE TABLE compra_det
(
	mov_tipo int DEFAULT 1 compra, 2 Adelanto, 3 Pago, 4 cobro,
	glosa String,
	cant_gr double,
	onza double,
	porc double,
	ley double,
	sistema double,
	precio_do double,
	tc double,
	esdolares int DEFAULT 1 si, 0 soles,
	precio_so double,
	total_do double,
	total_so double,
	esenefec int DEFAULT 1 si, 0 transf banc,
	fecha Date,
	fecha_pago Date,
	user_id int,
	compra_detID Integer NOT NULL,
	compraID Integer NOT NULL,
	userID Integer NOT NULL,
	PRIMARY KEY (compra_detID),
	KEY (compraID),
	KEY (userID)
) 
;


CREATE TABLE parametro
(
	onza double,
	porc double,
	ley double,
	sistema double,
	precio_do double,
	tc double,
	precio_so double,
	parametroID Integer NOT NULL,
	PRIMARY KEY (parametroID)
) 
;


CREATE TABLE user
(
	username String,
	pin String,
	privilegio int DEFAULT 2 admin, 1 user,
	userID Integer NOT NULL,
	PRIMARY KEY (userID)
) 
;


CREATE TABLE venta
(
	clie_id int,
	clie_nom String,
	activo int DEFAULT 1 abi, 0 cerr,
	date_created Date,
	last_updated Date,
	ventaID Integer NOT NULL,
	bancoclienteID Integer NOT NULL,
	PRIMARY KEY (ventaID),
	KEY (bancoclienteID)
) 
;


CREATE TABLE venta_det
(
	mov_tipo int DEFAULT 1 venta, 2 adelanto, 3 cobro, 4 pago,
	glosa String,
	cant_gr int,
	onza double,
	porc double,
	ley double,
	sistema double,
	precio_do double,
	tc double,
	esdolares int DEFAULT i si, 0 soles,
	precio_so double,
	total_do double,
	total_so double,
	esenefec int DEFAULT 1 si, 0 transf banc,
	fecha Date,
	fecha_cobro Date,
	user_id int,
	venta_detID Integer NOT NULL,
	userID Integer NOT NULL,
	ventaID Integer NOT NULL,
	PRIMARY KEY (venta_detID),
	KEY (userID),
	KEY (ventaID)
) 
;



SET FOREIGN_KEY_CHECKS=1;


ALTER TABLE caja_aper_cierre ADD CONSTRAINT FK_caja_aper_cierre_user 
	FOREIGN KEY (userID) REFERENCES user (userID)
;

ALTER TABLE compra ADD CONSTRAINT FK_compra_cliente 
	FOREIGN KEY (clienteID) REFERENCES cliente (clienteID)
;

ALTER TABLE compra_det ADD CONSTRAINT FK_compra_det_compra 
	FOREIGN KEY (compraID) REFERENCES compra (compraID)
;

ALTER TABLE compra_det ADD CONSTRAINT FK_compra_det_user 
	FOREIGN KEY (userID) REFERENCES user (userID)
;

ALTER TABLE venta ADD CONSTRAINT FK_venta_bancocliente 
	FOREIGN KEY (bancoclienteID) REFERENCES bancocliente (bancoclienteID)
;

ALTER TABLE venta_det ADD CONSTRAINT FK_venta_det_user 
	FOREIGN KEY (userID) REFERENCES user (userID)
;

ALTER TABLE venta_det ADD CONSTRAINT FK_venta_det_venta 
	FOREIGN KEY (ventaID) REFERENCES venta (ventaID)
;
