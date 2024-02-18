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
import kardex.dao.BarCodeDao;
import kardex.dao.InventarioDao;
import kardex.dao.KardexVentaDao;
import kardex.modelo.BarCode;
import org.primefaces.context.RequestContext;


/**
 *
 * @author Carlitos
 */
@ManagedBean
@ViewScoped
public class BarCodeBean {
    private BarCode barCode = new BarCode();
    private int codeConsecutivo = 0;
    private String cadenaBuscar;
    private List<BarCode > listaCodigos = new ArrayList<BarCode>();
    private BarCode barCodeSeleccionado = new BarCode();
    public BarCodeBean() {
        this.setNumeroFactura(); 
    }

    public BarCode getBarCodeSeleccionado() {
        return barCodeSeleccionado;
    }

    public void setBarCodeSeleccionado(BarCode barCodeSeleccionado) {
        this.barCodeSeleccionado = barCodeSeleccionado;
    }

    
    
    public List<BarCode> getListaCodigos() {
        return listaCodigos;
    }

    public void setListaCodigos(List<BarCode> listaCodigos) {
        this.listaCodigos = listaCodigos;
    }



 

    
    public String getCadenaBuscar() {
        return cadenaBuscar;
    }

    /**
     * Creates a new instance of InventarioActualizarBean
     */
    public void setCadenaBuscar(String cadenaBuscar) { 
        this.cadenaBuscar = cadenaBuscar;
    }

    public int getCodeConsecutivo() {
        return codeConsecutivo;
    }

    public void setCodeConsecutivo(int codeConsecutivo) {
        this.codeConsecutivo = codeConsecutivo;
    }

    
    public BarCode getBarCode() {
        return barCode;
    }

    public void setBarCode(BarCode barCode) {
        this.barCode = barCode;
    }
    
    
    public void buscarCodigos() throws Exception
    {
        try
        {
            BarCodeDao dao = new BarCodeDao();
            this.listaCodigos = dao.getListarCodigosBarras(cadenaBuscar);
        }catch(Exception err)
        {
            throw err;
        }
    }
    
    public void seleccionarItemCode(BarCode bar){
        this.barCodeSeleccionado = bar;
    }
       
    public String guardarBarCode(){
        try
        {
                
                BarCodeDao dao = new BarCodeDao();
                dao.registrarBarCode(this.barCode);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Aviso","El código de barras almacenado"));
                return "barcode?faces-redirect=true";
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
    
    
    

   public void setNumeroFactura() 
   {
      try
       {  
           BarCodeDao dao = new BarCodeDao();
           this.codeConsecutivo = dao.getConsecutivoCodeBar();
           this.barCode.setCodigo_barras(codeConsecutivo+"");
       }
       catch(Exception err)
       {
       } 
   }
    
    
    
   
}
