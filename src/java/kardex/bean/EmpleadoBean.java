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
import kardex.dao.EmpleadoDao;
import kardex.modelo.Empleado;

/**
 *
 * @author Carlitos
 */
@ManagedBean
@ViewScoped
public class EmpleadoBean {
    private Empleado empleado = new Empleado();
    
    private String cedula;
    private String contrasenia;

    /**
     * Creates a new instance of EmpleadoBean
     */
    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    
    
    
    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public String registraEmpleado()
    {
        try
        {
            if(empleado.getClave_acceso().compareTo(empleado.getClave_confirmar())==0)
            {
               if(empleado.getClave_acceso().length()>=6 && empleado.getClave_acceso().length() <=10) 
               {
                    EmpleadoDao dao = new EmpleadoDao();
                    dao.registrarEmpleado(empleado);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"KardexKdd:","Registro Almacenado con éxito"));
                    return "loginPrincipal?faces-redirect=true";
               }
               else
               {
                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:","La contraseña debe contener entre 6 y 10 caracteres")); 
               }

            }
            else
            {
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:","Las contraseñas no coinciden")); 
            }
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
  public void getDatosEmpleado(String cedula1)
  {
       try
        {
            
            EmpleadoDao dao = new EmpleadoDao();
            this.empleado = dao.getEmpleadoActu(cedula1);
        }
        catch(Exception err)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Fatal:",""+err));
        }   
  }
    
  public void verificarEmpleado()
  {
       try
        {     
            EmpleadoDao dao = new EmpleadoDao();
            this.empleado = dao.getEmpleadoPorCedula(cedula, contrasenia);
            if(this.empleado == null)
            {
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:","Usuario no existe")); 
            }
        }
        catch(Exception err)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Fatal:",""+err));
        }   
  }
  
  
  public String actualizarEmpleado()
    {
        try
        {
            if(empleado.getClave_acceso().compareTo(empleado.getClave_confirmar())==0)
            {
               if(empleado.getClave_acceso().length()>=6 && empleado.getClave_acceso().length() <=10) 
               {
                    EmpleadoDao dao = new EmpleadoDao();
                    dao.modificar(empleado);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"KardexKdd:","Registro Actualizado con éxito"));
                    return "loginPrincipal?faces-redirect=true";
               }
               else
               {
                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:","La contraseña debe contener entre 6 y 10 caracteres")); 
               }
            }
            else
            {
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:","Las contraseñas no coinciden")); 
            }
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
  
  
}
