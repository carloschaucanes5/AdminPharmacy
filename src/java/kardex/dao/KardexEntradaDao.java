/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kardex.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import kardex.modelo.Inventario;
import kardex.modelo.KardexEntrada;
import kardex.modelo.Laboratorio;
import kardex.modelo.Proveedor;

/**
 *
 * @author Carlitos
 */
public class KardexEntradaDao extends Dao{
    
    
    public void registrarEntrada(KardexEntrada kardexEntrada) throws Exception
    {
        try
        {
          int ultimoIndice = 0;
          this.conectar();
          this.getCn().setAutoCommit(false);
          this.registrarKadexEntrada(kardexEntrada);
          ultimoIndice = this.getUltimoCodigoInsertado();
          if(ultimoIndice!=-1)
          {
              this.actualizarExistenciasInventario(kardexEntrada);
              this.registrarKardexEntradaHistorico(kardexEntrada, ultimoIndice);
              this.eliminarEntradasEnCeros();
              this.getCn().commit();
          }
          else
          {
              this.getCn().rollback();
          }
          
        }catch(Exception e)
        {
           this.getCn().rollback();
           throw  e;
        }finally{
           this.cerrarConexion();
        }
    }
    //Registrar kardex entrada
    private void registrarKadexEntrada(KardexEntrada kardexEntrada) throws Exception
    {
        String sql1 = "";
        PreparedStatement st1 = null;
        try
        {
           sql1=""+
           "insert into kardex_entrada"+
           "(cod_tipo_transaccion,cod_producto,fecha_transaccion,hora_transaccion,fecha_vencimiento,"+
           "cantidad,cedula_empleado,detalle,total_costo,total_precio,numero_factura,nit_proveedor,iva)"+
           "values(100,"+kardexEntrada.getInventario().getCod_producto()+","+
           "'"+this.getFecha()+"','"+this.getHora()+"',"+
           "'"+kardexEntrada.getFecha_vencimiento()+"',"+kardexEntrada.getCantidad()+",'"+kardexEntrada.getEmpleado().getCedula_empleado()+"',"+
           "'"+kardexEntrada.getDetalle()+"',"+kardexEntrada.getTotal_costo()+","+kardexEntrada.getTotal_precio()+","+
           "'"+kardexEntrada.getNumero_factura()+"','"+kardexEntrada.getProveedor().getNit_proveedor()+"',"+kardexEntrada.getIva()+")";
          System.out.println(sql1); 
           st1  = this.getCn().prepareStatement(sql1);
          st1.executeUpdate();
          st1.close();
        }
        catch(Exception err)
        {
            throw err;
        }       
    }
    //Obtener ultimo id o codigo ingresado
    private int getUltimoCodigoInsertado() throws Exception
    {
        String sql1 = "";
        PreparedStatement st1 = null;
        ResultSet rs = null;
        int codEntrada = -1;
        try
        {
          sql1= "select lastval()";
          st1  = this.getCn().prepareStatement(sql1);
          rs = st1.executeQuery();
          if(rs.next()==true)
          {
              codEntrada = rs.getInt(1);
          }
          st1.close();
        }
        catch(Exception err)
        {
            throw err;
        }  
        return codEntrada;
    }
    //Actualizar existencias en inventario
    private void actualizarExistenciasInventario(KardexEntrada kardexEntrada) throws Exception
    {
        String sql1 = "";
        PreparedStatement st1 = null;
        int existencias = 0;
        try
        {
            existencias = kardexEntrada.getCantidad() + kardexEntrada.getInventario().getExistencias();
            sql1 = "update inventario set existencias = "+existencias+" where cod_producto = "+kardexEntrada.getInventario().getCod_producto()+"";
            st1 = this.getCn().prepareStatement(sql1);
            st1.executeUpdate();
            st1.close();
        }
        catch(Exception err)
        {
            throw err;
        }  
    }
    //Almacenar en la tabla historico
    private void registrarKardexEntradaHistorico(KardexEntrada kardexEntrada, int codEntrada) throws Exception
    {
        String sql1 = "";
        PreparedStatement st1 = null;
        try
        {
         sql1=""+
             "insert into kardex_entrada_historico"+
             "(cod_entrada,cod_tipo_transaccion,cod_producto,fecha_transaccion,hora_transaccion,fecha_vencimiento,"+
             "cantidad,cedula_empleado,detalle,total_costo,total_precio,numero_factura,nit_proveedor,iva)"+
             "values("+codEntrada+",100,"+kardexEntrada.getInventario().getCod_producto()+","+
             "'"+this.getFecha()+"','"+this.getHora()+"',"+
             "'"+kardexEntrada.getFecha_vencimiento()+"',"+kardexEntrada.getCantidad()+",'"+kardexEntrada.getEmpleado().getCedula_empleado()+"',"+
             "'"+kardexEntrada.getDetalle()+"',"+kardexEntrada.getTotal_costo()+","+kardexEntrada.getTotal_precio()+","+
             "'"+kardexEntrada.getNumero_factura()+"','"+kardexEntrada.getProveedor().getNit_proveedor()+"',"+kardexEntrada.getIva()+")";
              
         st1  = this.getCn().prepareStatement(sql1);
            st1.executeUpdate();
            st1.close();
        }
        catch(Exception err)
        {
            throw err;
        }   
    }
    //eliminar entradas que ya se encuentran en cero
    private void eliminarEntradasEnCeros() throws Exception
    {
        String sql1 = "";
        PreparedStatement st1 = null;
        try
        {

            sql1 = "delete from kardex_entrada where cantidad=0";
            st1 = this.getCn().prepareStatement(sql1);
            st1.executeUpdate();
            st1.close();
        }
        catch(Exception err)
        {
            throw err;
        }   
    }
    
