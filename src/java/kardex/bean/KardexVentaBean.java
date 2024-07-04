/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kardex.bean;


import com.google.gson.Gson;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.security.auth.message.callback.PrivateKeyCallback;
import kardex.dao.ClienteDao;
import kardex.dao.KardexVentaDao;
import kardex.modelo.Cliente;
import kardex.modelo.ConsultaProducto;
import kardex.modelo.CuentaCobrar;
import kardex.modelo.Empleado;
import kardex.modelo.Empresa;
import kardex.modelo.Inventario;
import kardex.modelo.ItemVenta;
import kardex.modelo.KardexVenta;
import kardex.modelo.Recibo;
import org.primefaces.context.RequestContext;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import kardex.modelo.MetodoPago;
import kardex.modelo.Municipio;
import kardex.modelo.TipoIdentificacion;


/**
 *
 * @author Carlitos
 */
@ManagedBean
@ViewScoped
public class KardexVentaBean {
    private String cadenaNombre;
    private KardexVenta kardexVenta = new KardexVenta();
    private List<ConsultaProducto> listaInventario = new ArrayList<ConsultaProducto>();
    private Inventario itemInventario = new Inventario();
    private ConsultaProducto itemProducto = new ConsultaProducto();
    private int itemCantidad;
    private double totalPago;
    private double formaPago;
    private double saldoRetorno;
    private String htmlItems;
    private List<TipoIdentificacion> tiposIdentificacion = new ArrayList<TipoIdentificacion>();
    
    private Municipio municipio = new Municipio();
    private Cliente cliente = new Cliente();
    private Cliente clienteSeleccionado = new Cliente();
    private List<MetodoPago> listaMetodosPago = new ArrayList<MetodoPago>();
    private MetodoPago metodoPago = new MetodoPago();
    private String activarBotonCliente;
    private String activarBotonEjecutarVenta = "none";
    private boolean activarCamposCliente;
    private List<Cliente> listaClientes = new ArrayList<Cliente>();
    private List<Municipio> listaMunicipios = new ArrayList<Municipio>();
    private String detalleCxC;
    private String cadenaClienteNombre;
    private String cadenaMunicipioNombre;
    
    private List<Recibo> listaRecibos = new ArrayList<Recibo>();
    private Empresa empresa = new Empresa();
    private String fecha;
    private String hora;

    public List<MetodoPago> getListaMetodosPago() {
        return listaMetodosPago;
    }

