/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kardex.bean;


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
public class InventarioBean {
    
    private Inventario inventario = new Inventario();
    
 
    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }
    

    
   public String  registrarInventario() throws Exception
    {
        try
        {
            if(inventario.getCosto_unitario() <= inventario.getPrecio_unitario())
            {
                InventarioDao dao = new InventarioDao();
                dao.conectar();
                dao.registrarInventario(this.inventario);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Aviso","Producto almacenado con exito"));
                return "inventario_nuevo?faces-redirect=true";
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","El costo/unitario no sebe ser superior al precio/unitario"));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia:","Advertencia:El punto(.) indica separador de miles y la coma(,) indica separador de valores decimales en los campos Precio/Unidad y Costo/Unidad"));
            }

        }
        catch(Exception e)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Aviso","Error:"+e));
        }finally
        {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
        return "";
    }
   
}

