CREATE TABLE sede (
	id_sede INTEGER NOT NULL AUTO_INCREMENT,
	nombre_sede VARCHAR(45) NOT NULL,
	PRIMARY KEY (id_sede)
);

CREATE TABLE ciclo (
	id_ciclo INTEGER NOT NULL AUTO_INCREMENT,
	nombre_ciclo VARCHAR(45) NOT NULL,
	fecha_inicio DATE NOT NULL,
	fecha_fin DATE NOT NULL,
	sede_id INTEGER NOT NULL,
	PRIMARY KEY (id_ciclo)
);
ALTER TABLE ciclo
	ADD FOREIGN KEY (sede_id)
	REFERENCES sede (id_sede);

CREATE TABLE pabellon (
	id INTEGER NOT NULL AUTO_INCREMENT,
	denominacion VARCHAR(145) NOT NULL,
	sede_id INTEGER NOT NULL,
	PRIMARY KEY (id)
);

ALTER TABLE pabellon
	ADD FOREIGN KEY (sede_id)
	REFERENCES sede (id_sede);

CREATE TABLE pabellon_e (
	id INTEGER NOT NULL AUTO_INCREMENT,
	denominacion VARCHAR(145) NOT NULL,
	sede_id INTEGER NOT NULL,
	PRIMARY KEY (id)
);
ALTER TABLE pabellon_e
	ADD FOREIGN KEY (sede_id)
	REFERENCES sede (id_sede);

CREATE TABLE escuela (
	id_escuela INTEGER NOT NULL AUTO_INCREMENT,
	nombre_escuela VARCHAR(45) NOT NULL,
	PRIMARY KEY (id_escuela)
);

CREATE TABLE aula (
	id INTEGER NOT NULL AUTO_INCREMENT,
	denominacion VARCHAR(30) NOT NULL,
	capacidad INTEGER,
	prioridad INTEGER NOT NULL,
	pabellon_id INTEGER NOT NULL,
	PRIMARY KEY (id)
);
ALTER TABLE aula
	ADD FOREIGN KEY (pabellon_id)
	REFERENCES pabellon (id);

CREATE TABLE aula_e (
	id INTEGER NOT NULL AUTO_INCREMENT,
	denominacion VARCHAR(30) NOT NULL,
	capacidad INTEGER,
	pabellone_id INTEGER NOT NULL,
	PRIMARY KEY (id)
);
ALTER TABLE aula_e
	ADD FOREIGN KEY (pabellone_id)
	REFERENCES pabellon_e (id);