    public void setListaMetodosPago(List<MetodoPago> listaMetodosPago) {
        this.listaMetodosPago = listaMetodosPago;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    
    public Cliente getClienteSeleccionado() {
        return clienteSeleccionado;
    }

    public void setClienteSeleccionado(Cliente clienteSeleccionado) {
        this.clienteSeleccionado = clienteSeleccionado;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

   
    public List<Municipio> getListaMunicipios() {
        return listaMunicipios;
    }

    public void setListaMunicipios(List<Municipio> listaMunicipios) {
        this.listaMunicipios = listaMunicipios;
    }

    public String getCadenaMunicipioNombre() {
        return cadenaMunicipioNombre;
    }

    public void setCadenaMunicipioNombre(String cadenaMunicipioNombre) {
        this.cadenaMunicipioNombre = cadenaMunicipioNombre;
    }



    
   

    public List<TipoIdentificacion> getTiposIdentificacion() {
        return tiposIdentificacion;
    }

    public void setTiposIdentificacion(List<TipoIdentificacion> tiposIdentificacion) {
        this.tiposIdentificacion = tiposIdentificacion;
    }

   
    public String getHtmlItems() {
        return htmlItems;
    }

    public void setHtmlItems(String htmlItems) {
        this.htmlItems = htmlItems;
    }

    
    public KardexVentaBean() {
    }

    public double getSaldoRetorno() {
        return saldoRetorno;
    }

    public void setSaldoRetorno(double saldoRetorno) {
        this.saldoRetorno = saldoRetorno;
    }

    public double getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(double formaPago) {
        this.formaPago = formaPago;
    }

    public ConsultaProducto getItemProducto() {
        return itemProducto;
    }

    public void setItemProducto(ConsultaProducto itemProducto) {
        this.itemProducto = itemProducto;
    }

    
    public List<ConsultaProducto> getListaInventario() {
        return listaInventario;
    }

    public void setListaInventario(List<ConsultaProducto> listaInventario) {
        this.listaInventario = listaInventario;
    }

    
    
    
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
        
    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    
    public List<Recibo> getListaRecibos() {
        return listaRecibos;
    }

    public void setListaRecibos(List<Recibo> listaRecibos) {
        this.listaRecibos = listaRecibos;
    }
    
    public String getCadenaClienteNombre() {
        return cadenaClienteNombre;
    }

    public void setCadenaClienteNombre(String cadenaClienteNombre) {
        this.cadenaClienteNombre = cadenaClienteNombre;
    }

  
    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }



    public String getCadenaNombre() {
        return cadenaNombre;
    }

    public void setCadenaNombre(String cadenaNombre) {
        this.cadenaNombre = cadenaNombre;
    }

    public KardexVenta getKardexVenta() {
        return kardexVenta;
    }

    public void setKardexVenta(KardexVenta kardexVenta) {
        this.kardexVenta = kardexVenta;
    }


    public Inventario getItemInventario() {
        return itemInventario;
    }

    public void setItemInventario(Inventario itemInventario) {
        this.itemInventario = itemInventario;
    }

    public int getItemCantidad() {
        return itemCantidad;
    }

    public void setItemCantidad(int itemCantidad) {
        this.itemCantidad = itemCantidad;
    }

    public double getTotalPago() {
        return totalPago;
    }

    public void setTotalPago(double totalPago) {
        this.totalPago = totalPago;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getActivarBotonCliente() {
        return activarBotonCliente;
    }

    public void setActivarBotonCliente(String activarBotonCliente) {
        this.activarBotonCliente = activarBotonCliente;
    }

    public String getActivarBotonEjecutarVenta() {
        return activarBotonEjecutarVenta;
    }

    public void setActivarBotonEjecutarVenta(String activarBotonEjecutarVenta) {
        this.activarBotonEjecutarVenta = activarBotonEjecutarVenta;
    }

    public boolean isActivarCamposCliente() {
        return activarCamposCliente;
    }

    public void setActivarCamposCliente(boolean activarCamposCliente) {
        this.activarCamposCliente = activarCamposCliente;
    }

    public String getDetalleCxC() {
        return detalleCxC;
    }

    public void setDetalleCxC(String detalleCxC) {
        this.detalleCxC = detalleCxC;
    }
    
    private boolean isPostBack()
    {
        boolean res;
        res = FacesContext.getCurrentInstance().isPostback();
        return res;
    }
    
    
    public void calcularDineroRetorno(){
        if(this.formaPago == 0){
            this.saldoRetorno =0;
        }
        else{
            this.saldoRetorno = this.formaPago - this.totalPago;
        }
        
    }
    
    public void adicionarListaKardexItemVenta(ConsultaProducto itemInventario, int itemCantidad)
    {
        Double precioTotal = itemInventario.getTotal_precio() * itemCantidad;
        Double costoTotal = itemInventario.getTotal_costo()* itemCantidad;
        ItemVenta itemVenta = new ItemVenta();
        itemVenta.setInventario(itemInventario);
        itemVenta.setCantidad(itemCantidad);
        itemVenta.getInventario().setTotal_costo(itemInventario.getTotal_costo());
        itemVenta.getInventario().setTotal_precio(itemInventario.getTotal_precio());
        itemVenta.setTotal_costo(costoTotal);
        itemVenta.setTotal_precio(precioTotal);

        if(verificarRepetidos(itemInventario)==false)
        {
            if(itemCantidad <= itemInventario.getCantidad())
            {
                this.kardexVenta.getListaItemsVenta().add(itemVenta);
                this.setCadenaNombre("");
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage(null,new  FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:","La cantidad ingresada no debe superar  la existente"));
            }
            
        }
        else
        {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","Este producto ya esta en la lista.Para modificar, elimina el item y vuelve  a ingresar"));
        }
        this.setTotalPago(calcularTotalVenta());
    }
    
    public void eliminarItemVenta(ItemVenta itemVenta)
    {
        
        List<ItemVenta> items = this.kardexVenta.getListaItemsVenta();
        int j = 0;
        int b = 0;
        int pos = -1;
        while(j<items.size() && b==0)
        {
            if(items.get(j).getInventario().getCod_producto() == itemVenta.getInventario().getCod_producto())
            {
                pos = j;
                b=1;
            }
            j++;
        }
        if(b==1)
        {
            this.kardexVenta.getListaItemsVenta().remove(pos);
        }
        this.setTotalPago(calcularTotalVenta());
    }
    
    public double calcularTotalVenta()
    {
      double total = 0;
      Iterator<ItemVenta> items = this.kardexVenta.getListaItemsVenta().iterator();
        while(items.hasNext() == true)
        {
            ItemVenta item = items.next();
            total = total + (item.getTotal_precio());
        }
        return total;
    }
   
    public boolean verificarRepetidos(ConsultaProducto inv)
    {   
        int b=0;
        Iterator<ItemVenta> items = this.kardexVenta.getListaItemsVenta().iterator();
        while(items.hasNext() == true && b==0)
        {
            ItemVenta item = items.next();
            if(item.getInventario().getCod_producto() == inv.getCod_producto())
            {
                b=1;
            }
        }
        if(b==1)
        {
            return true;
        }
        else
        {
            return false;
        }
        
    }
    
   public void buscarProducto() throws Exception
    {
        try
        {
            KardexVentaDao dao = new KardexVentaDao();
            this.listaInventario = dao.getListarNombresProductos(cadenaNombre);
            if(this.listaInventario.size()==1){
                RequestContext.getCurrentInstance().execute("PF('wDialogoCantidad').show()");
                RequestContext.getCurrentInstance().update("dDialogoCantidad");
                this.setItemProducto(listaInventario.get(0));
            }
        }catch(Exception err)
        {
            throw err;
        }
    }
   
   public void resetFormFactura(){
       RequestContext.getCurrentInstance().reset(":formFactura");
   }
   public void leerIdInventario(ConsultaProducto inventario)
   {
       
       this.setItemProducto(inventario);
   }
   
   @PostConstruct
   public void setNumeroFactura() 
   {
      try
       {  
           KardexVentaDao dao = new KardexVentaDao();
           ClienteDao daoCli = new ClienteDao();
           this.kardexVenta.setNumero_factura(dao.getConsecutivoNumeroFactura());
           this.clienteSeleccionado = this.getClienteFinal();
           setTiposIdentificacion(daoCli.getTiposIdentificacion());
           setListaMetodosPago(dao.getListaMetodosPago());
           
       }
       catch(Exception err){

       } 
   }
   
   public String registrarVentaContado(Empleado empleado)
   {
       try
       {
           KardexVentaDao dao = new KardexVentaDao();
           if(this.kardexVenta.getListaItemsVenta().size() > 0)
           {
               Gson g = new Gson();
               String cad = g.toJson(this.kardexVenta);
               System.out.println(cad);
               dao.registrarVenta(this.kardexVenta, 102, empleado);
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"KardexKDD","Venta Almacenada con éxito"));
               kardexVenta.getListaItemsVenta().clear();
               this.setNumeroFactura();
               this.buscarProducto();
               this.totalPago = 0;
               return "kardex_venta?faces-redirect=true";
           }
           else
           {
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","No se han seleccionado productos"));
               return "";
           }
           
       }
       catch(Exception err)
       {
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:",""+err));
           return "";
       }finally
       {
           FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
       }
   }
   //---------------------------------------------------------------------------
   public void generarFactura()
   {
      if(this.kardexVenta.getListaItemsVenta().size()==0)return;
       this.listaRecibos.clear();
      try
       {
           this.extraerInformacionEmpresa();
           this.extraerTiempo();
           String nombre = "";
           KardexVentaDao dao = new KardexVentaDao();
           if(this.kardexVenta.getListaItemsVenta().size() > 0)
           {
               for(int j=0;j<this.kardexVenta.getListaItemsVenta().size();j++)
               {
                   Recibo re = new Recibo();
                   ItemVenta iv = new ItemVenta();
                   iv = kardexVenta.getListaItemsVenta().get(j);
                   nombre = iv.getInventario().getNombre_producto()+" "+iv.getInventario().getConcentracion();
                   re.setVariable(nombre);
                   re.setCantidad(iv.getCantidad());
                   re.setTotal(iv.getTotal_precio());
                   re.setModo_venta(iv.getInventario().getDetalle());
                   this.listaRecibos.add(re);
               }
           }
           else
           {
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","No se han seleccionado productos"));
           }
           
       }
       catch(Exception err)
       {
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:",""+err));
       }finally
       {
           FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
       }
   }
   //---------------------------------------------------------------------------
      
   private void extraerInformacionEmpresa() throws Exception
   {
       try
       {
           
           KardexVentaDao dao = new KardexVentaDao();
           this.empresa = dao.getEmpresa();
       }
       catch(Exception err)
       {
         throw err;
       }
   }
   
   private void extraerTiempo() throws Exception
   {
       try
       {
        KardexVentaDao dao = new KardexVentaDao();
        dao.conectar();
        this.fecha = dao.getFecha();
        this.hora = dao.getHora();
        dao.cerrarConexion();
       }catch(Exception err)
       {
           throw err;
       }
   }
   //---------------------------------------------------------------------------
   public String registrarVentaCredito(Empleado empleado)
   {
       try
       {
           CuentaCobrar cxc = new CuentaCobrar();
           cxc.setNumero_factura(this.kardexVenta.getNumero_factura());
           cxc.setCedula_cliente(this.cliente.getCedula_cliente());
           cxc.setEstado("A");
           cxc.setSaldo_pendiente(this.totalPago);
           cxc.setDetalle(this.detalleCxC);
           KardexVentaDao dao = new KardexVentaDao();
           if(this.kardexVenta.getListaItemsVenta().size() > 0)
           {
               dao.registrarVentaCxc(this.kardexVenta, 103,cxc, empleado);
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"KardexKDD","Venta Almacenada con éxito"));
               kardexVenta.getListaItemsVenta().clear();
               this.setNumeroFactura();
               this.buscarProducto();
               this.totalPago = 0;
               return "kardex_venta?faces-redirect=true";
           }
           else
           {
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","No se han seleccionado productos"));
               return "";
           }
           
       }
       catch(Exception err)
       {
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:",""+err));
           return "";
       }finally
       {
           FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
       }
   }
   //-----------------------------listar Clientes-------------------------------
   
   public Cliente getClienteFinal(){
       Cliente clien = new Cliente();
       try{            
           ClienteDao dao = new ClienteDao();
           clien = dao.buscarClienteCedula("222222222222");
           if(clien == null)
           {
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:","El cliente no se encuentra registrado"));
               this.clienteSeleccionado.setNombres("");
               this.clienteSeleccionado.setApellidos("");
               this.clienteSeleccionado.setDireccion("");
               this.clienteSeleccionado.setTelefono("");
               this.activarBotonCliente = "show";
               this.activarCamposCliente = false;
               this.activarBotonEjecutarVenta = "none";
           }
           else
           {
               this.clienteSeleccionado = clien;
               this.activarBotonCliente = "none";
               this.activarBotonEjecutarVenta = "show";
               this.activarCamposCliente = true;
           } 
       }catch(Exception err){
           return null;
       }
      return  clien;
   }
   
   public void buscarClienteCedula()
   {
       try
       {
           Cliente clien = new Cliente();
           ClienteDao dao = new ClienteDao();
           clien = dao.buscarClienteCedula(cliente.getCedula_cliente());
           if(clien == null)
           {
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:","El cliente no se encuentra registrado"));
               this.cliente.setNombres("");
               this.cliente.setApellidos("");
               this.cliente.setDireccion("");
               this.cliente.setTelefono("");
               this.activarBotonCliente = "show";
               this.activarCamposCliente = false;
               this.activarBotonEjecutarVenta = "none";
           }
           else
           {
               this.cliente = clien;
               this.activarBotonCliente = "none";
               this.activarBotonEjecutarVenta = "show";
               this.activarCamposCliente = true;
           }
       }
       catch(Exception err)
       {
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:",""+err));
       }finally
       {
           FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
       } 
   }
   //---------------------------------------------------------------------------
   
   public void guardarCliente(Cliente cliente)
   {
       try
       {
          ClienteDao dao =  new ClienteDao();
          dao.registrar(cliente);
          this.buscarClienteCedula();
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"KardexKdd:","Cliente almacenado con éxito")); 
       }
       catch(Exception err)
       {
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:",""+err));
       }finally
       {
           FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
       } 
   }
   
   public void buscarClienteNombre() throws Exception
   {
       try
       {
           ClienteDao dao = new ClienteDao();
           this.listaClientes = dao.buscarClienteNombre(this.cadenaClienteNombre);
       }
       catch(Exception err)
       {
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:",""+err));
       }
   }
   
   public void buscarMunicipioNombre() throws Exception
   {
       try
       {
           ClienteDao dao = new ClienteDao();
           this.listaMunicipios = dao.buscarMunicipio(this.cadenaMunicipioNombre);
       }
       catch(Exception err)
       {
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:",""+err));
       }
   }
   
   public void generarHtmlItems(){
       String html = "<small><table>";
       html+="<tr><th>Producto</th><th>Cant.</th><th>Subtotal</th></tr>";
       for(int i = 0; i< this.listaRecibos.size();i++){
        html+="<tr><td>"+this.listaRecibos.get(i).getVariable()+"/"+this.listaRecibos.get(i).getModo_venta()+"</td><td>"+this.listaRecibos.get(i).getCantidad()+"</td><td>"+this.formatColombianCurrent(this.listaRecibos.get(i).getTotal())+"</td></tr>";
       }
       html += "</table></small>";
       this.setHtmlItems(html);
   }
   
   public String formatColombianCurrent(double money){
     DecimalFormat formatea = new DecimalFormat("###,###.##");
     return "$"+formatea.format(money)+"";
   }
   
   public String formatHora(String fecha){
       String output = "";
       DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       // Definir el formato de salida (24 horas)
        DateFormat outputformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
        try {
            Date date = df.parse(fecha);
             String f = outputformat.format(date);
             String a = f.split(" ")[1] + " " + f.split(" ")[2];
             output = a;
        } catch (ParseException ex) {
            Logger.getLogger(KardexVentaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
       return output;
   }
   
      
   public String formatFecha(String fecha){
       String output = "";
       DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       // Definir el formato de salida (24 horas)
        DateFormat outputformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
        try {
            Date date = df.parse(fecha);
             String f = outputformat.format(date);
             String a = f.split(" ")[0];
             output = a;
        } catch (ParseException ex) {
            Logger.getLogger(KardexVentaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
       return output;
   }
   

}