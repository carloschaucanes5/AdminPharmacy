/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kardex.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import kardex.modelo.ConsultaProducto;
import kardex.modelo.CuentaCobrar;
import kardex.modelo.Empleado;
import kardex.modelo.Empresa;
import kardex.modelo.Inventario;
import kardex.modelo.ItemVenta;
import kardex.modelo.KardexVenta;
import kardex.modelo.MetodoPago;
import kardex.modelo.Recibo;

/**
 *
 * @author Carlitos
 */
public class KardexVentaDao  extends Dao{
 


   
    public void registrarVenta(KardexVenta kardexVenta, int codigoTransaccion, Empleado empleado) throws Exception
    {      
        try
        {
          this.conectar();
          this.getCn().setAutoCommit(false);
          PreparedStatement st = null,st1=null,st2=null,st3=null;
          String sql = "", sql1 = "", sql2 = "", sql3=""; 
          //-------------------------------------------------------------------          
          sql3 = "insert into factura_venta(numero_factura,fecha_factura,hora_factura,cedula_empleado,contenido)values("+kardexVenta.getNumero_factura()+",'"+this.getFecha()+"','"+this.getHora()+"','"+empleado.getCedula_empleado()+"','"+kardexVenta.getContenido()+"')";
          System.out.println(sql3);
          st3  = this.getCn().prepareStatement(sql3);
          st3.executeUpdate();
          //-------------------------------------------------------------------
          Iterator<ItemVenta> listaItems = kardexVenta.getListaItemsVenta().iterator();
          while(listaItems.hasNext() ==true)
          {
            int existencias = 0;
            ItemVenta item = listaItems.next();
            //----------------------------------------------------------------------------------------------
            sql=""+
            "insert into kardex_venta"+
            "(numero_factura,cod_tipo_transaccion,cod_producto,"+
            "cantidad,total_costo,total_precio)"+
            "values("+kardexVenta.getNumero_factura()+","+codigoTransaccion+","+item.getInventario().getCod_entrada()+","+
            ""+item.getCantidad()+","+
            ""+item.getTotal_costo()+","+item.getTotal_precio()+")";
            st  = this.getCn().prepareStatement(sql);
            st.executeUpdate();
            existencias =  item.getInventario().getCantidad()-item.getCantidad();  
            //--------------------------------------------------------------------------------------------
            //sql1 = "update inventario set existencias = "+existencias+" where cod_producto = "+item.getInventario().getCod_producto()+"";
            sql1 = "update kardex_entrada set cantidad = "+existencias+" where cod_entrada = "+item.getInventario().getCod_entrada()+"";
            st1 = this.getCn().prepareStatement(sql1);
            st1.executeUpdate();
          }
          //-----------------------------------------------------------------------------------------------
          sql2 = "update consecutivo set actual = "+kardexVenta.getNumero_factura()+" where id_consecutivo=1";
          st2 = this.getCn().prepareStatement(sql2);
          st2.executeUpdate();
          //-----------------------------------------------------------------------------------------------        
          st.close();
          st1.close();
          st2.close();
          st3.close();
          this.verificarExistenciasCeros();
          this.getCn().commit();
        }catch(Exception e)
        {
           this.getCn().rollback();
           throw  e;
        }finally{
           this.cerrarConexion();
        }
    } 
    