    public String getFecha() throws Exception
    {
        String fecha = "";
        try
        {
          String sql= "select current_date";
          PreparedStatement stf  = this.getCn().prepareStatement(sql);
          ResultSet rs = stf.executeQuery();
          if(rs.next()==true)
          fecha = (String)rs.getString(1);
        }catch(Exception e)
        {
            throw e;
        }
          //--------------------------------------------------------------------
        return fecha;
    }
    
    public String getHora() throws Exception
    {
       
        String hora = "";
        try
        {
          String sql= "select current_time";
          PreparedStatement stf  = this.getCn().prepareStatement(sql);
          ResultSet rs = stf.executeQuery();
          if(rs.next()==true)
          hora = (String)rs.getString(1);
        }catch(Exception e)
        {
            throw e;
        }
          //--------------------------------------------------------------------
        return hora;
    }
    public List<Inventario> getListarNombresProductos(String nombreProducto) throws Exception
    {
        List<Inventario> li = new ArrayList<Inventario>();
        try
        {
            this.conectar();
            String sql = "select * from inventario  where nombre_producto ilike '"+nombreProducto+"%' or codigo_barras ilike '"+nombreProducto+"'";
            PreparedStatement st =  this.getCn().prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while(rs.next() == true)
            {
                Inventario inv = new Inventario();
                inv.setCod_producto(rs.getInt("cod_producto"));
                inv.setCodigo_barras(rs.getString("codigo_barras"));
                inv.setNombre(rs.getString("nombre_producto"));
                inv.setConcentracion(rs.getString("concentracion"));
                inv.setPresentacion(rs.getString("presentacion"));
                /*inv.setIva(rs.getDouble("iva"));*/
                /*inv.setCosto_unitario(rs.getDouble("costo_unitario"));
                inv.setPrecio_unitario(rs.getDouble("precio_unitario"));*/
                inv.setEstado(rs.getString("estado"));
                inv.setExistencias(rs.getInt("existencias"));
                li.add(inv);
            }
        }catch(Exception e)
        {
            throw e;
        }finally
        {
            this.cerrarConexion();
        }
        return li;
    }
    

    public List<Proveedor> getListaProveedores() throws Exception
    {
        List<Proveedor> lp = new ArrayList<>();
        try
        {
            ResultSet rs = null;
            this.conectar();
            PreparedStatement st = this.getCn().prepareStatement("select * from proveedor order by nombre_proveedor");
            rs=st.executeQuery();
            while(rs.next()==true)
            {
                Proveedor prov = new Proveedor();
                prov.setNit_proveedor((rs.getString("nit_proveedor")));
                prov.setNombre_proveedor((rs.getString("nombre_proveedor")));
                prov.setDireccion((rs.getString("direccion")));
                prov.setCiudad((rs.getString("ciudad")));
                prov.setReguimen((rs.getString("reguimen")));
                lp.add(prov);
            }
        }catch(Exception e)
        {
            throw e;
        }finally
        {
            this.cerrarConexion();
        }
        return lp;
    }
    
    
    public List<Laboratorio> getListaLaboratorios() throws Exception
    {
        List<Laboratorio> lb = new ArrayList<Laboratorio>();
        try
        {

            ResultSet rs = null;
            this.conectar();
            PreparedStatement st = this.getCn().prepareStatement("select * from laboratorio order by nombre_laboratorio ");
            rs=st.executeQuery();
            while(rs.next()==true)
            {
                Laboratorio lab = new Laboratorio();
                lab.setCod_laboratorio(rs.getInt("cod_laboratorio"));
                lab.setNombre_laboratorio(rs.getString("nombre_laboratorio"));
                lb.add(lab);
            }
        }catch(Exception e)
        {
            throw e;
        }finally
        {
            this.cerrarConexion();
        }
        return lb;
    }
    //funcion para obtener el prcentaje ddel precio
    public double getPorcentajePrecio() throws Exception
    {
        double porcentajePrecio = 0;
        try
        {
            ResultSet rs = null;
            this.conectar();
            PreparedStatement st = this.getCn().prepareStatement("select * from metadatos where metadato = 'PORCENTAJEPRECIO' and estado = 'A' ");
            rs=st.executeQuery();
            if(rs.next()==true)
            {
                porcentajePrecio = Double.parseDouble(rs.getString("valor"));
            }
            return porcentajePrecio;
        }catch(Exception e){
            throw e;
        }finally{
            this.cerrarConexion();
        }
    }      
}
    
    

