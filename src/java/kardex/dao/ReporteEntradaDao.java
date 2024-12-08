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
import kardex.modelo.ReporteEntrada;

/**
 *
 * @author Carlitos
 */
public class ReporteEntradaDao extends Dao{
    
    private double totalCosto;
    private double totalPrecio;
    private double utilidades;

    public double getTotalCosto() {
        return totalCosto;
    }

    public void setTotalCosto(double totalCosto) {
        this.totalCosto = totalCosto;
    }

    public double getTotalPrecio() {
        return totalPrecio;
    }

    public void setTotalPrecio(double totalPrecio) {
        this.totalPrecio = totalPrecio;
    }


    public double getUtilidades() {
        return utilidades;
    }

    public void setUtilidades(double utilidades) {
        this.utilidades = utilidades;
    }
    
 public  List<ReporteEntrada> getListaEntradas(String fechaInicial, String fechaFinal,String numeroFactura) throws Exception
{  
    String sql = "";
        double sumaPrecio = 0, sumaCosto = 0;
        ResultSet rs = null;
        List<ReporteEntrada> lr = new ArrayList<>();
        try
        {
            sql = validarSQL(fechaInicial, fechaFinal, numeroFactura);
            this.conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);
            rs=st.executeQuery();

            while(rs.next()==true)
            {
                ReporteEntrada re = new ReporteEntrada();
                re.setNumero_factura(rs.getString("numero_factura"));
                re.setFecha_transaccion(rs.getString("fecha_transaccion"));
                re.setHora_transaccion(rs.getString("hora_transaccion"));
                re.setNombre_producto(rs.getString("nombre_producto"));
                re.setConcentracion(rs.getString("concentracion"));
                re.setPresentacion(rs.getString("presentacion"));
                re.setCantidad(rs.getInt("cantidad"));
                re.setTotal_costo(rs.getDouble("total_costo"));
                re.setTotal_precio(rs.getDouble("total_precio"));
                re.setNombre_laboratorio(rs.getString("laboratorio"));
                re.setNombre_proveedor(rs.getString("nombre_proveedor"));
                re.setPrimer_nombre(rs.getString("primer_nombre"));
                re.setPrimer_apellido(rs.getString("primer_apellido"));
                re.setFecha_vencimiento(rs.getString("fecha_vencimiento"));
                re.setDetalle(rs.getString("detalle"));
                lr.add(re);
                sumaPrecio = sumaPrecio + rs.getDouble("total_precio");
                sumaCosto = sumaCosto + rs.getDouble("total_costo");
                
            }
            this.totalCosto = sumaCosto;
            this.totalPrecio = sumaPrecio;
            this.utilidades = totalPrecio - totalCosto;
        }catch(Exception e)
        {
            throw e;
        }finally
        {
            this.cerrarConexion();
        }
        return  lr; 
}
   
 private String validarSQL(String fechaInicial, String fechaFinal,String numeroFactura)
{
    String sql = "";
    if(numeroFactura.trim().length()==0)
    {    
        sql = "select ke.numero_factura,ke.fecha_transaccion, ke.hora_transaccion,ke.fecha_vencimiento,inv.concentracion,ke.detalle,\n" +
"                     inv.presentacion,ke.cantidad,ke.total_costo,ke.total_precio,inv.nombre_producto,inv.categoria,inv.laboratorio,\n" +
"                     pro.nombre_proveedor,emp.nombre,emp.primer_nombre,emp.primer_apellido\n" +
"                     from kardex_entrada ke\n" +
"                     inner join inventario inv on ke.cod_producto = inv.cod_producto\n" +
"                     inner join proveedor pro on pro.nit_proveedor = ke.nit_proveedor\n" +
"                     inner join empleado emp on ke.cedula_empleado = emp.cedula_empleado\n" +
"                     where ke.fecha_transaccion >= '"+fechaInicial +"' and ke.fecha_transaccion <= '"+fechaFinal+"'  and\n" +
"                     ke.cantidad > 0 order by inv.nombre_producto ";
    }
    else
    {

        sql = "select ke.numero_factura,ke.fecha_transaccion, ke.hora_transaccion,ke.fecha_vencimiento,inv.concentracion,ke.detalle,\n" +
"           inv.presentacion,ke.cantidad,ke.total_costo,ke.total_precio,inv.nombre_producto,inv.categoria,inv.laboratorio,\n" +
"           pro.nombre_proveedor,emp.nombre,emp.primer_nombre,emp.primer_apellido\n" +
"	    from kardex_entrada ke\n" +
"           inner join inventario inv on ke.cod_producto = inv.cod_producto\n" +
"		 inner join proveedor pro on pro.nit_proveedor = ke.nit_proveedor\n" +
"		 inner join empleado emp on ke.cedula_empleado = emp.cedula_empleado\n" +
"		 where ke.numero_factura = '"+numeroFactura+"'  and\n" +
"		 ke.cantidad > 0 order by inv.nombre_producto";
    }
    return sql;
}
    
}
