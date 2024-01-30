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
        sql="select fecha_vencimiento,cod_producto,nombre_producto,concentracion,presentacion,nombre_laboratorio,nit_proveedor,nombre_proveedor "
                + "from kardex_entrada natural join inventario natural join proveedor natural join laboratorio "
                + "order by fecha_vencimiento ";
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
                rv.setCod_producto(rs.getInt("cod_producto"));
                rv.setNombre_producto(rs.getString("nombre_producto"));
                rv.setConcentracion(rs.getString("concentracion"));
                rv.setPresentacion(rs.getString("presentacion"));
                rv.setNombre_laboratorio(rs.getString("nombre_laboratorio"));
                rv.setNit_proveedor(rs.getString("nit_proveedor"));
                rv.setNombre_proveedor(rs.getString("nombre_proveedor"));
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
