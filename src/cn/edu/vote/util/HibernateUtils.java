package cn.edu.vote.util;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate操作
 *
 * @author zzu
 */
public class HibernateUtils {
    private static SessionFactory factory;

    static {
        Configuration configuration = new Configuration();
        configuration.configure();
        factory = configuration.buildSessionFactory();
    }


    /**
     * 获取Session
     *
     * @return Session
     */
    public static Session getSession(){
        return factory.openSession();
    }

    public static void closeSession(Session session){
        session.close();
    }
}
