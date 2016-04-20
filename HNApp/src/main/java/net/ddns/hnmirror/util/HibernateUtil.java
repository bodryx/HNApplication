package net.ddns.hnmirror.util;

import java.io.File;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import net.ddns.hnmirror.main.App;



public class HibernateUtil {
    private static SessionFactory sessionFactory = null;
    
    static {
        try {
                //creates the session factory from hibernate.cfg.xml
        	//---------------
        	String hibernatePropsFilePath = App.configPath + "hibernate.cfg.xml";

        	File hibernatePropsFile = new File(hibernatePropsFilePath);

        	Configuration configuration = new Configuration();
        	
        	configuration.configure(hibernatePropsFile);

        	StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());

        	ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();

        	sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        	//----------------
        //        sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Exception e) {
              e.printStackTrace();
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}