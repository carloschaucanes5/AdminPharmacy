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
import kardex.dao.KardexSalidaDao;
import kardex.modelo.Empleado;
import kardex.modelo.Inventario;
import kardex.modelo.KardexSalida;

/**
 *
 * @author Carlitos
 */
@ManagedBean
@ViewScoped
public class KardexSalidaBean {

    /**
     * Creates a new instance of KardexSalidaBean
     */
    private KardexSalida kardexSalida = new KardexSalida();
    private String cadenaNombre;
    private List<Inventario> listaInventario = new ArrayList<Inventario>();
    private Inventario inventario = new Inventario();
    private int cantidad;
    private String detalle;

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    
    
    public String getCadenaNombre() {
        return cadenaNombre;
    }

    public void setCadenaNombre(String cadenaNombre) {
        this.cadenaNombre = cadenaNombre;
    }

    public List<Inventario> getListaInventario() {
        return listaInventario;
    }

    public void setListaInventario(List<Inventario> listaInventario) {
        this.listaInventario = listaInventario;
    }

    public KardexSalida getKardexSalida() {
        return kardexSalida;
    }

    public void setKardexSalida(KardexSalida kardexSalida) {
        this.kardexSalida = kardexSalida;
    }

    public void registrarSalida(Empleado empleado) 
    {
        try
        {
            if(this.cantidad <=this.inventario.getExistencias())
            {
                if(this.cantidad > 0)
                {
                    double costoTotal = this.cantidad * this.inventario.getCosto_unitario();
                    double precioTotal = this.cantidad * this.inventario.getPrecio_unitario();
                    this.kardexSalida.setTotal_costo(costoTotal);
                    this.kardexSalida.setTotal_precio(precioTotal);
                    this.kardexSalida.setInventario(inventario);
                    this.kardexSalida.setCantidad(cantidad);
                    this.kardexSalida.setDetalle(detalle);
                    this.kardexSalida.setEmpleado(empleado);
                    KardexSalidaDao dao = new KardexSalidaDao();
                    dao.registrarSalida(this.kardexSalida);
                    this.buscarProducto();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Aviso",this.kardexSalida.getCantidad()+" item(s) sale(n) de inventario"));
                    this.cantidad = 0;
                    this.detalle = "";
                }
                else
                {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Error","La cantidad ingresada es incorrecta"));
                }    
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Error","La cantidad ingresada no sebe exceder a las existentes"));
            }
        }catch(Exception err)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Error",""+err));
        }finally
        {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }
     
    public void buscarProducto() throws Exception
    {
        try
        {
            KardexSalidaDao dao = new KardexSalidaDao();
            this.listaInventario = dao.getListarNombresProductos(this.cadenaNombre);
            if(this.listaInventario.size() <= 0)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Aviso","cero resultados"));
            }
        }catch(Exception err)
        {
            throw err;
        }
    }
    
   public void leerIdKardexSalida(Inventario inventario)
    {     
        this.inventario = inventario;
    }
    
    
}