CREATE TABLE examen (
	id INTEGER NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(100) NOT NULL,
	fecha DATE NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE estudiante (
	id_estudiante INTEGER NOT NULL AUTO_INCREMENT,
	dni CHARACTER(8) NOT NULL,
	apellidos VARCHAR(45) NOT NULL,
	nombres VARCHAR(45) NOT NULL,
	fecha_nacimiento DATE NOT NULL,
	fotografia VARCHAR(45) NOT NULL,
	fecha_matricula TIMESTAMP NOT NULL,
	correo_electronico VARCHAR(45) NOT NULL,
	direccion VARCHAR(250) NOT NULL,
	telefono VARCHAR(9) NOT NULL,
	escuela_id INTEGER NOT NULL,
	sede_id INTEGER NOT NULL,
	turno VARCHAR(250) NOT NULL,
	PRIMARY KEY (id_estudiante,dni)
);
ALTER TABLE estudiante
	ADD FOREIGN KEY (escuela_id)
	REFERENCES escuela (id_escuela);
ALTER TABLE estudiante
	ADD FOREIGN KEY (sede_id)
	REFERENCES sede (id_sede);

CREATE TABLE aula_estudiante (
	id INTEGER NOT NULL AUTO_INCREMENT,
	turno VARCHAR(250) NOT NULL,
	estudiante_id INTEGER NOT NULL,
	aula_id INTEGER NOT NULL,
	PRIMARY KEY (id)
);
ALTER TABLE aula_estudiante
	ADD FOREIGN KEY (estudiante_id)
	REFERENCES estudiante (id_estudiante);
ALTER TABLE aula_estudiante
	ADD FOREIGN KEY (aula_id)
	REFERENCES aula (id);

CREATE TABLE huella (
	id INTEGER NOT NULL AUTO_INCREMENT,
	huella LONGVARBINARY NOT NULL,
	estudiante_id INTEGER NOT NULL,
	PRIMARY KEY (id)
);
ALTER TABLE huella
	ADD FOREIGN KEY (estudiante_id)
	REFERENCES estudiante (id_estudiante);

CREATE TABLE aula_examen (
	id INTEGER NOT NULL AUTO_INCREMENT,
	estudiante_id INTEGER NOT NULL,
	aulae_id INTEGER NOT NULL,
	examen_id INTEGER NOT NULL,
	PRIMARY KEY (id)
);
ALTER TABLE aula_examen
	ADD FOREIGN KEY (examen_id)
	REFERENCES examen (id);
ALTER TABLE aula_examen
	ADD FOREIGN KEY (estudiante_id)
	REFERENCES estudiante (id_estudiante);
ALTER TABLE aula_examen
	ADD FOREIGN KEY (aulae_id)
	REFERENCES aula_e (id);

CREATE TABLE asistencia_clase (
	id INTEGER NOT NULL AUTO_INCREMENT,
	fecha DATE NOT NULL,
	aula_estudio_id INTEGER NOT NULL,
	PRIMARY KEY (id)
);
ALTER TABLE asistencia_clase
	ADD FOREIGN KEY (aula_estudio_id)
	REFERENCES aula_estudiante (id);


CREATE TABLE asistencia_examen (
	id INTEGER NOT NULL AUTO_INCREMENT,
	fecha DATE NOT NULL,
	aula_examen_id INTEGER NOT NULL,
	examen_id INTEGER NOT NULL,
	PRIMARY KEY (id)
);
ALTER TABLE asistencia_examen
	ADD FOREIGN KEY (examen_id)
	REFERENCES examen (id);
ALTER TABLE asistencia_examen
	ADD FOREIGN KEY (aula_examen_id)
	REFERENCES aula_examen (id);


CREATE TABLE persona (
	id INTEGER NOT NULL AUTO_INCREMENT,
	documento_identidad VARCHAR(30),
	apellido_paterno VARCHAR(100),
	apellido_materno VARCHAR(100) NOT NULL,
	nombres VARCHAR(60) NOT NULL,
	direccion VARCHAR(200) NOT NULL,
	telefono VARCHAR(30) NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE rol (
	id INTEGER NOT NULL AUTO_INCREMENT,
	rol VARCHAR(50) NOT NULL,
	descripcion VARCHAR(255),
	PRIMARY KEY (id)
);


CREATE TABLE rol_permiso (
	rol_id INTEGER NOT NULL,
	permiso VARCHAR(50) NOT NULL,
	PRIMARY KEY (rol_id,permiso)
);
ALTER TABLE rol_permiso
	ADD FOREIGN KEY (rol_id)
	REFERENCES rol (id);

CREATE TABLE usuario (
	id INTEGER NOT NULL AUTO_INCREMENT,
	usuario VARCHAR(100) NOT NULL,
	passphrase VARCHAR(100) NOT NULL,
	huella LONGVARBINARY,
	salt VARCHAR(100) NOT NULL,
	persona_id INTEGER NOT NULL,
	PRIMARY KEY (id)
);
ALTER TABLE usuario
	ADD FOREIGN KEY (persona_id)
	REFERENCES persona (id);

CREATE TABLE usuario_rol (
	id INTEGER NOT NULL AUTO_INCREMENT,
	usuario_id INTEGER NOT NULL,
	rol_id INTEGER NOT NULL,
	PRIMARY KEY (id)
);
ALTER TABLE usuario_rol
	ADD FOREIGN KEY (usuario_id)
	REFERENCES usuario (id);

ALTER TABLE usuario_rol
	ADD FOREIGN KEY (rol_id)
	REFERENCES rol (id);

