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
public class Bono {

    private int cod_abono;
    private String cedula_cliente;
    private double saldo_abono;
    private String fecha;
    private String estado;
    private String hora;

    public int getCod_abono() {
        return cod_abono;
    }

    public void setCod_abono(int cod_abono) {
        this.cod_abono = cod_abono;
    }

    public String getCedula_cliente() {
        return cedula_cliente;
    }

    public void setCedula_cliente(String cedula_cliente) {
        this.cedula_cliente = cedula_cliente;
    }

    public double getSaldo_abono() {
        return saldo_abono;
    }

    public void setSaldo_abono(double saldo_abono) {
        this.saldo_abono = saldo_abono;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
    
    
}
