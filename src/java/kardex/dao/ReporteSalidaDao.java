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
import kardex.modelo.ReporteSalida;

/**
 *
 * @author Carlitos
 */
public class ReporteSalidaDao  extends Dao{
    
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
    
 public  List<ReporteSalida> getListaSalidas(String fechaInicial, String fechaFinal) throws Exception
{  
    String sql = "";
        double sumaPrecio = 0, sumaCosto = 0;
        ResultSet rs = null;
        List<ReporteSalida> lr = new ArrayList<>();
        try
        {
            sql = "select ks.fecha_salida,ks.hora_salida,ks.cod_producto, inv.nombre_producto, inv.concentracion, inv.presentacion ,ks.cantidad, ks.total_costo, ks.total_precio, em.primer_nombre, em.primer_apellido ,ks.detalle\n" +
"                  from kardex_salida ks inner join kardex_entrada_historico ke on ks.cod_producto = ke.cod_entrada\n" +
"				  inner join empleado em on ks.cedula_empleado = em.cedula_empleado\n" +
"				  inner join inventario inv on ke.cod_producto = inv.cod_producto\n" +
"                 where fecha_salida >= '"+fechaInicial+"' and  fecha_salida <= '"+fechaFinal+"'\n" +
"                 order by fecha_salida desc";
            this.conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);
            rs=st.executeQuery();

            while(rs.next()==true)
            {
                ReporteSalida res = new ReporteSalida();
                res.setFecha_salida(rs.getString("fecha_salida"));
                res.setHora_salida(rs.getString("hora_salida"));
                res.setCod_producto(rs.getInt("cod_producto"));
                res.setNombre_producto(rs.getString("nombre_producto"));
                res.setConcentracion(rs.getString("concentracion"));
                res.setPresentacion(rs.getString("presentacion"));
                res.setCantidad(rs.getInt("cantidad"));
                res.setTotal_costo(rs.getDouble("total_costo"));
                res.setTotal_precio(rs.getDouble("total_precio"));
                res.setPrimer_nombre(rs.getString("primer_nombre"));
                res.setPrimer_apellido(rs.getString("primer_apellido"));
                res.setDetalle(rs.getString("detalle"));
                lr.add(res);
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
}
