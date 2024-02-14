/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kardex.modelo;

/**
 *
 * @author HP
 */
 //inv.cod_producto,inv.nombre_producto,inv.concentracion,inv.presentacion,inv.existencias,inv.codigo_barras,
 //inv.categoria,inv.laboratorio,
//ke.fecha_vencimiento,ke.cantidad,ke.total_costo,ke.total_precio,ke.iva
public class ConsultaProducto {
    private long cod_producto = 0;
    private String nombre_producto = "";
    private String concentracion = "";
    private String presentacion = "";
    private int existencias = 0;
    private String codigo_barras = "";
    private String categoria = "";
    private String laboratorio = "";
    private String fecha_vencimiento = "";
    private int cantidad = 0;
    private double total_costo = 0.0;
    private double total_precio = 0.0;
    private double iva = 0.0;
    private long cod_entrada=0;
    private String detalle = "";
    
    public ConsultaProducto() {
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    
    
    public long getCod_entrada() {
        return cod_entrada;
    }

    public void setCod_entrada(long cod_entrada) {
        this.cod_entrada = cod_entrada;
    }

    public long getCod_producto() {
        return cod_producto;
    }

    public void setCod_producto(long cod_producto) {
        this.cod_producto = cod_producto;
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

    public int getExistencias() {
        return existencias;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }

    public String getCodigo_barras() {
        return codigo_barras;
    }

    public void setCodigo_barras(String codigo_barras) {
        this.codigo_barras = codigo_barras;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(String fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotal_costo() {
        return total_costo;
    }

    public void setTotal_costo(double total_costo) {
        this.total_costo = total_costo;
    }

    public double getTotal_precio() {
        return total_precio;
    }

    public void setTotal_precio(double total_precio) {
        this.total_precio = total_precio;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }
    
    
}
