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
import kardex.dao.Dao;
import kardex.modelo.Empleado;
import kardex.modelo.ReporteFacturaDetalle;
import kardex.modelo.ReporteFacturaGeneral;

/**
 *
 * @author Carlitos
 */
public class ReporteVentaDao extends Dao{
     private List<ReporteFacturaGeneral> listaFacturaGeneralD;
     private List<ReporteFacturaDetalle> listaFacturaDetalleD;
     private double total_costo;
     private double total_precio;
     private double total_ganancia;
     
     
    
     public ReporteVentaDao()
     {
        this.listaFacturaGeneralD = new ArrayList<ReporteFacturaGeneral>();
        this.listaFacturaDetalleD = new ArrayList<ReporteFacturaDetalle>();
        this.total_costo = 0;
        this.total_precio = 0;
        this.total_ganancia = 0;
     }

    public double getTotal_costo() {
        return total_costo;
    }

    public void setTotal_costo(double total_costo) {
        this.total_costo = total_costo;
    }

    public double getTotal_precio() {
        return total_precio;
    }

    public void setTotal_precio(double total_precio) {
        this.total_precio = total_precio;
    }

    public double getTotal_ganancia() {
        return total_ganancia;
    }

    public void setTotal_ganancia(double total_ganancia) {
        this.total_ganancia = total_ganancia;
    }

     
    public List<ReporteFacturaGeneral> getListaFacturaGeneralD() {
        return listaFacturaGeneralD;
    }

    public void setListaFacturaGeneralD(List<ReporteFacturaGeneral> listaFacturaGeneralD) {
        this.listaFacturaGeneralD = listaFacturaGeneralD;
    }

    public List<ReporteFacturaDetalle> getListaFacturaDetalleD() {
        return listaFacturaDetalleD;
    }

    public void setListaFacturaDetalleD(List<ReporteFacturaDetalle> listaFacturaDetalleD) {
        this.listaFacturaDetalleD = listaFacturaDetalleD;
    }
  
    
   public void generarReporteGeneral(Empleado  empleado, int tipoTransaccion, String fechaInicial, String fechaFinal) throws Exception
   {
       String sql = "";
       if(empleado == null && tipoTransaccion == -1)
       {
   
            sql="select  fv.numero_factura, fv.fecha_factura,fv.hora_factura,sum(kv.total_costo) as suma_costo, sum(kv.total_precio) as suma_precio\n" +
"                     from  empleado em inner join factura_venta fv on em.cedula_empleado = fv.cedula_empleado\n" +
"					inner join kardex_venta  kv on kv.numero_factura = fv.numero_factura\n" +
"					where   fecha_factura >= '"+fechaInicial+"' and fecha_factura <= '"+fechaFinal+"' \n" +
"				        group by(fv.numero_factura) \n" +
"					order by fv.fecha_factura desc";
                   
       }
      if(empleado != null && tipoTransaccion == -1)
       { 
            
            sql="select  fv.numero_factura, fv.fecha_factura,fv.hora_factura,sum(kv.total_costo) as suma_costo, sum(kv.total_precio) as suma_precio\n" +
"                     from  empleado em inner join factura_venta fv on em.cedula_empleado = fv.cedula_empleado\n" +
"					inner join kardex_venta  kv on kv.numero_factura = fv.numero_factura\n" +
"					where   fecha_factura >= '"+fechaInicial+"' and fecha_factura <= '"+fechaFinal+"' and fv.cedula_empleado = '"+empleado.getCedula_empleado()+"'  \n" +
"				        group by(fv.numero_factura) \n" +
"					order by fv.fecha_factura desc";
            
            
       }
      
      if(empleado == null && tipoTransaccion != -1)
       {
            
            sql="select  fv.numero_factura, fv.fecha_factura,fv.hora_factura,sum(kv.total_costo) as suma_costo, sum(kv.total_precio) as suma_precio\n" +
"                     from  empleado em inner join factura_venta fv on em.cedula_empleado = fv.cedula_empleado\n" +
"					inner join kardex_venta  kv on kv.numero_factura = fv.numero_factura\n" +
"					where   fecha_factura >= '"+fechaInicial+"' and fecha_factura <= '"+fechaFinal+"' and kv.cod_tipo_transaccion = "+tipoTransaccion+"  \n" +
"				        group by(fv.numero_factura) \n" +
"					order by fv.fecha_factura desc";
            
            
       }
      if(empleado != null && tipoTransaccion != -1)
       {
            
            sql="select  fv.numero_factura, fv.fecha_factura,fv.hora_factura,sum(kv.total_costo) as suma_costo, sum(kv.total_precio) as suma_precio\n" +
"                     from  empleado em inner join factura_venta fv on em.cedula_empleado = fv.cedula_empleado\n" +
"					inner join kardex_venta  kv on kv.numero_factura = fv.numero_factura\n" +
"					where   fecha_factura >= '"+fechaInicial+"' and fecha_factura <= '"+fechaFinal+"' and kv.cod_tipo_transaccion = "+tipoTransaccion+" and fv.cedula_empleado = '"+empleado.getCedula_empleado()+"'  \n" +
"				        group by(fv.numero_factura) \n" +
"					order by fv.fecha_factura desc";
       }
      try
      {
        this.listaFacturaGeneralD = this.generarListaFacturaGeneral(sql);
      }catch(Exception err)
      {
          throw err;
      }
   }
   