    public void registrarVentaCxc(KardexVenta kardexVenta, int codigoTransaccion, CuentaCobrar cxc , Empleado empleado) throws Exception
    {      
        try
        {
          this.conectar();
          this.getCn().setAutoCommit(false);
          PreparedStatement st = null,st1=null,st2=null,st3 = null, st4 = null;
          String sql="",sql1 = "", sql2 = "", sql3 = "", sql4 = null;
          //-------------------------------------------------------------------          
          sql4 = "insert into factura_venta(numero_factura,fecha_factura,hora_factura,cedula_empleado)"+
          "values("+kardexVenta.getNumero_factura()+",'"+this.getFecha()+"','"+this.getHora()+"','"+empleado.getCedula_empleado()+"')";
          st4  = this.getCn().prepareStatement(sql4);
          st4.executeUpdate();
          //-------------------------------------------------------------------
          Iterator<ItemVenta> listaItems = kardexVenta.getListaItemsVenta().iterator();
          while(listaItems.hasNext() ==true)
          {
            int existencias = 0;
            ItemVenta item = listaItems.next();
            //-----------------------------------------------------------------------------------------------
            sql=""+
            "insert into kardex_venta"+
            "(numero_factura,cod_tipo_transaccion,cod_producto,"+
            "cantidad,total_costo,total_precio)"+
            "values("+kardexVenta.getNumero_factura()+","+codigoTransaccion+","+item.getInventario().getCod_entrada()+","+
            ""+item.getCantidad()+","+
            ""+item.getTotal_costo()+","+item.getTotal_precio()+")";
            st  = this.getCn().prepareStatement(sql);
            st.executeUpdate();
            //----------------------------------------------------------------------------------------------
            existencias =  item.getInventario().getCantidad()-item.getCantidad();  
            sql1 = "update kardex_entrada set cantidad = "+existencias+" where cod_producto = "+item.getInventario().getCod_entrada()+"";
            st1 = this.getCn().prepareStatement(sql1);
            st1.executeUpdate();
          }
          //------------------------------------------------------------------------------------------------
          sql2 = "update consecutivo set actual = "+kardexVenta.getNumero_factura()+" where id_consecutivo=1";
          st2 = this.getCn().prepareStatement(sql2);
          st2.executeUpdate();
          //-----------------------------------------------------------------------------------------------
          sql3= "insert into cuentas_cobrar(numero_factura,cedula_cliente,estado,saldo_pendiente,detalle)values(?,?,?,?,?)";
          st3  = this.getCn().prepareStatement(sql3);

            st3.setInt(1, cxc.getNumero_factura());
            st3.setString(2, cxc.getCedula_cliente());
            st3.setString(3, cxc.getEstado());
            st3.setDouble(4, cxc.getSaldo_pendiente());
            st3.setString(5, cxc.getDetalle());
            st3.executeUpdate();   
         //-----------------------------------------------------------------------------------------
            
         //------------------------------------------------------------------------------------------   
          st.close();
          st1.close();
          st2.close();
          st3.close();
          this.verificarExistenciasCeros();
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
    
    //verificar si el inventario tiene existencias con ceros y eliminar las entradas
    //que corresponden a ese Id.
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
            sql1 = "delete from kardex_entrada where cantidad = 0";
            st1 = this.getCn().prepareStatement(sql1);
            rs1 =  st1.executeQuery();*/
            
            /*while(rs1.next() == true)
            {
                listaId.add(rs1.getInt("cod_entrada"));
            }
            if(listaId.size() != 0)
            {
                ListIterator<Integer> li = listaId.listIterator();
                while(li.hasNext() == true)
                {
                    int id = li.next();
                    sql2 = "delete from kardex_entrada where cod_entreda = "+id+"";
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
    
    public int getConsecutivoNumeroFactura() throws Exception
    {
        int numeroFacturaActual = 0;
        try
        {
            this.conectar();
            String sql = "select * from consecutivo where id_consecutivo = 1";
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
    public List<ConsultaProducto> getListarNombresProductos(String nombreProducto) throws Exception
    {
        List<ConsultaProducto> li = new ArrayList<ConsultaProducto>();
        try
        {
            this.conectar();
            String sql = "select inv.cod_producto,inv.nombre_producto,inv.concentracion,inv.presentacion,inv.existencias,inv.codigo_barras,inv.categoria,inv.laboratorio,\n" +
            "ke.fecha_vencimiento,ke.cantidad,ke.total_costo,ke.total_precio,ke.iva,cod_entrada,ke.detalle  \n" +
            "from inventario inv inner join kardex_entrada  ke on inv.cod_producto = ke.cod_producto \n" +
            "where ke.cantidad > 0  and (inv.nombre_producto ilike '%"+nombreProducto+"%' or inv.codigo_barras = '"+nombreProducto+"');";
            PreparedStatement st =  this.getCn().prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while(rs.next() == true)
            {
                //Inventario inv = new Inventario();
                 //inv.cod_producto,inv.nombre_producto,inv.concentracion,inv.presentacion,inv.existencias,inv.codigo_barras,
                 //inv.categoria,inv.laboratorio,
                 //ke.fecha_vencimiento,ke.cantidad,ke.total_costo,ke.total_precio,ke.iva,ke.
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
                cp.setCod_entrada(rs.getInt("cod_entrada"));
                cp.setDetalle(rs.getString("detalle"));
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
        return hora;
    } 
    
    public Empresa getEmpresa() throws Exception
    {
        Empresa em = null;
        try
        {
            this.conectar();
            String sql = "select * from empresa";
            PreparedStatement st =  this.getCn().prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            if(rs.next() == true)
            {
                em = new Empresa();
                em.setNit_empresa(rs.getString("nit_empresa"));
                em.setNombre_empresa(rs.getString("nombre_empresa"));
                em.setDireccion(rs.getString("direccion"));
                em.setTelefono(rs.getString("telefono"));
                em.setCiudad(rs.getString("ciudad"));
                em.setRazon_social(rs.getString("razon_social"));
  
            }
        }catch(Exception e)
        {
            throw e;
        }finally
        {
            this.cerrarConexion();
        }
        return em;
    }
    
        public List<MetodoPago> getListaMetodosPago() throws Exception
    {
        List<MetodoPago> li = new ArrayList<MetodoPago>();
        try
        {
            this.conectar();
            String sql = "select * from metodo_pago where estado = 'a'";
            PreparedStatement st =  this.getCn().prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while(rs.next() == true)
            {
                MetodoPago mp = new MetodoPago();
                mp.setId_metodo(rs.getInt("id_metodo"));
                mp.setNombre(rs.getString("nombre"));
                mp.setCode_dian(rs.getInt("code_dian"));
                mp.setDescripcion(rs.getString("descripcion"));
                li.add(mp);
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

