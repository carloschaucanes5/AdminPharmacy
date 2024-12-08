/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kardex.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP
 */
public class Saldo {
    public double total;
    public double subtotal;
    public double cambio;
    public List<MetodoPago> metodosPago = new ArrayList<MetodoPago>();
    public double faltante;
    public double sobrante;
    public int mil;
    public int dosmil;
    public int cincomil;
    public int diezmil;
    public int veintemil;
    public int cincuentamil;
    public int cienmil;

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

   
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getCambio() {
        return cambio;
    }

    public void setCambio(double cambio) {
        this.cambio = cambio;
    }



    public List<MetodoPago> getMetodosPago() {
        return metodosPago;
    }

    public void setMetodosPago(List<MetodoPago> metodosPago) {
        this.metodosPago = metodosPago;
    }

    public double getFaltante() {
        return faltante;
    }


    public void setFaltante(double faltante) {
        this.faltante = faltante;
    }

    public double getSobrante() {
        return sobrante;
    }

    public void setSobrante(double sobrante) {
        this.sobrante = sobrante;
    }

    public int getMil() {
        return mil;
    }

    public void setMil(int mil) {
        this.mil = mil;
    }

    public int getDosmil() {
        return dosmil;
    }

    public void setDosmil(int dosmil) {
        this.dosmil = dosmil;
    }

    public int getCincomil() {
        return cincomil;
    }

    public void setCincomil(int cincomil) {
        this.cincomil = cincomil;
    }

    public int getDiezmil() {
        return diezmil;
    }

    public void setDiezmil(int diezmil) {
        this.diezmil = diezmil;
    }

    public int getVeintemil() {
        return veintemil;
    }

    public void setVeintemil(int veintemil) {
        this.veintemil = veintemil;
    }

    public int getCincuentamil() {
        return cincuentamil;
    }

    public void setCincuentamil(int cincuentamil) {
        this.cincuentamil = cincuentamil;
    }

    public int getCienmil() {
        return cienmil;
    }

    public void setCienmil(int cienmil) {
        this.cienmil = cienmil;
    }
    
    
}
