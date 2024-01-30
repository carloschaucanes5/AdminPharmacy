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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import kardex.dao.Dao;
import kardex.modelo.ReporteCxcDetalle;
import kardex.modelo.ReporteCxcGeneral;

/**
 *
 * @author Carlitos
 */
public class ReporteCxcDao extends Dao{
    private double totalDeuda;
    
    
    
    public ReporteCxcDao()
    {
        this.totalDeuda = 0;
    }


    public double getTotalDeuda() {
        return totalDeuda;
    }

    public void setTotalDeuda(double totalDeuda) {
        this.totalDeuda = totalDeuda;
    }

   
/*public  List<ReporteCxcDetalle> generarListaCxcDetalle(String cedulaCliente) throws Exception
{
    String sql = "";
    double sumaPrecio = 0;
        List<ReporteCxcDetalle> lr = new ArrayList<ReporteCxcDetalle>();
        try
        {
          sql = "select numero_factura,nombre_producto,concentracion,presentacion,fecha_factura,hora_factura,cantidad,total_precio "
                + "from  kardex_venta natural join factura_venta natural join cuentas_cobrar natural join inventario "
                + "where cedula_cliente = '"+cedulaCliente+"' and estado='A'"
                + "order by fecha_factura desc";
            ResultSet rs = null;
            this.conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);
            rs=st.executeQuery();

            while(rs.next()==true)
            {
                ReporteCxcDetalle rccd = new ReporteCxcDetalle();
                rccd.setNumero_factura(rs.getInt("numero_factura"));
                rccd.setNombre_producto(rs.getString("nombre_producto"));
                rccd.setConcentracion(rs.getString("concentracion"));
                rccd.setPresentacion(rs.getString("presentacion"));
                rccd.setFecha_factura(rs.getString("fecha_factura"));
                rccd.setHora_factura(rs.getString("hora_factura"));
                rccd.setCantidad(rs.getInt("cantidad"));
                rccd.setTotal_precio(rs.getDouble("total_precio"));
                lr.add(rccd);
                sumaPrecio = sumaPrecio + rs.getDouble("total_precio");
            }
            this.totalDeuda = sumaPrecio;
        }catch(Exception e)
        {
            throw e;
        }finally
        {
            this.cerrarConexion();
        }
        return  lr; 
}*/
 
/*public  List<ReporteCxcGeneral> generarListaCxcGeneral(String cedulaCliente) throws Exception
{  
        String sql = "";
        double sumaPrecio = 0;
        List<ReporteCxcGeneral> lr = new ArrayList<ReporteCxcGeneral>();
        try
        {
               sql = "select numero_factura,fecha_factura,hora_factura,saldo_pendiente "
                + "from  factura_venta natural join cuentas_cobrar "
                + " where cedula_cliente = '"+cedulaCliente+"' and estado='A'"
                + " order by fecha_factura desc";
               
            ResultSet rs = null;
            this.conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);
            rs=st.executeQuery();

            while(rs.next()==true)
            {
                ReporteCxcGeneral rccg = new ReporteCxcGeneral();
                rccg.setNumero_factura(rs.getInt("numero_factura"));
                rccg.setFecha_factura(rs.getString("fecha_factura"));
                rccg.setHora_factura(rs.getString("hora_factura"));
                rccg.setSaldo_pendiente(rs.getDouble("saldo_pendiente"));
                lr.add(rccg);
                sumaPrecio = sumaPrecio + rs.getDouble("saldo_pendiente");
            }
            this.totalDeuda = sumaPrecio;
        }catch(Exception e)
        {
            throw e;
        }finally
        {
            this.cerrarConexion();
        }
        return  lr; 
}



*/
//----------------------------------------------------------------------------
public   List<ReporteCxcGeneral> getListaGeneralNombres(String nombres,String apellidos) throws Exception
{  
        String sql1 = "";
        double sumaPrecio = 0;
        ResultSet rs = null;
        List<ReporteCxcGeneral> lr = new ArrayList<ReporteCxcGeneral>();
        
        
        try
        {
            sql1 = validarSql(nombres, apellidos);
            this.conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql1);
            rs=st.executeQuery();

            while(rs.next()==true)
            {
                ReporteCxcGeneral rccg = new ReporteCxcGeneral();
                rccg.setNumero_factura(rs.getInt("numero_factura"));
                rccg.setFecha_factura(rs.getString("fecha_factura"));
                rccg.setHora_factura(rs.getString("hora_factura"));
                rccg.setSaldo_pendiente(rs.getDouble("saldo_pendiente"));
                rccg.setCedula(rs.getString("cedula_cliente"));
                rccg.setNombres(rs.getString("nombres"));
                rccg.setApellidos(rs.getString("apellidos"));
                lr.add(rccg);
                sumaPrecio = sumaPrecio + rs.getDouble("saldo_pendiente");
            }
            this.totalDeuda = sumaPrecio;
        }catch(Exception e)
        {
            throw e;
        }finally
        {
            this.cerrarConexion();
        }
        return  lr; 
}


