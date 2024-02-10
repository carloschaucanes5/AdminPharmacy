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
import kardex.modelo.ReporteVencimiento;

/**
 *
 * @author Carlitos
 */
public class ReporteVencimientoDao extends Dao{
  
    public  List<ReporteVencimiento> getProductosVencidos() throws Exception
    {
        String sql = "";
        sql="select inv.cod_producto,inv.nombre_producto,inv.concentracion,inv.presentacion,inv.existencias,inv.codigo_barras,inv.categoria,inv.laboratorio,\n" +
"            ke.fecha_vencimiento,ke.cantidad,ke.total_costo,ke.total_precio,ke.iva,ke.cod_entrada \n" +
"            from inventario inv inner join kardex_entrada  ke on inv.cod_producto = ke.cod_producto\n" +
"			where ke.cantidad <> 0\n" +
"			order by ke.fecha_vencimiento;";
        List<ReporteVencimiento> lv = new ArrayList<ReporteVencimiento>();
        try
        {
            ResultSet rs = null;
            this.conectar();
            PreparedStatement st = this.getCn().prepareStatement(sql);
            rs=st.executeQuery();

            while(rs.next()==true)
            {
                ReporteVencimiento rv = new ReporteVencimiento();
                rv.setFecha_vencimiento(rs.getString("fecha_vencimiento"));
                rv.setCod_entrada(rs.getInt("cod_entrada"));
                rv.setNombre_producto(rs.getString("nombre_producto"));
                rv.setConcentracion(rs.getString("concentracion"));
                rv.setPresentacion(rs.getString("presentacion"));
                rv.setLaboratorio(rs.getString("laboratorio"));
                rv.setCategoria(rs.getString("categoria"));
                rv.setCantidad(rs.getInt("cantidad"));
                lv.add(rv);
            }
        }catch(Exception e)
        {
            throw e;
        }finally
        {
            this.cerrarConexion();
        }
        return  lv;
    }
    
}
