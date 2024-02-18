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
import kardex.modelo.Categoria;
import kardex.modelo.Inventario;
import kardex.modelo.Laboratorio;


/**
 *
 * @author Carlitos
 */
public class InventarioDao extends Dao{

    public void registrarLaboratorio(Laboratorio lab) throws SQLException, Exception{
        try
        {          
          this.conectar();
          this.getCn().setAutoCommit(false);
          String sql=""+
           "insert into laboratorio"+
           "(nombre_laboratorio)"+
           "values('"+lab.getNombre_laboratorio()+"')";
          PreparedStatement st  = this.getCn().prepareStatement(sql);
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
    
    public void registrarInventario(Inventario inventario) throws Exception
    {
        try
        {          
          this.conectar();
          this.getCn().setAutoCommit(false);
          String sql=""+
           "insert into inventario"+
           "(nombre_producto,concentracion,presentacion,"+
           "estado,existencias,categoria,laboratorio,codigo_barras)"+
           "values('"+inventario.getNombre()+"','"+inventario.getConcentracion()+"',"
            + "'"+inventario.getPresentacion()+"',"
            + "'A',0,'"+inventario.getCategoria()+"','"+inventario.getLaboratorio()+"','"+inventario.getCodigo_barras()+"')";
          System.out.println(sql);
          PreparedStatement st  = this.getCn().prepareStatement(sql);
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
                  + "estado=?"
                  + "where cod_producto = ? ";
          PreparedStatement st  = this.getCn().prepareStatement(sql);
          st.setString(1, inventario.getNombre());
          st.setString(2, inventario.getConcentracion());
          st.setString(3, inventario.getPresentacion());
          st.setString(4, inventario.getEstado());
          st.setInt(5, inventario.getCod_producto());
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
                inv.setCategoria(rs.getString("categoria"));
                inv.setLaboratorio(rs.getString("laboratorio"));
                inv.setLaboratorio(rs.getString("codigo_barras"));
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
     
   public List<Categoria> getListaCategorias() throws Exception
    {
        List<Categoria> li = new ArrayList<Categoria>();
        try
        {
            this.conectar();
            String sql = "select * from categoria_inventario";
            PreparedStatement st =  this.getCn().prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while(rs.next() == true)
            {
                Categoria inv = new Categoria();
                inv.setId_categoria(rs.getInt("id_categoria"));
                inv.setDescripcion(rs.getString("descripcion"));
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
   public List<Laboratorio> getListaLaboratorios() throws Exception
    {
        List<Laboratorio> li = new ArrayList<Laboratorio>();
        try
        {
            this.conectar();
            String sql = "select * from laboratorio";
            PreparedStatement st =  this.getCn().prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while(rs.next() == true)
            {
                Laboratorio inv = new Laboratorio();
                inv.setCod_laboratorio(rs.getInt("cod_laboratorio"));
                inv.setNombre_laboratorio(rs.getString("nombre_laboratorio"));
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
