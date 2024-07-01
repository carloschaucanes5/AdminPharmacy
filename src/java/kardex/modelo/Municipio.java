/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kardex.modelo;

/**
 *
 * @author HP
 */
public class Municipio {
    public int municipality_id;
    public String departamento;
    public String municipio;
    public String titulo; 

    public int getMunicipality_id() {
        return municipality_id;
    }

    public void setMunicipality_id(int municipality_id) {
        this.municipality_id = municipality_id;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }



    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    
}
