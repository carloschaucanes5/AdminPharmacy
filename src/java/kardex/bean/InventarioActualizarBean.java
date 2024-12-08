/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kardex.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import kardex.dao.InventarioDao;
import kardex.modelo.Categoria;
import kardex.modelo.Estado;
import kardex.modelo.Inventario;
import kardex.modelo.Laboratorio;

/**
 *
 * @author Carlitos
 */
@ManagedBean
@ViewScoped
public class InventarioActualizarBean {

    /**
     * Creates a new instance of InventarioActualizarBean
     */
    private String cadenaNombre;
    private List<Inventario> listaInventario = new ArrayList<Inventario>();
    private List<Categoria> listaCategorias = new ArrayList<Categoria>();
    private List<Laboratorio> listaLaboratorios = new ArrayList<Laboratorio>();
    private Inventario inventario = new Inventario();
    private Laboratorio labNuevo = new Laboratorio();
    private List<Estado> listaEstados = new ArrayList<Estado>();
       @PostConstruct 
        private void cargarDatos()
        {
            this.listarCategorias();
            this.listarLaboratorios();
            this.cargarEstados();
        }

       
        public List<String> completeText(String query) {
        String queryLowerCase = query.toLowerCase();
        List<String> countryList = new ArrayList<>();
        for (Laboratorio country : this.listaLaboratorios) {
            countryList.add(country.getNombre_laboratorio());
        }
        //return countryList.stream().filter(t -> t.toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
        return countryList.stream().filter(t -> t.toLowerCase().contains(query)).collect(Collectors.toList());
        }

    public List<Estado> getListaEstados() {
        return listaEstados;
    }

    public void setListaEstados(List<Estado> listaEstados) {
        this.listaEstados = listaEstados;
    }

        
    
        
    public Laboratorio getLabNuevo() {
        return labNuevo;
    }

    public void setLabNuevo(Laboratorio labNuevo) {
        this.labNuevo = labNuevo;
    }
        
    public List<Laboratorio> getListaLaboratorios() {
        return listaLaboratorios;
    }

    public void setListaLaboratorios(List<Laboratorio> listaLaboratorios) {
        this.listaLaboratorios = listaLaboratorios;
    }


    public List<Categoria> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<Categoria> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }
    
    public String getCadenaNombre() {
        return cadenaNombre;
    }

    public void setCadenaNombre(String cadenaNombre) {
        this.cadenaNombre = cadenaNombre;
    }

    public List<Inventario> getListaInventario() {
        return listaInventario;
    }

    public void setListaInventario(List<Inventario> listaInventario) {
        this.listaInventario = listaInventario;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }
       
    public void leerIdInventario(Inventario inv)
    {
        this.setInventario(inv);
    }
    
    
    public void cargarEstados(){
        
        Estado es1 = new Estado();
        es1.setSimbolo("A");
        es1.setDescripcion("Activo");
       
        Estado es2 = new Estado();
        es2.setSimbolo("I");
        es2.setDescripcion("Inactivo");
        
        this.listaEstados.add(es1);
        this.listaEstados.add(es2);
    }
    
    public void buscarProducto() throws Exception
    {
        try
        {
            InventarioDao dao = new InventarioDao();
            this.listaInventario = dao.getListarNombresProductos(this.cadenaNombre);
            if(this.listaInventario.size() <= 0)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Aviso","cero resultados"));
            }
        }catch(Exception err)
        {
            throw err;
        }
    }
    
    public String actualizarProducto()
    {
        try
        {

                InventarioDao dao = new InventarioDao();
                dao.actualizarInventario(inventario);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Aviso","Producto actualizado con éxito"));
                return "inventario_actualizar?faces-redirect=true";
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
    public void cancelarActualizacionProducto()
    {
                this.inventario = null;
    }
    
    public void listarCategorias(){
         
        try{
            InventarioDao dao = new InventarioDao();
            dao.conectar();
            this.listaCategorias = dao.getListaCategorias();
            
        }catch(Exception err) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Aviso","Error:"+err));
        }finally
        {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }
    
    public void listarLaboratorios(){
         
        try{
            InventarioDao dao = new InventarioDao();
            dao.conectar();
            this.listaLaboratorios = dao.getListaLaboratorios();
            
        }catch(Exception err) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Aviso","Error:"+err));
        }finally
        {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }
    
   public String  registrarLaboratorio() throws Exception
    {
        try
        {
            InventarioDao dao = new InventarioDao();
            dao.conectar();
            dao.registrarLaboratorio(this.labNuevo);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Aviso","Laboratorio almacenado almacenado con exito")); 
            listarLaboratorios();
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
