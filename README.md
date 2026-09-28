TP 1 : Découverte de JPA et Hibernate avec H2
Ce projet est une première prise en main de JPA (Java Persistence API) et du framework Hibernate. L'objectif était de monter un projet Java de zéro avec Maven, de le connecter à une base de données en mémoire H2, puis de tester les opérations basiques de persistance (création, lecture, requêtes JPQL).

1. Choix techniques et dépendances
Pour éviter d'avoir à installer et configurer un serveur de base de données lourd (comme MySQL ou PostgreSQL), nous avons utilisé H2 en mode mémoire.

Dans le fichier pom.xml, nous avons déclaré :

JPA API (2.2) : l'interface standard de persistance Java.

Hibernate Core (5.6.5.Final) : l'implémentation concrète de JPA utilisée sous le capot.

H2 Database (2.1.214) : le moteur de base de données SQL léger.

SLF4J (Simple) : pour formater et afficher proprement les logs dans la console.

JUnit 4 : pour les tests.

2. Organisation du projet
Le code suit l'architecture standard Maven :

src/main/resources/META-INF/persistence.xml : contient toute la configuration JPA (pilote JDBC, identifiants H2, dialecte SQL, affichage des requêtes dans la console et politique de schéma create-drop).

com.example.model.Produit : l'entité Java mappée en table SQL.

com.example.App : la classe principale (main) qui initialise l'usine d'EntityManager, persiste des produits et lance le serveur Web H2.

3. Ce qu'il faut retenir sur le code
Plusieurs points clés ont été appliqués lors du développement :

L'entité Produit :

@Entity et @Id définissent la classe et sa clé primaire.

@GeneratedValue(strategy = GenerationType.IDENTITY) laisse la base de données gérer l'auto-incrémentation.

Le type BigDecimal a été choisi pour le prix afin d'éviter les erreurs d'arrondi typiques des flottants (float/double).

Un constructeur sans argument (public Produit() {}) est obligatoirement présent, car JPA en a besoin pour instancier l'objet par réflexion lors de la récupération des données.

La gestion des transactions :

Comme nous sommes dans une application Java SE standard (sans serveur d'application Java EE), la gestion des transactions est configurée en RESOURCE_LOCAL.

Toute écriture (em.persist(...)) doit obligatoirement être encadrée par em.getTransaction().begin() et commit(), avec un rollback() dans le bloc catch en cas d'erreur.

4. Visualisation dans la console Web H2
Puisque la base H2 est stockée en mémoire volatile (jdbc:h2:mem:testdb), elle s'efface dès que le programme Java s'arrête. Pour pouvoir inspecter visuellement les tables :

Nous avons démarré un serveur Web temporaire dans App.java sur le port 8082.

Une fois l'application lancée, il suffit d'ouvrir un navigateur à l'adresse :

http://localhost:8082

Renseigner les identifiants de connexion :

Driver Class : org.h2.Driver

JDBC URL : jdbc:h2:mem:testdb

User Name : sa

Password : (laisser vide)

Dans l'interface, exécuter SELECT * FROM PRODUIT; pour constater l'insertion effective des données.

Appuyer sur la touche Entrée dans la console de l'IDE pour clore la session et libérer les ressources.

https://github.com/user-attachments/assets/f98aecee-cfcb-4a12-bf60-8ec3154d7b9e

<img width="1307" height="707" alt="tp1 hib" src="https://github.com/user-attachments/assets/e4e3701b-65fe-4786-aaaf-dc91bcf039a8" />




