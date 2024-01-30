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
import kardex.modelo.CuentaCobrar;
import kardex.modelo.ReporteCxcDetalle;
import kardex.modelo.ReporteCxcGeneral;

/**
 *
 * @author Carlitos
 */
public class CuentasCobrarDao extends Dao{
    private double totalDeuda;
    private double totalDetalleCosto;
    private double totalDetallePrecio;

    
    public CuentasCobrarDao()
    {
      this.totalDeuda = 0;
      this.totalDetalleCosto = 0;
      this.totalDetallePrecio = 0;
    }
    
    public double getTotalDetalleCosto() {
        return totalDetalleCosto;
    }

    public void setTotalDetalleCosto(double totalDetalleCosto) {
        this.totalDetalleCosto = totalDetalleCosto;
    }

    public double getTotalDetallePrecio() {
        return totalDetallePrecio;
    }

    public void setTotalDetallePrecio(double totalDetallePrecio) {
        this.totalDetallePrecio = totalDetallePrecio;
    }

    
    public double getTotalDeuda() {
        return totalDeuda;
    }

    public void setTotalDeuda(double totalDeuda) {
        this.totalDeuda = totalDeuda;
    }
    
    
   
    public void registrarCuentaCobrar(CuentaCobrar cxc) throws Exception
    {
        try
        {
          this.conectar();
          String sql= "insert into cuentas_cobrar(numero_factura,cedula_cliente,estado,saldo_pendiente,detalle)values(?,?,?,?,?)";
          PreparedStatement st  = this.getCn().prepareStatement(sql);
          st.setInt(1, cxc.getNumero_factura());
          st.setString(2, cxc.getCedula_cliente());
          st.setString(3, cxc.getEstado());
          st.setDouble(4, cxc.getSaldo_pendiente());
          st.setString(5, cxc.getDetalle());
          st.executeUpdate();
          st.close();
        }catch(Exception e)
        {
           throw e;
        }finally{
           this.cerrarConexion();
        }
    }
    
 
    public  List<ReporteCxcGeneral> generarListaCxcGeneral(String cedulaCliente) throws Exception
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

 
    public List<ReporteCxcDetalle> getItemsFacturaDetalle(ReporteCxcGeneral general) throws Exception
    {
        double sumaCosto =0;
        double sumaPrecio = 0;
        List<ReporteCxcDetalle> lis = new ArrayList<ReporteCxcDetalle>();
        try
        {
            String sql = "select numero_factura,cod_producto,nombre_producto,concentracion,presentacion,cantidad,total_precio,total_costo,nombre "
                    + "from  factura_venta natural join kardex_venta natural join inventario natural join empleado  "
                    + "where  numero_factura = "+general.getNumero_factura()+"";
            
            ResultSet rs = null;
            this.conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);
            rs=st.executeQuery();

            while(rs.next()==true)
            {         
                ReporteCxcDetalle rccd = new ReporteCxcDetalle();
                rccd.setNumero_factura(rs.getInt("numero_factura"));
                rccd.setCod_producto(rs.getInt("cod_producto"));
                rccd.setNombre_producto(rs.getString("nombre_producto"));
                rccd.setConcentracion(rs.getString("concentracion"));
                rccd.setPresentacion(rs.getString("presentacion"));
                rccd.setCantidad(rs.getInt("cantidad"));
                rccd.setTotal_costo(rs.getDouble("total_costo"));
                rccd.setTotal_precio(rs.getDouble("total_precio"));
                rccd.setNombre_empleado(rs.getString("nombre"));
                lis.add(rccd);
                
                sumaCosto = sumaCosto + rs.getDouble("total_costo");
                sumaPrecio = sumaPrecio + rs.getDouble("total_precio");
                System.out.println(sumaPrecio+"\n");
            }
            
            this.totalDetalleCosto = sumaCosto;
            this.totalDetallePrecio = sumaPrecio;
        }catch(Exception err)
        {
            throw err;
        }
         return lis;
    }
    
   
    
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
   }
    
    
    
    
}
