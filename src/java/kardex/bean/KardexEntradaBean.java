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
    private String numeroFactura;
    private double precioSugerido = 0;
    private Proveedor proveedorFactura;
    private List<Inventario> listaInventario = new ArrayList<>();
    private List<Proveedor> listaProveedores = new ArrayList<>();
    private List<Laboratorio> listaLaboratorios = new ArrayList<>();

    public Proveedor getProveedorFactura() {
        return proveedorFactura;
    }

    public void setProveedorFactura(Proveedor proveedorFactura) {
        this.proveedorFactura = proveedorFactura;
    }

    
    
    public KardexEntradaBean() {
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
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
            this.cadenaNombre = "";
        }catch(Exception err)
        {
            throw err;
        }
    }
    public void leerIdKardexEntrada(Inventario inventario) throws Exception
    {   
        this.kardexEntrada.setNumero_factura(this.numeroFactura);
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
    
    public String registrarEntrada(Empleado empleado)
    {
        System.out.print(this.kardexEntrada.getFecha_vencimiento().toString());
        /*
        try
        {
            if(empleado != null)
            {
                this.kardexEntrada.setNumero_factura(this.numeroFactura);
                this.kardexEntrada.setProveedor(this.proveedorFactura);
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
        }*/
        return "";
    }
    
    public double calcularPrecioSugerido(double iva,double cantidad,double costoUnitario){
        double porcentajePrecio = 0;
        double totalDivicionPorcentaje = 0;
        double valorPrecioSugeridoUnitario = 0;
        double valorTotal = 0;
        try{
            KardexEntradaDao dao = new KardexEntradaDao();
            porcentajePrecio = dao.getPorcentajePrecio()/100;
            valorTotal = costoUnitario * cantidad;
            if(iva == 0){
                totalDivicionPorcentaje = valorTotal/porcentajePrecio;
                valorPrecioSugeridoUnitario = totalDivicionPorcentaje/cantidad;
            }else{
                double valorIva = ((iva * valorTotal)/100);
                double valorConIva = valorTotal + valorIva;
                totalDivicionPorcentaje = valorConIva /porcentajePrecio;
                valorPrecioSugeridoUnitario = totalDivicionPorcentaje/cantidad;
            }
            return Math.round(valorPrecioSugeridoUnitario);
        }catch(Exception err){
            System.out.println(err);
        }finally{
        }
        return 0;
    }
    public void calcularPrecio(){
        double iva = kardexEntrada.getIva();
        int cantidad = kardexEntrada.getCantidad();
        double  costo = kardexEntrada.getTotal_costo();
        this.kardexEntrada.setTotal_precio(this.calcularPrecioSugerido(iva,cantidad,costo));
        System.out.println("ESte es el precio =>>"+this.kardexEntrada.getTotal_precio());
    }
}