SET FOREIGN_KEY_CHECKS=0;



CREATE TABLE Cliente
(
	nombre TEXT,
	id INTEGER NOT NULL,
	PRIMARY KEY (id)
) 
;


CREATE TABLE Item
(
	nombre TEXT,
	precio DOUBLE,
	id INTEGER NOT NULL,
	PRIMARY KEY (id)
) 
;


CREATE TABLE User
(
	username TEXT,
	paswd TEXT,
	id INTEGER NOT NULL,
	PRIMARY KEY (id)
) 
;


CREATE TABLE Venta
(
	fecha Date,
	total_base DOUBLE,
	total_igv DOUBLE,
	total_descto DOUBLE,
	total_final DOUBLE,
	id INTEGER NOT NULL,
	clie_id INTEGER NOT NULL,
	user_id INTEGER NOT NULL,
	PRIMARY KEY (id),
	KEY (clie_id),
	KEY (user_id)
) 
;


CREATE TABLE VentaDet
(
	cantidad DOUBLE,
	precio DOUBLE,
	subtotal DOUBLE,
	igv DOUBLE,
	descto DOUBLE,
	prod_id INTEGER,
	venta_id INTEGER,
	ventaDetID INTEGER NOT NULL,
	itemID INTEGER NOT NULL,
	ventaID INTEGER NOT NULL,
	PRIMARY KEY (ventaDetID),
	KEY (itemID),
	KEY (ventaID)
) 
;



SET FOREIGN_KEY_CHECKS=1;


ALTER TABLE Venta ADD CONSTRAINT FK_Venta_Cliente 
	FOREIGN KEY (clie_id) REFERENCES Cliente (id)
;

ALTER TABLE Venta ADD CONSTRAINT FK_user_id 
	FOREIGN KEY (user_id) REFERENCES User (id)
;

ALTER TABLE VentaDet ADD CONSTRAINT FK_VentaDet_Item 
	FOREIGN KEY (itemID) REFERENCES Item (id)
;

ALTER TABLE VentaDet ADD CONSTRAINT FK_VentaDet_Venta 
	FOREIGN KEY (ventaID) REFERENCES Venta (id)
;
