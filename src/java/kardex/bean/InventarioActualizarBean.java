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
import kardex.dao.InventarioDao;
import kardex.modelo.Inventario;

/**
 *
 * @author Carlitos
 */
@ManagedBean
@ViewScoped
public class InventarioActualizarBean {

    /**
     * Creates a new instance of InventarioActualizarBean
     */
    private String cadenaNombre;
    private List<Inventario> listaInventario = new ArrayList<Inventario>();
    private Inventario inventario = new Inventario();

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

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }
       
    public void leerIdInventario(Inventario inv)
    {
        this.setInventario(inv);
    }
    
    
    public void buscarProducto() throws Exception
    {
        try
        {
            InventarioDao dao = new InventarioDao();
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
    
    public String actualizarProducto()
    {
        try
        {

                InventarioDao dao = new InventarioDao();
                dao.actualizarInventario(inventario);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Aviso","Producto actualizado con éxito"));
                return "inventario_actualizar?faces-redirect=true";
        }
        catch(Exception err)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Fatal:",""+err));
        }finally
        {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
        return "";
    }
    public void cancelarActualizacionProducto()
    {
                this.inventario = null;
    }
    
}
