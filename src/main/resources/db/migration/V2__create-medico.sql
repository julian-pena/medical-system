-- Tabla: medico
CREATE TABLE medico (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    registro_profesional VARCHAR(50) NOT NULL UNIQUE,
    especialidad_id INTEGER NOT NULL,
    telefono VARCHAR(20),
    correo VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    consultorio VARCHAR(100),
    activo BOOLEAN DEFAULT TRUE, -- El médico está activo por defecto
    FOREIGN KEY (especialidad_id) REFERENCES especialidad(id)
);
