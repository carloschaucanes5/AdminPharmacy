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
import kardex.modelo.Inventario;
import kardex.modelo.Laboratorio;

/**
 *
 * @author Carlitos
 */
@ManagedBean
@ViewScoped
public class InventarioBean {
   
    private Inventario inventario = new Inventario();
    private List<Categoria> listaCategorias = new ArrayList<>();
    private List<Laboratorio> listaLaboratorios = new ArrayList<>();
    private Laboratorio labNuevo = new Laboratorio();


   @PostConstruct 
   private void cargarDatos()
   {
       this.listarCategorias();
       this.listarLaboratorios();
      
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

    public Laboratorio getLabNuevo() {
        return labNuevo;
    }

    public void setLabNuevo(Laboratorio labNuevo) {
        this.labNuevo = labNuevo;
    }
   
       
       
    public List<Categoria> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<Categoria> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    public List<Laboratorio> getListaLaboratorios() {
        return listaLaboratorios;
    }

    public void setListaLaboratorios(List<Laboratorio> listaLaboratorios) {
        this.listaLaboratorios = listaLaboratorios;
    }
    
   
    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
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
    
    
    
   public String  registrarInventario() throws Exception
    {
        try
        {
            InventarioDao dao = new InventarioDao();
            dao.conectar();
            dao.registrarInventario(this.inventario);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Aviso","Producto almacenado con exito"));
            return "inventario_nuevo?faces-redirect=true";
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

