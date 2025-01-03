select codcoche from concesionarios c, distribucion d where c.cifc=d.cifc and c.ciudadc='barcelona';
select codcoche from concesionarios c, ventas v, clientes cl where c.ciudadc='madrid' and c.cifc=v.cifc and v.dni=cl.dni and cl.ciudad = 'madrid';
select m1.nombrem, m2.nombrem from marcas m1, marcas m2 where m1.ciudadm = m2.ciudadm and m1.nombrem <> m2.nombrem;
select * from clientes where ciudad='madrid';
select DISTINCT c.dni from clientes c, ventas v, concesionarios cs where c.dni=v.dni and v.cifc=cs.cifc and cs.ciudadc='madrid';
select DISTINCT v.color from ventas v, concesionarios c where v.cifc = c.cifc and c.nombrec='acar';
select c.nombre, c.apellido from clientes c where c.dni in((select dni from ventas where color='rojo') intersect (select dni from ventas where color='blanco'));
select DISTINCT c.dni from clientes c, ventas v where c.dni=v.dni and v.cifc=1;
select nombre from clientes where dni not in (select v.dni from ventas v, concesionarios c where v.color='rojo' and v.cifc=c.cifc and c.ciudadc='madrid');