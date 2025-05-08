CREATE TABLE cita (
    id SERIAL PRIMARY KEY,
    fecha_hora TIMESTAMP NOT NULL,
    motivo TEXT,
    estado VARCHAR(20) DEFAULT 'PENDIENTE',
    paciente_id INTEGER NOT NULL,
    medico_id INTEGER NOT NULL,
    FOREIGN KEY (paciente_id) REFERENCES paciente(id),
    FOREIGN KEY (medico_id) REFERENCES medico(id)
);