   public void generarReporteDetalle(Empleado empleado, int tipoTransaccion ,String fechaInicial, String fechaFinal) throws Exception
   {
      String sql = "";
       if(empleado == null && tipoTransaccion == -1)
       {   
           sql = "select fv.numero_factura, fv.fecha_factura, fv.hora_factura,fv.contenido,\n" +
        "		inv.nombre_producto,inv.concentracion,inv.presentacion,inv.categoria,inv.laboratorio,\n" +
        "		kv.cantidad,kv.total_precio,kv.total_costo,em.nombre,em.cedula_empleado,ke.total_precio as precio_unitario,ke.detalle                \n" +
        "from  \n" +
        "factura_venta fv inner join kardex_venta kv on fv.numero_factura = kv.numero_factura\n" +
        "                 inner join kardex_entrada_historico ke on kv.cod_producto = ke.cod_entrada\n" +
        "				 inner join inventario inv on ke.cod_producto = inv.cod_producto\n" +
        "				 inner join empleado em on em.cedula_empleado = fv.cedula_empleado\n" +
        "                 where fecha_factura >= '"+fechaInicial+"' and fecha_factura <= '"+fechaFinal+"'\n" +
        "                 order by(fv.numero_factura) desc";
                   
       }
      if(empleado != null && tipoTransaccion == -1)
       {
            
            sql = "select fv.numero_factura, fv.fecha_factura, fv.hora_factura,fv.contenido,\n" +
        "		inv.nombre_producto,inv.concentracion,inv.presentacion,inv.categoria,inv.laboratorio,\n" +
        "		kv.cantidad,kv.total_precio,kv.total_costo,em.nombre,em.cedula_empleado,ke.total_precio as precio_unitario,ke.detalle                \n" +
        "from  \n" +
        "factura_venta fv inner join kardex_venta kv on fv.numero_factura = kv.numero_factura\n" +
        "                 inner join kardex_entrada_historico ke on kv.cod_producto = ke.cod_entrada\n" +
        "				 inner join inventario inv on ke.cod_producto = inv.cod_producto\n" +
        "				 inner join empleado em on em.cedula_empleado = fv.cedula_empleado\n" +
        "                 where fecha_factura >= '"+fechaInicial+"' and fecha_factura <= '"+fechaFinal+"' and em.cedula_empleado = '"+empleado.getCedula_empleado()+"'\n" +
        "                 order by(fv.numero_factura) desc";
            
       }
      
      if(empleado == null && tipoTransaccion != -1)
       {
           
                       sql = "select fv.numero_factura, fv.fecha_factura, fv.hora_factura,fv.contenido,\n" +
        "		inv.nombre_producto,inv.concentracion,inv.presentacion,inv.categoria,inv.laboratorio,\n" +
        "		kv.cantidad,kv.total_precio,kv.total_costo,kv.cod_tipo_transaccion,em.nombre,em.cedula_empleado,ke.total_precio as precio_unitario,ke.detalle               \n" +
        "from  \n" +
        "factura_venta fv inner join kardex_venta kv on fv.numero_factura = kv.numero_factura\n" +
        "                 inner join kardex_entrada_historico ke on kv.cod_producto = ke.cod_entrada\n" +
        "				 inner join inventario inv on ke.cod_producto = inv.cod_producto\n" +
        "				 inner join empleado em on em.cedula_empleado = fv.cedula_empleado\n" +
        "                 where fecha_factura >= '"+fechaInicial+"' and fecha_factura <= '"+fechaFinal+"' and   kv.cod_tipo_transaccion = "+tipoTransaccion+"\n" +
        "                 order by(fv.numero_factura) desc";
            
            
       }
      if(empleado != null && tipoTransaccion != -1)
       {
            
           sql = "select fv.numero_factura, fv.fecha_factura, fv.hora_factura,fv.contenido,\n" +
        "		inv.nombre_producto,inv.concentracion,inv.presentacion,inv.categoria,inv.laboratorio,\n" +
        "		kv.cantidad,kv.total_precio,kv.total_costo,kv.cod_tipo_transaccion,em.nombre,em.cedula_empleado,ke.total_precio as precio_unitario,ke.detalle                \n" +
        "from  \n" +
        "factura_venta fv inner join kardex_venta kv on fv.numero_factura = kv.numero_factura\n" +
        "                 inner join kardex_entrada_historico ke on kv.cod_producto = ke.cod_entrada\n" +
        "				 inner join inventario inv on ke.cod_producto = inv.cod_producto\n" +
        "				 inner join empleado em on em.cedula_empleado = fv.cedula_empleado\n" +
        "                 where fecha_factura >= '"+fechaInicial+"' and fecha_factura <= '"+fechaFinal+"' and em.cedula_empleado = '"+empleado.getCedula_empleado()+"' and  kv.cod_tipo_transaccion = "+tipoTransaccion+"\n" +
        "                 order by(fv.numero_factura) desc";
            
       }
      try
      {
          this.listaFacturaDetalleD = this.generarListaFacturaDetalle(sql);
      }catch(Exception err)
      {
          throw err;
      }       
   }
   
   
    private List<ReporteFacturaGeneral> generarListaFacturaGeneral(String sql) throws Exception
    {
        double sumaCosto = 0, sumaPrecio = 0;
        List<ReporteFacturaGeneral> lr = new ArrayList<ReporteFacturaGeneral>();
        try
        {
            ResultSet rs = null;
            this.conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);
            rs=st.executeQuery();

            while(rs.next()==true)
            {
                ReporteFacturaGeneral rfg = new ReporteFacturaGeneral();
                rfg.setNumero_factura(rs.getInt("numero_factura"));
                rfg.setFecha_factura(rs.getString("fecha_factura"));
                rfg.setHora_factura(rs.getString("hora_factura"));
                rfg.setSuma_costo(rs.getDouble("suma_costo"));
                rfg.setSuma_precio(rs.getDouble("suma_precio"));
                lr.add(rfg);
                sumaCosto = sumaCosto +  rs.getDouble("suma_costo");
                sumaPrecio = sumaPrecio + rs.getDouble("suma_precio");
            }
            this.setTotal_costo(sumaCosto);
            this.setTotal_precio(sumaPrecio);
            this.total_ganancia = sumaPrecio - sumaCosto;
        }catch(Exception e)
        {
            throw e;
        }finally
        {
            this.cerrarConexion();
        }
        return  lr;
    }
    
    private List<ReporteFacturaDetalle> generarListaFacturaDetalle(String sql) throws Exception
    {
        double sumaCosto = 0, sumaPrecio = 0;
        List<ReporteFacturaDetalle> lr = new ArrayList<ReporteFacturaDetalle>();
        try
        {
            ResultSet rs = null;
            this.conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);
            rs=st.executeQuery();

            while(rs.next()==true)
            {
                ReporteFacturaDetalle rfd = new ReporteFacturaDetalle();
                rfd.setNumero_factura(rs.getInt("numero_factura"));
                rfd.setFecha_factura(rs.getString("fecha_factura"));
                rfd.setHora_factura(rs.getString("hora_factura"));
                rfd.setNombre_producto(rs.getString("nombre_producto"));
                rfd.setConcentracion(rs.getString("concentracion"));
                rfd.setPresentacion(rs.getString("presentacion"));
                rfd.setCantidad(rs.getInt("cantidad"));
                rfd.setTotal_costo(rs.getDouble("total_costo"));
                rfd.setTotal_precio(rs.getDouble("total_precio"));
                rfd.setNombre_empleado(rs.getString("nombre"));
                rfd.setCategoria(rs.getString("categoria"));
                rfd.setLaboratorio(rs.getString("laboratorio"));
                rfd.setPrecio_unitario(rs.getDouble("precio_unitario"));
                rfd.setContenido(rs.getString("contenido"));
                rfd.setDetalle(rs.getString("detalle"));
                lr.add(rfd);
                sumaCosto = sumaCosto +  rs.getDouble("total_costo");
                sumaPrecio = sumaPrecio + rs.getDouble("total_precio");
            }
            this.setTotal_costo(sumaCosto);
            this.setTotal_precio(sumaPrecio);
            this.total_ganancia = sumaPrecio - sumaCosto;
        }catch(Exception e)
        {
            throw e;
        }finally
        {
            this.cerrarConexion();
        }
        return  lr;
    }
    
}
