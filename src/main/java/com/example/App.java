package com.example;

import com.example.model.Produit;
import org.h2.tools.Server;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.List;

public class App {
    public static void main(String[] args) {
        try {
            // 1. Démarrage de la console Web H2 sur le port 8082
            Server webServer = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
            System.out.println("==================================================");
            System.out.println("Console H2 disponible sur : " + webServer.getURL());
            System.out.println("==================================================");

            // 2. Initialisation JPA et insertion des produits
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-demo");
            insererProduits(emf);
            lireProduits(emf);

            // 3. Pause : le programme reste actif pour vous laisser naviguer dans la console H2
            System.out.println("\n>>> Ouvrez votre navigateur sur http://localhost:8082");
            System.out.println(">>> Appuyez sur la touche [ENTRÉE] dans cette console pour arrêter le programme...");
            System.in.read();

            // 4. Fermeture propre à la sortie
            emf.close();
            webServer.stop();
            System.out.println("Application arrêtée.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insererProduits(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(new Produit("Laptop", new BigDecimal("999.99")));
            em.persist(new Produit("Smartphone", new BigDecimal("499.99")));
            em.persist(new Produit("Tablette", new BigDecimal("299.99")));
            em.getTransaction().commit();
            System.out.println("Produits insérés avec succès !");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    private static void lireProduits(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        try {
            List<Produit> produits = em.createQuery("SELECT p FROM Produit p", Produit.class).getResultList();
            System.out.println("\nListe des produits en base :");
            for (Produit p : produits) {
                System.out.println(p);
            }
        } finally {
            em.close();
        }
    }
}