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

/*
cod_devolucion serial NOT NULL,
  numero_factura integer,
  fecha_devolucion date,
  hora_devolucion time without time zone,
*/
public class KardexDevolucion {
    private int numero_factura;
    private String fecha_devolucion;
    private String hora_devolucion;
    private String cedula_empleado;
    private List<ItemDevolucion> listaItemsDevolucion = new ArrayList<ItemDevolucion>();

    public int getNumero_factura() {
        return numero_factura;
    }

    public void setNumero_factura(int numero_factura) {
        this.numero_factura = numero_factura;
    }

    public String getFecha_devolucion() {
        return fecha_devolucion;
    }

    public void setFecha_devolucion(String fecha_devolucion) {
        this.fecha_devolucion = fecha_devolucion;
    }

    public String getHora_devolucion() {
        return hora_devolucion;
    }

    public void setHora_devolucion(String hora_devolucion) {
        this.hora_devolucion = hora_devolucion;
    }

    public String getCedula_empleado() {
        return cedula_empleado;
    }

    public void setCedula_empleado(String cedula_empleado) {
        this.cedula_empleado = cedula_empleado;
    }

    public List<ItemDevolucion> getListaItemsDevolucion() {
        return listaItemsDevolucion;
    }

    public void setListaItemsDevolucion(List<ItemDevolucion> listaItemsDevolucion) {
        this.listaItemsDevolucion = listaItemsDevolucion;
    }
}
