/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kardex.modelo;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Carlitos
 */
public class KardexVenta {
    private int numero_factura;
    private List<ItemVenta> listaItemsVenta = new ArrayList<ItemVenta>();
    private String contenido;

    public int getNumero_factura() {
        return numero_factura;
    }

    public void setNumero_factura(int numero_factura) {
        this.numero_factura = numero_factura;
    }

    public List<ItemVenta> getListaItemsVenta() {
        return listaItemsVenta;
    }

    public void setListaItemsVenta(List<ItemVenta> listaItemsVenta) {
        this.listaItemsVenta = listaItemsVenta;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
 
}
