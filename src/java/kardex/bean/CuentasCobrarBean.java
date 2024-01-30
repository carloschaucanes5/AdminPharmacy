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
import kardex.dao.BonoDao;
import kardex.dao.ClienteDao;
import kardex.dao.CuentasCobrarDao;
import kardex.dao.ReporteCxcDao;
import kardex.modelo.Bono;
import kardex.modelo.Cliente;
import kardex.modelo.ReporteCxcDetalle;
import kardex.modelo.ReporteCxcGeneral;


/**
 *
 * @author Carlitos
 */
@ManagedBean
@ViewScoped
public class CuentasCobrarBean {

    /**
     * Creates a new instance of CuentasCobrarBean
     */
    private List<ReporteCxcGeneral> listaCxcGeneral;
    private List<ReporteCxcDetalle> listaCxcDetalle;
    private Cliente cliente;
    private Bono bono;
    private int facturaDetalle;
    
    
    private double totalDeuda;
    private double saldoDisponible;
    private double saldoTotalPagar;
    
    private double parcial_precio;
    private double parcial_costo;
    
    public CuentasCobrarBean()
    {
      this.listaCxcGeneral = new ArrayList<>();
      this.totalDeuda = 0;
      this.cliente = new Cliente();
      this.bono = new Bono();
      this.saldoDisponible = 0;
      this.saldoTotalPagar = 0;
      this.listaCxcDetalle = new ArrayList<>();
    }

    public double getParcial_precio() {
        return parcial_precio;
    }

    public void setParcial_precio(double parcial_precio) {
        this.parcial_precio = parcial_precio;
    }

    public double getParcial_costo() {
        return parcial_costo;
    }

    public void setParcial_costo(double parcial_costo) {
        this.parcial_costo = parcial_costo;
    }

    public int getFacturaDetalle() {
        return facturaDetalle;
    }

    public void setFacturaDetalle(int facturaDetalle) {
        this.facturaDetalle = facturaDetalle;
    }

    
    
    
    
    
    public double getSaldoTotalPagar() {
        return saldoTotalPagar;
    }

    public void setSaldoTotalPagar(double saldoTotalPagar) {
        this.saldoTotalPagar = saldoTotalPagar;
    }

    
    
    public double getSaldoDisponible() {
        return saldoDisponible;
    }

    public void setSaldoDisponible(double saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    
    
    public Bono getBono() {
        return bono;
    }

    public void setBono(Bono bono) {
        this.bono = bono;
    }
    
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }


    public List<ReporteCxcGeneral> getListaCxcGeneral() {
        return listaCxcGeneral;
    }

    public void setListaCxcGeneral(List<ReporteCxcGeneral> listaCxcGeneral) {
        this.listaCxcGeneral = listaCxcGeneral;
    }

    public List<ReporteCxcDetalle> getListaCxcDetalle() {
        return listaCxcDetalle;
    }

    public void setListaCxcDetalle(List<ReporteCxcDetalle> listaCxcDetalle) {
        this.listaCxcDetalle = listaCxcDetalle;
    }

    public double getTotalDeuda() {
        return totalDeuda;
    }

    public void setTotalDeuda(double totalDeuda) {
        this.totalDeuda = totalDeuda;
    }


    
    public void buscarCxC()
    {
         try
        {
                CuentasCobrarDao dao = new CuentasCobrarDao();
                this.listaCxcGeneral = dao.generarListaCxcGeneral(cliente.getCedula_cliente().trim());
                this.totalDeuda = dao.getTotalDeuda();
                ClienteDao daoC = new ClienteDao();
                this.cliente = daoC.buscarClienteCedula(cliente.getCedula_cliente().trim());
                this.bono.setCedula_cliente(cliente.getCedula_cliente().trim());
                BonoDao daoB = new BonoDao();
                this.saldoDisponible = daoB.getSaldoTotalDisponible(cliente.getCedula_cliente().trim());
                this.saldoTotalPagar = totalDeuda - saldoDisponible;

        }catch(Exception err)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Info:",""+err));
        }finally
        {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }
    
    public void pagarCxC()
    {
        try
        {
            if(listaCxcGeneral.size() > 0)
            {
                CuentasCobrarDao dao = new CuentasCobrarDao ();
                dao.pagarCxc(listaCxcGeneral);
                BonoDao daoB = new BonoDao();
                daoB.actualizarSaldoDisponible(cliente.getCedula_cliente());
                buscarCxC();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"KardexKdd:","Se a consignado con éxito por concepto de pago de cuenta por cobrar de este cliente "));            
            }
            else
            {
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"KardexKdd:","No hay cuentas por cobrar pendientes"));
            }          
        }
        catch(Exception err)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"KardexKdd:",""+err));
        }
    }
    
    public void registrarBono()
    {
      try
        {
            if(this.bono.getSaldo_abono() <=  this.saldoTotalPagar)
            {
                BonoDao dao = new BonoDao();
                dao.registrarBono(this.bono);
                buscarCxC();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"KardexKdd:","Consignación realizada con éxito"));
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"KardexKdd:","Cantidad Incorrecta"));
            }
        }
        catch(Exception err)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"KardexKdd:",""+err));
        }finally
        {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }
    
    
    public void  traerItemsFactura(ReporteCxcGeneral general) throws Exception
    {
        try
        {
            this.facturaDetalle = general.getNumero_factura();
            CuentasCobrarDao dao = new CuentasCobrarDao();
            this.listaCxcDetalle = dao.getItemsFacturaDetalle(general);
            this.parcial_costo = dao.getTotalDetalleCosto();
            this.parcial_precio = dao.getTotalDetallePrecio();
        }
        catch(Exception err)
        {
            throw err;
        }
                
            
    }
    
}


