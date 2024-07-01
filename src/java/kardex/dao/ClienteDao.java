/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kardex.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import kardex.modelo.Cliente;
import kardex.modelo.Municipio;
import kardex.modelo.TipoIdentificacion;

/**
 *
 * @author Carlitos
 */
public class ClienteDao extends Dao{
    
    public void registrar(Cliente cli) throws Exception
    {
        try
        {
          this.conectar();
          String sql= "insert into cliente(cedula_cliente,nombres,apellidos,direccion, telefono,correo,id_ciudad)values(?,?,?,?,?,?,?)";
          PreparedStatement st  = this.getCn().prepareStatement(sql);
          st.setString(1, cli.getCedula_cliente());
          st.setString(2, cli.getNombres());
          st.setString(3, cli.getApellidos());
          st.setString(4, cli.getDireccion());
          st.setString(5, cli.getTelefono());
          st.setString(6, cli.getCorreo());
          st.setInt(7,cli.getId_ciudad());
          st.executeUpdate();
          st.close();
        }catch(Exception e)
        {
           throw e;
        }finally{
           this.cerrarConexion();
        }
    }
    
    public void modificar(Cliente cli) throws Exception
    {
        try
        {
          this.conectar();
          String sql= "update cliente set nombres=?,apellidos=?,direccion=?,telefono=? where cedula_cliente=?";
          PreparedStatement st  = this.getCn().prepareStatement(sql);
          st.setString(1, cli.getNombres());
          st.setString(2, cli.getApellidos());
          st.setString(3, cli.getDireccion());
          st.setString(4, cli.getTelefono());
          st.setString(5, cli.getCedula_cliente());
          st.executeUpdate();
        }catch(Exception e)
        {
           throw e;
        }finally{
           this.cerrarConexion();
        }
    }
   
    public void eliminar(Cliente cli) throws Exception
    {
        try
        {
          this.conectar();
          String sql= "delete from cliente where cedula_cliente = ?";
          PreparedStatement st  = this.getCn().prepareStatement(sql);
          st.setString(1, cli.getCedula_cliente());
          st.executeUpdate();
        }catch(Exception e)
        {
           throw e;
        }finally{
           this.cerrarConexion();
        }
    }
    
     public List<Cliente> listarClientes() throws Exception
    {
        List<Cliente> lp = new ArrayList<>();
        try
        {
            ResultSet rs = null;
            this.conectar();
            PreparedStatement st = this.getCn().prepareStatement("select * from cliente");
            rs=st.executeQuery();

            while(rs.next()==true)
            {
                Cliente cli = new Cliente();
                cli.setCedula_cliente(rs.getString("cedula_cliente"));
                cli.setNombres(rs.getString("nombres"));
                cli.setApellidos(rs.getString("apellidos"));
                cli.setDireccion(rs.getString("direccion"));
                cli.setTelefono(rs.getString("telefono"));
                lp.add(cli);
            }
        }catch(Exception e)
        {
            throw e;
        }finally
        {
            this.cerrarConexion();
        }
        return lp;
    }
     
    public Cliente buscarClienteCedula(String cedula) throws Exception
    {
       Cliente cli = null;
       try
        {
          ResultSet rs;
          this.conectar();
          String sql= "select * from cliente where cedula_cliente = '"+cedula+"'";
          Statement smt = this.getCn().createStatement();
          rs = smt.executeQuery(sql);
          if(rs.next() == true)
          {
              cli = new Cliente();
              cli.setCedula_cliente(rs.getString("cedula_cliente"));
              cli.setNombres(rs.getString("nombres")); 
              cli.setApellidos(rs.getString("apellidos"));
              cli.setDireccion(rs.getString("direccion"));
              cli.setTelefono(rs.getString("telefono"));
              cli.setTipo_identificacion(rs.getInt("tipo_identificacion"));
              cli.setId_ciudad(rs.getInt("id_ciudad"));
          }
          else
          {
              cli = null;
          }
        }catch(Exception e)
        {
           throw e;
        }finally{
           this.cerrarConexion();
        }
       return cli;
    }
    
    public List<Cliente> buscarClienteNombre(String nombreCliente) throws Exception
    {
        List<Cliente> lc = new ArrayList<Cliente>();
        try
        {
            this.conectar();
            String sql = "select * from cliente  where nombres ilike '%"+nombreCliente+"%'";
            PreparedStatement st =  this.getCn().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next() == true)
            {
                Cliente cli = new Cliente();
                cli.setCedula_cliente(rs.getString("cedula_cliente"));
                cli.setNombres(rs.getString("nombres"));
                cli.setApellidos(rs.getString("apellidos"));
                cli.setDireccion(rs.getString("direccion"));
                cli.setTelefono(rs.getString("telefono"));
                lc.add(cli);
            }
        }catch(Exception e)
        {
            throw e;
        }finally
        {
            this.cerrarConexion();
        }
        return lc;
    }
    
    public List<TipoIdentificacion> getTiposIdentificacion()throws Exception{
        List<TipoIdentificacion> lisTI = new ArrayList<TipoIdentificacion>();
        try
        {
            this.conectar();
            String sql = "select * from tipo_identificacion";
            PreparedStatement st =  this.getCn().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next() == true)
            {
                TipoIdentificacion ti = new TipoIdentificacion();
                ti.setIdentificacion_id(rs.getInt("identificacion_id"));
                ti.setNombre(rs.getString("nombre"));
                ti.setCodigo(rs.getString("codigo"));
                lisTI.add(ti);
            }
        }catch(Exception e)
        {
            throw e;
        }finally
        {
            this.cerrarConexion();
        }
        return lisTI;
    }
    
        public List<Municipio> buscarMunicipio(String nombreMunicipio) throws Exception
    {
        List<Municipio> lc = new ArrayList<Municipio>();
        try
        {
            this.conectar();
            String sql = "select * from municipio  where titulo ilike '%"+nombreMunicipio+"%'";
            PreparedStatement st =  this.getCn().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next() == true)
            {
                Municipio cli = new Municipio();
                cli.setMunicipality_id(rs.getInt("municipality_id"));
                cli.setDepartamento(rs.getString("departamento"));
                cli.setMunicipio(rs.getString("municipio"));
                cli.setTitulo(rs.getString("titulo"));
                lc.add(cli);
            }
        }catch(Exception e)
        {
            throw e;
        }finally
        {
            this.cerrarConexion();
        }
        return lc;
    }
}
