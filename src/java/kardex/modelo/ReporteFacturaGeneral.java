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
public class ReporteFacturaGeneral {
  private int numero_factura;
  private String fecha_factura;
  private String hora_factura;
  private double suma_costo;
  private double suma_precio; 

    public int getNumero_factura() {
        return numero_factura;
    }

    public void setNumero_factura(int numero_factura) {
        this.numero_factura = numero_factura;
    }

    public String getFecha_factura() {
        return fecha_factura;
    }

    public void setFecha_factura(String fecha_factura) {
        this.fecha_factura = fecha_factura;
    }

    public String getHora_factura() {
        return hora_factura;
    }

    public void setHora_factura(String hora_factura) {
        this.hora_factura = hora_factura;
    }

    public double getSuma_costo() {
        return suma_costo;
    }

    public void setSuma_costo(double suma_costo) {
        this.suma_costo = suma_costo;
    }

    public double getSuma_precio() {
        return suma_precio;
    }

    public void setSuma_precio(double suma_precio) {
        this.suma_precio = suma_precio;
    }
}
