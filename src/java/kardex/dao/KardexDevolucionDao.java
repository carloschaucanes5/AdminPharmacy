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
import kardex.modelo.Empleado;
import kardex.modelo.Inventario;
import kardex.modelo.ItemDevolucion;
import kardex.modelo.ItemVenta;
import kardex.modelo.KardexDevolucion;

/**
 *
 * @author Carlitos
 */
public class KardexDevolucionDao  extends Dao{
    private double totalSaldoVenta;
    public KardexDevolucionDao ()
    {
        this.totalSaldoVenta = 0;
    }

    public double getTotalSaldoVenta() {
        return totalSaldoVenta;
    }

    public void setTotalSaldoVenta(double totalSaldoVenta) {
        this.totalSaldoVenta = totalSaldoVenta;
    }
    
    
    
    public List<ItemVenta> buscarKardexVenta(int numeroFactura) throws Exception
    {
        List<ItemVenta> liv = new ArrayList<ItemVenta>();
        try
        {
            this.conectar();
            String sql = "select * from kardex_venta natural join inventario where numero_factura = "+numeroFactura+"";
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
                ItemVenta iv = new ItemVenta();
                iv.setNumero_factura(numeroFactura);
                iv.setInventario(inv);
                iv.setCantidad(rs.getInt("cantidad"));
                iv.setTotal_costo(rs.getDouble("total_costo"));
                iv.setTotal_precio(rs.getDouble("total_precio"));
                liv.add(iv);
                totalSaldoVenta = totalSaldoVenta + rs.getDouble("total_precio");
            }
            return liv;
        }catch(Exception e)
        {
            throw e;
        }finally
        {
            this.cerrarConexion();
        }
    }
    
    
    public void registrarDevolucion(KardexDevolucion kardexDevolucion, Empleado empleado) throws Exception
    {      
        try
        {
          this.conectar();
          this.getCn().setAutoCommit(false);
          this.actualizarTabla_KardexVenta(kardexDevolucion);
          this.insertarTablaFacturaDevolucion(kardexDevolucion, empleado);
          int codigoDevolucion = this.getUltimoCodigoInsertado();
          this.insertarTabla_KardexDevolucion(kardexDevolucion, codigoDevolucion);
          if(this.isCxC(kardexDevolucion.getNumero_factura())==true)
          {
              double saldoPendiente = this.getSaldoPendiente(kardexDevolucion.getNumero_factura());
              this.actualizarCxC(kardexDevolucion.getNumero_factura(), saldoPendiente);
          }
          this.getCn().commit();
        }catch(Exception e)
        {
           this.getCn().rollback();
           throw  e;
        }finally{
           this.cerrarConexion();
        }
    } 
    
    //actualizo en la tabla kardex_venta
    private void actualizarTabla_KardexVenta(KardexDevolucion kardexDevolucion) throws Exception
    {
        String sql1 = "",sql2 = "";
        PreparedStatement st1 = null, st2 = null;
        ResultSet rs1 = null;
        int nuevaCantidad = 0; 
        double nuevoCosto = 0, nuevoPrecio = 0;
        try
        {
          Iterator<ItemDevolucion> listaItems = kardexDevolucion.getListaItemsDevolucion().iterator();
          while(listaItems.hasNext() ==true)
          {
            ItemDevolucion item = listaItems.next();
            sql1 = "select * from kardex_venta where numero_factura = "+kardexDevolucion.getNumero_factura()+"";
            st1 = this.getCn().prepareStatement(sql1);
            rs1 = st1.executeQuery();
            while(rs1.next() == true)
            {
                if(rs1.getInt("cod_producto") == item.getCod_producto())
                {
                    nuevoCosto = rs1.getInt("total_costo") - item.getTotal_costo();  
                    nuevoPrecio = rs1.getInt("total_precio") - item.getTotal_precio();  
                    nuevaCantidad = rs1.getInt("cantidad") - item.getCantidad();
                   sql2 = "update kardex_venta set cantidad = "+nuevaCantidad+",total_costo = "+nuevoCosto+","+
                           "total_precio="+nuevoPrecio+" where cod_producto = "+item.getCod_producto()+" "+
                           "and numero_factura = "+kardexDevolucion.getNumero_factura()+"";
                   st2 = this.getCn().prepareStatement(sql2);
                   st2.executeUpdate();
                   actualizarExistenciasInventario(item.getCod_producto(),item.getCantidad());
                }
            }
            
          }
          rs1.close();
          st1.close();
          st2.close();            
        }
        catch(Exception err)
        {
            throw err;
        }  
    }
    
    private void insertarTablaFacturaDevolucion(KardexDevolucion kardexDevolucion,Empleado empleado) throws Exception
    {
        try
        {
          PreparedStatement st = null;
          String sql = "";    
          sql = "insert into factura_devolucion(numero_factura,fecha_devolucion,hora_devolucion,cedula_empleado)"+
          "values("+kardexDevolucion.getNumero_factura()+",'"+this.getFecha()+"','"+this.getHora()+"','"+empleado.getCedula_empleado()+"')";
          st  = this.getCn().prepareStatement(sql);
          st.executeUpdate();
          st.close();
        }catch(Exception e)
        {
           throw  e;
        }
    }
    
    
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
        }
        catch(Exception err)
        {
            throw err;
        }
        rs.close();
        st1.close();
        return codEntrada;
    }
    
    private void insertarTabla_KardexDevolucion(KardexDevolucion kardexDevolucion, int codigoDevolucion) throws Exception
    {
        String sql1 = "";
        PreparedStatement st1 = null;
        try
        {
          Iterator<ItemDevolucion> listaItems = kardexDevolucion.getListaItemsDevolucion().iterator();
          while(listaItems.hasNext() ==true)
          {
            ItemDevolucion item = listaItems.next();
             sql1 = "insert into kardex_devolucion(cod_devolucion,cod_producto,cantidad,total_costo,total_precio)values("+
                    ""+codigoDevolucion+","+item.getCod_producto()+","+item.getCantidad()+","+item.getTotal_costo()+","+
                    ""+item.getTotal_precio()+")";
             st1 = this.getCn().prepareStatement(sql1);
             st1.executeUpdate();
          }
          st1.close();
        }
        catch(Exception err)
        {
            throw err;
        }  
    }
    
    private boolean isCxC(int numeroFactura) throws Exception
    {
        String sql1 = "";
        PreparedStatement st1 = null;
        ResultSet rs = null;
        try
        {
            sql1 = "select * from cuentas_cobrar where numero_factura = "+numeroFactura+"";
            st1 = this.getCn().prepareStatement(sql1);
            rs = st1.executeQuery();
            if(rs.next() == true)
            {
                rs.close();
                st1.close();
                return true;
            }       
        }
        catch(Exception err)
        {
            throw err;
        }
       
       rs.close();
       st1.close();
       return false;
    }
    
    private double getSaldoPendiente(int numeroFactura) throws Exception
    {
        String sql1 = "";
        PreparedStatement st1 = null;
        ResultSet rs = null;
        double suma = 0;
        try
        {
            sql1 = "select * from kardex_venta where numero_factura = "+numeroFactura+"";
            st1 = this.getCn().prepareStatement(sql1);
            rs = st1.executeQuery();
            while(rs.next() == true)
            {
                suma = suma + rs.getDouble("total_precio");
            }
            rs.close();
            st1.close();
        }
        catch(Exception err)
        {
            throw err;
        }
        return suma;
    }
    
    private void actualizarCxC(int numeroFactura, double saldoPendiente) throws Exception
    {
        String sql1 = "";
        PreparedStatement st1 = null;
        try
        {
            sql1 = "update cuentas_cobrar set saldo_pendiente = "+saldoPendiente+" where numero_factura = "+numeroFactura+"";
            st1 = this.getCn().prepareStatement(sql1);
            st1.executeUpdate();
            st1.close();
        }
        catch(Exception err)
        {
            throw err;
        }
    }
    
    private void actualizarExistenciasInventario(int codigoProducto,int cantidadRetornada) throws Exception
    {
        String sql1 = "", sql2 = "";
        PreparedStatement st1 = null, st2 = null;
        ResultSet rs = null;
        double suma = 0;
        try
        {
            sql1 = "select * from inventario where cod_producto = "+codigoProducto+"";
            st1 = this.getCn().prepareStatement(sql1);
            rs = st1.executeQuery();
            if(rs.next() == true)
            {
                int cantidadNueva = rs.getInt("existencias") + cantidadRetornada;
                sql2 = "update inventario set existencias = "+cantidadNueva+" where cod_producto = "+codigoProducto+"";
                st2 = this.getCn().prepareStatement(sql2);
                st2.executeUpdate();
            }
            rs.close();
            st1.close();
            st2.close();
        }
        catch(Exception err)
        {
            throw err;
        }
    }
    
//--------------------------------------------------------------------------
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

    
}




