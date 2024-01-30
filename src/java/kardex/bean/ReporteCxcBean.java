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
import kardex.dao.ReporteCxcDao;
import kardex.modelo.ReporteCxcGeneral;

/**
 *
 * @author Carlitos
 */
@ManagedBean
@ViewScoped
public class ReporteCxcBean {

    /**
     * Creates a new instance of ReporteCxcBean
     */
    private String nombres;
    private String apellidos;
    private List<ReporteCxcGeneral> listaGeneral;
    private double saldo;

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
    public ReporteCxcBean()
    {
        this.listaGeneral = new ArrayList<>();
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }



    public List<ReporteCxcGeneral> getListaGeneral() {
        return listaGeneral;
    }

    public void setListaGeneral(List<ReporteCxcGeneral> listaGeneral) {
        this.listaGeneral = listaGeneral;
    }    
    public void buscarCxc() throws Exception
    {
        try
        {

           ReporteCxcDao dao = new ReporteCxcDao();
           this.listaGeneral = dao.getListaGeneralNombres(this.nombres.trim(), this.apellidos.trim());
           this.saldo = dao.getTotalDeuda();
                if(this.listaGeneral.isEmpty()==true)
                {
                    
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Información:",this.listaGeneral.size()+" resultados"));
                }


        }catch(Exception err)
        {
            throw err;
        }
        
    }
    
}
