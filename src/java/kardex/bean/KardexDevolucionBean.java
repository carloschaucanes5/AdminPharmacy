/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kardex.bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import kardex.dao.KardexDevolucionDao;
import kardex.modelo.Empleado;
import kardex.modelo.Inventario;
import kardex.modelo.ItemDevolucion;
import kardex.modelo.ItemVenta;
import kardex.modelo.KardexDevolucion;

/**
 *
 * @author Carlitos
 */
@ManagedBean
@ViewScoped
public class KardexDevolucionBean {

    /**
     * Creates a new instance of KardexDevolucionBean
     */
    private KardexDevolucion kardeDevolucion = new KardexDevolucion();
    private Empleado empleado = new Empleado();
    private int facturaBuscar;
    private List<ItemVenta> listaItemsVenta = new ArrayList<ItemVenta>();
    private ItemVenta itemVenta = new ItemVenta();
    private int cantidadRetornada;
    private double saldoDevolucion;
    private double saldoTotalVenta;

    public double getSaldoTotalVenta() {
        return saldoTotalVenta;
    }

    public void setSaldoTotalVenta(double saldoTotalVenta) {
        this.saldoTotalVenta = saldoTotalVenta;
    }
  
    public double getSaldoDevolucion() {
        return saldoDevolucion;
    }

    public void setSaldoDevolucion(double saldoDevolucion) {
        this.saldoDevolucion = saldoDevolucion;
    }

    
    
    public int getCantidadRetornada() {
        return cantidadRetornada;
    }

    public void setCantidadRetornada(int cantidadRetornada) {
        this.cantidadRetornada = cantidadRetornada;
    }
    
    public ItemVenta getItemVenta() {
        return itemVenta;
    }

    public void setItemVenta(ItemVenta itemVenta) {
        this.itemVenta = itemVenta;
    }
   
    
    public List<ItemVenta> getListaItemsVenta() {
        return listaItemsVenta;
    }

    public void setListaItemsVenta(List<ItemVenta> listaItemsVenta) {
        this.listaItemsVenta = listaItemsVenta;
    }

    public int getFacturaBuscar() {
        return facturaBuscar;
    }

    public void setFacturaBuscar(int facturaBuscar) {
        this.facturaBuscar = facturaBuscar;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    
    public KardexDevolucion getKardeDevolucion() {
        return kardeDevolucion;
    }

    public void setKardeDevolucion(KardexDevolucion kardeDevolucion) {
        this.kardeDevolucion = kardeDevolucion;
    }
    
    
    public void leerIdItemVenta(ItemVenta itemVenta)
    {
        this.setItemVenta(itemVenta);
    }
    
    
    public String registrarDevolucion(Empleado empleado)
    {
        try
        {
            if(this.kardeDevolucion.getListaItemsDevolucion().size()!=0)
            {
                this.kardeDevolucion.setNumero_factura(facturaBuscar);
                this.kardeDevolucion.setCedula_empleado(empleado.getCedula_empleado());
                KardexDevolucionDao dao = new KardexDevolucionDao();
                dao.registrarDevolucion(kardeDevolucion, empleado);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"KardexKdd","Devolucion realizada con éxito"));
                return "kardex_devolucion?faces-redirect=true";
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"KardexKdd","Debes seleccionar almenos un item"));
            }
         }
        catch(Exception err)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Fatal",""+err));
        }finally
        {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
       return ""; 
    }
    
    public void buscarKardexVenta()
    {
        try
        {
            this.kardeDevolucion.getListaItemsDevolucion().clear();
            this.saldoDevolucion = 0;
            KardexDevolucionDao dao = new KardexDevolucionDao();
            this.listaItemsVenta =  dao.buscarKardexVenta(this.facturaBuscar);
            this.saldoTotalVenta = dao.getTotalSaldoVenta();
        }
        catch(Exception err)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Fatal",""+err));
        }
    }
    
public void adicionarKardexDevolucionItems(ItemVenta itemVenta1)
{

    double costoUnitario = itemVenta1.getTotal_costo();
    double precioUnitario = itemVenta1.getTotal_precio();
    double nuevoCosto = 0;
    double nuevoPrecio = 0;
    if(cantidadRetornada <= itemVenta1.getCantidad())
    {
        if(cantidadRetornada >0 )
        {
            if(verificarRepetidos(itemVenta1) == false)
            {
                nuevoCosto = cantidadRetornada * costoUnitario;
                nuevoPrecio = cantidadRetornada * precioUnitario;
                ItemDevolucion itemDevolucion = new ItemDevolucion();
                itemDevolucion.setCod_entrada(itemVenta1.getInventario().getCod_entrada());
                itemDevolucion.setCod_producto(itemVenta1.getInventario().getCod_entrada());
                itemDevolucion.setCantidad(cantidadRetornada);
                itemDevolucion.setTotal_costo(nuevoCosto);
                itemDevolucion.setTotal_precio(nuevoPrecio);
                itemDevolucion.setPrecio_unitario(precioUnitario);
                kardeDevolucion.getListaItemsDevolucion().add(itemDevolucion);
                saldoDevolucion = this.getTotalSaldoDevoluion();
            }
            else
            {
                  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fatal","Si deseas modificar, elimina la lista de item devolucion e ingresa nuevamente la cantidad correcta"));
            }
        }
        else
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fatal","La cantidad no debe ser cero o infeior"));
        }
    }
    else
    {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Fatal","La cantidad no debe superar a la ingresada en la venta"));        
    }
    
}

private double getTotalSaldoDevoluion()
{
    double sumaSaldo = 0;
    try
    {
        int b=0;
        Iterator<ItemDevolucion> items = this.kardeDevolucion.getListaItemsDevolucion().iterator();
        while(items.hasNext() == true)
        {
            ItemDevolucion item = new ItemDevolucion();
            item = items.next();
            sumaSaldo = sumaSaldo + item.getTotal_precio();
        }
    }catch(Exception err)
    {
        throw err;
    }
    
return sumaSaldo;
}

public void eliminarItemDevolucion(ItemDevolucion itemDevolucion)
    {
        List<ItemDevolucion> items = this.kardeDevolucion.getListaItemsDevolucion();
        int j = 0;
        int b = 0;
        int pos = -1;
        while(j<items.size() && b==0)
        {
            if(items.get(j).getCod_producto() == itemDevolucion.getCod_producto())
            {
                pos = j;
                b=1;
            }
            j++;
        }
        if(b==1)
        {
            this.kardeDevolucion.getListaItemsDevolucion().remove(pos);
        }
    }

public boolean verificarRepetidos(ItemVenta itemVenta1)
    {   
        int b=0;
        Iterator<ItemDevolucion> items = this.kardeDevolucion.getListaItemsDevolucion().iterator();
        while(items.hasNext() == true && b==0)
        {
            ItemDevolucion item = items.next();
            if(item.getCod_producto() == itemVenta1.getInventario().getCod_producto())
            {
                b=1;
            }
        }
        if(b==1)
        {
            return true;
        }
        else
        {
            return false;
        }
        
    }    
    

}



