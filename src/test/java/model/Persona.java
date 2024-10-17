package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Persona {
    private String rut;
    private String nombres;
    private String primerApellido;
    private String segundoApellido;
    private byte edad;
    private LocalDate fechaNacimiento;

    public Persona() {

    }

    public Persona(String rut, String nombres, String primerApellido, String segundoApellido, byte edad, LocalDate fechaNacimiento) {
        this.rut = rut;
        this.nombres = nombres;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.edad = edad;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public byte getEdad() {
        return edad;
    }

    public void setEdad(byte edad) {
        this.edad = edad;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String toString() {
        final String textoResumido = "Persona[rut = '%s', nombres = '%s', primerApellido = '%s', segundoApellido = '%s', edad = '%d', fechaNacimiento = '%s']";
        String fechaNacimientoFormateada = fechaNacimiento.format(DateTimeFormatter.ofPattern("dd/MM/uuuu"));
        return String.format(textoResumido, rut, nombres, primerApellido, segundoApellido, edad, fechaNacimientoFormateada);
    }
}
