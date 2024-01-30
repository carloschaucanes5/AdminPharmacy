/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kardex.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import kardex.modelo.Bono;
/**
 *
 * @author Carlitos
 */
public class BonoDao extends Dao {
    
    public void registrarBono(Bono bono) throws Exception
    {
        this.conectar();
        String sql1 = "insert into bono(cedula_cliente,saldo_abono, fecha, estado, hora)values('"+bono.getCedula_cliente()+"',"+bono.getSaldo_abono()+",'"+this.getFecha()+"','A','"+this.getHora()+"')";
        PreparedStatement st1 = null;
        try
        {
          st1 = this.getCn().prepareStatement(sql1);
          st1.executeUpdate();
          st1.close();
        }
        catch(Exception err)
        {
            throw err;
        }finally{
            this.cerrarConexion();
        }
        
        
    }
    
    public double getSaldoTotalDisponible(String cedula) throws Exception
    {
        this.conectar();
        double suma = 0;
        String sql1 = "select sum(saldo_abono) as saldo_disponible from bono  where cedula_cliente = '"+cedula+"' and estado = 'A'   group by(cedula_cliente)";
        PreparedStatement st1 = null;
        ResultSet rs = null;
        try
        {
          st1 = this.getCn().prepareStatement(sql1);
          rs = st1.executeQuery();
          if(rs.next())
          {
              suma = suma + rs.getDouble("saldo_disponible");
          }
          else
          {
              suma = 0;
          }
          st1.close();
        }
        catch(Exception err)
        {
            throw err;
        }finally
        {
            this.cerrarConexion();
        }
        
        return suma;
    }
    
    public void actualizarSaldoDisponible(String cedula_cliente) throws Exception
    {
        this.conectar();
        String sql1 = "update bono set estado = 'I' where cedula_cliente = '"+cedula_cliente+"'";
        PreparedStatement st1 = null;

        try
        {
          st1 = this.getCn().prepareStatement(sql1);
          st1.executeUpdate();
          st1.close();
        }
        catch(Exception err)
        {
            throw err;
        }finally
        {
            this.cerrarConexion();
        }
        
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
          //--------------------------------------------------------------------
        return hora;
    }
    
    
}
