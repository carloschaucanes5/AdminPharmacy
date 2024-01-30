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
public class ItemVenta {

  private int cod_salida;
  private int numero_factura;
  private int cod_tipo_transaccion; 
  private Inventario inventario; 
  private int cantidad; 
  private double total_costo;
  private double total_precio;

    public int getCod_salida() {
        return cod_salida;
    }

    public void setCod_salida(int cod_salida) {
        this.cod_salida = cod_salida;
    }

    public int getNumero_factura() {
        return numero_factura;
    }

    public void setNumero_factura(int numero_factura) {
        this.numero_factura = numero_factura;
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
