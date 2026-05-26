package com.example.autenticacion.model;

// enum define un conjunto fijo de valores posibles
// en vez de guardar texto libre como "admin" o "ADMIN", se usan estos valores controlados
public enum RolUsuario {
    ATLETA,        // usuario cliente de la app
    TRABAJADOR,    // staff operativo de la sede
    ADMINISTRADOR  // control total del sistema
}
