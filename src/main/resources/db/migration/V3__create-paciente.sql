-- Tabla: paciente
CREATE TABLE paciente (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    documento VARCHAR(50) NOT NULL UNIQUE,
    fecha_nacimiento DATE NOT NULL,  -- Debería ser DATE en lugar de STRING
    telefono VARCHAR(20),
    correo VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    activo BOOLEAN DEFAULT TRUE  -- El paciente está activo por defecto
);
