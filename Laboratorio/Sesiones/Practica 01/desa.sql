select * from marcas where ciudadm='barcelona';
select * from clientes where ciudad='madrid' and apellido='garcia';
select apellido,ciudad from clientes;
select apellido from clientes where ciudad='madrid';
select distinct nombrem from marcas m, marco mc, coches c where m.cifm = mc.cifm and mc.codcoche=c.codcoche and c.modelo='gtd';
select nombrem from marcas, marco, coches, ventas v where v.color='rojo';
select distinct c2.nombrech from coches c1, coches c2 where c1.modelo=c2.modelo and c1.nombrech='cordoba';
select distinct nombrech from coches where not modelo = 'gtd';
select * from concesionarios;

