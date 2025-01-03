insert into compras values ('11','Pedro','Gonzalez',3,null);
select * from audita_compras;
select distinct * from compras;
update compras set nombre = 'Josete' where nombre = 'ana';
select * from audita_compras;
delete from compras where nombre = 'antonio' and apellido = 'lopez';
select * from audita_compras;
select color from ventas;
