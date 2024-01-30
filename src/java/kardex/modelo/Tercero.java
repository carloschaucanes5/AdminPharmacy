/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kardex.modelo;

/**
 *
 * @author Carlitos
 */

/*
cedula_tercero character varying(15) NOT NULL,
  nombres character(100),
  apellidos character(100),
  direccion character varying(20),
  telefono character varying(15),
*/
public class Tercero {
    private String cedula_tercero;
    private String nombres;
    private String apellidos;
    private String direccion;
    private String telefono;

    public String getCedula_tercero() {
        return cedula_tercero;
    }

    public void setCedula_tercero(String cedula_tercero) {
        this.cedula_tercero = cedula_tercero;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
}
