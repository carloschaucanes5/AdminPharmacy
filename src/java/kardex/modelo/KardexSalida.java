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

*/
public class KardexSalida {
  private int cod_salida;
  private int cod_tipo_transaccion;
  private Inventario inventario;
  private String fecha_salid;
  private String hora_salida;
  private int cantidad;
  private Empleado empleado;
  private String detalle;
  private double total_costo;
  private double total_precio;

    public int getCod_salida() {
        return cod_salida;
    }

    public void setCod_salida(int cod_salida) {
        this.cod_salida = cod_salida;
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

    public String getFecha_salid() {
        return fecha_salid;
    }

    public void setFecha_salid(String fecha_salid) {
        this.fecha_salid = fecha_salid;
    }

    public String getHora_salida() {
        return hora_salida;
    }

    public void setHora_salida(String hora_salida) {
        this.hora_salida = hora_salida;
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
}
