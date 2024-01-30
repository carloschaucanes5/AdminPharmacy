/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kardex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author Carlitos
 */
public class Dao {
    private Connection cn;

    public Connection getCn() {
        return cn;
    }

    public void setCn(Connection cn) {
        this.cn = cn;
    }
    public void conectar() throws Exception
    {
        try
        {
            Class.forName("org.postgresql.Driver");
            this.cn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/kardex","postgres","postgres1"); 
        }catch(Exception e)
        {
            throw e;
        }
    }
    
    public void cerrarConexion() throws Exception
    {
        try
        {
            if(cn.isClosed()==false)
            {
                cn.close();
            }
        }catch(Exception e)
        {   
            throw e;
        }
    }
    
    public static void main(String arg[])
    {
        try
        {
            Dao dao = new Dao();
            dao.conectar();
        }catch(Exception e)
        {
            System.out.print(e);
        }
    }
}
