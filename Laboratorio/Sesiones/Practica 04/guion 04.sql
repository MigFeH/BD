select nombre_cliente from cliente 
where exists (select * from cuenta
    inner join depositante on cuenta.num_cuenta = depositante.num_cuenta
    where depositante.nombre_cliente = cliente.nombre_cliente
    and nombre_sucursal = 'Perryridge'
)
    
and exists (select * from prestamo
    inner join prestatario on prestamo.num_prestamo = prestatario.num_prestamo
    where prestatario.nombre_cliente = cliente.nombre_cliente
    and nombre_sucursal = 'Perryridge');
  
    
select nombre_cliente from cliente 
where exists (select * from cuenta
    inner join depositante on cuenta.num_cuenta = depositante.num_cuenta
    where depositante.nombre_cliente = cliente.nombre_cliente
    and nombre_sucursal = 'Perryridge'
)
    
and not exists (select * from prestamo
    inner join prestatario on prestamo.num_prestamo = prestatario.num_prestamo
    where prestatario.nombre_cliente = cliente.nombre_cliente
    and nombre_sucursal = 'Perryridge');


select distinct d.nombre_cliente from Depositante d, Cuenta c 
where d.num_cuenta = c.num_cuenta and c.nombre_sucursal in
(select c.nombre_sucursal from Cuenta c, Depositante d where c.num_cuenta = d.num_cuenta and d.nombre_cliente = 'Hayes');


select nombre_sucursal from Sucursal 
where activo > SOME (select activo from Sucursal where ciudad_sucursal = 'Brooklyn');


select nombre_sucursal from Sucursal where activo > all (select activo from Sucursal where ciudad_sucursal = 'Brooklyn');


select distinct d.nombre_cliente from Depositante d, Cuenta c where d.num_cuenta = c.num_cuenta and c.nombre_sucursal = 'Perryridge' order by d.nombre_cliente asc;


select s.nombre_sucursal from Sucursal s, Cuenta c where s.nombre_sucursal = c.nombre_sucursal and c.saldo = (select max(avg(saldo)) from Cuenta group by nombre_sucursal);