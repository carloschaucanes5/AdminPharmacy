/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kardex.bean;

import kardex.modelo.Permiso;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
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
    private Permiso permiso = new Permiso("", "");
    
    private String cedula;
    private String contrasenia;
    private List<Empleado> listaEmpleados =  new ArrayList<Empleado>();
    private List<Permiso>  listaPermisos = new ArrayList<Permiso>();

    public Permiso getPermiso() {
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }

    public List<Permiso> getListaPermisos() {
        return listaPermisos;
    }

    public void setListaPermisos(List<Permiso> listaPermisos) {
        this.listaPermisos = listaPermisos;
    }
    
    public List<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    public void setListaEmpleados(List<Empleado> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }
    

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

    @PostConstruct
    public void listarEmpleados()
    {  
        try
        {
            EmpleadoDao dao = new EmpleadoDao();
            this.listaEmpleados = dao.getListaEmpleados1();
            Permiso per1 = new Permiso("Soporte","s");
            Permiso per2 = new Permiso("Administrador","a");
            Permiso per3 = new Permiso("Empleado","e");
            this.listaPermisos.add(per1);
            this.listaPermisos.add(per2);
            this.listaPermisos.add(per3);
        }
        catch(Exception e)
        {
            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Info:",""+e));
            
        }
                
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
  
  public String actualizarPermiso(){
      try
        {

            EmpleadoDao dao = new EmpleadoDao();
            dao.modificarPermiso(empleado,permiso);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"KardexKdd:","El usuario "+empleado.getNombre()+" ha cambiado el rol como "+permiso.getDescripcion()));
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
