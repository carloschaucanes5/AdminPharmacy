/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kardex.bean;

import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import kardex.dao.ReporteSalidaDao;
import kardex.modelo.ReporteSalida;


/**
 *
 * @author Carlitos
 *//**
 *
 * @author Carlitos
 */
@ManagedBean
@ViewScoped
public class ReporteSalidaBean {

    /**
     * Creates a new instance of ReporteSalidaBean
     */
private String fechaInicial;
private String fechaFinal;
private String numeroFactura;
    
private List<ReporteSalida> listaSalidas = new ArrayList<>();
private double total_costo;
private double total_precio;
private double utilidad;

    public String getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(String fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public List<ReporteSalida> getListaSalidas() {
        return listaSalidas;
    }

    public void setListaSalidas(List<ReporteSalida> listaSalidas) {
        this.listaSalidas = listaSalidas;
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

    public double getUtilidad() {
        return utilidad;
    }

    public void setUtilidad(double utilidad) {
        this.utilidad = utilidad;
    }

     public void generarReporteSalidas()
    {
        try
        {
            ReporteSalidaDao dao = new ReporteSalidaDao();
            this.listaSalidas = dao.getListaSalidas(fechaInicial.trim(), fechaFinal.trim());
            this.total_costo = dao.getTotalCosto();
            this.total_precio = dao.getTotalPrecio();
            this.utilidad = total_precio - total_costo;

        }
        catch(Exception err)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Fatal:",""+err));
        }
    }



}
