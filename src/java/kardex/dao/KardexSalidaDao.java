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
import kardex.modelo.ConsultaProducto;
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
           "values(101,"+kardexSalida.getInventario().getCod_entrada()+","+
           "'"+this.getFecha()+"','"+this.getHora()+"',"+kardexSalida.getCantidad()+","+
           "'"+kardexSalida.getEmpleado().getCedula_empleado()+"','"+kardexSalida.getDetalle()+"',"+
           ""+kardexSalida.getTotal_costo()+","+kardexSalida.getTotal_precio()+")";
          st1  = this.getCn().prepareStatement(sql1);
          st1.executeUpdate();
          existencias = kardexSalida.getInventario().getCantidad()-kardexSalida.getCantidad();
          sql2 = "update kardex_entrada set cantidad = "+existencias+" where cod_entrada = "+kardexSalida.getInventario().getCod_entrada()+"";
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
    
     public List<ConsultaProducto> getListarNombresProductos(String nombreProducto) throws Exception
    {
        List<ConsultaProducto> li = new ArrayList<ConsultaProducto>();
        try
        {
            this.conectar();
            String sql = "select inv.cod_producto,inv.nombre_producto,inv.concentracion,inv.presentacion,inv.existencias,inv.codigo_barras,inv.categoria,inv.laboratorio,\n" +
            "ke.fecha_vencimiento,ke.cantidad,ke.total_costo,ke.total_precio,ke.iva,cod_entrada \n" +
            "from inventario inv inner join kardex_entrada  ke on inv.cod_producto = ke.cod_producto \n" +
            "where ke.cantidad > 0  and (inv.nombre_producto ilike '"+nombreProducto+"%' or inv.codigo_barras = '"+nombreProducto+"');";
            PreparedStatement st =  this.getCn().prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while(rs.next() == true)
            {
                ConsultaProducto cp = new ConsultaProducto();
                cp.setCod_producto(rs.getInt("cod_producto"));
                cp.setNombre_producto(rs.getString("nombre_producto"));
                cp.setConcentracion(rs.getString("concentracion"));
                cp.setPresentacion(rs.getString("presentacion"));
                cp.setCategoria(rs.getString("categoria"));
                cp.setLaboratorio(rs.getString("laboratorio"));
                cp.setExistencias(rs.getInt("existencias"));
                cp.setFecha_vencimiento(rs.getString("fecha_vencimiento"));
                cp.setCantidad(rs.getInt("cantidad"));
                cp.setTotal_costo(rs.getDouble("total_costo"));
                cp.setTotal_precio(rs.getDouble("total_precio"));
                cp.setCodigo_barras(rs.getString("codigo_barras"));
                cp.setIva(rs.getDouble("iva"));
                cp.setCod_entrada(rs.getInt("cod_entrada"));;
                li.add(cp);
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
            sql2 = "delete from kardex_entrada where cantidad = 0";
            st2 = this.getCn().prepareStatement(sql2);
            st2.executeUpdate();
            /*
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
            }*/
        }
        catch(Exception err)
        {
            throw err;
        }
        
    }
}
