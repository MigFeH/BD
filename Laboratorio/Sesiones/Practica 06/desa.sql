SET SERVEROUTPUT ON
EXEC PROCEDURE_HOLA;
EXEC PROCEDURE_HOLAN('Miguel');

CREATE TABLE Totales
(
    num_total_ventas decimal(3,0),
    num_total_coches decimal(3,0),
    num_total_marcas decimal(3,0),
    num_total_clientes decimal(3,0),
    num_total_concesionarios decimal(3,0)
);

CREATE TABLE HistoricoClientes
(
    DNI varchar(9),
    NOMBRE varchar(40),
    APELLIDO varchar(40),
    CIUDAD varchar(25),
    CONSTRAINT PK_HISTORICO_CLIENTES PRIMARY KEY (DNI)
);
