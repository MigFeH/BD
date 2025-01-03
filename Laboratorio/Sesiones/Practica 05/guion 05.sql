SELECT e.last_name, j.job_title, e.salary, e.commission_pct from Employees e, Jobs j where j.job_id = e.job_id and e.commission_pct > 0.00 order by j.job_title asc;
SELECT e.last_name, j.job_title, e.salary, e.commission_pct from Employees e, Jobs j where j.job_id = e.job_id and e.commission_pct > 0.00 order by e.salary desc;
SELECT last_name from Employees where last_name like 'J%' or last_name like 'K%' or last_name like 'L%' or last_name like 'M%';
SELECT last_name from Employees where SUBSTR(last_name,1,1) in ('J','K','L','M');
SELECT e.last_name as Apellido_empleado, e.employee_id as Numero_empleado, m.last_name as Apellido_manager, m.manager_id as Numero_manager from Employees e, Employees m where m.manager_id = e.employee_id;
SELECT e1.last_name, e1.hire_date from Employees e1, Employees e2 where e1.hire_date > e2.hire_date and e2.last_name = 'Davies';
SELECT e.first_name as Nombre_empleado, e.hire_date as Fecha_empleado_contratado, m.first_name as Nombre_manager, m.hire_date as Fecha_manager_contratado from Employees e, Employees m where e.employee_id = m.manager_id and e.hire_date < m.hire_date;
SELECT department_id, min(salary) from Employees group by department_id having avg(salary) >= all (select avg(salary) from Employees group by department_id);
SELECT d.department_id as Numero_departamento, d.department_name as Nombre_departamento, d.location_id from Departments d where d.department_id not in (select department_id from Employees where job_id = 'SA_REP');

--8a
select d.department_id, d.department_name, count(*) from departments d, employees e where d.department_id = e.department_id group by d.department_id, d.department_name having count(*) < 3;
--8b
select d.department_id, d.department_name, count(*) from departments d, employees e where d.department_id = e.department_id group by d.department_id, d.department_name having count(*) = (select max(total) from (select count(*) as total from employees group by department_id));

