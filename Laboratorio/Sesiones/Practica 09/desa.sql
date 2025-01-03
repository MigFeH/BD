ALTER TABLE compras ADD DatosMayus varchar(100);
insert into compras values('1','antonio','fernandez gonzalez',1,null);
update compras set datosmayus = 'daniel losa' where datosmayus = 'ANTONIOFERNANDEZ GONZALEZ';
SELECT * FROM COMPRAS order by dni;

insert into ventas values('2','1',32,null);
select * from compras order by dni;

create table AUDITORIA_CLIENTES
(
    Dniant varchar2(9),
    Nombreant varchar2(40),
    Apellidoant varchar2(40),
    Cuidadant varchar2(25),
    Dniact varchar2(9), 
    Nombreact varchar2(40), 
    Apellidoact varchar2(40),
    Ciudadact varchar2(25), 
    Fechahora timestamp
);

INSERT INTO CLIENTES VALUES ('33','FERNANDO','ALONSO','OVIEDO');
SELECT * FROM AUDITORIA_CLIENTES;




