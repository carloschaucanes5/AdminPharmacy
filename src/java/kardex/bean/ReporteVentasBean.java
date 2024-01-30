/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kardex.bean;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import kardex.dao.EmpleadoDao;
import kardex.modelo.Empleado;
import kardex.dao.ReporteVentaDao;
import kardex.modelo.ReporteFacturaDetalle;
import kardex.modelo.ReporteFacturaGeneral;

/**
 *
 * @author Carlitos
 */
@ManagedBean
@ViewScoped
public class ReporteVentasBean {

    /**
     * Creates a new instance of ReporteVentas
     */
    private Empleado empleado = new Empleado();
    private String fechaInicio;
    private String fechaFinal;
    private int tipoTransaccion;
    private int tipoReporte;
    private List<ReporteFacturaDetalle> listaFacturaDetalle = new ArrayList<ReporteFacturaDetalle>();
    private List<ReporteFacturaGeneral> listafacturaGeneral = new ArrayList<ReporteFacturaGeneral>();
    private List<Empleado> listaEmpleados = new ArrayList<Empleado>();
    private String displayTablaGeneral;
    private String displayTablaDetalle;
    private double totalCosto;
    private double totalPrecio;
    private double ganancia;
    

    public ReporteVentasBean()
    {
        this.displayTablaDetalle = "none";
        this.displayTablaGeneral = "none";
        this.totalCosto = 0;
        this.totalPrecio = 0;
        this.ganancia = 0;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public int getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(int tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public int getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(int tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public List<ReporteFacturaDetalle> getListaFacturaDetalle() {
        return listaFacturaDetalle;
    }

    public void setListaFacturaDetalle(List<ReporteFacturaDetalle> listaFacturaDetalle) {
        this.listaFacturaDetalle = listaFacturaDetalle;
    }

    public List<ReporteFacturaGeneral> getListafacturaGeneral() {
        return listafacturaGeneral;
    }

    public void setListafacturaGeneral(List<ReporteFacturaGeneral> listafacturaGeneral) {
        this.listafacturaGeneral = listafacturaGeneral;
    }

    public List<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    public void setListaEmpleados(List<Empleado> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    public String getDisplayTablaGeneral() {
        return displayTablaGeneral;
    }

    public void setDisplayTablaGeneral(String displayTablaGeneral) {
        this.displayTablaGeneral = displayTablaGeneral;
    }

    public String getDisplayTablaDetalle() {
        return displayTablaDetalle;
    }

    public void setDisplayTablaDetalle(String displayTablaDetalle) {
        this.displayTablaDetalle = displayTablaDetalle;
    }

    public double getTotalCosto() {
        return totalCosto;
    }

    public void setTotalCosto(double totalCosto) {
        this.totalCosto = totalCosto;
    }

    public double getTotalPrecio() {
        return totalPrecio;
    }

    public void setTotalPrecio(double totalPrecio) {
        this.totalPrecio = totalPrecio;
    }

    public double getGanancia() {
        return ganancia;
    }

    public void setGanancia(double ganancia) {
        this.ganancia = ganancia;
    }

   private boolean isPostBack()
    {
        boolean res;
        res = FacesContext.getCurrentInstance().isPostback();
        return res;
    }
    
    /**
     *
     * @param ajax
     * @throws Exception
     */
    @PostConstruct
    public void listarEmpleados()
    {  
        try
        {
            EmpleadoDao dao = new EmpleadoDao();
            this.listaEmpleados = dao.getListaEmpleados();
        }
        catch(Exception e)
        {
            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Info:",""+e));
            
        }
                
    }
    
    public void generarReporte() throws Exception
    {
        try
        {
            if(this.tipoReporte == 1)
            {
                ReporteVentaDao dao = new ReporteVentaDao();
                dao.generarReporteGeneral(this.empleado,this.tipoTransaccion,this.fechaInicio,fechaFinal);
                this.listafacturaGeneral = dao.getListaFacturaGeneralD();
                this.displayTablaGeneral = "show";
                this.displayTablaDetalle = "none";
                this.totalCosto = dao.getTotal_costo();
                this.totalPrecio = dao.getTotal_precio();
                this.ganancia = dao.getTotal_ganancia();
                //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Info:","General"));
            }
            else
            {
                ReporteVentaDao dao = new ReporteVentaDao();
                dao.generarReporteDetalle(this.empleado, this.tipoTransaccion, this.fechaInicio, this.fechaFinal);
                this.listaFacturaDetalle = dao.getListaFacturaDetalleD();
                this.displayTablaGeneral = "none";
                this.displayTablaDetalle = "show";
                this.totalCosto = dao.getTotal_costo();
                this.totalPrecio = dao.getTotal_precio();
                this.ganancia = dao.getTotal_ganancia();
                //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Info:","Detallado"));
            }
        }catch(Exception err)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Info:",""+err));
        }finally
        {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }
}
