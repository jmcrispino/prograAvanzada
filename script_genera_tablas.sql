drop table if exists Empleado;
drop table if exists Departamento;

create table Departamento
(
	nroDepto integer not null primary key,
	nombre varchar(50) not null
);

create table Empleado 
(
	documento varchar(20) not null,
	tipoDocumento varchar(30) not null,
	apellido varchar(100) not null,
	nombre varchar(100) not null,
	cargo varchar(50) null,
	fechaIngreso date,
	nroDepto integer not null,
	primary key (documento, tipoDocumento),
	foreign key (nroDepto) references Departamento(nroDepto)
);

insert into Departamento values(1, 'RRHH');
insert into Departamento values(2, 'Sistemas');
insert into Departamento values(3, 'Logística');

Insert into Empleado values('10235450', 'DNI', 'Valenti', 'Soledad', 'Secretaria',  current_date, 1);
Insert into Empleado values('27199432', 'DNI', 'Martinez', 'Daniel Hector', 'Coordinador', current_date,2); 
Insert into Empleado values('21235489', 'DNI', 'Perez', 'Juan Manuel', 'Empleado', current_date, 3); 





