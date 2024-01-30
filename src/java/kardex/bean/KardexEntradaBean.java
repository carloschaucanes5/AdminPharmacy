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
import kardex.dao.KardexEntradaDao;
import kardex.modelo.Empleado;
import kardex.modelo.Inventario;
import kardex.modelo.KardexEntrada;
import kardex.modelo.Laboratorio;
import kardex.modelo.Proveedor;


/**
 *
 * @author Carlitos
 */
@ManagedBean
@ViewScoped
public class KardexEntradaBean {
    private KardexEntrada kardexEntrada = new KardexEntrada();
    private String cadenaNombre;
    private List<Inventario> listaInventario = new ArrayList<>();
    private List<Proveedor> listaProveedores = new ArrayList<>();
    private List<Laboratorio> listaLaboratorios = new ArrayList<>();

    public KardexEntradaBean() {
    }
    
    
    
    
    public KardexEntrada getKardexEntrada() {
        return kardexEntrada;
    }

    public void setKardexEntrada(KardexEntrada kardexEntrada) {
        this.kardexEntrada = kardexEntrada;
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

    public List<Proveedor> getListaProveedores() {
        return listaProveedores;
    }

    public void setListaProveedores(List<Proveedor> listaProveedores) {
        this.listaProveedores = listaProveedores;
    }

    public List<Laboratorio> getListaLaboratorios() {
        return listaLaboratorios;
    }

    public void setListaLaboratorios(List<Laboratorio> listaLaboratorios) {
        this.listaLaboratorios = listaLaboratorios;
    }
     
    public void buscarProducto() throws Exception
    {
        try
        {
            KardexEntradaDao dao = new KardexEntradaDao();
            this.listaInventario = dao.getListarNombresProductos(this.cadenaNombre);
        }catch(Exception err)
        {
            throw err;
        }
    }
    public void leerIdKardexEntrada(Inventario inventario) throws Exception
    {     
        this.kardexEntrada.setInventario(inventario);
    }
   
   @PostConstruct 
   private void cargarDatos()
   {
       listarProveedores();
       listarLaboratorios();
   }

    public void listarProveedores() 
    {
        try
        {
           KardexEntradaDao dao = new KardexEntradaDao();
           this.listaProveedores = dao.getListaProveedores();
        }
        catch(Exception e)
        {
            
        }
    }
    

    public void listarLaboratorios() 
    {
        try
        {
            KardexEntradaDao dao = new KardexEntradaDao();
            this.listaLaboratorios = dao.getListaLaboratorios();
        }
        catch(Exception e)
        {
        }
    }
    
    public String registrarEntrada(Empleado empleado) throws Exception
    {
        try
        {
            if(empleado != null)
            {
                double costoTotal = this.kardexEntrada.getCantidad() * this.kardexEntrada.getInventario().getCosto_unitario();
                double precioTotal = this.kardexEntrada.getCantidad() * this.kardexEntrada.getInventario().getPrecio_unitario();
                this.kardexEntrada.setTotal_costo(costoTotal);
                this.kardexEntrada.setTotal_precio(precioTotal);
                this.kardexEntrada.setEmpleado(empleado);
                KardexEntradaDao dao = new KardexEntradaDao();
                dao.registrarEntrada(this.kardexEntrada);
                this.buscarProducto();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Aviso","Entrada almacenada con éxito"));
                return "kardex_entrada?faces-redirect=true";
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","No se ha iniciado sesión"));
            }
            }catch(Exception err)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Aviso","Error:"+err));
        }finally
        {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
        return "";
    }
}