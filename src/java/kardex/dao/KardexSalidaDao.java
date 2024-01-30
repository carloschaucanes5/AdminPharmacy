/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kardex.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import kardex.modelo.Inventario;
import kardex.modelo.KardexSalida;


/**
 *
 * @author Carlitos
 */
public class KardexSalidaDao extends Dao{

  public void registrarSalida(KardexSalida kardexSalida) throws SQLException, Exception
  {
          int existencias = 0;
          PreparedStatement st1 = null,st2=null;
          String sql1 = "", sql2 = "";
      try
        {

          this.conectar();
          this.getCn().setAutoCommit(false);
           sql1=""+
           "insert into kardex_salida"+
           "(cod_tipo_transaccion,cod_producto,fecha_salida,hora_salida,cantidad,"+
           "cedula_empleado,detalle,total_costo,total_precio)"+
           "values(101,"+kardexSalida.getInventario().getCod_producto()+","+
           "'"+this.getFecha()+"','"+this.getHora()+"',"+kardexSalida.getCantidad()+","+
           "'"+kardexSalida.getEmpleado().getCedula_empleado()+"','"+kardexSalida.getDetalle()+"',"+
           ""+kardexSalida.getTotal_costo()+","+kardexSalida.getTotal_precio()+")";
          st1  = this.getCn().prepareStatement(sql1);
          st1.executeUpdate();
          existencias = kardexSalida.getInventario().getExistencias()-kardexSalida.getCantidad();
          sql2 = "update inventario set existencias = "+existencias+" where cod_producto = "+kardexSalida.getInventario().getCod_producto()+"";
          st2 = this.getCn().prepareStatement(sql2);
          st2.executeUpdate();
          this.verificarExistenciasCeros();
          st1.close();
          st2.close();
          this.getCn().commit();
        }catch(Exception e)
        {
           this.getCn().rollback();
           throw  e;
        }finally{
           this.cerrarConexion();
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
            String sql = "select * from inventario  where nombre_producto ilike '"+nombreProducto+"%'";
            PreparedStatement st =  this.getCn().prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while(rs.next() == true)
            {
                Inventario inv = new Inventario();
                inv.setCod_producto(rs.getInt("cod_producto"));
                inv.setNombre(rs.getString("nombre_producto"));
                inv.setConcentracion(rs.getString("concentracion"));
                inv.setPresentacion(rs.getString("presentacion"));
                inv.setIva(rs.getDouble("iva"));
                inv.setCosto_unitario(rs.getDouble("costo_unitario"));
                inv.setPrecio_unitario(rs.getDouble("precio_unitario"));
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
    
     
      public void verificarExistenciasCeros() throws Exception
    {
        List<Integer> listaId = new ArrayList<Integer>();
        String sql1 = "", sql2="";
        PreparedStatement st1 = null,st2 = null;
        ResultSet rs1 = null;
        try
        {
            sql1 = "select * from inventario where existencias = 0";
            st1 = this.getCn().prepareStatement(sql1);
            rs1 =  st1.executeQuery();
            while(rs1.next() == true)
            {
                listaId.add(rs1.getInt("cod_producto"));
            }
            if(listaId.size() != 0)
            {
                ListIterator<Integer> li = listaId.listIterator();
                while(li.hasNext() == true)
                {
                    int id = li.next();
                    sql2 = "delete from kardex_entrada where cod_producto = "+id+"";
                    st2 = this.getCn().prepareStatement(sql2);
                    st2.executeUpdate();
                }
            }
        }
        catch(Exception err)
        {
            throw err;
        }
        
    }
}
