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
public class Recibo {
    private String variable;
    private int cantidad;
    private double total;
    private String modo_venta;

    public String getModo_venta() {
        return modo_venta;
    }

    public void setModo_venta(String modo_venta) {
        this.modo_venta = modo_venta;
    }
    
    
    
    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }


}
