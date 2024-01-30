/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kardex.bean;



import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import kardex.dao.ReporteVencimientoDao;
import kardex.modelo.ReporteVencimiento;



/**
 *
 * @author Carlitos
 */
@ManagedBean
@ViewScoped
public class ReporteVencidosBean {

    /**
     * Creates a new instance of ReporteVencidos
     */
private List<ReporteVencimiento> listaVencidos = new ArrayList<ReporteVencimiento>();


    public List<ReporteVencimiento> getListaVencidos() {
        return listaVencidos;
    }

    public void setListaVencidos(List<ReporteVencimiento> listaVencidos) {
        this.listaVencidos = listaVencidos;
    }
    
    
    private boolean isPostBack()
    {
        boolean res;
        res = FacesContext.getCurrentInstance().isPostback();
        return res;
    }
    
     /**
     *
     * @param ajax
     * @throws Exception
     */
    @PostConstruct
    public void listarProductosVencidos() 
    {
        try
        {

                    ReporteVencimientoDao dao = new ReporteVencimientoDao();
                    this.listaVencidos = dao.getProductosVencidos();

        }

        catch(Exception e)
        {
        }
    }
    
    
}
