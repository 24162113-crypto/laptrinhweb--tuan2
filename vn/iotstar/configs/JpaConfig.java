package vn.iotstar.configs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaConfig {
    
    // Khởi tạo factory 1 lần duy nhất khi ứng dụng load
    private static final EntityManagerFactory factory = 
            Persistence.createEntityManagerFactory("jpa-hibernate-sql");

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }
    
    public static void shutdown() {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }
}