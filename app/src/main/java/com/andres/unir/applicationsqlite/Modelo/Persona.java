package com.andres.unir.applicationsqlite.Modelo;

/**
 * Created by INNSO SAS on 07/05/2015.
 */
public class Persona {

    private String Nombre;
    private String DNI;

    public Persona(String nombre, String dni){
        Nombre = nombre;
        DNI = dni;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getDNI() {
        return DNI;
    }
}
