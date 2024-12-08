/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import kardex.modelo.Empleado;

/**
 *
 * @author Carlitos
 */
@ManagedBean
@RequestScoped
public class SessionBean {

    /**
     * Creates a new instance of MbSesion
     */
    private String cedula_empleado;
    private String nombre;
    private String tipo;
    private boolean administrador;
    private Empleado empleado = new Empleado();
    private final HttpServletRequest httpServletRequest;
    private final  FacesContext faceContext;
    private FacesMessage facesMessage;
    private boolean estadoSession;
    private boolean mensajeSession;
    private boolean mostrarModulos;

    public boolean isMostrarModulos() {
        return mostrarModulos;
    }

    public void setMostrarModulos(boolean mostrarModulos) {
        this.mostrarModulos = mostrarModulos;
    }

    
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
 
    
    
        public boolean isMensajeSession() {
        return mensajeSession;
    }

    public void setMensajeSession(boolean mensajeSession) {
        this.mensajeSession = mensajeSession;
    }
    
    public boolean isEstadoSession() {
        return estadoSession;
    }

    public void setEstadoSession(boolean estadoSession) {
        this.estadoSession = estadoSession;
    }

    public String getCedula_empleado() {
        return cedula_empleado;
    }

    public void setCedula_empleado(String cedula_empleado) {
        this.cedula_empleado = cedula_empleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    public FacesMessage getFacesMessage() {
        return facesMessage;
    }

    public void setFacesMessage(FacesMessage facesMessage) {
        this.facesMessage = facesMessage;
    }
    
    public SessionBean() 
    {
        faceContext=FacesContext.getCurrentInstance();
        httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
        if(httpServletRequest.getSession().getAttribute("sessionCedula")!=null)
        {
            this.cedula_empleado=httpServletRequest.getSession().getAttribute("sessionCedula").toString();
            this.nombre = httpServletRequest.getSession().getAttribute("sessionNombre").toString();
            this.tipo = httpServletRequest.getSession().getAttribute("sessionTipo").toString();
            this.empleado.setCedula_empleado(this.cedula_empleado);
            if(this.tipo.compareTo("a")==0 || this.tipo.compareTo("s")==0)
            {
                this.setMostrarModulos(true);
            }
            else
            {
                this.setMostrarModulos(false);
            }
            this.empleado.setNombre(nombre);
            this.setEstadoSession(true);
            this.setMensajeSession(false);
            if(this.tipo.compareTo("a")!=0 || this.tipo.compareTo("s")!=0){
                this.setAdministrador(false);
            }
            else
            {
                this.setAdministrador(true);
            }
  
        }
        else
        {
            this.empleado = null;
            this.setEstadoSession(false);
            this.setMensajeSession(true);
        }
    }
     
    public String cerrarSession()
    {
        httpServletRequest.getSession().removeAttribute("sessionCedula");
        httpServletRequest.getSession().removeAttribute("sessionNombre");
        httpServletRequest.getSession().removeAttribute("sessionTipo");
        facesMessage=new FacesMessage(FacesMessage.SEVERITY_INFO, "Session cerrada correctamente", null);
        faceContext.addMessage(null, facesMessage);
        this.cedula_empleado = "";
        this.nombre ="";
        this.empleado = null;
        return "loginPrincipal?faces-redirect=true";
    }
    
}
