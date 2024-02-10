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
public class ReporteVencimiento {
   private String fecha_vencimiento;
   private int cod_entrada;
   private String nombre_producto;
   private String concentracion;
   private String presentacion;
   private String laboratorio;
   private String categoria;
   private int cantidad;

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

   
   
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
   
   
   
    public String getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(String fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public int getCod_entrada() {
        return cod_entrada;
    }

    public void setCod_entrada(int cod_entrada) {
        this.cod_entrada = cod_entrada;
    }



    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public String getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(String concentracion) {
        this.concentracion = concentracion;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }


   
   
    
}
