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
                re.setNombre_laboratorio(rs.getString("nombre_laboratorio"));
                re.setNombre_proveedor(rs.getString("nombre_proveedor"));
                re.setPrimer_nombre(rs.getString("primer_nombre"));
                re.setPrimer_apellido(rs.getString("primer_apellido"));
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
    if(numeroFactura.length()==0)
    {
        sql = "select numero_factura,fecha_transaccion, hora_transaccion,nombre_producto, concentracion,presentacion,cantidad ,total_costo,total_precio,nombre_laboratorio,nombre_proveedor,primer_nombre,primer_apellido  "
              + "from  kardex_entrada_historico natural join inventario natural join laboratorio natural join proveedor natural join empleado "
              + "where fecha_transaccion >= '"+fechaInicial +"' and fecha_transaccion <= '"+fechaFinal+"' order by fecha_transaccion desc";
    }
    else
    {
        sql = "select numero_factura,fecha_transaccion, hora_transaccion,nombre_producto, concentracion,presentacion,cantidad ,total_costo,total_precio,nombre_laboratorio,nombre_proveedor,primer_nombre,primer_apellido  "
              + "from  kardex_entrada_historico natural join inventario natural join laboratorio natural join proveedor natural join empleado "
              + "where  numero_factura = '"+numeroFactura+"' order by fecha_transaccion desc";
    }
    return sql;
}
    
}
