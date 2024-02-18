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
import kardex.modelo.BarCode;
import kardex.modelo.Categoria;
import kardex.modelo.Inventario;
import kardex.modelo.Laboratorio;
/**
 *
 * @author Carlitos
 */
public class BarCodeDao extends Dao{
   
    
   public int getConsecutivoCodeBar() throws Exception
    {
        int numeroFacturaActual = 0;
        try
        {
            this.conectar();
            String sql = "select * from consecutivo where id_consecutivo = 2";
            PreparedStatement st = this.getCn().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if(rs.next() == true)
            {
                numeroFacturaActual = rs.getInt("actual") + 1;
            }
        }catch(Exception err)
        {
            throw err;
        }
        return numeroFacturaActual;
    }
    
    public void registrarBarCode(BarCode bar) throws SQLException, Exception{
        try
        {   
          int code = Integer.parseInt(bar.getCodigo_barras());
          this.conectar();
          this.getCn().setAutoCommit(false);
          String sql=""+
           "insert into codigo_barras"+
           "(estado,nombre_producto,codigo_barras,detalle)"+
           "values('"+bar.getEstado()+"','"+bar.getNombre_producto()+"','"+bar.getCodigo_barras()+"','"+bar.getDetalle()+"')";
          PreparedStatement st  = this.getCn().prepareStatement(sql);
          st.executeUpdate();
          sql = "update consecutivo set actual = "+code+" where id_consecutivo=2";
          st = this.getCn().prepareStatement(sql);
          st.executeUpdate();
          
          
          st.close();
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
    

  
 public List<BarCode> getListarCodigosBarras(String nombreProducto) throws Exception
    {
        List<BarCode> li = new ArrayList<BarCode>();
        try
        {
            this.conectar();
            String sql = "select * from codigo_barras where nombre_producto ilike '%"+nombreProducto+"%'";
            PreparedStatement st =  this.getCn().prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while(rs.next() == true)
            {
                BarCode bar = new BarCode();
                bar.setId_codigo_barras(rs.getInt("id_codigo_barras"));
                bar.setCodigo_barras(rs.getString("codigo_barras"));
                bar.setDetalle(rs.getString("detalle"));
                bar.setNombre_producto(rs.getString("nombre_producto"));  
                li.add(bar);
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
