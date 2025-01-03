select distinct * from marcas where ciudadm = 'barcelona';
select distinct * from clientes where ciudad='madrid' and apellido='garcia';
select apellido, ciudad from clientes;
select apellido from clientes where ciudad='madrid';
select distinct m.nombrem from marcas m, marco mr, coches c where m.cifm=mr.cifm and mr.codcoche=c.codcoche and c.modelo='gtd';
select distinct m.nombrem from marcas m, marco mr, coches c, ventas v where m.cifm=mr.cifm and mr.codcoche=c.codcoche and c.codcoche=v.codcoche and v.color='rojo';
select distinct nombrech from coches where modelo in (select modelo from coches where nombrech='cordoba');
select distinct nombrech from coches where modelo <> 'gtd' order by nombrech;
select * from concesionarios;
select m.cifm, c.dni from marcas m, clientes c where c.ciudad=m.ciudadm order by m.cifm;
select m.cifm, c.dni from marcas m, clientes c where c.ciudad <> m.ciudadm order by m.cifm;
select c.codcoche from coches c, distribucion d, concesionarios cs where c.codcoche=d.codcoche and d.cifc=cs.cifc and cs.ciudadc='barcelona' order by c.codcoche;
select c.codcoche from coches c, ventas v, concesionarios cs, clientes cl where c.codcoche=v.codcoche and cs.cifc=v.cifc and v.dni=cl.dni and cl.ciudad='madrid' and cl.ciudad=cs.ciudadc order by c.codcoche;
select distinct c.codcoche from coches c, ventas v, concesionarios cs, clientes cl where c.codcoche=v.codcoche and cs.cifc=v.cifc and v.dni=cl.dni and cl.ciudad=cs.ciudadc order by c.codcoche;
select distinct m1.nombrem, m2.nombrem from marcas m1, marcas m2 where m1.ciudadm = m2.ciudadm and m1.nombrem <> m2.nombrem;
select distinct * from clientes where ciudad='madrid';
select distinct c.dni from clientes c, ventas v, concesionarios cn where c.dni=v.dni and v.cifc=cn.cifc and cn.ciudadc='madrid';
select distinct v.color from ventas v, concesionarios c where v.cifc=c.cifc and c.nombrec='acar';
select distinct v.codcoche from ventas v, concesionarios c where v.cifc=c.cifc and c.ciudadc='madrid';
select distinct c.nombre from clientes c, ventas v, concesionarios cn where c.dni=v.dni and v.cifc=cn.cifc and cn.nombrec='dcar';
select distinct c.nombre, c.apellido from clientes c, ventas v, coches ch where c.dni=v.dni and v.codcoche=ch.codcoche and ch.modelo='gti' and v.color='blanco';
select distinct c.nombre, c.apellido from clientes c, ventas v where c.dni=v.dni and v.cifc in (select cn.cifc from concesionarios cn, distribucion d, coches c where cn.ciudadc='madrid' and cn.cifc=d.cifc and d.codcoche=c.codcoche and c.modelo='gti');
select distinct c.nombre, c.apellido from clientes c where c.dni in ((select dni from ventas where color='blanco') intersect (select dni from ventas where color='rojo'));
select distinct dni from ventas where cifc=1;
select distinct nombre from clientes where dni not in (select v.dni from ventas v, concesionarios c where v.color='rojo' and v.cifc=c.cifc and c.ciudadc='madrid');
select distinct c.cifc, sum(d.cantidad) from concesionarios c, distribucion d where c.cifc=d.cifc group by c.cifc;
select distinct c.cifc, avg(d.cantidad) from concesionarios c, distribucion d where c.cifc=d.cifc group by c.cifc having avg(d.cantidad) > 10;
select cifc, sum(cantidad) from distribucion group by cifc having sum(cantidad) <= 18 and sum(cantidad) >= 10;
select count(cifm) from marcas;
select count(min(ciudadm)) from marcas group by ciudadm;
select distinct c.nombre, c.apellido from clientes c, ventas v where c.dni=v.dni and v.cifc in (select cifc from concesionarios where ciudadc='madrid') and c.nombre like 'j%';
select distinct * from clientes order by nombre;
select distinct nombre, apellido from clientes c, ventas v where c.dni=v.dni and v.cifc in (select distinct c.cifc from concesionarios c, ventas v where v.dni=2 and v.cifc=c.cifc) and v.dni <> 2;
select distinct nombre, apellido from clientes c, ventas v where c.dni=v.dni and v.cifc in (select distinct c.cifc from concesionarios c, ventas v where v.dni=1 and v.cifc=c.cifc) and v.dni <> 1;


select distinct c.cifc, c.nombrec, c.ciudadc from concesionarios c, distribucion d where c.cifc=d.cifc and sum(d.cantidad) > avg(sum((select cantidad from distribucion))); -- no funciona (de momento no sé hacerlo)

