package utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DBUtils {
	

    private static SessionFactory sessionFactory; 
    public static SessionFactory creadorSessionFactory() {
        if (sessionFactory == null) {
            try {
                sessionFactory = new Configuration().configure().buildSessionFactory();
            } catch (Throwable ex) {
                System.err.println("Error al crear el objeto SessionFactory" + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }
        return sessionFactory;
    }
	
	

	public static Connection conexion() {
		Connection connection = null;
		Context ctx = null;
		DataSource ds = null;
		try {
			ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/miBBDD");
			connection = ds.getConnection();
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
		}
		return connection;

	}

}
