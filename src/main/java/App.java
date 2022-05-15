import model.MyUser;
import org.h2.tools.Server;
import org.hibernate.Session;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App {
    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistence-h2");

    public static void main(String[] args) {
        var entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        new Thread(() -> entityManager.unwrap(Session.class).doWork(Server::startWebServer)).start();
        MyUser user = new MyUser();
        user.setName("Username");
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        user.setName("Changed username");
        entityManager.merge(user);
        entityManager.getTransaction().commit();
    }
}
