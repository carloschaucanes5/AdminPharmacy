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
import kardex.modelo.Inventario;


/**
 *
 * @author Carlitos
 */
public class InventarioDao extends Dao{
    
    public void registrarInventario(Inventario inventario) throws Exception
    {
        try
        {          
          this.conectar();
          this.getCn().setAutoCommit(false);
          String sql=""+
           "insert into inventario"+
           "(nombre_producto,concentracion,presentacion,"+
           "iva,costo_unitario,precio_unitario,estado,existencias)"+
           "values('"+inventario.getNombre()+"','"+inventario.getConcentracion()+"',"
            + "'"+inventario.getPresentacion()+"',"+inventario.getIva()+","+inventario.getCosto_unitario()+","
                  + ""+inventario.getPrecio_unitario()+",'A',0)";
          PreparedStatement st  = this.getCn().prepareStatement(sql);
          //st.setString(1, inventario.getNombre());
          /*st.setString(2, inventario.getConcentracion());
          st.setString(3, inventario.getPresentacion());
          st.setDouble(4, inventario.getIva());
          st.setDouble(5, inventario.getCosto_unitario());
          st.setDouble(6, inventario.getPrecio_unitario());
          st.setString(7, "A");
          st.setInt(8, 0);*/
          st.executeUpdate();
          st.close();
          System.out.println("Registro almacenado con exito");
          //---------------------------------------------------------------------------
          this.getCn().commit();
        }catch(Exception e)
        {
           this.getCn().rollback();
           throw  e;
        }finally{
           this.cerrarConexion();
        }
    }
    
    public void actualizarInventario(Inventario inventario) throws SQLException, Exception
    {
         try
        {          
          this.conectar();
          this.getCn().setAutoCommit(false);
          String sql="update inventario set "
                  + "nombre_producto = ?, concentracion = ?, presentacion = ? , "
                  + "iva = ?,costo_unitario = ?, precio_unitario = ?, estado=? "
                  + "where cod_producto = ? ";
          PreparedStatement st  = this.getCn().prepareStatement(sql);
          st.setString(1, inventario.getNombre());
          st.setString(2, inventario.getConcentracion());
          st.setString(3, inventario.getPresentacion());
          st.setDouble(4, inventario.getIva());
          st.setDouble(5, inventario.getCosto_unitario());
          st.setDouble(6, inventario.getPrecio_unitario());
          st.setString(7, inventario.getEstado());
          st.setInt(8, inventario.getCod_producto());
          st.executeUpdate();
          st.close();
          System.out.println("Registro almacenado con éxito");
          this.getCn().commit();
        }catch(Exception e)
        {
           this.getCn().rollback();
           throw  e;
        }finally{
           this.cerrarConexion();
        }
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
}
