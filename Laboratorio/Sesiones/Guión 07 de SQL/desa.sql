--1
select e.enombre, e.transformadores from Estacion e, entrega en 
where en.enombre = e.enombre and en.pnombre in 
    (select pnombre from Nuclear where residuos > 25000 );
--2   
select distinct r.cnombre from pertenece r 
where r.cnombre like 'C%' and r.numred in 
    (select numred_envia from envia_energia) and
    r.numred not in (select numred_recibe from envia_energia);
--3    
select ee.numred_envia, ee.numred_recibe, volumen
from envia_energia ee, red_distribucion rde, estacion ese, red_distribucion rdr, estacion esr
where ee.numred_envia = rde.numred and rde.enombre = ese.enombre
    and ee.numred_recibe = rdr.numred and rdr.enombre = esr.enombre
    and volumen > 16000 and ese.transformadores > esr.transformador;
--4 
select enombre, transformadores from estacion 
where transformadores > 800
    and enombre in (select enombre from red_distribucion 
    where numred not in (select numred from linea
    where nlinea in (select nlinea from subestacion
    where nsubestacion in (select nsubestacion from distribuye
    where zcodigo in (select zcodigo from zona
    group by zcodigo having sum(consParticulares) > sum(consEmpresas))))));
--5 
select n.pnombre, n.numreactores from nuclear n, entrega en, estacion e
where n.pnombre like 'C%' 
    and n.pnombre = en.pnombre
    and e.enombre = en.enombre
    and 
    order by residuos desc;

-- Obtener el nombre y el número de reactores de los productores nucleares (ordenados de
-- forma descendente por residuos generados) cuyo nombre comienza por 'C' 

-- y se trate de
-- productores que entregan energía a estaciones con más de 300 transformadores y que sean
-- cabecera de redes de distribución de longitud máxima superior a 125000 y que dichas redes
-- dispongan de al menos una línea instalada. 
    
    
    