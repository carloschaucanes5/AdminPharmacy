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
import kardex.modelo.Empleado;

/**
 *
 * @author Carlitos
 */
public class EmpleadoDao extends Dao{
    
    
    public  Empleado getEmpleadoPorCedula(String cedula ,String contrasenia) throws Exception
    {
       Empleado em = null;
        try
        {
            this.conectar();
            String sql = "select * from empleado  where cedula_empleado='"+cedula+"' and clave_acceso = '"+contrasenia+"'";
            PreparedStatement st =  this.getCn().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if(rs.next() == true)
            {
                em = new Empleado();
                em.setCedula_empleado(rs.getString("cedula_empleado"));
                em.setNombre(rs.getString("nombre"));
                em.setClave_acceso(rs.getString("clave_acceso"));
                em.setPrimer_nombre(rs.getString("primer_nombre"));
                em.setSegundo_nombre(rs.getString("segundo_nombre"));
                em.setPrimer_apellido(rs.getString("primer_apellido"));
                em.setSegundo_apellido(rs.getString("segundo_apellido"));
                em.setTipo(rs.getString("tipo"));
                return em;
            }
        }catch(Exception e)
        {
            throw e;
        }            
       return em; 
    }
    
    public List<Empleado> getListaEmpleados() throws Exception
    {
        List<Empleado> le = new ArrayList<Empleado>();
         try
        {
            ResultSet rs = null;
            this.conectar();
            PreparedStatement st = this.getCn().prepareStatement("select * from empleado");
            rs=st.executeQuery();
            while(rs.next()==true)
            {
                Empleado emp = new Empleado();
                emp.setCedula_empleado(rs.getString("cedula_empleado"));
                emp.setNombre(rs.getString("nombre"));
                le.add(emp);
            }
        }catch(Exception e)
        {
            throw e;
        }finally
        {
            this.cerrarConexion();
        }
        return le;
    }
    
    public void registrarEmpleado(Empleado empleado) throws Exception
    {
        String alias = empleado.getPrimer_nombre()+" " +empleado.getPrimer_apellido();
        try
        {
          this.conectar();
          String sql= "insert into empleado(cedula_empleado,nombre,clave_acceso,primer_nombre,segundo_nombre,primer_apellido,segundo_apellido)values(?,?,?,?,?,?,?)";
          PreparedStatement st  = this.getCn().prepareStatement(sql);
          st.setString(1,empleado.getCedula_empleado());
          st.setString(2,alias);
          st.setString(3,empleado.getClave_acceso());
          st.setString(4,empleado.getPrimer_nombre());
          st.setString(5,empleado.getSegundo_nombre());
          st.setString(6,empleado.getPrimer_apellido());
          st.setString(7,empleado.getSegundo_apellido());
          st.executeUpdate();
          st.close();
        }catch(Exception e)
        {
           throw e;
        }finally{
           this.cerrarConexion();
        }
    }
    
    public void modificar(Empleado empleado) throws Exception
    {
        try
        {
          String alias = empleado.getPrimer_nombre()+" " +empleado.getPrimer_apellido();
          this.conectar();
          String sql= "update empleado set nombre=?,clave_acceso=?,primer_nombre=?,segundo_nombre=?,primer_apellido=?,segundo_apellido=? where cedula_empleado=?";
          PreparedStatement st  = this.getCn().prepareStatement(sql);
          st.setString(1,alias );
          st.setString(2,empleado.getClave_acceso() );
          st.setString(3,empleado.getPrimer_nombre() );
          st.setString(4,empleado.getSegundo_nombre() );
          st.setString(5, empleado.getPrimer_apellido());
          st.setString(6, empleado.getSegundo_apellido());
          st.setString(7, empleado.getCedula_empleado());
          st.executeUpdate();
        }catch(Exception e)
        {
           throw e;
        }finally{
           this.cerrarConexion();
        }
    }
    
     public  Empleado getEmpleadoActu(String cedula) throws Exception
    {
       Empleado em = null;
        try
        {
            this.conectar();
            String sql = "select * from empleado  where cedula_empleado='"+cedula+"'";
            PreparedStatement st =  this.getCn().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if(rs.next() == true)
            {
                em = new Empleado();
                em.setCedula_empleado(rs.getString("cedula_empleado"));
                em.setNombre(rs.getString("nombre"));
                em.setClave_acceso(rs.getString("clave_acceso"));
                em.setClave_confirmar(rs.getString("clave_acceso"));
                em.setPrimer_nombre(rs.getString("primer_nombre"));
                em.setSegundo_nombre(rs.getString("segundo_nombre"));
                em.setPrimer_apellido(rs.getString("primer_apellido"));
                em.setSegundo_apellido(rs.getString("segundo_apellido"));
                return em;
            }
        }catch(Exception e)
        {
            throw e;
        }            
       return em; 
    }
    
}