private String  validarSql(String nombres, String apellidos)
{
    String sql = "";
    if(nombres.length()==0 && apellidos.length()!=0)
    {
       sql = "select numero_factura,fecha_factura,hora_factura,saldo_pendiente, cedula_cliente, nombres, apellidos "
                + "from  factura_venta natural join cuentas_cobrar natural join cliente  "
                + "where estado='A'  and  apellidos ilike '%"+apellidos+"%'";
    }
    if(nombres.length()!=0 && apellidos.length()==0)
    {
        sql = "select numero_factura,fecha_factura,hora_factura,saldo_pendiente, cedula_cliente, nombres, apellidos "
              + "from  factura_venta natural join cuentas_cobrar natural join cliente  "
              + "where estado='A' and nombres ilike '%"+nombres+"%'";
    }
    if(nombres.length()!=0 && apellidos.length()!=0)
    {
        sql = "select numero_factura,fecha_factura,hora_factura,saldo_pendiente, cedula_cliente, nombres, apellidos "
            + "from  factura_venta natural join cuentas_cobrar natural join cliente  "
            + "where estado='A'  and (nombres ilike '%"+nombres+"%' or apellidos ilike '%"+apellidos+"%')";
    }
    if(nombres.length()==0 && apellidos.length()==0)
    {
        sql = "select numero_factura,fecha_factura,hora_factura,saldo_pendiente, cedula_cliente, nombres, apellidos "
            + "from  factura_venta natural join cuentas_cobrar natural join cliente  "
            + "where estado='A'"; 
    }

    
    return sql;
}


/*public  List<ReporteCxcDetalle> getListaDetalleNombres(String nombres, String apellidos) throws Exception
{
    String sql = "";
     ResultSet rs = null;
    double sumaPrecio = 0;
        List<ReporteCxcDetalle> lr = new ArrayList<ReporteCxcDetalle>();
        try
        {
          sql = "select numero_factura,nombre_producto,concentracion,presentacion,fecha_factura,hora_factura,cantidad,total_precio,cedula_cliente ,nombres,apellidos "
                  + "from  kardex_venta natural join factura_venta natural join cuentas_cobrar natural join inventario  natural join cliente "
                  + "where  estado='A' and nombres ilike '%"+nombres+"%'  and apellidos ilike '%"+apellidos+"%'";
           
            this.conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);
            rs=st.executeQuery();

            while(rs.next()==true)
            {
                ReporteCxcDetalle rccd = new ReporteCxcDetalle();
                rccd.setNumero_factura(rs.getInt("numero_factura"));
                rccd.setNombre_producto(rs.getString("nombre_producto"));
                rccd.setConcentracion(rs.getString("concentracion"));
                rccd.setPresentacion(rs.getString("presentacion"));
                rccd.setFecha_factura(rs.getString("fecha_factura"));
                rccd.setHora_factura(rs.getString("hora_factura"));
                rccd.setCantidad(rs.getInt("cantidad"));
                rccd.setTotal_precio(rs.getDouble("total_precio"));
                rccd.setCedula(rs.getString("cedula_cliente"));
                rccd.setNombres(rs.getString("nombres"));
                rccd.setApellidos(rs.getString("apellidos"));
                lr.add(rccd);
                sumaPrecio = sumaPrecio + rs.getDouble("total_precio");
            }
            this.totalDeuda = sumaPrecio;
        }catch(Exception e)
        {
            throw e;
        }finally
        {
            this.cerrarConexion();
        }
        return  lr; 
}


//----------------------------------------------------------------------------

   public void pagarCxc(List<ReporteCxcGeneral> listaFacturas) throws Exception
    {
        try
        {
          this.conectar();
          this.getCn().setAutoCommit(false);
          this.actualizarTipoTransaccionKardexVenta(listaFacturas);
          this.actualizarEstadoCxC(listaFacturas);
          this.getCn().commit();
        }
        catch(Exception err)
        {
            this.getCn().rollback();
            throw err;
        }finally
        {
            this.cerrarConexion();
        }
        
    }

   private void actualizarTipoTransaccionKardexVenta(List<ReporteCxcGeneral> listaFacturas) throws Exception
   {
        String sql1 = "";
        PreparedStatement st1 = null;
        try
        {
          Iterator<ReporteCxcGeneral> listaItems = listaFacturas.iterator();
          while(listaItems.hasNext() ==true)
          {
            ReporteCxcGeneral item = listaItems.next();
            sql1 = "update kardex_venta set cod_tipo_transaccion = 102 "
                    + "where numero_factura = "+item.getNumero_factura()+"";
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
   
   private void actualizarEstadoCxC(List<ReporteCxcGeneral> listaFacturas) throws Exception
   {
        String sql1 = "";
        PreparedStatement st1 = null;
        try
        {
          Iterator<ReporteCxcGeneral> listaItems = listaFacturas.iterator();
          while(listaItems.hasNext() ==true)
          {
            ReporteCxcGeneral item = listaItems.next();
            sql1 = "update cuentas_cobrar set estado = 'P' "
                    + "where numero_factura = "+item.getNumero_factura()+"";
            st1 = this.getCn().prepareStatement(sql1);
            st1.executeUpdate();        
          }
          st1.close();            
        }
        catch(Exception err)
        {
            throw err;
        }  
   }*/
    
}

