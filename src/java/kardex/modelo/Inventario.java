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
  cod_producto integer NOT NULL DEFAULT nextval('inventario1_cod_producto_seq'::regclass),
  ombren character varying(100) NOT NULL,
  concentracion character varying(50),
  presentacion character varying(50),
  nit_proveedor character varying(15),
  cod_laboratorio integer,
  iva double precision NOT NULL,
  costo_unitario double precision NOT NULL,
  precio_unitario double precision NOT NULL,
  cantidad_disponoble integer,
*/
public class Inventario {
    private int cod_producto;
    private String nombre;
    private String concentracion;
    private String presentacion;
    private double iva;
    private double costo_unitario;
    private double precio_unitario;
    private String estado;
    private int existencias;
    private String codigo_barras;
    public int getCod_producto() {
        return cod_producto;
    }

    public void setCod_producto(int cod_producto) {
        this.cod_producto = cod_producto;
    }

    public String getCodigo_barras() {
        return codigo_barras;
    }

    public void setCodigo_barras(String codigo_barras) {
        this.codigo_barras = codigo_barras;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getCosto_unitario() {
        return costo_unitario;
    }

    public void setCosto_unitario(double costo_unitario) {
        this.costo_unitario = costo_unitario;
    }

    public double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }    

    public int getExistencias() {
        return existencias;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }
       
    
}
