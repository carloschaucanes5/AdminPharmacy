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




public class ItemDevolucion {
  private int cod_devolucion;
  private long cod_producto;
  private int cantidad;
  private double  total_costo;
  private double total_precio;
  private long cod_entrada;
  private double precio_unitario;

    public long getCod_producto() {
        return cod_producto;
    }

    public void setCod_producto(long cod_producto) {
        this.cod_producto = cod_producto;
    }

  
  
    public double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }
  
  
  
    public long getCod_entrada() {
        return cod_entrada;
    }

    public void setCod_entrada(long cod_entrada) {
        this.cod_entrada = cod_entrada;
    }
  
  
    public int getCod_devolucion() {
        return cod_devolucion;
    }

    public void setCod_devolucion(int cod_devolucion) {
        this.cod_devolucion = cod_devolucion;
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
  
  
  
}
