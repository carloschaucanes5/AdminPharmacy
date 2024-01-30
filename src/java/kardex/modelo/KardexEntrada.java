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
public class KardexEntrada {
   private int cod_entrada;
   private int cod_tipo_transaccion;
   private Inventario inventario = new Inventario();
   private String fecha_transaccion;
   private String hora_transaccion;
   private String fecha_vencimiento;
   private int cantidad;
   private Empleado empleado = new Empleado();
   private String detalle;
   private double total_costo;
   private double total_precio;
   private String numero_factura;
   private Laboratorio laboratorio = new Laboratorio();
   private Proveedor proveedor = new Proveedor(); 

    public int getCod_entrada() {
        return cod_entrada;
    }

    public void setCod_entrada(int cod_entrada) {
        this.cod_entrada = cod_entrada;
    }

    public int getCod_tipo_transaccion() {
        return cod_tipo_transaccion;
    }

    public void setCod_tipo_transaccion(int cod_tipo_transaccion) {
        this.cod_tipo_transaccion = cod_tipo_transaccion;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public String getFecha_transaccion() {
        return fecha_transaccion;
    }

    public void setFecha_transaccion(String fecha_transaccion) {
        this.fecha_transaccion = fecha_transaccion;
    }

    public String getHora_transaccion() {
        return hora_transaccion;
    }

    public void setHora_transaccion(String hora_transaccion) {
        this.hora_transaccion = hora_transaccion;
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

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
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

    public String getNumero_factura() {
        return numero_factura;
    }

    public void setNumero_factura(String numero_factura) {
        this.numero_factura = numero_factura;
    }

    public Laboratorio getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(Laboratorio laboratorio) {
        this.laboratorio = laboratorio;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
    
   
   
   
}
