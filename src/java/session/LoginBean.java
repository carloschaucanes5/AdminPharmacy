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
import kardex.dao.EmpleadoDao;
import kardex.modelo.Empleado;
/**
 *
 * @author Carlitos
 */
@ManagedBean
@RequestScoped
public class LoginBean {

    private String cedula_empleado;
    private String contrasenia;
    private final HttpServletRequest httpServletRequest;
    private final FacesContext faceContext;
    private FacesMessage facesMessage;
     
    public String getCedula_empleado() {
        return cedula_empleado;
    }

    public void setCedula_empleado(String cedula_empleado) {
        this.cedula_empleado = cedula_empleado;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public LoginBean() 
    {
        faceContext=FacesContext.getCurrentInstance();
        httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
    }
     
    public String login()
    {
        try
        { 
          EmpleadoDao dao = new EmpleadoDao();
          Empleado em = dao.getEmpleadoPorCedula(this.cedula_empleado, this.contrasenia);
            if(em !=null)
            {
                httpServletRequest.getSession().setAttribute("sessionCedula", em.getCedula_empleado());
                httpServletRequest.getSession().setAttribute("sessionNombre", em.getNombre());
                httpServletRequest.getSession().setAttribute("sessionTipo", em.getTipo());
                facesMessage=new FacesMessage(FacesMessage.SEVERITY_INFO, "Acceso Correcto", null);
                faceContext.addMessage(null, facesMessage);
                return "tareaPrincipal?faces-redirect=true";
            }
            else
            {
                facesMessage=new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario o contraseña incorrecto", null);
                faceContext.addMessage(null, facesMessage);
                return "/faces/loginPrincipal.xhtml";
            }
        }catch(Exception err)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Error Fatal:",""+err));        
        }finally
        {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
        return "";
    }
}
